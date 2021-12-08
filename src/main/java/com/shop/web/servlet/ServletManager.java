package com.shop.web.servlet;

import com.shop.service.ProductService;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.ArrayList;
import java.util.List;

public class ServletManager {

    public static ServletContextHandler getContext(ProductService service) {

        List<String> tokenStorage = new ArrayList<>();

        CssServlet cssServlet = new CssServlet();
        LoginServlet loginServlet = new LoginServlet(tokenStorage);

        ProductsServlet productsServlet = new ProductsServlet(service, tokenStorage);
        AddProductServlet addProductServlet = new AddProductServlet(service, tokenStorage);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(service, tokenStorage);
        EditProductServlet editProductServlet = new EditProductServlet(service, tokenStorage);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        cssServlet.addMapping(context);
        productsServlet.addMapping(context);
        addProductServlet.addMapping(context);
        deleteProductServlet.addMapping(context);
        editProductServlet.addMapping(context);
        loginServlet.addMapping(context);

        return context;
    }
}
