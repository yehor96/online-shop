package com.shop;

import com.shop.dao.product.JdbcProductDaoImpl;
import com.shop.dao.product.ProductDao;
import com.shop.dao.user.JdbcUserDaoImpl;
import com.shop.dao.user.UserDao;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import com.shop.web.servlet.ServletManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        ProductDao productDao = new JdbcProductDaoImpl();
        ProductService productService = new ProductService(productDao);

        UserDao userDao = new JdbcUserDaoImpl();
        UserService userService = new UserService(userDao);

        ServletContextHandler context = ServletManager.getContext(productService, userService);
        server.setHandler(context);

        server.start();
    }
}
