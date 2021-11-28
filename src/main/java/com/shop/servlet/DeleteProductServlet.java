package com.shop.servlet;

import com.shop.dao.ProductDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {

    private ProductDao productDao;

    public DeleteProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        productDao.delete(Long.parseLong(request.getParameter("id")));
        response.sendRedirect(request.getContextPath() + "/products");
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/products/delete");
    }
}
