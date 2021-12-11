package com.shop.web.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserSessionHandlerTest {

//    @Test
//    void testIsLoggedInReturnsTrueWhenUserTokenPresentInStorage() {
//        UserSessionHandler userSessionHandler = new UserSessionHandler();
//        String header = "user-token";
//        String uuid = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";
//        List<String> tokenStorage = List.of(uuid);
//
//        HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
//        when(mockedRequest.getCookies()).thenReturn(new Cookie[]{new Cookie(header, uuid)});
//
//        assertTrue(userSessionHandler.isLoggedIn(tokenStorage, mockedRequest));
//    }
//
//    @Test
//    void testIsLoggedInReturnsFalseWhenUserTokenPresentInStorage() {
//        UserSessionHandler userSessionHandler = new UserSessionHandler();
//        String header = "user-token";
//        String uuid = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";
//        List<String> tokenStorage = new ArrayList<>();
//
//        HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
//        when(mockedRequest.getCookies()).thenReturn(new Cookie[]{new Cookie(header, uuid)});
//
//        assertFalse(userSessionHandler.isLoggedIn(tokenStorage, mockedRequest));
//    }
//
//    @Test
//    void testAddUserToken() {
//        UserSessionHandler userSessionHandler = new UserSessionHandler();
//        HttpServletResponse mockedResponse = mock(HttpServletResponse.class);
//        List<String> tokenStorage = mock(ArrayList.class);
//
//        userSessionHandler.addUserToken(tokenStorage, mockedResponse);
//
//        verify(mockedResponse, times(1))
//                .addCookie(any(Cookie.class));
//        verify(tokenStorage, times(1))
//                .add(anyString());
//    }
}