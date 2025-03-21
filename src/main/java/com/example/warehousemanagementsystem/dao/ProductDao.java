package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.entity.Product;
import com.example.warehousemanagementsystem.jdbc.Executor;
import com.example.warehousemanagementsystem.jdbc.ReflectiveResultSetMapper;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProductDao {
    private final Executor executor;
    private final ReflectiveResultSetMapper<Product> extractor = new ReflectiveResultSetMapper<>(Product.class);

    public ProductDao(Executor executor) {
        this.executor = executor;
    }

    public void create(Product product) throws SQLException {
        final String sql = "INSERT INTO products(name, description, price, quantity, is_active) VALUES (?, ?, ?, ?, ?)";
        executor.executeUpdate(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO,
                product.getQuantity(),
                product.isActive());
    }

    public Product findById(long id) throws SQLException {
        final String sql = "SELECT * FROM products WHERE id = ?";
        return executor.executeSingleResult(sql, extractor, id);
    }

    public List<Product> findAll() throws SQLException {
        final String sql = "SELECT * FROM products ORDER BY id";
        return executor.executeQuery(sql, extractor);
    }

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

    public void delete(long id) throws SQLException {
        final String sql = "DELETE FROM products WHERE id = ?";
        executor.executeUpdate(sql, id);
    }
}
