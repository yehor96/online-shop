package com.shop.service;

import com.shop.dao.user.JdbcUserDaoImpl;
import com.shop.dao.user.UserDao;
import com.shop.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private static UserService userService;
    private static UserDao mockDao;

    @BeforeAll
    static void beforeAll() {
        mockDao = mock(JdbcUserDaoImpl.class);
        userService = new UserService(mockDao);
    }

    @Test
    void testIsRegisteredReturnsTrueWhenUserExists() {
        String username = "user1";
        User user = User.builder().username(username).build();

        when(mockDao.findOne(username)).thenReturn(Optional.of(user));

        assertTrue(userService.isRegistered(username));
    }

    @Test
    void testIsRegisteredReturnsFalseWhenUserDoesNotExist() {
        String username = "user1";

        when(mockDao.findOne(username)).thenReturn(Optional.empty());

        assertFalse(userService.isRegistered(username));
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

        when(mockDao.findOne(username)).thenReturn(Optional.of(existingUser));

        assertTrue(userService.areValidCredentials(userUnderTest));
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

        when(mockDao.findOne(username)).thenReturn(Optional.of(existingUser));

        assertFalse(userService.areValidCredentials(userUnderTest));
    }

    @Test
    void testAreValidCredentialsWhenPassingNotExistingCredentials() {
        String username = "user1";
        String password = "pswd";

        User userUnderTest = User.builder()
                .username(username)
                .password(password)
                .build();

        when(mockDao.findOne(username)).thenReturn(Optional.empty());

        assertFalse(userService.areValidCredentials(userUnderTest));
    }

    @Test
    void testAddNewInvokesRequiredMethod() {
        String username = "user1";
        String password = "pswd";

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        userService.addNew(user);

        verify(mockDao, times(1))
                .addNew(any(User.class));
    }

}