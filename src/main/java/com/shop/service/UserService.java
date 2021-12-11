package com.shop.service;

import com.shop.dao.user.UserDao;
import com.shop.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final SecurityService securityService;

    public void addNew(User user) {
        securityService.addSalt(user);
        userDao.addNew(user);
    }

    public boolean areValidCredentials(User user) {
        return userDao.findOne(user.getUsername())
                .map(existingUser -> securityService.isPasswordValidForUser(user.getPassword(), existingUser))
                .orElse(false);
    }

    public boolean isRegistered(String username) {
        return userDao.findOne(username).isPresent();
    }
}
