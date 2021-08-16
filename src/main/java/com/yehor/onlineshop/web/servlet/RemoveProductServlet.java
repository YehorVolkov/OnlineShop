package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProductServlet extends HttpServlet {

    private final ProductService productService;

    public RemoveProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        this.productService.removeProduct(id);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
