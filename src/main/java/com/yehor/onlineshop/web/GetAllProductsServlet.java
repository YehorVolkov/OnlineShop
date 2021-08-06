package com.yehor.onlineshop.web;

import com.yehor.onlineshop.entity.Product;
import com.yehor.onlineshop.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetAllProductsServlet extends HttpServlet {

    private final ProductService productService;

    public GetAllProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Iterable<Product> dbProducts = this.productService.findAll();

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", dbProducts);

        String page = new PageGenerator().getPage("products.html", parametersMap);
        resp.getWriter().println(page);
    }
}
