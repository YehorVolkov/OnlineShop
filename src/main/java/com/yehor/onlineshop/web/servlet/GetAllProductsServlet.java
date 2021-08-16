package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.entity.Product;
import com.yehor.onlineshop.service.ProductService;
import com.yehor.onlineshop.web.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllProductsServlet extends HttpServlet {

    private final ProductService productService;
    private final List<String> sessionList;

    public GetAllProductsServlet(ProductService productService, List<String> sessionList) {
        this.productService = productService;
        this.sessionList = sessionList;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isValid = false;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    if (sessionList.contains(cookie.getValue())) {
                        isValid = true;
                    }
                    break;
                }
            }
        }

        if (!isValid) {
            resp.sendRedirect("/login");
        }

        Iterable<Product> dbProducts = this.productService.findAll();

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", dbProducts);

        String page = new PageGenerator().getPage("products.html", parametersMap);
        resp.getWriter().println(page);
    }
}
