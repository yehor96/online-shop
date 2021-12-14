package com.shop.dao.mapper;

import com.shop.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {

    @Test
    void testMapRow() throws SQLException {
        ProductRowMapper productRowMapper = new ProductRowMapper();
        long expectedId = 5L;
        String expectedName = "test_name";
        double expectedPrice = 10.0;
        LocalDate expectedDate = LocalDate.now();

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong(1)).thenReturn(expectedId);
        when(mockResultSet.getString(2)).thenReturn(expectedName);
        when(mockResultSet.getDouble(3)).thenReturn(expectedPrice);
        when(mockResultSet.getDate(4)).thenReturn(Date.valueOf(expectedDate));

        Product actualProduct = productRowMapper.mapRow(mockResultSet);

        assertEquals(expectedId, actualProduct.getId());
        assertEquals(expectedName, actualProduct.getName());
        assertEquals(expectedPrice, actualProduct.getPrice());
        assertEquals(expectedDate, actualProduct.getCreatedDate());
    }

}