package com.example.warehousemanagementsystem.service;

import com.example.warehousemanagementsystem.dao.InventoryOperationDao;
import com.example.warehousemanagementsystem.dao.ProductDao;
import com.example.warehousemanagementsystem.entity.InventoryOperation;
import com.example.warehousemanagementsystem.entity.Product;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class OperationService {
    private final InventoryOperationDao operationDao;
    private final ProductDao productDao;

    public OperationService(InventoryOperationDao operationDao, ProductDao productDao) {
        this.operationDao = operationDao;
        this.productDao = productDao;
    }

    public boolean receiveProduct(long productId, long userId, int amount, String comment) throws SQLException {
        if (amount <= 0) {
            return false;
        }
        Product product = productDao.findById(productId);
        if (product == null || !product.isActive()) {
            return false;
        }
        product.setQuantity(product.getQuantity() + amount);
        productDao.update(product);
        InventoryOperation op = new InventoryOperation();
        op.setProductId(productId);
        op.setUserId(userId);
        op.setOperationTimestamp(OffsetDateTime.now());
        op.setQuantity(amount);
        op.setComment(comment);

        operationDao.create(op);

        return true;
    }

    public boolean shipProduct(long productId, long userId, int amount, String comment) throws SQLException {
        if (amount <= 0) {
            return false;
        }
        Product product = productDao.findById(productId);
        if (product == null || !product.isActive()) {
            return false;
        }
        if (product.getQuantity() < amount) {
            return false;
        }
        product.setQuantity(product.getQuantity() - amount);
        productDao.update(product);
        InventoryOperation op = new InventoryOperation();
        op.setProductId(productId);
        op.setUserId(userId);
        op.setOperationTimestamp(OffsetDateTime.now());
        op.setQuantity(-amount);
        op.setComment(comment);
        operationDao.create(op);
        return true;
    }

    public List<InventoryOperation> getAllOperations() throws SQLException {
        return operationDao.findAll();
    }
}
