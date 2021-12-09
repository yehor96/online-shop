package com.shop.web.servlet;

import com.shop.web.PageProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;

public class FailedRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("failed_registration.html"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/failed_registration");
    }

}
