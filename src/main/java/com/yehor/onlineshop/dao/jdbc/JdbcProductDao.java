package com.yehor.onlineshop.dao.jdbc;

import com.yehor.onlineshop.dao.ProductDao;
import com.yehor.onlineshop.entity.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private static final String FIND_ALL_QUERY = "SELECT id, name, price from products";

    private ConnectionFactory connectionFactory;

    public JdbcProductDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Iterable<Product> findAll() {
        try (Connection connection = connectionFactory.getConnection();
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
}
