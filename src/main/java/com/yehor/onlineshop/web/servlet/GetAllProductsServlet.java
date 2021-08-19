package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.entity.Product;
import com.yehor.onlineshop.service.ProductService;
import com.yehor.onlineshop.web.PageGenerator;

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
        Map<String, Object> parametersMap = new HashMap<>();
        Iterable<Product> dbProducts = this.productService.findAll();
        parametersMap.put("products", dbProducts);

        String page = PageGenerator.getPage("products.html", parametersMap);
        resp.getWriter().println(page);
    }
}
