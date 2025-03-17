package com.example.warehousemanagementsystem.listener;

import com.example.warehousemanagementsystem.config.DatabaseConfig;
import com.example.warehousemanagementsystem.connection.ConnectionProvider;
import com.example.warehousemanagementsystem.connection.DatabaseConnection;
import com.example.warehousemanagementsystem.connection.DriverManagerStrategy;
import com.example.warehousemanagementsystem.dao.InventoryOperationDao;
import com.example.warehousemanagementsystem.dao.ProductDao;
import com.example.warehousemanagementsystem.dao.UserDao;
import com.example.warehousemanagementsystem.service.OperationService;
import com.example.warehousemanagementsystem.service.ProductService;
import com.example.warehousemanagementsystem.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        String url = System.getenv("JDBC_URL");
        String user = System.getenv("JDBC_USER");
        String password = System.getenv("JDBC_PASSWORD");

        try {
            ctx.log("Initializing AppContextListener with JDBC_URL: " + url);
            DatabaseConfig dbConfig = new DatabaseConfig(url, user, password);
            DriverManagerStrategy strategy = new DriverManagerStrategy();
            String driverClassName = "org.postgresql.Driver";
            ConnectionProvider connectionProvider = new DatabaseConnection(dbConfig, strategy, driverClassName);

            UserDao userDao = new UserDao(connectionProvider);
            ProductDao productDao = new ProductDao(connectionProvider);
            InventoryOperationDao opDao = new InventoryOperationDao(connectionProvider);

            UserService userService = new UserService(userDao);
            ProductService productService = new ProductService(productDao);
            OperationService operationService = new OperationService(opDao, productDao);

            ctx.setAttribute("userService", userService);
            ctx.setAttribute("productService", productService);
            ctx.setAttribute("operationService", operationService);
            ctx.log("AppContextListener initialization successful");
        } catch (Exception ex) {
            ctx.log("Error during AppContextListener initialization", ex);
            throw new RuntimeException("Listener initialization failed", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
