package com.shop.web.servlet;

import com.shop.entity.User;
import com.shop.service.SecurityService;
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

@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {

    private final SecurityService securityService;
    private final PageProvider pageProvider;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> userTokens = securityService.getUserTokens(request.getCookies());
        if (securityService.isLoggedIn(userTokens)) {
            response.sendRedirect("/products");
        } else {
            try (PrintWriter writer = response.getWriter()) {
                writer.println(pageProvider.getPage("login.html"));
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = User.builder().username(username).password(password).build();

        if (securityService.areValidCredentials(user)) {
            Cookie cookie = securityService.generateCookie();
            response.addCookie(cookie);
            response.sendRedirect("/products");
        } else {
            response.sendRedirect("/failed_login");
        }
    }

    public void addMapping(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(this), "/login");
    }
}
