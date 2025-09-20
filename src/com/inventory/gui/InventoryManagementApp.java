package com.inventory.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.inventory.managers.InventoryManager;
import com.inventory.managers.SupplierManager;
import com.inventory.managers.OrderManager;
import com.inventory.managers.ReportManager;
import com.inventory.managers.UserManager;
import com.inventory.DataStore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class InventoryManagementApp extends Application {
    
    private static InventoryManager inventoryManager;
    private static SupplierManager supplierManager;
    private static OrderManager orderManager;
    private static ReportManager reportManager;
    private static UserManager userManager;
    
    private Stage primaryStage;
    private LoginScreen loginScreen;
    private DashboardScreen dashboardScreen;
    
    private static String currentTheme = "light"; // Always start in light mode

    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static void setTheme(String theme) {
        currentTheme = theme;
        // Theme will be applied by each screen when they refresh
    }
    
    private void applyTheme(Scene scene) {
        // No-op: styling is now handled directly in each screen's Java code
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            // Initialize managers
            initializeManagers();
            // Set up the primary stage
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);

            // Create a single Scene that will be used for the entire application
            Scene mainScene = new Scene(new VBox(), 1200, 800);
            primaryStage.setScene(mainScene);
            
            // Set up window state change listener
            primaryStage.maximizedProperty().addListener((obs, wasMaximized, isMaximized) -> {
                if (!isMaximized) {
                    // If user manually restores down, allow it but suggest fullscreen
                    primaryStage.setMaximized(true);
                }
            });
            
            // Show login screen first
            showLoginScreen();
            
            primaryStage.setMaximized(true); // Always start maximized
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
        loginScreen = new LoginScreen(this);
        primaryStage.getScene().setRoot(loginScreen.getRoot());
    }
    
    public void showDashboard() {
        dashboardScreen = new DashboardScreen(this);
        primaryStage.getScene().setRoot(dashboardScreen.getRoot());
    }
    
    public void showProductsScreen() {
        ProductsScreen productsScreen = new ProductsScreen(this);
        primaryStage.getScene().setRoot(productsScreen.getRoot());
    }
    
    public void showSuppliersScreen() {
        SuppliersScreen suppliersScreen = new SuppliersScreen(this);
        primaryStage.getScene().setRoot(suppliersScreen.getRoot());
    }
    
    public void showOrdersScreen() {
        OrdersScreen ordersScreen = new OrdersScreen(this);
        primaryStage.getScene().setRoot(ordersScreen.getRoot());
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public void showReportsScreen() {
        ReportsScreen reportsScreen = new ReportsScreen(this);
        primaryStage.getScene().setRoot(reportsScreen.getRoot());
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