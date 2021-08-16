package com.yehor.onlineshop;

import com.yehor.onlineshop.dao.ProductDao;
import com.yehor.onlineshop.dao.jdbc.ConnectionFactory;
import com.yehor.onlineshop.dao.jdbc.JdbcProductDao;
import com.yehor.onlineshop.service.ProductService;
import com.yehor.onlineshop.util.CachedPropertiesReader;
import com.yehor.onlineshop.util.PropertiesReader;
import com.yehor.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {
        PropertiesReader cachedPropertiesReader = new CachedPropertiesReader("application.properties");
        Properties properties = cachedPropertiesReader.getProperties();

//        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(properties.getProperty("db_url"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        ProductDao jdbcProductDao = new JdbcProductDao(dataSource);
        ProductService productService = new ProductService(jdbcProductDao);

        List<String> sessionList = new ArrayList<>();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ServletHolder getAllProductsServletHolder = new ServletHolder(new GetAllProductsServlet(productService, sessionList));
        context.addServlet(getAllProductsServletHolder, "/");
        context.addServlet(getAllProductsServletHolder, "/products");

        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/products/add");

        context.addServlet(new ServletHolder(new EditProductServlet(productService)), "/products/edit");

        context.addServlet(new ServletHolder(new RemoveProductServlet(productService)), "/products/remove");

        context.addServlet(new ServletHolder(new LoginServlet(sessionList)), "/login");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }
}
