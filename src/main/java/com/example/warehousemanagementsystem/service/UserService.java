package com.example.warehousemanagementsystem.service;

import com.example.warehousemanagementsystem.dao.UserDao;
import com.example.warehousemanagementsystem.entity.User;
import com.example.warehousemanagementsystem.entity.UserRole;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean register(String username, String rawPassword, String email, String fullName) throws SQLException {
        User existing = userDao.findByUsername(username);
        if (existing != null) {
            return false;
        }
        String hashedPassword = hashPassword(rawPassword);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole(UserRole.USER);
        user.setActive(true);

        userDao.create(user);
        return true;
    }

    public User login(String username, String rawPassword) throws SQLException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (checkPassword(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean changeUserRole(long userId, UserRole newRole) throws SQLException {
        User user = userDao.findById(userId);
        if (user == null) {
            return false;
        }
        user.setRole(newRole);
        userDao.update(user);
        return true;
    }

    private String hashPassword(String rawPassword) {
        return rawPassword;
    }

    private boolean checkPassword(String rawPassword, String hashed) {
        return rawPassword.equals(hashed);
    }
}
