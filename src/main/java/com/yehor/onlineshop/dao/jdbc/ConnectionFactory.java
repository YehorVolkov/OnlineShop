package com.yehor.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.util.Properties;

public class ConnectionFactory {
    private String url;
    private String username;
    private String password;

    public ConnectionFactory(Properties properties) {

    }

    public Connection getConnection() {
        return null; // DriverManager;
    }
}
