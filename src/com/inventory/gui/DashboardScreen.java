package com.inventory.gui;

import com.inventory.managers.InventoryManager;
import com.inventory.managers.SupplierManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.inventory.managers.UserManager;
import com.inventory.models.User;
import javafx.scene.layout.Priority;

// No new imports are needed for this change, as List is part of java.util which is often implicitly available.

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
        // This method is now consolidated into the constructor for simplicity.
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
        
        // --- NEW CODE START ---
        // Add the new stats panel to the dashboard view
        root.getChildren().add(createStatsPanel());
        // --- NEW CODE END ---
    }

    // --- NEW METHOD START ---
    /**
     * Creates a VBox that displays live statistics about the inventory system.
     * This panel fetches data from the managers to show current counts.
     * @return A VBox containing the styled statistics panel.
     */
    private VBox createStatsPanel() {
        // 1. Get live data from your managers
        int productCount = InventoryManagementApp.getInventoryManager().getProducts().size();
        int supplierCount = InventoryManagementApp.getSupplierManager().getSuppliers().size();
        long lowStockCount = InventoryManagementApp.getInventoryManager().getProducts().stream()
                .filter(p -> p.getQuantity() <= p.getReorderLevel()).count();

        // 2. Create the UI panel
        VBox statsBox = new VBox(15);
        statsBox.setPadding(new Insets(20));
        statsBox.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 15;");

        Label title = new Label("📈 System Snapshot");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label productsLabel = new Label("Total Products: " + productCount);
        productsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label suppliersLabel = new Label("Total Suppliers: " + supplierCount);
        suppliersLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label lowStockLabel = new Label("Items Low on Stock: " + lowStockCount);

        // 3. Highlight the low stock count in red if it's greater than zero
        if (lowStockCount > 0) {
            lowStockLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        } else {
            lowStockLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71;");
        }

        statsBox.getChildren().addAll(title, productsLabel, suppliersLabel, lowStockLabel);
        return statsBox;
    }
    // --- NEW METHOD END ---

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
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return container;
    }


    public void applyCurrentTheme() {
        String theme = InventoryManagementApp.getCurrentTheme();
        boolean isDark = "dark".equals(theme);
        String bg = isDark ? "#10142b" : "#f5f7fa";
        String card = isDark ? "#22304a" : "#eaf6fb";
        String headerBg = isDark ? "#34495e" : "#4a90e2";
        String text = isDark ? "#ecf0f1" : "#22304a";
        String textHeader = "#ffffff";

        root.setStyle("-fx-background-color: " + bg + ";");

        if (root.getChildren().size() > 0 && root.getChildren().get(0) instanceof HBox) {
            HBox headerBox = (HBox) root.getChildren().get(0);
            headerBox.setStyle("-fx-background-color: " + headerBg + "; -fx-background-radius: 15;");
            // Find and style title and welcome label inside header
            if (headerBox.getChildren().get(0) instanceof VBox) {
                VBox titleVBox = (VBox) headerBox.getChildren().get(0);
                for (javafx.scene.Node node : titleVBox.getChildren()) {
                    if (node instanceof Text) {
                        ((Text) node).setFill(javafx.scene.paint.Color.valueOf(textHeader));
                    } else if (node instanceof Label) {
                        node.setStyle("-fx-text-fill: " + textHeader + ";");
                    }
                }
            }
        }

        // Style cards and other VBox containers
        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof VBox && node != root) {
                VBox vbox = (VBox) node;
                // This condition targets the "Quick Access" VBox specifically
                if(vbox.getChildren().size() > 1 && vbox.getChildren().get(1) instanceof GridPane){
                    vbox.setStyle("-fx-background-color: " + card + "; -fx-background-radius: 20;");
                    if(vbox.getChildren().get(0) instanceof Label){
                        ((Label)vbox.getChildren().get(0)).setStyle("-fx-text-fill: " + text + ";");
                    }
                }
            }
        }
    }
}
