package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import com.shop.web.handler.RequestParameterHandler;
import com.shop.web.handler.ResponseErrorHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

@RequiredArgsConstructor
public class DeleteProductServlet extends HttpServlet {

    private static final RequestParameterHandler PARAMETER_HANDLER = new RequestParameterHandler();
    private static final ResponseErrorHandler ERROR_HANDLER = new ResponseErrorHandler();

    private final ProductService productService;
    private final SecurityService securityService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (securityService.isLoggedIn(request)) {
            try {
                Long id = PARAMETER_HANDLER.getAsLong(request, "id");
                productService.delete(id);
            } catch (IllegalArgumentException e) {
                ERROR_HANDLER.processBadRequest(response,
                        "Not able to delete a product, since provided id is not valid");
            }
            response.sendRedirect(request.getContextPath() + "/products");
        } else {
            response.sendRedirect("/login");
        }
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/products/delete");
    }
}
