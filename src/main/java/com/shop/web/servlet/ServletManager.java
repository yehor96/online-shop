package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import com.shop.service.UserService;
import com.shop.web.Mappable;
import com.shop.web.PageProvider;
import lombok.experimental.UtilityClass;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.List;

@UtilityClass
public class ServletManager {

    public static List<Mappable> getServlets(PageProvider pageProvider,
                                             ProductService productService,
                                             UserService userService,
                                             SecurityService securityService) {
        return List.of(
                new CssServlet(pageProvider),
                new FailedLoginServlet(pageProvider),
                new FailedRegistrationServlet(pageProvider),
                new RegistrationServlet(userService, securityService, pageProvider),
                new LoginServlet(securityService, pageProvider),
                new LogoutServlet(securityService),
                new ProductsServlet(productService, securityService, pageProvider),
                new AddProductServlet(productService, securityService, pageProvider),
                new DeleteProductServlet(productService, securityService),
                new EditProductServlet(productService, securityService, pageProvider)
        );
    }

    public static ServletContextHandler addMappings(List<Mappable> servlets) {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        servlets.forEach(servlet -> servlet.addMapping(contextHandler));

        return contextHandler;
    }
}
