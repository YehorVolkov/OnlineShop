package com.yehor.onlineshop.dao;

import com.yehor.onlineshop.entity.Product;

public interface ProductDao {
    Iterable<Product> findAll();
    void add(String name, double price, String description);
    void remove(long id);
    Product getProduct(long id);
    void updateProduct(long id, String name, double price, String description);
}
