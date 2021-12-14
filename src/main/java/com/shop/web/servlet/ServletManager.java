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
        LogoutServlet logoutServlet = new LogoutServlet(securityService);

        ProductsServlet productsServlet = new ProductsServlet(productService, securityService);
        AddProductServlet addProductServlet = new AddProductServlet(productService, securityService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService, securityService);
        EditProductServlet editProductServlet = new EditProductServlet(productService, securityService);

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
