package com.shop.dao.product;

import com.shop.dao.ConnectionManager;
import com.shop.dao.mapper.ProductRowMapper;
import com.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductDao implements ProductDao {

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private static final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();

    private static final String CREATE_PRODUCT_QUERY = "INSERT INTO products (name, price) VALUES (?, ?);";
    public static final String READ_ALL_PRODUCTS_QUERY = "SELECT id, name, price, created_date FROM products;";
    public static final String READ_PRODUCT_WITH_ID_QUERY = "SELECT id, name, price, created_date FROM products WHERE id = ?;";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?;";

    @Override
    public void save(Product product) {
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PRODUCT_QUERY)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to save the product " + product);
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_PRODUCTS_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to read products");
        }

        return products;
    }

    @Override
    public Optional<Product> findOne(Long id) {
        Product product = null;

        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_PRODUCT_WITH_ID_QUERY)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to read product with id #" + id);
        }

        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) {
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to update the product " + product);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = CONNECTION_MANAGER.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to delete the product " + id);
        }
    }
}
