package com.shop.web.servlet;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class EditProductServletTest {

    @Mock
    private ProductService mockProductService;
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;

    @BeforeEach
    void beforeEach() {
        openMocks(this);
    }

    @Test
    void testDoPostInvokesRequiredMethods() throws IOException {
        EditProductServlet addProductServlet = new EditProductServlet(mockProductService);
        Long testId = 1L;
        String name = "test_name";
        double price = 15.0;
        Product product = Product.builder().id(testId).name(name).price(price).build();

        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(testId));
        when(mockHttpServletRequest.getParameter("name")).thenReturn(name);
        when(mockHttpServletRequest.getParameter("price")).thenReturn(String.valueOf(price));
        when(mockHttpServletRequest.getContextPath()).thenReturn("");

        addProductServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockProductService, times(1))
                .update(product);
        verify(mockHttpServletResponse, times(1))
                .sendRedirect("/products");
    }
}