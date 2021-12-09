package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.service.UserService;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.ArrayList;
import java.util.List;

public class ServletManager {

    public static ServletContextHandler getContext(ProductService productService, UserService userService) {

        List<String> tokenStorage = new ArrayList<>();

        CssServlet cssServlet = new CssServlet();
        FailedLoginServlet failedLoginServlet = new FailedLoginServlet();
        LoginServlet loginServlet = new LoginServlet(userService, tokenStorage);

        ProductsServlet productsServlet = new ProductsServlet(productService, tokenStorage);
        AddProductServlet addProductServlet = new AddProductServlet(productService, tokenStorage);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService, tokenStorage);
        EditProductServlet editProductServlet = new EditProductServlet(productService, tokenStorage);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        cssServlet.addMapping(context);
        productsServlet.addMapping(context);
        addProductServlet.addMapping(context);
        deleteProductServlet.addMapping(context);
        editProductServlet.addMapping(context);
        loginServlet.addMapping(context);
        failedLoginServlet.addMapping(context);

        return context;
    }
}
