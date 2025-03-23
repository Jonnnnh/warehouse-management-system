package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.entity.User;
import com.example.warehousemanagementsystem.jdbc.Executor;
import java.sql.*;

public class UserDao extends AbstractCrudDao<User, Long> {

    public UserDao(Executor executor) {
        super(executor, User.class);
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password, email, full_name, role, is_active) VALUES (?, ?, ?, ?, ?::user_role, ?)";
        executor.executeUpdate(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().getDbValue(),
                user.isActive());
    }

    @Override
    public void update(User user) throws SQLException {
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

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        return executor.executeSingleResult(sql, mapper, username);
    }
}
