package com.shop.web.servlet;

import com.shop.dao.ProductDao;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServletManager {

    public static ServletContextHandler getContext(ProductDao dao) {
        CssServlet cssServlet = new CssServlet();
        ProductsServlet productsServlet = new ProductsServlet(dao);
        AddProductServlet addProductServlet = new AddProductServlet(dao);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(dao);
        EditProductServlet editProductServlet = new EditProductServlet(dao);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        cssServlet.addMapping(context);
        productsServlet.addMapping(context);
        addProductServlet.addMapping(context);
        deleteProductServlet.addMapping(context);
        editProductServlet.addMapping(context);

        return context;
    }
}
