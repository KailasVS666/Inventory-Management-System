package com.inventory.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.inventory.managers.UserManager;

public class DashboardScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private final UserManager userManager;
    
    public DashboardScreen(InventoryManagementApp app) {
        this.app = app;
        this.userManager = InventoryManagementApp.getUserManager();
        
        // Create main container
        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Create header
        createHeader();
        
        // Create navigation buttons
        createNavigationButtons();
        
        // Create footer
        createFooter();
    }
    
    private void createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 30, 0));
        
        Text title = new Text("Inventory Management System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setStyle("-fx-fill: #2c3e50;");
        
        Text subtitle = new Text("Phase 8 - Advanced Features Dashboard");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-fill: #7f8c8d;");
        
        // User info
        HBox userInfo = new HBox(10);
        userInfo.setAlignment(Pos.CENTER);
        
        Label userLabel = new Label("Welcome,");
        userLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        
        Text username = new Text("Admin");
        username.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        username.setStyle("-fx-fill: #3498db;");
        
        userInfo.getChildren().addAll(userLabel, username);
        
        header.getChildren().addAll(title, subtitle, userInfo);
        root.getChildren().add(header);
    }
    
    private void createNavigationButtons() {
        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMaxWidth(600);
        
        // Create button grid
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(20);
        buttonGrid.setVgap(20);
        buttonGrid.setAlignment(Pos.CENTER);
        
        // Products Management Button
        Button productsBtn = createModuleButton("Products Management", "Manage inventory items, stock levels, and product information", "#e74c3c");
        productsBtn.setOnAction(e -> showProductsScreen());
        
        // Suppliers Management Button
        Button suppliersBtn = createModuleButton("Suppliers Management", "Manage supplier information and relationships", "#f39c12");
        suppliersBtn.setOnAction(e -> showSuppliersScreen());
        
        // Orders & Sales Button
        Button ordersBtn = createModuleButton("Orders & Sales", "Process orders, track sales, and manage transactions", "#27ae60");
        ordersBtn.setOnAction(e -> showOrdersScreen());
        
        // Reports & Analytics Button
        Button reportsBtn = createModuleButton("Reports & Analytics", "Generate reports, view analytics, and export data", "#9b59b6");
        reportsBtn.setOnAction(e -> showReportsScreen());
        
        // User Management Button (Admin only)
        Button usersBtn = createModuleButton("User Management", "Manage user accounts and permissions", "#34495e");
        usersBtn.setOnAction(e -> showUsersScreen());
        usersBtn.setVisible(userManager.getCurrentUser() != null && userManager.getCurrentUser().isAdmin());
        
        // Data Management Button (Admin only)
        Button dataBtn = createModuleButton("Data Management", "Manage data files and system settings", "#95a5a6");
        dataBtn.setOnAction(e -> showDataManagementScreen());
        dataBtn.setVisible(userManager.getCurrentUser() != null && userManager.getCurrentUser().canAccessDataManagement());
        
        // Add buttons to grid
        buttonGrid.add(productsBtn, 0, 0);
        buttonGrid.add(suppliersBtn, 1, 0);
        buttonGrid.add(ordersBtn, 0, 1);
        buttonGrid.add(reportsBtn, 1, 1);
        buttonGrid.add(usersBtn, 0, 2);
        buttonGrid.add(dataBtn, 1, 2);
        
        buttonContainer.getChildren().add(buttonGrid);
        root.getChildren().add(buttonContainer);
    }
    
    private Button createModuleButton(String title, String description, String color) {
        VBox buttonContent = new VBox(5);
        buttonContent.setAlignment(Pos.CENTER);
        buttonContent.setPadding(new Insets(15));
        
        Text titleText = new Text(title);
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleText.setStyle("-fx-fill: white;");
        
        Text descText = new Text(description);
        descText.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        descText.setStyle("-fx-fill: white; -fx-opacity: 0.9;");
        descText.setWrappingWidth(200);
        
        buttonContent.getChildren().addAll(titleText, descText);
        
        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setPrefSize(250, 120);
        button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0, 0, 0);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);"));
        
        return button;
    }
    
    private void createFooter() {
        HBox footer = new HBox(20);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(30, 0, 0, 0));
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefSize(100, 40);
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        logoutBtn.setOnAction(e -> app.logout());
        
        Button exitBtn = new Button("Exit");
        exitBtn.setPrefSize(100, 40);
        exitBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        exitBtn.setOnAction(e -> app.exit());
        
        footer.getChildren().addAll(logoutBtn, exitBtn);
        root.getChildren().add(footer);
    }
    
    // Navigation methods - these will be implemented when we create the individual screens
    private void showProductsScreen() {
        // TODO: Implement Products screen navigation
        InventoryManagementApp.showInfo("Products", "Products Management", "Products screen will be implemented next.");
    }
    
    private void showSuppliersScreen() {
        // TODO: Implement Suppliers screen navigation
        InventoryManagementApp.showInfo("Suppliers", "Suppliers Management", "Suppliers screen will be implemented next.");
    }
    
    private void showOrdersScreen() {
        // TODO: Implement Orders screen navigation
        InventoryManagementApp.showInfo("Orders", "Orders & Sales", "Orders screen will be implemented next.");
    }
    
    private void showReportsScreen() {
        // TODO: Implement Reports screen navigation
        InventoryManagementApp.showInfo("Reports", "Reports & Analytics", "Reports screen will be implemented next.");
    }
    
    private void showUsersScreen() {
        // TODO: Implement Users screen navigation
        InventoryManagementApp.showInfo("Users", "User Management", "Users screen will be implemented next.");
    }
    
    private void showDataManagementScreen() {
        // TODO: Implement Data Management screen navigation
        InventoryManagementApp.showInfo("Data", "Data Management", "Data Management screen will be implemented next.");
    }
    
    public VBox getRoot() {
        return root;
    }
}
