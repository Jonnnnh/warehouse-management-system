package com.example.warehousemanagementsystem.web;

import com.example.warehousemanagementsystem.service.OperationService;
import com.example.warehousemanagementsystem.service.ProductService;
import com.example.warehousemanagementsystem.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {
    protected UserService userService;
    protected ProductService productService;
    protected OperationService operationService;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext ctx = getServletContext();
        this.userService = (UserService) ctx.getAttribute("userService");
        this.productService = (ProductService) ctx.getAttribute("productService");
        this.operationService = (OperationService) ctx.getAttribute("operationService");
    }
}

