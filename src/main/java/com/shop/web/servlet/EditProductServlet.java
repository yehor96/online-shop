package com.shop.web.servlet;

import com.shop.dao.ProductDao;
import com.shop.entity.Product;
import com.shop.web.handler.ErrorHandler;
import com.shop.web.PageProvider;
import com.shop.web.handler.RequestParameterHandler;
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

    private static final RequestParameterHandler PARAMETER_HANDLER = new RequestParameterHandler();

    private final ProductDao productDao;

    public EditProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> idParameter = new HashMap<>();
        String id = PARAMETER_HANDLER.getAsString(request, "id");
        idParameter.put("id", id);

        try (PrintWriter writer = response.getWriter()) {
            writer.println(PageProvider.getPage("products_edit.html", idParameter));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = PARAMETER_HANDLER.getAsLong(request, "id");
            String name = PARAMETER_HANDLER.getAsString(request, "name");
            Double price = PARAMETER_HANDLER.getAsDouble(request, "price");

            Product product = Product.builder()
                    .id(id)
                    .name(name)
                    .price(price)
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