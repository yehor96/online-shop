package com.shop.servlet;

import com.shop.dao.ProductDao;
import com.shop.entity.Product;
import com.shop.helper.ErrorHandler;
import com.shop.helper.PageProvider;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class EditProductServlet extends HttpServlet {

    private ProductDao productDao;

    public EditProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> idParameter = new HashMap<>();
        idParameter.put("id", request.getParameter("id"));

        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("products_edit.html", idParameter));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Product product = Product.builder()
                    .id(Long.parseLong(request.getParameter("id")))
                    .name(request.getParameter("name"))
                    .price(Double.parseDouble(request.getParameter("price")))
                    .build();
            productDao.update(product);
        } catch (IllegalArgumentException e) {
            ErrorHandler.processBadRequest(response,
                    "Not able to edit product, since provided values are not valid");
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/products/edit");
    }
}