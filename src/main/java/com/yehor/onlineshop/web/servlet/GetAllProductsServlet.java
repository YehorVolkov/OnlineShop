package com.yehor.onlineshop.web.servlet;

import com.yehor.onlineshop.entity.Product;
import com.yehor.onlineshop.service.ProductService;
import com.yehor.onlineshop.service.SecurityService;
import com.yehor.onlineshop.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetAllProductsServlet extends HttpServlet {

    private final ProductService productService;
    private final SecurityService securityService;

    public GetAllProductsServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!securityService.cookieExists(req.getCookies())) {
            resp.sendRedirect("/login");
        } else {
            Iterable<Product> dbProducts = this.productService.findAll();

            Map<String, Object> parametersMap = new HashMap<>();
            parametersMap.put("products", dbProducts);

            String page = new PageGenerator().getPage("products.html", parametersMap);
            resp.getWriter().println(page);
        }
    }
}
