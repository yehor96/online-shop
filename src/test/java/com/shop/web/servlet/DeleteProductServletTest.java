package com.shop.web.servlet;

import com.shop.service.ProductService;
import com.shop.service.SecurityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

//TODO update tests after adding Security Filter
class DeleteProductServletTest {

//    private final Cookie[] cookies = new Cookie[]{};
//
//    @Mock
//    private ProductService mockProductService;
//    @Mock
//    private SecurityService mockSecurityService;
//    @Mock
//    private HttpServletRequest mockHttpServletRequest;
//    @Mock
//    private HttpServletResponse mockHttpServletResponse;
//
//    private DeleteProductServlet deleteProductServlet;
//
//    @BeforeEach
//    void beforeEach() {
//        openMocks(this);
//        deleteProductServlet = new DeleteProductServlet(mockProductService, mockSecurityService);
//    }
//
//    @Test
//    void testDoPostInvokesRequiredMethodsWhenUserIsLoggedIn() throws IOException {
//        Long testId = 1L;
//
//        when(mockHttpServletRequest.getCookies()).thenReturn(cookies);
//        when(mockSecurityService.isLoggedIn(cookies)).thenReturn(true);
//        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(testId));
//        when(mockHttpServletRequest.getContextPath()).thenReturn("");
//
//        deleteProductServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);
//
//        verify(mockProductService, times(1))
//                .delete(testId);
//        verify(mockHttpServletResponse, times(1))
//                .sendRedirect("/products");
//        verify(mockHttpServletResponse, never())
//                .sendRedirect("/login");
//    }
//
//    @Test
//    void testDoPostInvokesRequiredMethodsWhenUserIsNotLoggedIn() throws IOException {
//        Long testId = 1L;
//
//        when(mockHttpServletRequest.getCookies()).thenReturn(cookies);
//        when(mockSecurityService.isLoggedIn(cookies)).thenReturn(false);
//        when(mockHttpServletRequest.getParameter("id")).thenReturn(String.valueOf(testId));
//        when(mockHttpServletRequest.getContextPath()).thenReturn("");
//
//        deleteProductServlet.doPost(mockHttpServletRequest, mockHttpServletResponse);
//
//        verify(mockProductService, never())
//                .delete(testId);
//        verify(mockHttpServletResponse, never())
//                .sendRedirect("/products");
//        verify(mockHttpServletResponse, times(1))
//                .sendRedirect("/login");
//    }

}