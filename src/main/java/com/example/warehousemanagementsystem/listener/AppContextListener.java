package com.example.warehousemanagementsystem.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        try {
            ApplicationContext appContext = ApplicationContextFactory.create();
            ctx.setAttribute("userService", appContext.userService());
            ctx.setAttribute("productService", appContext.productService());
            ctx.setAttribute("operationService", appContext.operationService());
            ctx.log("AppContextListener initialization successful");
        } catch (Exception ex) {
            ctx.log("Error during AppContextListener initialization", ex);
            throw new RuntimeException("Listener initialization failed", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
