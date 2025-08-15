package com.inventory.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.inventory.managers.UserManager;

public class LoginScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button loginButton;
    private final Label statusLabel;
    
    public LoginScreen(InventoryManagementApp app) {
        this.app = app;
        
        // Create main container
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #4a90e2, #357abd);");
        
        // Create title
        Text title = new Text("Inventory Management System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: white;");
        
        // Create subtitle
        Text subtitle = new Text("Phase 8 - Advanced Features");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-fill: white; -fx-opacity: 0.9;");
        
        // Create login form container
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(30));
        loginForm.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        loginForm.setMaxWidth(400);
        
        // Create form title
        Text formTitle = new Text("User Authentication");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        formTitle.setStyle("-fx-fill: #333;");
        
        // Create username field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-font-size: 14; -fx-background-radius: 5;");
        
        // Create password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-font-size: 14; -fx-background-radius: 5;");
        
        // Create login button
        loginButton = new Button("Login");
        loginButton.setPrefHeight(45);
        loginButton.setPrefWidth(200);
        loginButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #4a90e2; -fx-text-fill: white; -fx-background-radius: 5;");
        loginButton.setOnAction(e -> handleLogin());
        
        // Create status label
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        statusLabel.setVisible(false);
        
        // Create default credentials hint
        Text hint = new Text("Default: admin / admin123");
        hint.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        hint.setStyle("-fx-fill: #666; -fx-opacity: 0.8;");
        
        // Add components to login form
        loginForm.getChildren().addAll(
            formTitle,
            usernameLabel, usernameField,
            passwordLabel, passwordField,
            loginButton,
            statusLabel,
            hint
        );
        
        // Add components to main container
        root.getChildren().addAll(title, subtitle, loginForm);
        
        // Set up enter key handling
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> handleLogin());
        
        // Set initial focus
        usernameField.requestFocus();
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }
        
        try {
            // Create a temporary scanner for authentication
            // In a real GUI app, we'd modify the UserManager to accept direct parameters
            UserManager userManager = InventoryManagementApp.getUserManager();
            
            // For now, we'll simulate the login process
            // TODO: Modify UserManager to have a direct login method
            if (authenticateUser(username, password)) {
                // Login successful
                statusLabel.setVisible(false);
                app.showDashboard();
            } else {
                showError("Invalid username or password.");
                passwordField.clear();
                passwordField.requestFocus();
            }
            
        } catch (Exception e) {
            showError("Login error: " + e.getMessage());
        }
    }
    
    private boolean authenticateUser(String username, String password) {
        // This is a temporary authentication method
        // TODO: Integrate with UserManager's authentication
        UserManager userManager = InventoryManagementApp.getUserManager();
        
        // For now, check against default admin credentials
        if ("admin".equals(username) && "admin123".equals(password)) {
            return true;
        }
        
        // TODO: Implement proper authentication using UserManager
        return false;
    }
    
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);
    }
    
    public VBox getRoot() {
        return root;
    }
}
