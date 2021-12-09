package com.shop.service;

import com.shop.dao.user.UserDao;
import com.shop.entity.User;
import com.shop.helper.PasswordHelper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private static final PasswordHelper PASSWORD_HELPER = new PasswordHelper();

    private final UserDao userDao;

    public void addNew(User user) {
        String salt = PASSWORD_HELPER.generateSalt();
        String saltedPassword = user.getPassword() + salt;
        String hashedSaltedPassword = PASSWORD_HELPER.hash(saltedPassword);

        user.setPassword(hashedSaltedPassword);
        user.setSalt(salt);

        userDao.addNew(user);
    }

    public boolean isRegistered(User user) {
        return userDao.findOne(user.getUsername())
                .map(existingUser -> {
                    String password = PASSWORD_HELPER.hash(user.getPassword() + existingUser.getSalt());
                    return password.equals(existingUser.getPassword());
                }).orElse(false);
    }
}
