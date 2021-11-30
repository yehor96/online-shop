package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.web.handler.ErrorHandler;
import com.shop.web.handler.RequestParameterHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {

    private static final RequestParameterHandler PARAMETER_HANDLER = new RequestParameterHandler();

    private final ProductService productService;

    public DeleteProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = PARAMETER_HANDLER.getAsLong(request, "id");
            productService.delete(id);
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
