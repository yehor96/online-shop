package com.shop.web.servlet;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.ResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class EditProductServletTest {

    private final Cookie[] cookies = new Cookie[]{};

    @Mock
    private ProductService mockProductService;
    @Mock
    private SecurityService mockSecurityService;
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;

    EditProductServlet editProductServlet;

    @BeforeEach
    void beforeEach() {
        openMocks(this);
        editProductServlet = new EditProductServlet(mockProductService, mockSecurityService);
    }

    @Test
    void testDoGetInvokesRequiredMethodsWhenUserIsLoggedIn() throws IOException {
        Long testId = 1L;
        ResponseWriter writer = mock(ResponseWriter.class);

        when(mockHttpServletRequest.getCookies()).thenReturn(cookies);
        when(mockSecurityService.isLoggedIn(cookies)).thenReturn(true);
        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(testId));
        when(mockHttpServletResponse.getWriter()).thenReturn(writer);

        editProductServlet.doGet(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockHttpServletResponse, times(1))
                .getWriter();
        verify(mockHttpServletResponse, never())
                .sendRedirect("/login");
    }

    @Test
    void testDoGetInvokesRequiredMethodsWhenUserIsNotLoggedIn() throws IOException {
        Long testId = 1L;
        ResponseWriter writer = mock(ResponseWriter.class);

        when(mockHttpServletRequest.getCookies()).thenReturn(cookies);
        when(mockSecurityService.isLoggedIn(cookies)).thenReturn(false);
        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(testId));
        when(mockHttpServletResponse.getWriter()).thenReturn(writer);

        editProductServlet.doGet(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockHttpServletResponse, never())
                .getWriter();
        verify(mockHttpServletResponse, times(1))
                .sendRedirect("/login");
    }

    @Test
    void testDoPostInvokesRequiredMethodsWhenUserIsLoggedIn() throws IOException {
        Long id = 1L;
        String name = "test_name";
        double price = 15.0;
        Product product = Product.builder().id(id).name(name).price(price).build();

        when(mockHttpServletRequest.getCookies()).thenReturn(cookies);
        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(id));
        when(mockHttpServletRequest.getParameter("name")).thenReturn(name);
        when(mockHttpServletRequest.getParameter("price")).thenReturn(String.valueOf(price));
        when(mockHttpServletRequest.getContextPath()).thenReturn("");
        when(mockSecurityService.isLoggedIn(cookies)).thenReturn(true);

        editProductServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockProductService, times(1))
                .update(product);
        verify(mockHttpServletResponse, times(1))
                .sendRedirect("/products");
        verify(mockHttpServletResponse, never())
                .sendRedirect("/login");
    }

    @Test
    void testDoPostInvokesRequiredMethodsWhenUserIsNotLoggedIn() throws IOException {
        Long id = 1L;
        String name = "test_name";
        double price = 15.0;
        Product product = Product.builder().id(id).name(name).price(price).build();

        when(mockHttpServletRequest.getCookies()).thenReturn(cookies);
        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(id));
        when(mockHttpServletRequest.getParameter("name")).thenReturn(name);
        when(mockHttpServletRequest.getParameter("price")).thenReturn(String.valueOf(price));
        when(mockHttpServletRequest.getContextPath()).thenReturn("");
        when(mockSecurityService.isLoggedIn(cookies)).thenReturn(false);

        editProductServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockProductService, never())
                .update(product);
        verify(mockHttpServletResponse, never())
                .sendRedirect("/products");
        verify(mockHttpServletResponse, times(1))
                .sendRedirect("/login");
    }
}