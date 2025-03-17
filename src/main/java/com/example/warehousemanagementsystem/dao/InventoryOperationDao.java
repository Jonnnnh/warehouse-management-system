package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.connection.ConnectionProvider;
import com.example.warehousemanagementsystem.entity.InventoryOperation;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class InventoryOperationDao {
    private final ConnectionProvider connectionProvider;

    public InventoryOperationDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void create(InventoryOperation operation) throws SQLException {
        final String sql = "INSERT INTO inventory_operations(product_id, user_id, operation_timestamp, quantity, comment) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, operation.getProductId());
            ps.setLong(2, operation.getUserId());
            if (operation.getOperationTimestamp() != null) {
                ps.setObject(3, operation.getOperationTimestamp());
            } else {
                ps.setNull(3, Types.TIMESTAMP_WITH_TIMEZONE);
            }
            ps.setInt(4, operation.getQuantity());
            ps.setString(5, operation.getComment());

            ps.executeUpdate();
        }
    }

    public InventoryOperation findById(long id) throws SQLException {
        final String sql = "SELECT * FROM inventory_operations WHERE id = ?";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToInventoryOperation(rs);
                }
            }
        }
        return null;
    }

    public List<InventoryOperation> findAll() throws SQLException {
        final String sql = "SELECT * FROM inventory_operations ORDER BY id";
        List<InventoryOperation> list = new ArrayList<>();

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToInventoryOperation(rs));
            }
        }
        return list;
    }

    /**
     * Пример: найти все операции по конкретному товару.
     */
    public List<InventoryOperation> findByProductId(long productId) throws SQLException {
        final String sql = "SELECT * FROM inventory_operations WHERE product_id = ? ORDER BY operation_timestamp ASC";
        List<InventoryOperation> list = new ArrayList<>();

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToInventoryOperation(rs));
                }
            }
        }
        return list;
    }

    /**
     * Аналогично можно сделать findByUserId, findBetweenDates и т.п.
     */

    public void delete(long id) throws SQLException {
        final String sql = "DELETE FROM inventory_operations WHERE id = ?";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private InventoryOperation mapRowToInventoryOperation(ResultSet rs) throws SQLException {
        InventoryOperation op = new InventoryOperation();
        op.setId(rs.getLong("id"));
        op.setProductId(rs.getLong("product_id"));
        op.setUserId(rs.getLong("user_id"));
        Timestamp timestamp = rs.getTimestamp("operation_timestamp");
        if (timestamp != null) {
            op.setOperationTimestamp(timestamp.toInstant().atOffset(ZoneOffset.UTC));
        }
        op.setQuantity(rs.getInt("quantity"));
        op.setComment(rs.getString("comment"));
        return op;
    }
}
