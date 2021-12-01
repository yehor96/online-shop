package com.shop.web.handler;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ResponseErrorHandlerTest {

    @Test
    void testProcessBadRequest() throws IOException {
        String message = "test";
        ResponseErrorHandler errorHandler = new ResponseErrorHandler();
        HttpServletResponse mockHttpServletResponse = mock(HttpServletResponse.class);

        errorHandler.processBadRequest(mockHttpServletResponse, message);

        verify(mockHttpServletResponse, times(1))
                .sendError(400, message);
    }

}