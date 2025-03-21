package com.example.warehousemanagementsystem.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper<T> {
    T extract(ResultSet rs) throws SQLException;
}
