package com.yehor.onlineshop.service;

import com.yehor.onlineshop.dao.ProductDao;
import com.yehor.onlineshop.entity.Product;

public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Iterable<Product> findAll() {
        return productDao.findAll();
    }

    public void addProduct(String name, double price, String description) {
        productDao.add(name, price, description);
    }

    public Iterable<Product> findProduct(String query) {
        return productDao.findProduct(query);
    }

    public Product getProduct(long id) {
        return productDao.getProduct(id);
    }

    public void updateProduct(long id, String name, double price, String description) {
        productDao.updateProduct(id, name, price, description);
    }

    public void removeProduct(long id) {
        productDao.remove(id);
    }
}
