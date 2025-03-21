package com.example.warehousemanagementsystem.dao;

import com.example.warehousemanagementsystem.jdbc.Executor;
import com.example.warehousemanagementsystem.jdbc.ReflectiveResultSetMapper;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractCrudDao<T, ID> implements CrudDao<T, ID> {
    protected final Executor executor;
    protected final ReflectiveResultSetMapper<T> mapper;

    public AbstractCrudDao(Executor executor, Class<T> clazz) {
        this.executor = executor;
        this.mapper = new ReflectiveResultSetMapper<>(clazz);
    }

    @Override
    public T findById(ID id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        return executor.executeSingleResult(sql, mapper, id);
    }

    @Override
    public List<T> findAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " ORDER BY id";
        return executor.executeQuery(sql, mapper);
    }

    @Override
    public abstract void create(T entity) throws SQLException;

    @Override
    public abstract void update(T entity) throws SQLException;

    @Override
    public void delete(ID id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        executor.executeUpdate(sql, id);
    }

    protected abstract String getTableName();
}

