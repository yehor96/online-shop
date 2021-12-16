package com.shop.web.filter;

import com.shop.service.SecurityService;
import com.shop.web.handler.CookieHandler;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

@RequiredArgsConstructor
public class SecurityFilter implements Filter {

    private static final CookieHandler COOKIE_HANDLER = new CookieHandler();

    private static final String CSS_PATH = "/css";
    private static final String LOGIN_PATH = "/login";
    private static final List<String> ALLOWED_PATHS =
            List.of("/products", LOGIN_PATH, "/failed_login", "/registration", "/failed_registration");

    private final SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (isAllowed(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        List<String> userTokens = COOKIE_HANDLER.getUserTokens(request.getCookies());
        if (!securityService.isLoggedIn(userTokens)) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(LOGIN_PATH);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isAllowed(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        return requestURI.startsWith(CSS_PATH) ||
                ALLOWED_PATHS.stream().anyMatch(path -> request.getRequestURI().equals(path));
    }

    public void register(ServletContextHandler contextHandler) {
        contextHandler.addFilter(new FilterHolder(this), "/*", EnumSet.of(DispatcherType.REQUEST));
    }

}
