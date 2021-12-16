package com.shop.service;

import com.shop.dao.user.JdbcUserDao;
import com.shop.dao.user.UserDao;
import com.shop.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private static UserService userService;
    private static UserDao mockDao;

    @BeforeAll
    static void beforeAll() {
        mockDao = mock(JdbcUserDao.class);
        userService = new UserService(mockDao);
    }

    @Test
    void testAddNew() {
        User user = User.builder().username("user").password("pswd").build();

        userService.addNew(user);

        verify(mockDao, times(1))
                .addNew(user);
    }

    @Test
    void testFindOne() {
        String username = "user";

        userService.findOne(username);

        verify(mockDao, times(1))
                .findOne(username);
    }
}