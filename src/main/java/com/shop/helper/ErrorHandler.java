package com.shop.helper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class ErrorHandler {

    public static void processBadRequest(HttpServletResponse response, String message) throws IOException {
        response.sendError(400, message);
    }
}
