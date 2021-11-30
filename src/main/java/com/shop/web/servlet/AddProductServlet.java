package com.shop.web.servlet;

import com.shop.dao.ProductDao;
import com.shop.entity.Product;
import com.shop.web.handler.ErrorHandler;
import com.shop.web.PageProvider;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;

public class AddProductServlet extends HttpServlet {

    private ProductDao productDao;

    public AddProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("products_add.html"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Product product = Product.builder()
                    .name(request.getParameter("name"))
                    .price(Double.parseDouble(request.getParameter("price")))
                    .build();
            productDao.save(product);
        } catch (IllegalArgumentException e) {
            ErrorHandler.processBadRequest(response,
                    "Not able to add a product, since provided values are not valid");
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/products/add");
    }
}
