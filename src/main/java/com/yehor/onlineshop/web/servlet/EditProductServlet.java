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

public class EditProductServlet extends HttpServlet {

    private final ProductService productService;

    public EditProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product dbProduct = this.productService.getProduct(Long.parseLong(req.getParameter("id")));

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("product", dbProduct);

        String page = new PageGenerator().getPage("editProduct.html", parametersMap);
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String priceString = req.getParameter("price");
        if (name.isEmpty() || priceString.isEmpty()) { // TODO ok to do this logic here? Maybe better to somehow implement it in Service layer?
            resp.sendRedirect("/products/edit?id=" + req.getParameter("id"));
        } else {
            double price = Double.parseDouble(priceString.replaceAll(",", ""));
            String description = req.getParameter("description");
            this.productService.updateProduct(id, name, price, description);
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
