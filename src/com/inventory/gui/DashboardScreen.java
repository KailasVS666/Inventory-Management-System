package com.inventory.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.inventory.managers.UserManager;
import com.inventory.models.User;
import javafx.scene.layout.Priority;

public class DashboardScreen {
    
    private InventoryManagementApp app;
    private VBox root;
    private ScrollPane scrollPane;
    private ToggleButton themeToggle;
    private HBox header;
    private String username;
    private Text title;
    private Label welcomeLabel;
    
    public DashboardScreen(InventoryManagementApp app) {
        this.app = app;
        
        // Get username from current user
        User currentUser = InventoryManagementApp.getUserManager().getCurrentUser();
        this.username = currentUser != null ? currentUser.getUsername() : "User";
        
        // Create root layout with beautiful dark background
        root = new VBox(25);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e 0%, #16213e 100%); -fx-background-radius: 0;");
        
        // Initialize UI components
        title = new Text("🚀 Dashboard");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        welcomeLabel.setStyle("-fx-text-fill: #e8e8e8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 2, 0, 0, 0);");
        
        themeToggle = new ToggleButton("🌙 Dark Mode");
        themeToggle.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50 0%, #34495e 100%); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0); -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 25;");
        
        Button logoutButton = new Button("🚪 Logout");
        logoutButton.setStyle("-fx-background-color: rgba(231, 76, 60, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #c0392b; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 10 20; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        
        // Create header with darker theme
        header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50 0%, #34495e 100%); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0); -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 15;");
        
        VBox titleBox = new VBox(8);
        titleBox.getChildren().addAll(title, welcomeLabel);
        
        HBox rightSection = new HBox(15);
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.getChildren().addAll(themeToggle, logoutButton);
        
        header.getChildren().addAll(titleBox, rightSection);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        
        VBox.setMargin(header, new Insets(0, 0, 30, 0));
        root.getChildren().add(header);
        
        // Add theme toggle functionality
        themeToggle.setOnAction(e -> {
            if (themeToggle.isSelected()) {
                themeToggle.setText("☀️ Light Mode");
                themeToggle.setStyle("-fx-background-color: linear-gradient(to right, #f39c12 0%, #e67e22 100%); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0); -fx-border-color: #f39c12; -fx-border-width: 2; -fx-border-radius: 25;");
                InventoryManagementApp.setTheme("dark");
            } else {
                themeToggle.setText("🌙 Dark Mode");
                themeToggle.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50 0%, #34495e 100%); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0); -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 25;");
                InventoryManagementApp.setTheme("light");
            }
            applyCurrentTheme();
        });
        
        // Add logout functionality
        logoutButton.setOnAction(e -> {
            InventoryManagementApp.showConfirmation("Logout", "Confirm Logout", "Are you sure you want to logout?", () -> {
                app.showLoginScreen();
            });
        });
        
        // Add hover effects
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: rgba(231, 76, 60, 1.0); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #c0392b; -fx-border-width: 3; -fx-border-radius: 20; -fx-padding: 10 20; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0, 0, 0);"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: rgba(231, 76, 60, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #c0392b; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 10 20; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);"));
        
        themeToggle.setOnMouseEntered(e -> themeToggle.setStyle("-fx-background-color: linear-gradient(to right, #34495e 0%, #2c3e50 100%); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0, 0, 0); -fx-border-color: #4a90e2; -fx-border-width: 3; -fx-border-radius: 25;"));
        themeToggle.setOnMouseExited(e -> themeToggle.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50 0%, #34495e 100%); -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0); -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 25;"));
        
        // Create dashboard content
        createDashboardContent();
        
        // Add additional content to test scrolling
        addAdditionalContent();
        
        // Apply initial theme
        applyCurrentTheme();
        
        // Create scrollable container
        scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(800);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        // Improve scroll pane behavior
        scrollPane.setPannable(true);
        scrollPane.setMinViewportHeight(400);
        scrollPane.setMinViewportWidth(600);
    }
    
    private void createHeader() {
        // Header with title and user info
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: linear-gradient(to right, #667eea 0%, #764ba2 100%); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        
        title = new Text("🚀 Dashboard");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        // Add user info on the right
        HBox userBox = new HBox(15);
        userBox.setAlignment(Pos.CENTER_RIGHT);
        
        // Check if user is logged in before accessing user info
        User currentUser = InventoryManagementApp.getUserManager().getCurrentUser();
        if (currentUser != null) {
            welcomeLabel = new Label("👋 Welcome, " + currentUser.getUsername() + "!");
        } else {
            welcomeLabel = new Label("👋 Welcome, User!");
        }
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 2, 0, 0, 0);");
        
        Button logoutButton = new Button("🚪 Logout");
        logoutButton.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 10 20;");
        logoutButton.setOnAction(e -> app.logout());
        
        // Hover effect for logout button
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 10 20;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 10 20;"));
        
        userBox.getChildren().addAll(welcomeLabel, logoutButton);
        
        header.getChildren().addAll(title, userBox);
        HBox.setHgrow(title, Priority.ALWAYS);
        VBox.setMargin(header, new Insets(0, 0, 30, 0));
        root.getChildren().add(header);
    }
    
    private void createDashboardContent() {
        // Create dashboard cards with darker, professional colors
        Button productsBtn = createDashboardButton("📦 Products", "Manage your inventory", "#2c3e50", "#34495e");
        productsBtn.setOnAction(e -> app.showProductsScreen());
        
        Button suppliersBtn = createDashboardButton("🏢 Suppliers", "Manage suppliers", "#34495e", "#2c3e50");
        suppliersBtn.setOnAction(e -> app.showSuppliersScreen());
        
        Button ordersBtn = createDashboardButton("📋 Orders", "Manage orders", "#2c3e50", "#34495e");
        ordersBtn.setOnAction(e -> app.showOrdersScreen());
        
        Button reportsBtn = createDashboardButton("📊 Reports", "View reports", "#34495e", "#2c3e50");
        reportsBtn.setOnAction(e -> app.showReportsScreen());
        
        // Create grid layout for cards
        GridPane cardGrid = new GridPane();
        cardGrid.setHgap(30);
        cardGrid.setVgap(30);
        cardGrid.setAlignment(Pos.CENTER);
        cardGrid.setPadding(new Insets(30));
        
        // Add cards to grid
        cardGrid.add(productsBtn, 0, 0);
        cardGrid.add(suppliersBtn, 1, 0);
        cardGrid.add(ordersBtn, 0, 1);
        cardGrid.add(reportsBtn, 1, 1);
        
        // Add cards section with darker theme
        VBox cardsSection = new VBox(20);
        cardsSection.setPadding(new Insets(25));
        cardsSection.setStyle("-fx-background-color: linear-gradient(135deg, #2c3e50 0%, #34495e 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label cardsTitle = new Label("🎯 Quick Access");
        cardsTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        cardsSection.getChildren().addAll(cardsTitle, cardGrid);
        root.getChildren().add(cardsSection);
    }
    
    private void addAdditionalContent() {
        // System Information Section
        VBox additionalSection = new VBox(20);
        additionalSection.setPadding(new Insets(25));
        additionalSection.setStyle("-fx-background-color: linear-gradient(135deg, #34495e 0%, #2c3e50 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label additionalTitle = new Label("ℹ️ System Information");
        additionalTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(20));
        
        // Add system info labels with better visibility
        infoGrid.add(createInfoLabel("🖥️ System:", "Windows 10"), 0, 0);
        infoGrid.add(createInfoLabel("💾 Memory:", "8GB RAM"), 1, 0);
        infoGrid.add(createInfoLabel("💿 Storage:", "500GB SSD"), 0, 1);
        infoGrid.add(createInfoLabel("🌐 Network:", "Connected"), 1, 1);
        
        additionalSection.getChildren().addAll(additionalTitle, infoGrid);
        root.getChildren().add(additionalSection);
        
        // Quick Actions Section
        VBox quickActions = new VBox(20);
        quickActions.setPadding(new Insets(25));
        quickActions.setStyle("-fx-background-color: linear-gradient(135deg, #2c3e50 0%, #34495e 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label quickTitle = new Label("⚡ Quick Actions");
        quickTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        HBox actionButtons = new HBox(20);
        actionButtons.setAlignment(Pos.CENTER);
        
        Button refreshBtn = new Button("🔄 Refresh Dashboard");
        refreshBtn.setStyle("-fx-background-color: rgba(74, 144, 226, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        refreshBtn.setOnAction(e -> refreshDashboard());
        
        Button settingsBtn = new Button("⚙️ Settings");
        settingsBtn.setStyle("-fx-background-color: rgba(52, 73, 94, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #34495e; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        settingsBtn.setOnAction(e -> showSettings());
        
        // Add hover effects
        addButtonHoverEffects(refreshBtn);
        addButtonHoverEffects(settingsBtn);
        
        actionButtons.getChildren().addAll(refreshBtn, settingsBtn);
        quickActions.getChildren().addAll(quickTitle, actionButtons);
        root.getChildren().add(quickActions);
        
        // Navigation Section
        VBox navigationSection = new VBox(20);
        navigationSection.setPadding(new Insets(25));
        navigationSection.setStyle("-fx-background-color: linear-gradient(135deg, #34495e 0%, #2c3e50 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label navTitle = new Label("🧭 Navigation");
        navTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        HBox navButtons = new HBox(20);
        navButtons.setAlignment(Pos.CENTER);
        
        Button helpBtn = new Button("❓ Help");
        helpBtn.setStyle("-fx-background-color: rgba(155, 89, 182, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #9b59b6; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        helpBtn.setOnAction(e -> showHelp());
        
        Button aboutBtn = new Button("ℹ️ About");
        aboutBtn.setStyle("-fx-background-color: rgba(46, 204, 113, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #2ecc71; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        aboutBtn.setOnAction(e -> showAbout());
        
        // Add hover effects
        addButtonHoverEffects(helpBtn);
        addButtonHoverEffects(aboutBtn);
        
        navButtons.getChildren().addAll(helpBtn, aboutBtn);
        navigationSection.getChildren().addAll(navTitle, navButtons);
        root.getChildren().add(navigationSection);
    }
    
    private Label createInfoLabel(String title, String value) {
        Label label = new Label(title + " " + value);
        label.setStyle("-fx-text-fill: #e8e8e8; -fx-font-size: 16px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 2, 0, 0, 0);");
        return label;
    }
    
    private void addButtonHoverEffects(Button button) {
        button.setOnMouseEntered(e -> {
            button.setStyle(button.getStyle().replace("0.8", "1.0").replace("2", "3"));
        });
        button.setOnMouseExited(e -> {
            button.setStyle(button.getStyle().replace("1.0", "0.8").replace("3", "2"));
        });
    }
    
    private void refreshDashboard() {
        // Refresh dashboard data
        InventoryManagementApp.showInfo("Dashboard", "Refresh", "Dashboard refreshed successfully!");
    }
    
    private Button createDashboardButton(String title, String description, String color1, String color2) {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setStyle(
            "-fx-background-color: linear-gradient(135deg, " + color1 + " 0%, " + color2 + " 100%);" +
            "-fx-background-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 0);" +
            "-fx-border-color: rgba(255,255,255,0.2);" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 20;"
        );
        box.setMinSize(200, 150);
        box.setMaxSize(250, 180);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 3, 0, 0, 0);");
        
        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: #f0f0f0; -fx-wrap-text: true; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 0, 0, 0);");
        
        box.getChildren().addAll(titleLabel, descLabel);
        
        Button button = new Button();
        button.setGraphic(box);
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-padding: 0;" +
            "-fx-background-radius: 20;"
        );
        
        // Enhanced hover effects with scaling and glow
        box.setOnMouseEntered(e -> {
            box.setStyle(
                "-fx-background-color: linear-gradient(135deg, " + color1 + " 30%, " + color2 + " 100%);" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 20, 0, 0, 0);" +
                "-fx-border-color: rgba(255,255,255,0.4);" +
                "-fx-border-width: 3;" +
                "-fx-border-radius: 20;" +
                "-fx-scale-x: 1.05;" +
                "-fx-scale-y: 1.05;"
            );
            box.setTranslateZ(10);
        });
        
        box.setOnMouseExited(e -> {
            box.setStyle(
                "-fx-background-color: linear-gradient(135deg, " + color1 + " 0%, " + color2 + " 100%);" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 0);" +
                "-fx-border-color: rgba(255,255,255,0.2);" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 20;"
            );
            box.setScaleX(1.0);
            box.setScaleY(1.0);
            box.setTranslateZ(0);
        });
        
        return button;
    }
    
    public VBox getRoot() {
        // Return a container with the scroll pane
        VBox container = new VBox();
        container.getChildren().add(scrollPane);
        return container;
    }
    
    private void showSettings() {
        InventoryManagementApp.showInfo("Settings", "Settings", "Settings functionality coming soon!");
    }
    
    private void showHelp() {
        InventoryManagementApp.showInfo("Help", "Help", "Help documentation coming soon!");
    }
    
    private void showAbout() {
        InventoryManagementApp.showInfo("About", "About", "Inventory Management System v1.0\nDeveloped with JavaFX");
    }
    
    public void applyCurrentTheme() {
        String theme = InventoryManagementApp.getCurrentTheme();
        boolean isDark = "dark".equals(theme);
        String bg = isDark ? "#10142b" : "#f5f7fa";
        String card = isDark ? "#22304a" : "#eaf6fb";
        String header = isDark ? "#34495e" : "#4a90e2";
        String accent = isDark ? "#4a90e2" : "#2980b9";
        String btnPrimary = isDark ? "#2980b9" : "#3498db";
        String btnSuccess = "#27ae60";
        String btnDanger = "#e74c3c";
        String text = isDark ? "#ecf0f1" : "#22304a";
        String textHeader = "#ffffff";

        root.setStyle("-fx-background-color: " + bg + ";");

        if (root.getChildren().size() > 0 && root.getChildren().get(0) instanceof HBox) {
            HBox headerBox = (HBox) root.getChildren().get(0);
            headerBox.setStyle("-fx-background-color: " + header + "; -fx-background-radius: 15;");
            for (javafx.scene.Node node : headerBox.getChildren()) {
                if (node instanceof VBox) {
                    VBox innerVBox = (VBox) node;
                    for (javafx.scene.Node innerNode : innerVBox.getChildren()) {
                        if (innerNode instanceof Label || innerNode instanceof Text) {
                            innerNode.setStyle("-fx-text-fill: " + textHeader + ";");
                        }
                    }
                }
            }
        }

        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                vbox.setStyle("-fx-background-color: " + card + "; -fx-background-radius: 20;");
                for (javafx.scene.Node child : vbox.getChildren()) {
                    if (child instanceof Label) {
                        child.setStyle("-fx-text-fill: " + text + ";");
                    }
                    if (child instanceof TableView) {
                        child.setStyle("-fx-background-color: " + card + ";");
                    }
                    if (child instanceof Button) {
                        Button btn = (Button) child;
                        String btnColor = btn.getText().contains("Add") ? btnSuccess :
                                          btn.getText().contains("Update") ? btnPrimary :
                                          btn.getText().contains("Delete") ? btnDanger :
                                          btnPrimary;
                        btn.setStyle("-fx-background-color: " + btnColor + "; -fx-text-fill: " + textHeader + ";");
                    }
                }
            }
        }
    }
}
