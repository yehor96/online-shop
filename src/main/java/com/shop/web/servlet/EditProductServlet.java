package com.shop.web.servlet;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import com.shop.web.Mappable;
import com.shop.web.PageProvider;
import com.shop.web.handler.ResponseErrorHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class EditProductServlet extends HttpServlet implements Mappable {

    private static final ResponseErrorHandler ERROR_HANDLER = new ResponseErrorHandler();

    private final ProductService productService;
    private final SecurityService securityService;
    private final PageProvider pageProvider;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> userTokens = securityService.getUserTokens(request.getCookies());
        if (securityService.isLoggedIn(userTokens)) {
            Map<String, Object> idParameter = new HashMap<>();
            String id = request.getParameter("id");
            idParameter.put("id", id);

            try (PrintWriter writer = response.getWriter()) {
                writer.println(pageProvider.getPage("products_edit.html", idParameter));
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> userTokens = securityService.getUserTokens(request.getCookies());
        if (securityService.isLoggedIn(userTokens)) {
            try {
                long id = Long.parseLong(request.getParameter("id"));
                String name = request.getParameter( "name");
                double price = Double.parseDouble(request.getParameter("price"));

                Product product = Product.builder()
                        .id(id)
                        .name(name)
                        .price(price)
                        .build();
                productService.update(product);
            } catch (IllegalArgumentException e) {
                ERROR_HANDLER.processBadRequest(response,
                        "Not able to edit product, since provided values are not valid");
            }
            response.sendRedirect(request.getContextPath() + "/products");
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    public void addMapping(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(this), "/products/edit");
    }
}