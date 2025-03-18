package com.example.warehousemanagementsystem.web;

import com.example.warehousemanagementsystem.entity.InventoryOperation;
import com.example.warehousemanagementsystem.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/operation-history")
public class OperationHistoryServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String productIdParam = request.getParameter("productId");
        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");

        try {
            List<InventoryOperation> operations = operationService.getAllOperations();

            if (productIdParam != null && !productIdParam.isEmpty()) {
                long productId = Long.parseLong(productIdParam);
                operations = operations.stream()
                        .filter(op -> op.getProductId() == productId)
                        .collect(Collectors.toList());
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (startDateParam != null && !startDateParam.isEmpty()) {
                LocalDate startDate = LocalDate.parse(startDateParam, formatter);
                operations = operations.stream()
                        .filter(op -> op.getOperationTimestamp() != null &&
                                !op.getOperationTimestamp().toLocalDate().isBefore(startDate))
                        .collect(Collectors.toList());
            }

            if (endDateParam != null && !endDateParam.isEmpty()) {
                LocalDate endDate = LocalDate.parse(endDateParam, formatter);
                operations = operations.stream()
                        .filter(op -> op.getOperationTimestamp() != null &&
                                !op.getOperationTimestamp().toLocalDate().isAfter(endDate))
                        .collect(Collectors.toList());
            }

            request.setAttribute("operations", operations);
            request.getRequestDispatcher("/WEB-INF/jsp/operationHistory.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
