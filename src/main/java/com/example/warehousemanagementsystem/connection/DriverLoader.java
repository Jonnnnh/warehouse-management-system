package com.example.warehousemanagementsystem.connection;

public class DriverLoader {

    public static void load(String driverClassName) {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC driver not found: " + driverClassName, e);
        }
    }
}
