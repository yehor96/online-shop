package com.shop.web.servlet;

import com.shop.service.ProductService;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServletManager {

    public static ServletContextHandler getContext(ProductService service) {
        CssServlet cssServlet = new CssServlet();
        ProductsServlet productsServlet = new ProductsServlet(service);
        AddProductServlet addProductServlet = new AddProductServlet(service);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(service);
        EditProductServlet editProductServlet = new EditProductServlet(service);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        cssServlet.addMapping(context);
        productsServlet.addMapping(context);
        addProductServlet.addMapping(context);
        deleteProductServlet.addMapping(context);
        editProductServlet.addMapping(context);

        return context;
    }
}
