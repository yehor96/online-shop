package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import com.shop.service.UserService;
import com.shop.web.PageProvider;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServletManager {

    public static ServletContextHandler getContextHandler(PageProvider pageProvider,
                                                          ProductService productService,
                                                          UserService userService,
                                                          SecurityService securityService) {

        CssServlet cssServlet = new CssServlet(pageProvider);
        FailedLoginServlet failedLoginServlet = new FailedLoginServlet(pageProvider);
        FailedRegistrationServlet failedRegistrationServlet = new FailedRegistrationServlet(pageProvider);
        RegistrationServlet registrationServlet = new RegistrationServlet(userService, securityService, pageProvider);
        LoginServlet loginServlet = new LoginServlet(securityService, pageProvider);
        LogoutServlet logoutServlet = new LogoutServlet(securityService);

        ProductsServlet productsServlet = new ProductsServlet(productService, securityService, pageProvider);
        AddProductServlet addProductServlet = new AddProductServlet(productService, securityService, pageProvider);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService, securityService);
        EditProductServlet editProductServlet = new EditProductServlet(productService, securityService, pageProvider);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        cssServlet.addMapping(contextHandler);
        productsServlet.addMapping(contextHandler);
        addProductServlet.addMapping(contextHandler);
        deleteProductServlet.addMapping(contextHandler);
        editProductServlet.addMapping(contextHandler);
        loginServlet.addMapping(contextHandler);
        failedLoginServlet.addMapping(contextHandler);
        registrationServlet.addMapping(contextHandler);
        failedRegistrationServlet.addMapping(contextHandler);
        logoutServlet.addMapping(contextHandler);

        return contextHandler;
    }
}
