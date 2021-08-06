package com.yehor.onlineshop.dao.jdbc;

import com.yehor.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");

        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }
}
