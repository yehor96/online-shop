package com.shop.web.handler;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseErrorHandler {

    public void processBadRequest(HttpServletResponse response, String message) throws IOException {
        response.sendError(400, message);
    }
}
