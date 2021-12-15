package com.shop.dao.user;

import com.shop.dao.ConnectionManager;
import com.shop.dao.mapper.UserRowMapper;
import com.shop.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDao implements UserDao {

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    private static final String ADD_USER_QUERY = "INSERT INTO users (username, password, salt) VALUES (?, ?, ?);";
    private static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";

    @Override
    public void addNew(User user) {
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to add user " + user);
        }
    }

    @Override
    public Optional<User> findOne(String username) {
        User user = null;

        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME_QUERY)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = USER_ROW_MAPPER.mapRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to find user with username: " + username);
        }

        return Optional.ofNullable(user);
    }

}
