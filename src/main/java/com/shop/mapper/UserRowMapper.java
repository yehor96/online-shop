package com.shop.mapper;

import com.shop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong(1))
                .username(resultSet.getString(2))
                .password(resultSet.getString(3))
                .salt(resultSet.getString(4))
                .build();
    }
}
