package com.shop.web.servlet;

import com.shop.entity.User;
import com.shop.service.SecurityService;
import com.shop.service.UserService;
import com.shop.web.PageProvider;
import com.shop.web.handler.RequestParameterHandler;
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

@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {

    private static final RequestParameterHandler PARAMETER_HANDLER = new RequestParameterHandler();

    private final UserService userService;
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (securityService.isLoggedIn(cookies)) {
            response.sendRedirect("/products");
        } else {
            try (PrintWriter writer = response.getWriter()) {
                writer.println(PageProvider.getPage("login.html"));
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = PARAMETER_HANDLER.getAsString(request, "username");
        String password = PARAMETER_HANDLER.getAsString(request, "password");
        User user = User.builder().username(username).password(password).build();

        if (userService.areValidCredentials(user)) {
            Cookie cookie = securityService.getCookie();
            response.addCookie(cookie);
            response.sendRedirect("/products");
        } else {
            response.sendRedirect("/failed_login");
        }
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/login");
    }
}
