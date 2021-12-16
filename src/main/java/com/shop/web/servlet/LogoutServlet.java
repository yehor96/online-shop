package com.shop.web.servlet;

import com.shop.service.SecurityService;
import com.shop.web.Mappable;
import com.shop.web.handler.CookieHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class LogoutServlet extends HttpServlet implements Mappable {

    private static final CookieHandler COOKIE_HANDLER = new CookieHandler();

    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> userTokens = COOKIE_HANDLER.getUserTokens(request.getCookies());
        securityService.clearUserTokens(userTokens);

        response.addCookie(COOKIE_HANDLER.generateRemovalCookie());
        response.sendRedirect("/products");
    }

    @Override
    public void addMapping(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(this), "/logout");
    }
}
