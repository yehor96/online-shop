package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import com.shop.service.UserService;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServletManager {

    public static ServletContextHandler getContextHandler(ProductService productService,
                                                          UserService userService,
                                                          SecurityService securityService) {

        CssServlet cssServlet = new CssServlet();
        FailedLoginServlet failedLoginServlet = new FailedLoginServlet();
        FailedRegistrationServlet failedRegistrationServlet = new FailedRegistrationServlet();
        RegistrationServlet registrationServlet = new RegistrationServlet(userService, securityService);
        LoginServlet loginServlet = new LoginServlet(securityService);

        ProductsServlet productsServlet = new ProductsServlet(productService, securityService);
        AddProductServlet addProductServlet = new AddProductServlet(productService, securityService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService, securityService);
        EditProductServlet editProductServlet = new EditProductServlet(productService, securityService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        cssServlet.addMapping(context);
        productsServlet.addMapping(context);
        addProductServlet.addMapping(context);
        deleteProductServlet.addMapping(context);
        editProductServlet.addMapping(context);
        loginServlet.addMapping(context);
        failedLoginServlet.addMapping(context);
        registrationServlet.addMapping(context);
        failedRegistrationServlet.addMapping(context);

        return context;
    }
}
