package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.entity.Product;
import com.example.warehousemanagementsystem.jdbc.Executor;
import com.example.warehousemanagementsystem.jdbc.ReflectiveResultSetMapper;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProductDao extends AbstractCrudDao<Product, Long> {
    private final ReflectiveResultSetMapper<Product> extractor = new ReflectiveResultSetMapper<>(Product.class);

    public ProductDao(Executor executor) {
        super(executor, Product.class);
    }

    @Override
    protected String getTableName() {
        return "products";
    }

    @Override
    public void create(Product product) throws SQLException {
        final String sql = "INSERT INTO products(name, description, price, quantity, is_active) VALUES (?, ?, ?, ?, ?)";
        executor.executeUpdate(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO,
                product.getQuantity(),
                product.isActive());
    }

    @Override
    public void update(Product product) throws SQLException {
        final String sql = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ?, is_active = ? WHERE id = ?";
        executor.executeUpdate(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO,
                product.getQuantity(),
                product.isActive(),
                product.getId());
    }
}
