package com.shop.dao.user;

import com.shop.entity.User;

import java.util.Optional;

public interface UserDao {

    void addNew(User user);

    Optional<User> findOne(String username);
}