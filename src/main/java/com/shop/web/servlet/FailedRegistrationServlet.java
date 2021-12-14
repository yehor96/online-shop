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
import java.util.HashMap;
import java.util.Map;

public class FailedRegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter writer = response.getWriter()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("message", request.getAttribute("message"));
            writer.println(PageProvider.getPage("failed_registration.html", parameters));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void addMapping(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(this), "/failed_registration");
    }

}
