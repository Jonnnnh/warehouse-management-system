package com.example.warehousemanagementsystem.service;

import com.example.warehousemanagementsystem.dao.ProductDao;
import com.example.warehousemanagementsystem.entity.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void createProduct(Product product) throws SQLException {
        productDao.create(product);
    }

    public Product findById(long id) throws SQLException {
        return productDao.findById(id);
    }

    public List<Product> findAll() throws SQLException {
        return productDao.findAll();
    }

    public void updateProduct(Product product) throws SQLException {
        productDao.update(product);
    }

    public void deleteProduct(long id) throws SQLException {
        productDao.delete(id);
    }
}
