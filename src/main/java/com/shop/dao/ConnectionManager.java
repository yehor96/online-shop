package com.shop.dao;

import com.shop.helper.ClasspathReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String DB_NAME = "/shop.db";

    public Connection getConnection() throws SQLException {
        String dbPath = ClasspathReader.getFilePath("/db");
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath + DB_NAME);
    }

}
