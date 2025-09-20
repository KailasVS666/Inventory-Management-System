package com.inventory.gui;

import com.inventory.managers.ReportManager;
import com.inventory.managers.InventoryManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import javafx.scene.layout.Priority;

public class ReportsScreen {
    private final InventoryManagementApp app;
    private final VBox root;
    private final ReportManager reportManager;
    private final InventoryManager inventoryManager;
    
    public ReportsScreen(InventoryManagementApp app) {
        this.app = app;
        this.reportManager = InventoryManagementApp.getReportManager();
        this.inventoryManager = InventoryManagementApp.getInventoryManager();
        
        // Create root layout with beautiful background
        root = new VBox(25);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #DDA0DD 0%, #F7DC6F 100%); -fx-background-radius: 0;");
        
        // Create UI sections
        createHeader();
        createReportsSection();
        createExportSection();
        createButtons();
        
        // Apply initial theme
        applyCurrentTheme();
    }
    
    private void createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: linear-gradient(to right, #DDA0DD 0%, #F7DC6F 100%); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        
        Text title = new Text("📊 Reports & Analytics");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        Text subtitle = new Text("Generate reports and export data");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-fill: rgba(255,255,255,0.9); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 0);");
        
        VBox titleBox = new VBox(8);
        titleBox.getChildren().addAll(title, subtitle);
        
        header.getChildren().add(titleBox);
        VBox.setMargin(header, new Insets(0, 0, 30, 0));
        root.getChildren().add(header);
    }
    
    private void createReportsSection() {
        VBox reportsContainer = new VBox(20);
        reportsContainer.setPadding(new Insets(25));
        reportsContainer.setStyle("-fx-background-color: linear-gradient(135deg, #667eea 0%, #764ba2 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label reportsTitle = new Label("📈 Available Reports");
        reportsTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        GridPane reportsGrid = new GridPane();
        reportsGrid.setHgap(20);
        reportsGrid.setVgap(20);
        reportsGrid.setPadding(new Insets(20));
        
        // Create report buttons with enhanced styling
        Button inventoryReportBtn = createReportButton("📦 Inventory Report", "View current stock levels", "#FF6B6B", "#4ECDC4");
        inventoryReportBtn.setOnAction(e -> generateInventoryReport());
        
        Button salesReportBtn = createReportButton("💰 Sales Report", "Analyze sales performance", "#45B7D1", "#96CEB4");
        salesReportBtn.setOnAction(e -> generateSalesReport());
        
        Button supplierReportBtn = createReportButton("🏢 Supplier Report", "Review supplier information", "#FFA07A", "#98D8C8");
        supplierReportBtn.setOnAction(e -> generateSupplierReport());
        
        Button lowStockReportBtn = createReportButton("⚠️ Low Stock Report", "Identify items needing reorder", "#DDA0DD", "#F7DC6F");
        lowStockReportBtn.setOnAction(e -> generateLowStockReport());
        
        // Add buttons to grid
        reportsGrid.add(inventoryReportBtn, 0, 0);
        reportsGrid.add(salesReportBtn, 1, 0);
        reportsGrid.add(supplierReportBtn, 0, 1);
        reportsGrid.add(lowStockReportBtn, 1, 1);
        
        reportsContainer.getChildren().addAll(reportsTitle, reportsGrid);
        root.getChildren().add(reportsContainer);
    }
    
    private Button createReportButton(String title, String description, String color1, String color2) {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(25));
        box.setStyle(
            "-fx-background-color: linear-gradient(135deg, " + color1 + " 0%, " + color2 + " 100%);" +
            "-fx-background-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);" +
            "-fx-border-color: rgba(255,255,255,0.3);" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 20;"
        );
        box.setMinSize(200, 120);
        box.setMaxSize(250, 150);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 2, 0, 0, 0);");
        
        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.9); -fx-wrap-text: true; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 1, 0, 0, 0);");
        descLabel.setAlignment(Pos.CENTER);
        
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
                "-fx-background-color: linear-gradient(135deg, " + color1 + " 20%, " + color2 + " 100%);" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 0);" +
                "-fx-border-color: rgba(255,255,255,0.5);" +
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
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);" +
                "-fx-border-color: rgba(255,255,255,0.3);" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 20;"
            );
            box.setScaleX(1.0);
            box.setScaleY(1.0);
            box.setTranslateZ(0);
        });
        
        return button;
    }
    
    private void createExportSection() {
        VBox exportContainer = new VBox(20);
        exportContainer.setPadding(new Insets(25));
        exportContainer.setStyle("-fx-background-color: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label exportTitle = new Label("📤 Export Data");
        exportTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        GridPane exportGrid = new GridPane();
        exportGrid.setHgap(20);
        exportGrid.setVgap(20);
        exportGrid.setPadding(new Insets(20));
        
        // Create export buttons with enhanced styling
        Button exportLowStockBtn = createExportButton("⚠️ Low Stock Data", "Export products below reorder level", "#FF6B6B", "#4ECDC4");
        exportLowStockBtn.setOnAction(e -> exportLowStockData());
        
        Button exportFullInventoryBtn = createExportButton("📦 Full Inventory", "Export complete inventory data", "#45B7D1", "#96CEB4");
        exportFullInventoryBtn.setOnAction(e -> exportFullInventory());
        
        Button exportOrdersBtn = createExportButton("📋 Orders Data", "Export order history and details", "#FFA07A", "#98D8C8");
        exportOrdersBtn.setOnAction(e -> exportOrdersData());
        
        Button exportSuppliersBtn = createExportButton("🏢 Suppliers Data", "Export supplier information", "#DDA0DD", "#F7DC6F");
        exportSuppliersBtn.setOnAction(e -> exportSuppliersData());
        
        // Add buttons to grid
        exportGrid.add(exportLowStockBtn, 0, 0);
        exportGrid.add(exportFullInventoryBtn, 1, 0);
        exportGrid.add(exportOrdersBtn, 0, 1);
        exportGrid.add(exportSuppliersBtn, 1, 1);
        
        exportContainer.getChildren().addAll(exportTitle, exportGrid);
        root.getChildren().add(exportContainer);
    }
    
    private Button createExportButton(String title, String description, String color1, String color2) {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle(
            "-fx-background-color: linear-gradient(135deg, " + color1 + " 0%, " + color2 + " 100%);" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 0);" +
            "-fx-border-color: rgba(255,255,255,0.3);" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 15;"
        );
        box.setMinSize(180, 100);
        box.setMaxSize(220, 120);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 2, 0, 0, 0);");
        
        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.9); -fx-wrap-text: true; -fx-font-size: 12px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 1, 0, 0, 0);");
        descLabel.setAlignment(Pos.CENTER);
        
        box.getChildren().addAll(titleLabel, descLabel);
        
        Button button = new Button();
        button.setGraphic(box);
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-padding: 0;" +
            "-fx-background-radius: 15;"
        );
        
        // Enhanced hover effects
        box.setOnMouseEntered(e -> {
            box.setStyle(
                "-fx-background-color: linear-gradient(135deg, " + color1 + " 20%, " + color2 + " 100%);" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 0);" +
                "-fx-border-color: rgba(255,255,255,0.5);" +
                "-fx-border-width: 3;" +
                "-fx-border-radius: 15;" +
                "-fx-scale-x: 1.03;" +
                "-fx-scale-y: 1.03;"
            );
            box.setTranslateZ(5);
        });
        
        box.setOnMouseExited(e -> {
            box.setStyle(
                "-fx-background-color: linear-gradient(135deg, " + color1 + " 0%, " + color2 + " 100%);" +
                "-fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 0);" +
                "-fx-border-color: rgba(255,255,255,0.3);" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 15;"
            );
            box.setScaleX(1.0);
            box.setScaleY(1.0);
            box.setTranslateZ(0);
        });
        
        return button;
    }
    
    private void createButtons() {
        VBox buttonContainer = new VBox(20);
        buttonContainer.setPadding(new Insets(25));
        buttonContainer.setStyle("-fx-background-color: linear-gradient(135deg, #fa709a 0%, #fee140 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label buttonTitle = new Label("🎯 Navigation");
        buttonTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button backBtn = new Button("🏠 Back to Dashboard");
        backBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        backBtn.setOnAction(e -> app.showDashboard());
        
        Button refreshBtn = new Button("🔄 Refresh Reports");
        refreshBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        refreshBtn.setOnAction(e -> refreshReports());
        
        // Add hover effects for all buttons
        addHoverEffects(backBtn);
        addHoverEffects(refreshBtn);
        
        buttonBox.getChildren().addAll(backBtn, refreshBtn);
        
        buttonContainer.getChildren().addAll(buttonTitle, buttonBox);
        root.getChildren().add(buttonContainer);
    }
    
    private void addHoverEffects(Button button) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 3; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 0);");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        });
    }
    
    // Stub methods for report generation
    private void generateInventoryReport() {
        InventoryManagementApp.showInfo("Report", "Inventory Report", "Inventory report generated.");
    }
    
    private void generateSalesReport() {
        InventoryManagementApp.showInfo("Report", "Sales Report", "Sales report generated.");
    }
    
    private void generateSupplierReport() {
        InventoryManagementApp.showInfo("Report", "Supplier Report", "Supplier report generated.");
    }
    
    private void generateLowStockReport() {
        InventoryManagementApp.showInfo("Report", "Low Stock Report", "Low stock report generated.");
    }
    
    private void exportLowStockData() {
        InventoryManagementApp.showInfo("Export", "Low Stock Data", "Low stock data exported successfully.");
    }
    
    private void exportFullInventory() {
        InventoryManagementApp.showInfo("Export", "Full Inventory", "Full inventory data exported successfully.");
    }
    
    private void exportOrdersData() {
        InventoryManagementApp.showInfo("Export", "Orders Data", "Orders data exported successfully.");
    }
    
    private void exportSuppliersData() {
        InventoryManagementApp.showInfo("Export", "Suppliers Data", "Suppliers data exported successfully.");
    }
    
    private void refreshReports() {
        InventoryManagementApp.showInfo("Refresh", "Reports Refreshed", "All reports have been refreshed successfully!");
    }
    
    public VBox getRoot() {
        // Create scroll pane wrapper
        ScrollPane scrollPane = new ScrollPane();
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
        
        VBox container = new VBox();
        container.getChildren().add(scrollPane);

        // --- THIS IS THE NEW LINE ---
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        return container;
    }
    
    public void applyCurrentTheme() {
        String theme = InventoryManagementApp.getCurrentTheme();
        if ("dark".equals(theme)) {
            root.setStyle("-fx-background-color: #10142b;");
        } else {
            root.setStyle("-fx-background-color: #ffffff;");
        }
        // ...existing code for other theme elements...
    }
}
