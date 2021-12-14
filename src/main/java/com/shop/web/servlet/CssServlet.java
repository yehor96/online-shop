package com.shop.web.servlet;

import com.shop.web.PageProvider;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;

public class CssServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getCssPage(request.getPathInfo()));
        }
    }

    public void addMapping(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(this), "/css/*");
    }
}
