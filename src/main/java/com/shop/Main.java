package com.shop;

import com.shop.dao.MockProductDaoImpl;
import com.shop.servlet.ServletManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
        ServletContextHandler context = ServletManager.getContext(new MockProductDaoImpl());
        server.setHandler(context);

        server.start();
    }
}
