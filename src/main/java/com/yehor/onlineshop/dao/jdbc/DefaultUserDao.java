package com.yehor.onlineshop.dao.jdbc;

import com.yehor.onlineshop.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DefaultUserDao implements UserDao {

    public static final String CHECK_CREDENTIALS_QUERY = "SELECT * FROM Users WHERE username = '%s' AND password = '%s'";

    private final DataSource dataSource;

    public DefaultUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean credentialsValid(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format(CHECK_CREDENTIALS_QUERY, username, password))) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot validate credentials", e);
        }
    }
}
