package com.example.warehousemanagementsystem.connection;

import com.example.warehousemanagementsystem.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection implements ConnectionProvider {

    private final DatabaseConfig config;
    private final ConnectionStrategy strategy;

    public DatabaseConnection(DatabaseConfig config, ConnectionStrategy strategy, String driverClassName) {
        this.config = config;
        this.strategy = strategy;
        DriverLoader.load(driverClassName);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return strategy.createConnection(config);
    }
}
