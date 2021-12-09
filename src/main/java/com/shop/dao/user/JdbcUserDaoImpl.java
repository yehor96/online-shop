package com.shop.dao.user;

import com.shop.entity.User;
import com.shop.mapper.UserRowMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JdbcUserDaoImpl implements UserDao {

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private static final String PATH_TO_DB = "src/main/resources/db/";
    private static final String DB_NAME = "shop.db";

    private static final String CREATE_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY,
            username TEXT UNIQUE NOT NULL,
            password TEXT NOT NULL,
            salt TEXT NOT NULL
            );
             """;
    private static final String ADD_USER_QUERY = "INSERT INTO users (username, password, salt) VALUES (?, ?, ?);";
    private static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";

    public JdbcUserDaoImpl() throws SQLException {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_QUERY);
        }
    }

    @Override
    public void addNew(User user) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Not able to add user " + user);
        }
    }

    @Override
    public Optional<User> findOne(String username) {
        User user = null;

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME_QUERY)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = USER_ROW_MAPPER.mapRow(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Not able to find user with username: " + username);
        }

        return Optional.ofNullable(user);
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + PATH_TO_DB + DB_NAME);
    }

}
