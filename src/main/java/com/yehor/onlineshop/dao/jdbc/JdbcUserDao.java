package com.yehor.onlineshop.dao.jdbc;

import com.yehor.onlineshop.dao.UserDao;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcUserDao implements UserDao {

    private static final String CHECK_CREDENTIALS_QUERY = "SELECT * FROM Users WHERE username = ? AND password = ?";

    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean credentialsValid(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_CREDENTIALS_QUERY)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            } catch (SQLException e) { // TODO do we need nested catch in nested try-with-resources?
                throw new RuntimeException("Cannot validate credentials", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot validate credentials", e);
        }
    }
}
