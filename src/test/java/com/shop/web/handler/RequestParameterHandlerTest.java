package com.shop.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestParameterHandlerTest {

    private static RequestParameterHandler parameterHandler = new RequestParameterHandler();
    private static HttpServletRequest mockHttpServletRequest;

    @BeforeAll
    static void beforeAll() {
        mockHttpServletRequest = mock(HttpServletRequest.class);
    }

    @Test
    void testGetAsStringReturnsExpectedValue() {
        when(mockHttpServletRequest.getParameter("parameter"))
                .thenReturn("value");

        String actual = parameterHandler.getAsString(mockHttpServletRequest, "parameter");

        assertEquals("value", actual);
    }

    @Test
    void testGetAsStringReturnsEscapedHtmlValue() {
        when(mockHttpServletRequest.getParameter("parameter"))
                .thenReturn("<alert>text</alert>");

        String actual = parameterHandler.getAsString(mockHttpServletRequest, "parameter");

        assertEquals("&lt;alert&gt;text&lt;/alert&gt;", actual);
    }

    @Test
    void testGetAsLongReturnsExpectedValue() {
        when(mockHttpServletRequest.getParameter("parameter"))
                .thenReturn("15");

        Long actual = parameterHandler.getAsLong(mockHttpServletRequest, "parameter");

        assertEquals(15L, actual);
    }

    @Test
    void testGetAsDoubleReturnsExpectedValue() {
        when(mockHttpServletRequest.getParameter("parameter"))
                .thenReturn("10.50");

        Double actual = parameterHandler.getAsDouble(mockHttpServletRequest, "parameter");

        assertEquals(10.50, actual);
    }
}