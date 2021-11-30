package com.shop.dao;

import com.shop.entity.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductDaoImpl implements ProductDao {

    private static final String PATH_TO_DB = "src/main/resources/db/";
    private static final String DB_NAME = "shop.db";

    private static final String CREATE_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS products (
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            price REAL NOT NULL,
            created_date datetime default current_timestamp
            );
             """;
    private static final String CREATE_PRODUCT_QUERY = "INSERT INTO products (name, price) VALUES (?, ?);";
    public static final String READ_ALL_PRODUCTS_QUERY = "SELECT id, name, price, created_date FROM products;";
    public static final String READ_PRODUCT_WITH_ID_QUERY = "SELECT id, name, price, created_date FROM products WHERE id = ?;";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?;";

    public JdbcProductDaoImpl() throws SQLException {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_QUERY);
        }
    }

    @Override
    public void save(Product product) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PRODUCT_QUERY)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Not able to save the product " + product);
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_PRODUCTS_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = readProductFrom(resultSet);
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Not able to read products");
        }

        return products;
    }

    @Override
    public Optional<Product> findOne(Long id) {
        Product product = null;

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_PRODUCT_WITH_ID_QUERY)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = readProductFrom(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Not able to read product with id #" + id);
        }

        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Not able to update the product " + product);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Not able to delete the product " + id);
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + PATH_TO_DB + DB_NAME);
    }

    private Product readProductFrom(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .price(resultSet.getDouble(3))
                .createdDate(resultSet.getDate(4).toLocalDate())
                .build();
    }
}
