package com.yehor.onlineshop.web;

import com.yehor.onlineshop.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllProductsServlet extends HttpServlet {

    private PageGenerator pageGenerator = PageGenerator.instance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> mockProducts = List.of(
                Product.builder().id(1L).name("Guitar").price(100).build(),
                Product.builder().id(2L).name("Phone").price(1110).build(),
                Product.builder().id(3L).name("TV").price(44).build()
        );

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", mockProducts);

        String page = pageGenerator.getPage("products.html", parametersMap);
        resp.getWriter().println(page);
    }
}
