package com.shop.web;

import org.eclipse.jetty.servlet.ServletContextHandler;

public interface Mappable {

    void addMapping(ServletContextHandler contextHandler);

}
