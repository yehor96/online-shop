package com.shop.web.servlet;

import com.shop.entity.User;
import com.shop.helper.PasswordHelper;
import com.shop.service.UserService;
import com.shop.web.PageProvider;
import com.shop.web.handler.UserSessionHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.shop.helper.PasswordHelper.PASSWORD_IS_NOT_VALID_MESSAGE;

@RequiredArgsConstructor
public class RegistrationServlet extends HttpServlet {

    private static final UserSessionHandler USER_SESSION_HANDLER = new UserSessionHandler();
    private static final PasswordHelper PASSWORD_HELPER = new PasswordHelper();

    private final UserService userService;
    private final List<String> tokenStorage;

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
        } else if (!PASSWORD_HELPER.isPasswordValid(password)) {
            request.setAttribute("message", PASSWORD_IS_NOT_VALID_MESSAGE);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/failed_registration");
            dispatcher.forward(request, response);
        } else {
            User user = User.builder().username(username).password(password).build();
            userService.addNew(user);
            USER_SESSION_HANDLER.addUserToken(tokenStorage, response);
            response.sendRedirect("/products");
        }
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/registration");
    }
}
