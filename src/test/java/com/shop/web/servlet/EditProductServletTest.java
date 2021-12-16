package com.shop.web.servlet;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import com.shop.web.PageProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.ResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class EditProductServletTest {

    @Mock
    private ProductService mockProductService;
    @Mock
    private PageProvider pageProvider;
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;

    EditProductServlet editProductServlet;

    @BeforeEach
    void beforeEach() {
        openMocks(this);
        editProductServlet = new EditProductServlet(mockProductService, pageProvider);
    }

    @Test
    void testDoGetInvokesRequiredMethodsWhenUserIsLoggedIn() throws IOException {
        Long testId = 1L;
        ResponseWriter writer = mock(ResponseWriter.class);

        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(testId));
        when(mockHttpServletResponse.getWriter()).thenReturn(writer);

        editProductServlet.doGet(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockHttpServletResponse, times(1))
                .getWriter();
    }

    @Test
    void testDoPostInvokesRequiredMethodsWhenUserIsLoggedIn() throws IOException {
        Long id = 1L;
        String name = "test_name";
        double price = 15.0;
        Product product = Product.builder().id(id).name(name).price(price).build();

        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(id));
        when(mockHttpServletRequest.getParameter("name")).thenReturn(name);
        when(mockHttpServletRequest.getParameter("price")).thenReturn(String.valueOf(price));
        when(mockHttpServletRequest.getContextPath()).thenReturn("");

        editProductServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockProductService, times(1))
                .update(product);
        verify(mockHttpServletResponse, times(1))
                .sendRedirect("/products");
    }
}