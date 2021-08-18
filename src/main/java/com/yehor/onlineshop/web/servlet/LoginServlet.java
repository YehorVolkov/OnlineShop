package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.service.SecurityService;
import com.yehor.onlineshop.web.PageGenerator;

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
        String page = new PageGenerator().getPage("login.html");
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        if (userName.isEmpty() || password.isEmpty() || !securityService.credentialsValid(userName, password)) {
            resp.sendRedirect("/login");
        } else {
            resp.addCookie(securityService.createCookie());
            resp.sendRedirect("/products");
        }
    }
}
