package com.shop.dao.mapper;

import com.shop.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRowMapperTest {

    @Test
    void testMapRow() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();
        long expectedId = 5L;
        String expectedUsername = "user1";
        String expectedPassword = "pswd";
        String expectedSalt = "h1q7hc3";

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong(1)).thenReturn(expectedId);
        when(mockResultSet.getString(2)).thenReturn(expectedUsername);
        when(mockResultSet.getString(3)).thenReturn(expectedPassword);
        when(mockResultSet.getString(4)).thenReturn(expectedSalt);

        User actualUser = userRowMapper.mapRow(mockResultSet);

        assertEquals(expectedId, actualUser.getId());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertEquals(expectedSalt, actualUser.getSalt());
    }

}