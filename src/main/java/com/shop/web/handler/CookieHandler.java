package com.shop.web.handler;

import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CookieHandler {

    private static final String TOKEN_KEY = "user-os-token";

    public List<String> getUserTokens(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return Collections.emptyList();
        }

        List<String> userTokens = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), TOKEN_KEY)) {
                userTokens.add(cookie.getValue());
            }
        }
        return userTokens;
    }

    public Cookie generateCookie() {
        String tokenValue = getTokenValue();
        return new Cookie(TOKEN_KEY, tokenValue);
    }

    public Cookie generateRemovalCookie() {
        Cookie cookie = new Cookie(TOKEN_KEY, "");
        cookie.setMaxAge(0);
        return cookie;
    }

    private String getTokenValue() {
        return UUID.randomUUID().toString();
    }
}
