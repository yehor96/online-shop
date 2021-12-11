package com.shop.web.servlet;

import com.shop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DeleteProductServletTest {

    @Mock
    private ProductService mockProductService;
    @Mock
    private List<String> cookies;
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;

    @BeforeEach
    void beforeEach() {
        openMocks(this);
    }

    @Test
    @Disabled // TODO update test
    void testDoPostInvokesRequiredMethods() throws IOException {
//        DeleteProductServlet addProductServlet = new DeleteProductServlet(mockProductService, cookies);
//        Long testId = 1L;
//
//        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(testId));
//        when(mockHttpServletRequest.getContextPath()).thenReturn("");
//
//        addProductServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);
//
//        verify(mockProductService, times(1))
//                .delete(testId);
//        verify(mockHttpServletResponse, times(1))
//                .sendRedirect("/products");
    }

}