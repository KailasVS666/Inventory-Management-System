package com.inventory.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.inventory.managers.InventoryManager;
import com.inventory.managers.SupplierManager;
import com.inventory.managers.OrderManager;
import com.inventory.managers.ReportManager;
import com.inventory.managers.UserManager;
import com.inventory.DataStore;

public class InventoryManagementApp extends Application {
    
    private static InventoryManager inventoryManager;
    private static SupplierManager supplierManager;
    private static OrderManager orderManager;
    private static ReportManager reportManager;
    private static UserManager userManager;
    
    private Stage primaryStage;
    private LoginScreen loginScreen;
    private DashboardScreen dashboardScreen;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        try {
            // Initialize managers
            initializeManagers();
            
            // Set up the primary stage
            primaryStage.setTitle("Inventory Management System - Phase 8");
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);
            
            // Show login screen first
            showLoginScreen();
            
            primaryStage.show();
            
        } catch (Exception e) {
            showError("Application Error", "Failed to start application", e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initializeManagers() {
        try {
            inventoryManager = new InventoryManager();
            supplierManager = new SupplierManager();
            orderManager = new OrderManager(inventoryManager);
            reportManager = new ReportManager(inventoryManager, orderManager);
            userManager = new UserManager();
            
            System.out.println("✓ All managers initialized successfully");
            
        } catch (Exception e) {
            showError("Initialization Error", "Failed to initialize managers", e.getMessage());
            throw new RuntimeException("Manager initialization failed", e);
        }
    }
    
    public void showLoginScreen() {
        // Create new instance each time to avoid VBox reuse error
        loginScreen = new LoginScreen(this);
        
        Scene scene = new Scene(loginScreen.getRoot());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
    
    public void showDashboard() {
        // Create new instance each time to avoid VBox reuse error
        dashboardScreen = new DashboardScreen(this);
        
        Scene scene = new Scene(dashboardScreen.getRoot());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
    
    public void showProductsScreen() {
        ProductsScreen productsScreen = new ProductsScreen(this);
        Scene scene = new Scene(productsScreen.getRoot());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
    
    public void showSuppliersScreen() {
        SuppliersScreen suppliersScreen = new SuppliersScreen(this);
        Scene scene = new Scene(suppliersScreen.getRoot());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
    
    public void showOrdersScreen() {
        OrdersScreen ordersScreen = new OrdersScreen(this);
        Scene scene = new Scene(ordersScreen.getRoot());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
    
    public void showReportsScreen() {
        ReportsScreen reportsScreen = new ReportsScreen(this);
        Scene scene = new Scene(reportsScreen.getRoot());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
    
    public void logout() {
        if (userManager != null) {
            userManager.logout();
        }
        showLoginScreen();
    }
    
    public void exit() {
        try {
            // Save all data before exiting
            if (inventoryManager != null) inventoryManager.saveProducts();
            if (supplierManager != null) supplierManager.saveSuppliers();
            if (orderManager != null) orderManager.saveOrders();
            if (userManager != null) userManager.saveUsers();
            
            System.out.println("✓ All data saved successfully");
            
        } catch (Exception e) {
            showError("Save Error", "Failed to save data before exit", e.getMessage());
        }
        
        System.exit(0);
    }
    
    // Static getters for managers
    public static InventoryManager getInventoryManager() { return inventoryManager; }
    public static SupplierManager getSupplierManager() { return supplierManager; }
    public static OrderManager getOrderManager() { return orderManager; }
    public static ReportManager getReportManager() { return reportManager; }
    public static UserManager getUserManager() { return userManager; }
    
    // Utility methods for showing alerts
    public static void showInfo(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void showWarning(String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void showConfirmation(String title, String header, String content, Runnable onConfirm) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                onConfirm.run();
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
