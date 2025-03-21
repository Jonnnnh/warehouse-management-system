package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.entity.InventoryOperation;
import com.example.warehousemanagementsystem.jdbc.Executor;
import com.example.warehousemanagementsystem.jdbc.ReflectiveResultSetMapper;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InventoryOperationDao {
    private final Executor executor;
    private final ReflectiveResultSetMapper<InventoryOperation> extractor = new ReflectiveResultSetMapper<>(InventoryOperation.class);

    public InventoryOperationDao(Executor executor) {
        this.executor = executor;
    }

    public void create(InventoryOperation operation) throws SQLException {
        final String sql = "INSERT INTO inventory_operations(product_id, user_id, operation_timestamp, quantity, comment) VALUES (?, ?, ?, ?, ?)";
        executor.executeUpdate(sql,
                operation.getProductId(),
                operation.getUserId(),
                operation.getOperationTimestamp() != null ? operation.getOperationTimestamp() : OffsetDateTime.now(),
                operation.getQuantity(),
                operation.getComment());
    }

    public InventoryOperation findById(long id) throws SQLException {
        final String sql = "SELECT * FROM inventory_operations WHERE id = ?";
        return executor.executeSingleResult(sql, extractor, id);
    }

    public List<InventoryOperation> findAll() throws SQLException {
        final String sql = "SELECT * FROM inventory_operations ORDER BY id";
        return executor.executeQuery(sql, extractor);
    }

    public List<InventoryOperation> findByProductId(long productId) throws SQLException {
        final String sql = "SELECT * FROM inventory_operations WHERE product_id = ? ORDER BY operation_timestamp ASC";
        return executor.executeQuery(sql, extractor, productId);
    }

    public void delete(long id) throws SQLException {
        final String sql = "DELETE FROM inventory_operations WHERE id = ?";
        executor.executeUpdate(sql, id);
    }
}
