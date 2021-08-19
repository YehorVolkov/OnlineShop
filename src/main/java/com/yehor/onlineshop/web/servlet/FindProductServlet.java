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

public class FindProductServlet extends HttpServlet {

    private final ProductService productService;

    public FindProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = PageGenerator.getPage("findProduct.html");
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("query");
        Iterable<Product> dbProducts = this.productService.findProduct(query);
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", dbProducts);
        String page = PageGenerator.getPage("searchResults.html", parametersMap);
        resp.getWriter().println(page);
    }
}
