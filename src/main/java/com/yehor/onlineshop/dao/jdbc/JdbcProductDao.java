package com.yehor.onlineshop.dao.jdbc;

import com.yehor.onlineshop.dao.ProductDao;
import com.yehor.onlineshop.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private static final String FIND_ALL_QUERY = "SELECT id, name, price, description FROM products";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, price, description) VALUES (?, ?, ?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String GET_PRODUCT_QUERY = "SELECT id, name, price, description from products WHERE id = ?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = ?, price = ?, description = ? WHERE id = ?";

    private final DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get products from db", e);
        }
    }

    @Override
    public void add(String name, double price, String description) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_QUERY)) {
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setString(3, description);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot insert product to db", e);
        }
    }

    @Override
    public Iterable<Product> findProduct(String query) {
        String[] queryWords = query.split(" ");

        // TODO BAD DESIGN!!!
        StringBuilder searchQuery = new StringBuilder("SELECT id, name, price, description FROM products WHERE");

        for (String queryWord : queryWords) {
            searchQuery.append(" name LIKE '").append(queryWord).append("' OR ");
        }
        for (String queryWord : queryWords) {
            searchQuery.append(" description LIKE '").append(queryWord).append("' OR ");
        }
        searchQuery.delete(searchQuery.length() - 4, searchQuery.length());

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(searchQuery.toString())) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get products from db", e);
        }
    }

    @Override
    public void remove(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot remove product from db", e);
        }
    }

    @Override
    public Product getProduct(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return PRODUCT_ROW_MAPPER.mapRow(resultSet);
            } catch (SQLException e) { // TODO do we need nested catch in nested try-with-resources?
                throw new RuntimeException("Cannot get product from db", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get product from db", e);
        }
    }

    @Override
    public void updateProduct(long id, String name, double price, String description) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setString(3, description);
            statement.setLong(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update product in db", e);
        }
    }
}
