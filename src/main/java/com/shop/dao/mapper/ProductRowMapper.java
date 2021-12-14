package com.shop.dao.mapper;

import com.shop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {

    public Product mapRow(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .price(resultSet.getDouble(3))
                .createdDate(resultSet.getDate(4).toLocalDate())
                .build();
    }

}
