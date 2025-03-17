package com.example.warehousemanagementsystem.entity;

import java.time.OffsetDateTime;

public class InventoryOperation {
    private long id;
    private long productId;
    private long userId;
    private OffsetDateTime operationTimestamp;
    private int quantity;
    private String comment;

    public InventoryOperation() {
    }

    public InventoryOperation(long id, long productId, long userId, OffsetDateTime operationTimestamp, int quantity, String comment) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.operationTimestamp = operationTimestamp;
        this.quantity = quantity;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OffsetDateTime getOperationTimestamp() {
        return operationTimestamp;
    }

    public void setOperationTimestamp(OffsetDateTime operationTimestamp) {
        this.operationTimestamp = operationTimestamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
