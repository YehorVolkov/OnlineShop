package com.yehor.onlineshop.dao.jdbc;

import com.yehor.onlineshop.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JdbcProductDaoITest {
    @Test
    public void testDaoReturnListOfProducts() {
        // prepare
        ConnectionFactory connectionFactory = null;
        JdbcProductDao jdbcProductDao = new JdbcProductDao(connectionFactory);

        // when
        Iterable<Product> products = jdbcProductDao.findAll();

        //then
        assertNotNull(products);
        for (Product product : products) {
//            assertNotNull(product.getName());
//            assertNotNull(product.getId());
        }
    }

}