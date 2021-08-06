package com.yehor.onlineshop;

import com.yehor.onlineshop.dao.jdbc.ConnectionFactory;
import com.yehor.onlineshop.dao.jdbc.JdbcProductDao;
import com.yehor.onlineshop.util.CachedPropertiesReader;
import com.yehor.onlineshop.web.GetAllProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {
        // properties
/*        CachedPropertiesReader cachedPropertiesReader = new CachedPropertiesReader("application.properties");
        Properties properties = cachedPropertiesReader.getCachedProperties();*/
//
//
//        // dao
//        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
//        JdbcProductDao jdbcProductDao = new JdbcProductDao(connectionFactory);

//        // service
//        ProductService productService = new ProductService();

        // servlet
        GetAllProductsServlet getAllProductsServlet = new GetAllProductsServlet();

        // config web server
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(getAllProductsServlet), "/");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();

    }
}
