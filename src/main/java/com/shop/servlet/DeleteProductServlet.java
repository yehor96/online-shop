package com.shop.servlet;

import com.shop.dao.ProductDao;
import com.shop.helper.ErrorHandler;
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
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            productDao.delete(id);
        } catch (IllegalArgumentException e) {
            ErrorHandler.processBadRequest(response,
                    "Not able to delete a product, since provided id is not valid");
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/products/delete");
    }
}
