package com.yehor.onlineshop.dao.jdbc.mapper;

import com.yehor.onlineshop.dao.jdbc.ProductRowMapper;
import com.yehor.onlineshop.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        // prepare
        ProductRowMapper productRowMapper = new ProductRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Guitar");
        when(resultSet.getDouble("price")).thenReturn(55.55);

        // when
        Product actualProduct = productRowMapper.mapRow(resultSet);

        // then
        assertNotNull(actualProduct);
//        assertEquals(1, actualProduct.getId());
//        assertEquals("Guitar", actualProduct.getName());
//        assertEquals(55.55, actualProduct.getPrice());
    }
}
