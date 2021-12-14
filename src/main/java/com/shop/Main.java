package com.shop;

import com.shop.dao.product.JdbcProductDao;
import com.shop.dao.product.ProductDao;
import com.shop.dao.user.JdbcUserDao;
import com.shop.dao.user.UserDao;
import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import com.shop.service.UserService;
import com.shop.web.servlet.ServletManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        ProductDao productDao = new JdbcProductDao();
        UserDao userDao = new JdbcUserDao();

        ProductService productService = new ProductService(productDao);
        UserService userService = new UserService(userDao);
        SecurityService securityService = new SecurityService(userService);


        ServletContextHandler contextHandler = ServletManager.getContextHandler(productService, userService, securityService);
        server.setHandler(contextHandler);

        server.start();
    }
}
