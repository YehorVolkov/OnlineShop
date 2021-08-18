package com.yehor.onlineshop.service;

import com.yehor.onlineshop.dao.UserDao;
import jakarta.xml.bind.DatatypeConverter;
import lombok.SneakyThrows;

import javax.servlet.http.Cookie;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityService {

    private final UserDao userDao;
    private final List<String> sessionList = new ArrayList<>();

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    @SneakyThrows
    public boolean credentialsValid(String username, String password) {
        // TODO should this logic be here?
        MessageDigest md = MessageDigest.getInstance("MD5"); // TODO SneakyThrows?
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hashedPassword = DatatypeConverter.printHexBinary(digest).toLowerCase(); // TODO toLowerCase bad or don't mind?
        return userDao.credentialsValid(username, hashedPassword);
    }

    public Cookie createCookie() {
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        Cookie cookie = new Cookie("user-token", uuid);
        cookie.setMaxAge(180); // TODO magic number?
        return cookie;
    }

    public boolean cookieExists(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    if (sessionList.contains(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
