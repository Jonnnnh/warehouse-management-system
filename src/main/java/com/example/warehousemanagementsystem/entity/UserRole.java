package com.example.warehousemanagementsystem.entity;

public enum UserRole {
    ADMIN("admin"),
    MANAGER("manager"),
    USER("user");

    private final String dbValue;

    UserRole(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static UserRole fromDbValue(String dbValue) {
        for (UserRole role : values()) {
            if (role.dbValue.equalsIgnoreCase(dbValue)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + dbValue);
    }
}
