package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.connection.ConnectionProvider;
import com.example.warehousemanagementsystem.entity.User;
import com.example.warehousemanagementsystem.entity.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final ConnectionProvider connectionProvider;

    public UserDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void createUser(User user) throws SQLException {
        final String sql = "INSERT INTO users(username, password, email, full_name, role, is_active) VALUES (?, ?, ?, ?, ?::user_role, ?)";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getRole().getDbValue());
            ps.setBoolean(6, user.isActive());

            ps.executeUpdate();
        }
    }

    public User findById(long id) throws SQLException {
        final String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }

    public User findByUsername(String username) throws SQLException {
        final String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }

    public List<User> findAll() throws SQLException {
        final String sql = "SELECT * FROM users ORDER BY id";
        List<User> result = new ArrayList<>();

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRowToUser(rs));
            }
        }
        return result;
    }

    public void updateUser(User user) throws SQLException {
        final String sql = "UPDATE users SET username = ?, password = ?, email = ?, full_name = ?, role = ?::user_role, is_active = ? WHERE id = ?";

        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getRole().getDbValue());
            ps.setBoolean(6, user.isActive());
            ps.setLong(7, user.getId());
            ps.executeUpdate();
        }
    }

    public void deleteUser(long id) throws SQLException {
        final String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("full_name"));
        user.setActive(rs.getBoolean("is_active"));
        String roleString = rs.getString("role");
        user.setRole(UserRole.fromDbValue(roleString));
        return user;
    }
}
