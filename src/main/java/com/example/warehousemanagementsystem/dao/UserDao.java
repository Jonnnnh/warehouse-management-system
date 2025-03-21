package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.entity.User;
import com.example.warehousemanagementsystem.jdbc.Executor;
import com.example.warehousemanagementsystem.jdbc.ReflectiveResultSetMapper;

import java.sql.*;
import java.util.List;

public class UserDao {
    private final Executor executor;

    public UserDao(Executor executor) {
        this.executor = executor;
    }

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password, email, full_name, role, is_active) VALUES (?, ?, ?, ?, ?::user_role, ?)";
        executor.executeUpdate(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().getDbValue(),
                user.isActive());
    }

    public List<User> findAll() throws SQLException {
        return executor.executeQuery("SELECT * FROM users ORDER BY id", new ReflectiveResultSetMapper<>(User.class));
    }

    public User findById(long id) throws SQLException {
        return executor.executeSingleResult("SELECT * FROM users WHERE id = ?", new ReflectiveResultSetMapper<>(User.class), id);
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        return executor.executeSingleResult(sql, new ReflectiveResultSetMapper<>(User.class), username);
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, full_name = ?, role = ?::user_role, is_active = ? WHERE id = ?";
        executor.executeUpdate(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().getDbValue(),
                user.isActive(),
                user.getId());
    }
}
