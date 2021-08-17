package com.yehor.onlineshop.dao.jdbc;

import com.yehor.onlineshop.dao.ProductDao;
import com.yehor.onlineshop.entity.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private static final String FIND_ALL_QUERY = "SELECT id, name, price, description FROM products";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, price, description) VALUES ('%s', %.2f, '%s')";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = %d";
    private static final String GET_PRODUCT_QUERY = "SELECT id, name, price, description from products WHERE id = %d";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = '%s', price = '%.2f', description = '%s' WHERE id = %d";

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
             Statement statement = connection.createStatement()) {
            String query = String.format(Locale.US, ADD_PRODUCT_QUERY, name, price, description); // TODO Locale.US - bad solution to comma in statement?
            statement.executeUpdate(query);
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
            searchQuery.append(" name LIKE '" + queryWord + "' OR ");
        }
        for (String queryWord : queryWords) {
            searchQuery.append(" description LIKE '" + queryWord + "' OR ");
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
             Statement statement = connection.createStatement()) {
            String query = String.format(DELETE_PRODUCT_QUERY, id);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot remove product from db", e);
        }
    }

    @Override
    public Product getProduct(long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format(GET_PRODUCT_QUERY, id))) {
            resultSet.next();
            return PRODUCT_ROW_MAPPER.mapRow(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get product from db", e);
        }
    }

    @Override
    public void updateProduct(long id, String name, double price, String description) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = String.format(Locale.US, UPDATE_PRODUCT_QUERY, name, price, description, id); // TODO Locale.US - bad solution to comma in statement?
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update product in db", e);
        }
    }
}
