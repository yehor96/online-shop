package com.shop.service;

import com.shop.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityServiceTest {

    private static SecurityService securityService;
    private static UserService mockedUserService;

    @BeforeAll
    static void beforeAll() {
        mockedUserService = mock(UserService.class);
        securityService = new SecurityService(mockedUserService);
    }

    @Test
    void testIsLoggedInReturnsTrueWhenUserTokenPresentInStorage() {
        String token = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";

        securityService.addToken(token);

        assertTrue(securityService.isLoggedIn(List.of(token)));
    }

    @Test
    void testIsLoggedInReturnsFalseWhenUserTokenAbsentInStorage() {
        String value = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";

        assertFalse(securityService.isLoggedIn(List.of(value)));
    }

    @Test
    void testIsLoggedInReturnsFalseWhenUserTokensPassedAreEmpty() {
        assertFalse(securityService.isLoggedIn(Collections.emptyList()));
    }

    @Test
    void testClearUserTokensRemovesAllProvidedTokens() {
        String token1 = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";
        String token2 = "f7760f5f-6a07-4dd5-98a7-197be21472ec";
        List<String> tokens = List.of(token1, token2);
        securityService.addToken(token1);
        securityService.addToken(token2);

        securityService.clearUserTokens(tokens);

        assertFalse(securityService.isLoggedIn(tokens));
    }

    @Test
    void testClearUserTokensRemovesOnlyProvidedTokens() {
        String token1 = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";
        String token2 = "f7760f5f-6a07-4dd5-98a7-197be21472ec";
        securityService.addToken(token1);
        securityService.addToken(token2);

        securityService.clearUserTokens(List.of(token1));

        assertTrue(securityService.isLoggedIn(List.of(token2)));
    }

    @Test
    void testAddTokenAddsProvidedToken() {
        String token1 = "a4fc4f5a-fe08-4120-ad49-a2dd55ee8ce0";

        assertFalse(securityService.isLoggedIn(List.of(token1)));

        securityService.addToken(token1);

        assertTrue(securityService.isLoggedIn(List.of(token1)));
    }

    @Test
    void testAddSaltSetsNewHashedPasswordAndSalt() {
        String username = "user1";
        String notHashedPassword = "pswd";
        User user = User.builder().username(username).password(notHashedPassword).build();

        securityService.addSalt(user);

        assertNotNull(user.getSalt());
        assertNotEquals(notHashedPassword, user.getPassword());
    }

    @Test
    void testAreValidCredentialsWhenPassingValidCredentials() {
        String username = "user1";
        String password = "pswd";
        String salt = "123";
        String hashOfPasswordAndSalt = "e2aea7ce7eb227fed4f8e9b80959c327";

        User userUnderTest = User.builder()
                .username(username)
                .password(password)
                .build();
        User existingUser = User.builder()
                .username(username)
                .password(hashOfPasswordAndSalt)
                .salt(salt)
                .build();

        when(mockedUserService.findOne(username)).thenReturn(Optional.of(existingUser));

        assertTrue(securityService.areValidCredentials(userUnderTest));
    }

    @Test
    void testAreValidCredentialsWhenPassingCredentialsWithInvalidPassword() {
        String username = "user1";
        String password = "pswd";
        String salt = "123";
        String invalidHashOfPasswordAndSalt = "193c848e901825965359fa19d7304121";

        User userUnderTest = User.builder()
                .username(username)
                .password(password)
                .build();
        User existingUser = User.builder()
                .username(username)
                .password(invalidHashOfPasswordAndSalt)
                .salt(salt)
                .build();

        when(mockedUserService.findOne(username)).thenReturn(Optional.of(existingUser));

        assertFalse(securityService.areValidCredentials(userUnderTest));
    }

    @Test
    void testAreValidCredentialsReturnsFalseWhenPassingNotRegisteredUsername() {
        String username = "user1";
        String password = "pswd";

        User userUnderTest = User.builder()
                .username(username)
                .password(password)
                .build();

        when(mockedUserService.findOne(username)).thenReturn(Optional.empty());

        assertFalse(securityService.areValidCredentials(userUnderTest));
    }

    @Test
    void testAreValidCredentialsWhenPassingNotExistingCredentials() {
        String username = "user1";
        String password = "pswd";

        User userUnderTest = User.builder()
                .username(username)
                .password(password)
                .build();

        when(mockedUserService.findOne(username)).thenReturn(Optional.empty());

        assertFalse(securityService.areValidCredentials(userUnderTest));
    }

    @Test
    void testIsUserRegisteredReturnsTrueWhenUserExists() {
        String username = "user1";
        User user = User.builder().username(username).build();

        when(mockedUserService.findOne(username)).thenReturn(Optional.of(user));

        assertTrue(securityService.isUserRegistered(username));
    }

    @Test
    void testIsUserRegisteredReturnsFalseWhenUserDoesNotExist() {
        String username = "user1";

        when(mockedUserService.findOne(username)).thenReturn(Optional.empty());

        assertFalse(securityService.isUserRegistered(username));
    }

    @Test
    void testIsPasswordValidForUserReturnsTrueWhenPassedCorrectPassword() {
        String username = "user1";
        String password = "pswd";
        String salt = "123";
        String hashOfPasswordAndSalt = "e2aea7ce7eb227fed4f8e9b80959c327";

        User existingUser = User.builder()
                .username(username)
                .password(hashOfPasswordAndSalt)
                .salt(salt)
                .build();

        assertTrue(securityService.isPasswordValidForUser(password, existingUser));
    }

    @Test
    void testIsPasswordValidForUserReturnsFalseWhenPassedIncorrectPassword() {
        String username = "user1";
        String password = "pswd";
        String hashOfDifferentPasswordAndSalt = "193c848e901825965359fa19d7304121";

        User existingUser = User.builder()
                .username(username)
                .password(hashOfDifferentPasswordAndSalt)
                .build();

        assertFalse(securityService.isPasswordValidForUser(password, existingUser));
    }

    @Test
    void testGenerateSaltReturnsUniqueSalt() {
        String salt1 = securityService.generateSalt();
        String salt2 = securityService.generateSalt();

        assertNotEquals(salt1, salt2);
    }

    @Test
    void testHashReturnsSameHashForSameString() {
        String value = "test_value";

        String hash1 = securityService.hash(value);
        String hash2 = securityService.hash(value);

        assertEquals(hash1, hash2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "test with spaces", "123"})
    void testPasswordRequirementInvalidPassword(String value) {
        assertFalse(securityService.isPasswordValid(value));
    }

    @Test
    void testPasswordRequirementPasswordIsNull() {
        String value = null;

        assertFalse(securityService.isPasswordValid(value));
    }

    @Test
    void testPasswordRequirementValidPassword() {
        String value = "12345";

        assertTrue(securityService.isPasswordValid(value));
    }
}