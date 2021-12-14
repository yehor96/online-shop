package com.shop.service;

import com.shop.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO update tests after adding Security Filter
class SecurityServiceTest {

//    private static SecurityService securityService;
//    private static List<String> mockTokenStorage;
//
//    @BeforeAll
//    static void beforeAll() {
//        mockTokenStorage = mock(ArrayList.class);
//        securityService = new SecurityService(mockTokenStorage);
//    }
//
//    @Test
//    void testIsLoggedInReturnsTrueWhenUserTokenPresentInStorage() {
//        String key = "user-token";
//        String value = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";
//
//        when(mockTokenStorage.contains(value)).thenReturn(true);
//
//        assertTrue(securityService.isLoggedIn(new Cookie[]{new Cookie(key, value)}));
//    }
//
//    @Test
//    void testIsLoggedInReturnsFalseWhenUserTokenAbsentInStorage() {
//        String key = "user-token";
//        String value = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";
//
//        when(mockTokenStorage.contains(value)).thenReturn(false);
//
//        assertFalse(securityService.isLoggedIn(new Cookie[]{new Cookie(key, value)}));
//    }
//
//    @Test
//    void testGetCookieAddsNewCookieToStorageAndReturnsIt() {
//        Cookie cookie = securityService.getCookie();
//
//        assertNotNull(cookie);
//        assertNotNull(cookie.getValue());
//        assertNotNull(cookie.getName());
//
//        verify(mockTokenStorage, times(1))
//                .add(cookie.getValue());
//    }
//
//    @Test
//    void testAddSaltSetsNewHashedPasswordAndSalt() {
//        String username = "user1";
//        String notHashedPassword = "pswd";
//        User user = User.builder().username(username).password(notHashedPassword).build();
//
//        securityService.addSalt(user);
//
//        assertNotNull(user.getSalt());
//        assertNotEquals(notHashedPassword, user.getPassword());
//    }
//
//    @Test
//    void testIsPasswordValidForUserReturnsTrueWhenPassedCorrectPassword() {
//        String username = "user1";
//        String password = "pswd";
//        String salt = "123";
//        String hashOfPasswordAndSalt = "e2aea7ce7eb227fed4f8e9b80959c327";
//
//        User existingUser = User.builder()
//                .username(username)
//                .password(hashOfPasswordAndSalt)
//                .salt(salt)
//                .build();
//
//        assertTrue(securityService.isPasswordValidForUser(password, existingUser));
//    }
//
//    @Test
//    void testIsPasswordValidForUserReturnsFalseWhenPassedIncorrectPassword() {
//        String username = "user1";
//        String password = "pswd";
//        String hashOfDifferentPasswordAndSalt = "193c848e901825965359fa19d7304121";
//
//        User existingUser = User.builder()
//                .username(username)
//                .password(hashOfDifferentPasswordAndSalt)
//                .build();
//
//        assertFalse(securityService.isPasswordValidForUser(password, existingUser));
//    }
//
//    @Test
//    void testGenerateSaltReturnsUniqueSalt() {
//        String salt1 = securityService.generateSalt();
//        String salt2 = securityService.generateSalt();
//
//        assertNotEquals(salt1, salt2);
//    }
//
//    @Test
//    void testHashReturnsSameHashForSameString() {
//        String value = "test_value";
//
//        String hash1 = securityService.hash(value);
//        String hash2 = securityService.hash(value);
//
//        assertEquals(hash1, hash2);
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"", "test with spaces", "123"})
//    void testPasswordRequirementInvalidPassword(String value) {
//        assertFalse(securityService.isPasswordValid(value));
//    }
//
//    @Test
//    void testPasswordRequirementPasswordIsNull() {
//        String value = null;
//
//        assertFalse(securityService.isPasswordValid(value));
//    }
//
//    @Test
//    void testPasswordRequirementValidPassword() {
//        String value = "12345";
//
//        assertTrue(securityService.isPasswordValid(value));
//    }
//
//    @Test
//    void getTokenValueReturnsUniqueToken() {
//        String token1 = securityService.getTokenValue();
//        String token2 = securityService.getTokenValue();
//
//        assertNotEquals(token1, token2);
//    }
}