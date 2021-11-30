package com.shop;

import com.shop.dao.JdbcProductDaoImpl;
import com.shop.dao.ProductDao;
import com.shop.service.ProductService;
import com.shop.web.servlet.ServletManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
        ProductDao dao = new JdbcProductDaoImpl();
        ProductService service = new ProductService(dao);

        ServletContextHandler context = ServletManager.getContext(service);
        server.setHandler(context);

        server.start();
    }
}
