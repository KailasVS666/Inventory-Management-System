package com.inventory.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.inventory.managers.ReportManager;

public class ReportsScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private final ReportManager reportManager;
    
    public ReportsScreen(InventoryManagementApp app) {
        this.app = app;
        this.reportManager = InventoryManagementApp.getReportManager();
        
        // Create main container
        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Create header
        createHeader();
        
        // Create report buttons
        createReportButtons();
        
        // Create export section
        createExportSection();
        
        // Create back button
        createBackButton();
    }
    
    private void createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));
        
        Text title = new Text("Reports & Analytics");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: #2c3e50;");
        
        Text subtitle = new Text("Generate reports, view analytics, and export data");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitle.setStyle("-fx-fill: #7f8c8d;");
        
        header.getChildren().addAll(title, subtitle);
        root.getChildren().add(header);
    }
    
    private void createReportButtons() {
        VBox reportsContainer = new VBox(20);
        reportsContainer.setAlignment(Pos.CENTER);
        reportsContainer.setPadding(new Insets(20));
        reportsContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text reportsTitle = new Text("Generate Reports");
        reportsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        reportsTitle.setStyle("-fx-fill: #2c3e50;");
        
        // Create report buttons grid
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(20);
        buttonGrid.setVgap(20);
        buttonGrid.setAlignment(Pos.CENTER);
        
        // Low Stock Report Button
        Button lowStockBtn = createReportButton("Low Stock Report", "View products below reorder levels", "#e74c3c");
        lowStockBtn.setOnAction(e -> generateLowStockReport());
        
        // Inventory Value Report Button
        Button inventoryValueBtn = createReportButton("Inventory Value Report", "View total inventory worth and item values", "#f39c12");
        inventoryValueBtn.setOnAction(e -> generateInventoryValueReport());
        
        // Sales Summary Report Button
        Button salesSummaryBtn = createReportButton("Sales Summary Report", "View sales analytics and order history", "#27ae60");
        salesSummaryBtn.setOnAction(e -> generateSalesSummaryReport());
        
        // Add buttons to grid
        buttonGrid.add(lowStockBtn, 0, 0);
        buttonGrid.add(inventoryValueBtn, 1, 0);
        buttonGrid.add(salesSummaryBtn, 0, 1);
        
        reportsContainer.getChildren().addAll(reportsTitle, buttonGrid);
        root.getChildren().add(reportsContainer);
    }
    
    private Button createReportButton(String title, String description, String color) {
        VBox buttonContent = new VBox(5);
        buttonContent.setAlignment(Pos.CENTER);
        buttonContent.setPadding(new Insets(15));
        
        Text titleText = new Text(title);
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleText.setStyle("-fx-fill: white;");
        
        Text descText = new Text(description);
        descText.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        descText.setStyle("-fx-fill: white; -fx-opacity: 0.9;");
        descText.setWrappingWidth(180);
        
        buttonContent.getChildren().addAll(titleText, descText);
        
        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setPrefSize(200, 100);
        button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0, 0, 0);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);"));
        
        return button;
    }
    
    private void createExportSection() {
        VBox exportContainer = new VBox(20);
        exportContainer.setAlignment(Pos.CENTER);
        exportContainer.setPadding(new Insets(20));
        exportContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text exportTitle = new Text("Export Reports to CSV");
        exportTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        exportTitle.setStyle("-fx-fill: #2c3e50;");
        
        Text exportDescription = new Text("Export reports to CSV format for external analysis and sharing");
        exportDescription.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        exportDescription.setStyle("-fx-fill: #7f8c8d;");
        
        // Create export buttons
        HBox exportButtons = new HBox(20);
        exportButtons.setAlignment(Pos.CENTER);
        
        Button exportLowStockBtn = new Button("Export Low Stock Report");
        exportLowStockBtn.setPrefSize(180, 40);
        exportLowStockBtn.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        exportLowStockBtn.setOnAction(e -> exportLowStockReport());
        
        Button exportInventoryBtn = new Button("Export Inventory Value Report");
        exportInventoryBtn.setPrefSize(180, 40);
        exportInventoryBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        exportInventoryBtn.setOnAction(e -> exportInventoryValueReport());
        
        exportButtons.getChildren().addAll(exportLowStockBtn, exportInventoryBtn);
        
        exportContainer.getChildren().addAll(exportTitle, exportDescription, exportButtons);
        root.getChildren().add(exportContainer);
    }
    
    private void createBackButton() {
        HBox backContainer = new HBox();
        backContainer.setAlignment(Pos.CENTER_LEFT);
        backContainer.setPadding(new Insets(20, 0, 0, 0));
        
        Button backBtn = new Button("← Back to Dashboard");
        backBtn.setPrefSize(150, 40);
        backBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        backBtn.setOnAction(e -> app.showDashboard());
        
        backContainer.getChildren().add(backBtn);
        root.getChildren().add(backContainer);
    }
    
    // Report generation methods
    private void generateLowStockReport() {
        try {
            // TODO: Call reportManager.viewLowStockProducts()
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Low Stock Report", "Report Generated", "Low stock report will be displayed when integration is complete.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to generate low stock report", e.getMessage());
        }
    }
    
    private void generateInventoryValueReport() {
        try {
            // TODO: Call reportManager.viewTotalInventoryValue()
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Inventory Value Report", "Report Generated", "Inventory value report will be displayed when integration is complete.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to generate inventory value report", e.getMessage());
        }
    }
    
    private void generateSalesSummaryReport() {
        try {
            // TODO: Call reportManager.viewSalesSummary()
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Sales Summary Report", "Report Generated", "Sales summary report will be displayed when integration is complete.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to generate sales summary report", e.getMessage());
        }
    }
    
    // Export methods
    private void exportLowStockReport() {
        try {
            // TODO: Call reportManager.exportLowStockReport()
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Export Low Stock Report", "Export Successful", "Low stock report will be exported to CSV when integration is complete.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to export low stock report", e.getMessage());
        }
    }
    
    private void exportInventoryValueReport() {
        try {
            // TODO: Call reportManager.exportInventoryValueReport()
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Export Inventory Value Report", "Export Successful", "Inventory value report will be exported to CSV when integration is complete.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to export inventory value report", e.getMessage());
        }
    }
    
    public VBox getRoot() {
        return root;
    }
}
