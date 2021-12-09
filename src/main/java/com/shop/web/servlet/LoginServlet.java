package com.shop.web.servlet;

import com.shop.entity.User;
import com.shop.service.UserService;
import com.shop.web.PageProvider;
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
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {

    private final UserService userService;
    private final List<String> tokenStorage;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("login.html"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = User.builder().username(username).password(password).build();

        if (userService.isRegistered(user)) {
            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user-token", uuid);
            tokenStorage.add(cookie.getValue());
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
