package com.example.warehousemanagementsystem.connection;

import com.example.warehousemanagementsystem.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerStrategy implements ConnectionStrategy {
    @Override
    public Connection createConnection(DatabaseConfig config) throws SQLException {
        return DriverManager.getConnection(config.url(), config.user(), config.password());
    }
}
