package com.example.warehousemanagementsystem.web;

import com.example.warehousemanagementsystem.entity.Product;
import com.example.warehousemanagementsystem.entity.User;
import com.example.warehousemanagementsystem.entity.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/product-list", "/product-create", "/product-edit"})
public class ProductServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String path = request.getServletPath();

        if ("/product-list".equals(path)) {
            try {
                List<Product> products = productService.findAll();
                request.setAttribute("products", products);
                request.getRequestDispatcher("/WEB-INF/jsp/productList.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        else if ("/product-edit".equals(path)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    long id = Long.parseLong(idParam);
                    Product product = productService.findById(id);
                    if (product == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/jsp/productEdit.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else if ("/product-create".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/jsp/productCreate.jsp").forward(request, response);
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null ||
                !(currentUser.getRole() == UserRole.MANAGER || currentUser.getRole() == UserRole.ADMIN)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Нет прав на создание/редактирование товаров");
            return;
        }

        if ("/product-create".equals(path)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceParam = request.getParameter("price");
            String quantityParam = request.getParameter("quantity");
            BigDecimal price = new BigDecimal(priceParam != null ? priceParam : "0");
            int quantity = Integer.parseInt(quantityParam != null ? quantityParam : "0");

            Product p = new Product();
            p.setName(name);
            p.setDescription(description);
            p.setPrice(price);
            p.setQuantity(quantity);
            p.setActive(true);

            try {
                productService.createProduct(p);
                response.sendRedirect(request.getContextPath() + "/product-list");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        else if ("/product-edit".equals(path)) {
            String idParam = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            try {
                Product p = productService.findById(Long.parseLong(idParam));
                if (p == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                p.setName(name);
                p.setDescription(description);
                p.setPrice(price);
                p.setQuantity(quantity);

                productService.updateProduct(p);
                response.sendRedirect(request.getContextPath() + "/product-list");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
