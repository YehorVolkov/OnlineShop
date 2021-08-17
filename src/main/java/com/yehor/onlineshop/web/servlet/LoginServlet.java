package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.web.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

    private final List<String> sessionList;

    public LoginServlet(List<String> sessionList) {
        this.sessionList = sessionList;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = new PageGenerator().getPage("login.html");
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        Cookie cookie = new Cookie("user-token", uuid);
        resp.addCookie(cookie);
        resp.sendRedirect("/products");
    }
}
