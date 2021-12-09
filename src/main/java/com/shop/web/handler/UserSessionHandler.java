package com.shop.web.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class UserSessionHandler {

    public boolean isLoggedIn(List<String> storage, HttpServletRequest request) {
        return Stream.of(request.getCookies())
                .anyMatch(cookie -> storage.contains(cookie.getValue()));
    }

    public void addUserToken(List<String> tokenStorage, HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("user-token", uuid);
        tokenStorage.add(cookie.getValue());
        response.addCookie(cookie);
    }

}
