package com.shop.web.servlet;

import com.shop.entity.User;
import com.shop.service.SecurityService;
import com.shop.service.UserService;
import com.shop.web.PageProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;

import static com.shop.service.SecurityService.PASSWORD_IS_NOT_VALID_MESSAGE;

@RequiredArgsConstructor
public class RegistrationServlet extends HttpServlet {

    private final UserService userService;
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("registration.html"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userService.isRegistered(username)) {
            request.setAttribute("message", "User with username " + username + " already exists");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/failed_registration");
            dispatcher.forward(request, response);
        } else if (!securityService.isPasswordValid(password)) {
            request.setAttribute("message", PASSWORD_IS_NOT_VALID_MESSAGE);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/failed_registration");
            dispatcher.forward(request, response);
        } else {
            User user = User.builder().username(username).password(password).build();
            userService.addNew(user);
            Cookie cookie = securityService.getCookie();
            response.addCookie(cookie);
            response.sendRedirect("/products");
        }
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/registration");
    }
}
