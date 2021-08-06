package com.yehor.onlineshop;

import com.yehor.onlineshop.dao.ProductDao;
import com.yehor.onlineshop.dao.jdbc.ConnectionFactory;
import com.yehor.onlineshop.dao.jdbc.JdbcProductDao;
import com.yehor.onlineshop.service.ProductService;
import com.yehor.onlineshop.util.CachedPropertiesReader;
import com.yehor.onlineshop.web.AddProductServlet;
import com.yehor.onlineshop.web.EditProductServlet;
import com.yehor.onlineshop.web.GetAllProductsServlet;
import com.yehor.onlineshop.web.RemoveProductServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {
        // properties
        CachedPropertiesReader cachedPropertiesReader = new CachedPropertiesReader("application.properties");
        Properties properties = cachedPropertiesReader.getCachedProperties();

        // dao
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(connectionFactory);

        // service
        ProductService productService = new ProductService(jdbcProductDao);

        // config web server
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // servlet 1
        GetAllProductsServlet getAllProductsServlet = new GetAllProductsServlet(productService);
        ServletHolder getAllProductsServletHolder = new ServletHolder(getAllProductsServlet);
        context.addServlet(getAllProductsServletHolder, "/");
        context.addServlet(getAllProductsServletHolder, "/products");

        AddProductServlet addProductServlet = new AddProductServlet(productService);
        ServletHolder addProductServletHolder = new ServletHolder(addProductServlet);
        context.addServlet(addProductServletHolder, "/products/add");

        EditProductServlet editProductServlet = new EditProductServlet(productService);
        ServletHolder editProductServletHolder = new ServletHolder(editProductServlet);
        context.addServlet(editProductServletHolder, "/products/edit");

        RemoveProductServlet removeProductServlet = new RemoveProductServlet(productService);
        ServletHolder removeProductServletHolder = new ServletHolder(removeProductServlet);
        context.addServlet(removeProductServletHolder, "/products/remove");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();

    }
}
