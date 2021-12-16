package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.web.servlet.setup.Mappable;
import com.shop.web.handler.ResponseErrorHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

@RequiredArgsConstructor
public class DeleteProductServlet extends HttpServlet implements Mappable {

    private static final ResponseErrorHandler ERROR_HANDLER = new ResponseErrorHandler();

    private final ProductService productService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            productService.delete(id);
        } catch (IllegalArgumentException e) {
            ERROR_HANDLER.processBadRequest(response,
                    "Not able to delete a product, since provided id is not valid");
        }
        response.sendRedirect(request.getContextPath() + "/products");
    }

    @Override
    public void addMapping(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(this), "/products/delete");
    }
}
