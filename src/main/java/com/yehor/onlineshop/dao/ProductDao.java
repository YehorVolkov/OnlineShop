package com.yehor.onlineshop.dao;

import com.yehor.onlineshop.entity.Product;

public interface ProductDao {
    Iterable<Product> findAll();
}
