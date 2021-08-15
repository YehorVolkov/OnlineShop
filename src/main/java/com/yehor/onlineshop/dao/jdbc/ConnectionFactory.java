package com.yehor.onlineshop.dao.jdbc;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionFactory implements DataSource {
    // TODO local variables?
    private final String url;
    private final String username;
    private final String password;

    public ConnectionFactory(Properties properties) { // TODO throw custom exception if one of properties is null?
        this.url = properties.getProperty("db_url");
        this.username = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection(String username, String password) {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) {

    }

    @Override
    public void setLoginTimeout(int seconds) {

    }

    @Override
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public Logger getParentLogger() {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }
}
