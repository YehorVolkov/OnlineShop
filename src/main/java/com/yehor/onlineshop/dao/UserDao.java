package com.yehor.onlineshop.dao;

public interface UserDao {
    boolean credentialsValid(String username, String password);
}
