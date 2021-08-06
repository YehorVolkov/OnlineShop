package com.yehor.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private final String url;
    private final String username;
    private final String password;

    public ConnectionFactory(Properties properties) {
        this.url = properties.getProperty("db_url");
        this.username = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
