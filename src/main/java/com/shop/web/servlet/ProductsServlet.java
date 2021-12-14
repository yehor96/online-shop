package com.shop.web.servlet;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import com.shop.web.PageProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ProductsServlet extends HttpServlet {

    private final ProductService productService;
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productService.findAll();

        List<String> userTokens = securityService.getUserTokens(request.getCookies());
        boolean isLoggedIn = securityService.isLoggedIn(userTokens);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        parameters.put("login", Boolean.toString(isLoggedIn));

        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("products.html", parameters));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "");
        context.addServlet(new ServletHolder(this), "/products");
    }
}
