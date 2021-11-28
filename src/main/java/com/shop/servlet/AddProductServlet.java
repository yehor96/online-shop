package com.shop.servlet;

import com.shop.PageProvider;
import com.shop.dao.ProductDao;
import com.shop.entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class AddProductServlet extends HttpServlet {

    private ProductDao productDao;

    public AddProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("products_add.html"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = Product.builder()
                .name(request.getParameter("name"))
                .price(Double.parseDouble(request.getParameter("price")))
                .createdDate(LocalDate.now())
                .build();
        productDao.save(product);
        response.sendRedirect(request.getContextPath() + "/products");
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/products/add");
    }
}
