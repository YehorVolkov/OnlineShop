package com.yehor.onlineshop.service;

import jakarta.xml.bind.DatatypeConverter;
import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityService {

    private final UserService userService;
    private final List<String> tokensList = new ArrayList<>();

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public String loginAndGenerateToken(String username, String password) {
        if (username == null
                || password == null
                || !userService.credentialsValid(username, hash(password))) {
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        tokensList.add(uuid);
        return uuid;
    }

    public boolean isTokenValid(String token) {
        return tokensList.contains(token);
    }

    @SneakyThrows
    private String hash(String password) {
        // TODO add salt
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
    }
}
