package com.example.warehousemanagementsystem.listener;

import com.example.warehousemanagementsystem.config.DatabaseConfig;
import com.example.warehousemanagementsystem.connection.ConnectionProvider;
import com.example.warehousemanagementsystem.connection.DatabaseConnection;
import com.example.warehousemanagementsystem.connection.DriverManagerStrategy;
import com.example.warehousemanagementsystem.dao.InventoryOperationDao;
import com.example.warehousemanagementsystem.dao.ProductDao;
import com.example.warehousemanagementsystem.dao.UserDao;
import com.example.warehousemanagementsystem.jdbc.Executor;
import com.example.warehousemanagementsystem.service.OperationService;
import com.example.warehousemanagementsystem.service.ProductService;
import com.example.warehousemanagementsystem.service.UserService;

public class ApplicationContextFactory {
    public static ApplicationContext create() {
        String url = System.getenv("JDBC_URL");
        String user = System.getenv("JDBC_USER");
        String password = System.getenv("JDBC_PASSWORD");

        DatabaseConfig dbConfig = new DatabaseConfig(url, user, password);
        DriverManagerStrategy strategy = new DriverManagerStrategy();
        String driverClassName = "org.postgresql.Driver";

        ConnectionProvider connectionProvider = new DatabaseConnection(dbConfig, strategy, driverClassName);
        Executor executor = new Executor(connectionProvider);

        UserDao userDao = new UserDao(executor);
        ProductDao productDao = new ProductDao(executor);
        InventoryOperationDao opDao = new InventoryOperationDao(executor);

        UserService userService = new UserService(userDao);
        ProductService productService = new ProductService(productDao);
        OperationService operationService = new OperationService(opDao, productDao);

        return new ApplicationContext(userService, productService, operationService);
    }
}

