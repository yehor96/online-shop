package com.shop.service;

import com.shop.dao.user.UserDao;
import com.shop.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void addNew(User user) {
        userDao.addNew(user);
    }

    public Optional<User> findOne(String username) {
        return userDao.findOne(username);
    }
}
