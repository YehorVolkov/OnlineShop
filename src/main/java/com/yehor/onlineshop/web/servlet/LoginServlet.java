package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.service.SecurityService;
import com.yehor.onlineshop.web.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final SecurityService securityService;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = PageGenerator.getPage("login.html");
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = securityService.loginAndGenerateToken(req.getParameter("username"), req.getParameter("password"));
        if (token == null) {
            resp.sendRedirect("/login");
        } else {
            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(180); // TODO magic number
            resp.addCookie(cookie);
            resp.sendRedirect("/products");
        }
    }
}
