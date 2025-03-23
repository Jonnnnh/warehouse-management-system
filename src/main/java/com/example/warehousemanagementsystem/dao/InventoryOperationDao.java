package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.entity.InventoryOperation;
import com.example.warehousemanagementsystem.jdbc.Executor;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class InventoryOperationDao extends AbstractCrudDao<InventoryOperation, Long> {

    public InventoryOperationDao(Executor executor) {
        super(executor, InventoryOperation.class);
    }

    @Override
    protected String getTableName() {
        return "inventory_operations";
    }

    @Override
    public void create(InventoryOperation operation) throws SQLException {
        final String sql = "INSERT INTO inventory_operations(product_id, user_id, operation_timestamp, quantity, comment) VALUES (?, ?, ?, ?, ?)";
        executor.executeUpdate(sql,
                operation.getProductId(),
                operation.getUserId(),
                operation.getOperationTimestamp() != null ? operation.getOperationTimestamp() : OffsetDateTime.now(),
                operation.getQuantity(),
                operation.getComment());
    }

    @Override
    public void update(InventoryOperation entity) throws SQLException {
        throw new UnsupportedOperationException("Операция обновления для inventory_operations не поддерживается");
    }

}
