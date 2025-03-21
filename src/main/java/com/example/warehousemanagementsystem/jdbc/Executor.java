package com.example.warehousemanagementsystem.jdbc;

import com.example.warehousemanagementsystem.connection.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Executor {
    private final ConnectionProvider connectionProvider;

    public Executor(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            throw e;
        }
    }

    public <T> List<T> executeQuery(String sql, ResultSetMapper<T> extractor, Object... params) throws SQLException {
        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(extractor.extract(rs));
                }
                return result;
            }
        }
    }

    public <T> T executeSingleResult(String sql, ResultSetMapper<T> extractor, Object... params) throws SQLException {
        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractor.extract(rs);
                }
                return null;
            }
        }
    }

    private void setParams(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
}
