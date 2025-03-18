package com.example.warehousemanagementsystem.web;

import com.example.warehousemanagementsystem.entity.User;
import com.example.warehousemanagementsystem.entity.UserRole;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/operation")
public class OperationServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null ||
                !(currentUser.getRole() == UserRole.MANAGER || currentUser.getRole() == UserRole.ADMIN)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Нет прав");
            return;
        }

        String action = request.getParameter("action");
        long productId = Long.parseLong(request.getParameter("productId"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        String comment = request.getParameter("comment");

        try {
            boolean success;
            if ("receive".equals(action)) {
                success = operationService.receiveProduct(productId, currentUser.getId(), amount, comment);
            } else if ("ship".equals(action)) {
                success = operationService.shipProduct(productId, currentUser.getId(), amount, comment);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неподдерживаемое действие");
                return;
            }

            if (!success) {
                request.setAttribute("error", "Операция не выполнена (возможно, недостаточно товара или неверное количество)");
            }
            response.sendRedirect(request.getContextPath() + "/product-list");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
