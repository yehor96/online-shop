package com.shop;

import com.shop.dao.product.JdbcProductDaoImpl;
import com.shop.dao.product.ProductDao;
import com.shop.dao.user.JdbcUserDaoImpl;
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

        ProductDao productDao = new JdbcProductDaoImpl();
        UserDao userDao = new JdbcUserDaoImpl();

        SecurityService securityService = new SecurityService();
        ProductService productService = new ProductService(productDao);
        UserService userService = new UserService(userDao, securityService);

        ServletContextHandler context = ServletManager.getContext(productService, userService, securityService);
        server.setHandler(context);

        server.start();
    }
}
