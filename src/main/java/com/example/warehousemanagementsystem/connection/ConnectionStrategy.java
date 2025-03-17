package com.example.warehousemanagementsystem.connection;

import com.example.warehousemanagementsystem.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionStrategy {
    Connection createConnection(DatabaseConfig config) throws SQLException;
}
