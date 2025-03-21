package com.example.warehousemanagementsystem.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T, ID> {
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void create(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(ID id) throws SQLException;
}

