package com.example.warehousemanagementsystem.listener;

import com.example.warehousemanagementsystem.service.OperationService;
import com.example.warehousemanagementsystem.service.ProductService;
import com.example.warehousemanagementsystem.service.UserService;

public record ApplicationContext(UserService userService, ProductService productService,
                                 OperationService operationService) {
}

