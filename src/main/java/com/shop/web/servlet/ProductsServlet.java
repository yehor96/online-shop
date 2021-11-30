package com.shop.web.servlet;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import com.shop.web.PageProvider;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsServlet extends HttpServlet {

    private final ProductService productService;

    public ProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productService.findAll();
        Map<String, Object> productMap = new HashMap<>();

        try (PrintWriter writer = response.getWriter()) {
            productMap.put("products", products);
            writer.println(PageProvider.getPage("products.html", productMap));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "");
        context.addServlet(new ServletHolder(this), "/products");
    }
}
