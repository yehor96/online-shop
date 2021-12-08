package com.shop.web.handler;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Stream;

public class UserSessionHandler {

    public boolean isLoggedIn(List<String> storage, HttpServletRequest request) {
        return Stream.of(request.getCookies())
                .anyMatch(cookie -> storage.contains(cookie.getValue()));
    }

}
