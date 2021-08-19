package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.service.ProductService;
import com.yehor.onlineshop.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {

    private final ProductService productService;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = PageGenerator.getPage("addProduct.html");
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String priceString = req.getParameter("price");

        if (name.isEmpty() || priceString.isEmpty()) { // TODO ok to do this logic here? Maybe better to somehow implement it in Service layer?
            resp.sendRedirect("/products/add");
        } else {
            double price = Double.parseDouble(priceString.replaceAll(",", ""));
            String description = req.getParameter("description");
            this.productService.addProduct(name, price, description);
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}

