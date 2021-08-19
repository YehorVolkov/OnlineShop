package com.yehor.onlineshop.service;

import com.yehor.onlineshop.dao.UserDao;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean credentialsValid(String username, String hashedPassword) {
        return userDao.credentialsValid(username, hashedPassword);
    }
}
