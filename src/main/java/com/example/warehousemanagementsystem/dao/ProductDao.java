package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.connection.ConnectionProvider;
import com.example.warehousemanagementsystem.entity.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private final ConnectionProvider connectionProvider;

    public ProductDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void create(Product product) throws SQLException {
        final String sql = "INSERT INTO products(name, description, price, quantity, is_active) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO);
            ps.setInt(4, product.getQuantity());
            ps.setBoolean(5, product.isActive());
            ps.executeUpdate();
        }
    }

    public Product findById(long id) throws SQLException {
        final String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }
        }
        return null;
    }

    public List<Product> findAll() throws SQLException {
        final String sql = "SELECT * FROM products ORDER BY id";
        List<Product> products = new ArrayList<>();

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        }
        return products;
    }

    public void update(Product product) throws SQLException {
        final String sql = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ?, is_active = ? WHERE id = ?";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO);
            ps.setInt(4, product.getQuantity());
            ps.setBoolean(5, product.isActive());
            ps.setLong(6, product.getId());
            ps.executeUpdate();
        }
    }


    public void delete(long id) throws SQLException {
        final String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setActive(rs.getBoolean("is_active"));
        return product;
    }
}
