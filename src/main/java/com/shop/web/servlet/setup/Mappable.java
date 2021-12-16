package com.shop.web.servlet.setup;

import org.eclipse.jetty.servlet.ServletContextHandler;

public interface Mappable {

    void addMapping(ServletContextHandler contextHandler);

}
