package com.yehor.onlineshop;

import com.yehor.onlineshop.dao.jdbc.JdbcUserDao;
import com.yehor.onlineshop.dao.jdbc.JdbcProductDao;
import com.yehor.onlineshop.service.ProductService;
import com.yehor.onlineshop.service.SecurityService;
import com.yehor.onlineshop.service.UserService;
import com.yehor.onlineshop.util.CachedPropertiesReader;
import com.yehor.onlineshop.util.PropertiesReader;
import com.yehor.onlineshop.web.filter.SecurityFilter;
import com.yehor.onlineshop.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {
        PropertiesReader cachedPropertiesReader = new CachedPropertiesReader("application.properties");
        Properties properties = cachedPropertiesReader.getProperties();

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));

        ProductService productService = new ProductService(new JdbcProductDao(dataSource));
        SecurityService securityService = new SecurityService(new UserService(new JdbcUserDao(dataSource)));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ServletHolder getAllProductsServletHolder = new ServletHolder(new GetAllProductsServlet(productService));
        context.addServlet(getAllProductsServletHolder, "");
        context.addServlet(getAllProductsServletHolder, "/products");

        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/products/add");

        context.addServlet(new ServletHolder(new FindProductServlet(productService)), "/products/find");

        context.addServlet(new ServletHolder(new EditProductServlet(productService)), "/products/edit");

        context.addServlet(new ServletHolder(new RemoveProductServlet(productService)), "/products/remove");

        context.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");

        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }
}
