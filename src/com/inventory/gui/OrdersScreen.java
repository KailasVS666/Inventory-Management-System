package com.inventory.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import com.inventory.models.Order;
import com.inventory.managers.OrderManager;
import java.util.List;

public class OrdersScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private TableView<Order> ordersTable;
    private final ObservableList<Order> ordersData;
    private final OrderManager orderManager;
    
    public OrdersScreen(InventoryManagementApp app) {
        this.app = app;
        this.orderManager = InventoryManagementApp.getOrderManager();
        
        // Initialize data
        ordersData = FXCollections.observableArrayList();
        loadOrdersData();
        
        // Create main container
        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Create header
        createHeader();
        
        // Create table
        createOrdersTable();
        
        // Create action buttons
        createActionButtons();
        
        // Create back button
        createBackButton();
    }
    
    private void createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));
        
        Text title = new Text("Orders & Sales Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: #2c3e50;");
        
        Text subtitle = new Text("Process orders, track sales, and manage transactions");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitle.setStyle("-fx-fill: #7f8c8d;");
        
        header.getChildren().addAll(title, subtitle);
        root.getChildren().add(header);
    }
    
    private void createOrdersTable() {
        VBox tableContainer = new VBox(15);
        tableContainer.setAlignment(Pos.CENTER);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text tableTitle = new Text("Orders History");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableTitle.setStyle("-fx-fill: #2c3e50;");
        
        // Create table
        ordersTable = new TableView<>();
        ordersTable.setItems(ordersData);
        ordersTable.setPrefHeight(400);
        ordersTable.setPlaceholder(new Label("No orders found"));
        
        // Create columns
        TableColumn<Order, String> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(100);
        
        TableColumn<Order, String> productIdCol = new TableColumn<>("Product ID");
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productIdCol.setPrefWidth(100);
        
        TableColumn<Order, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(100);
        
        TableColumn<Order, Double> totalCol = new TableColumn<>("Total Amount");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        totalCol.setPrefWidth(150);
        totalCol.setCellFactory(col -> new TableCell<Order, Double>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", amount));
                }
            }
        });
        
        // Add columns to table
        ordersTable.getColumns().addAll(idCol, productIdCol, quantityCol, totalCol);
        
        tableContainer.getChildren().addAll(tableTitle, ordersTable);
        root.getChildren().add(tableContainer);
    }
    
    private void createActionButtons() {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));
        
        // Create order button
        Button createOrderBtn = new Button("Create New Order");
        createOrderBtn.setPrefSize(150, 40);
        createOrderBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        createOrderBtn.setOnAction(e -> showCreateOrderDialog());
        
        // Process sale button
        Button processSaleBtn = new Button("Process Direct Sale");
        processSaleBtn.setPrefSize(150, 40);
        processSaleBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        processSaleBtn.setOnAction(e -> showProcessSaleDialog());
        
        buttonContainer.getChildren().addAll(createOrderBtn, processSaleBtn);
        root.getChildren().add(buttonContainer);
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
    
    private void loadOrdersData() {
        try {
            List<Order> orders = orderManager.getOrders();
            ordersData.clear();
            ordersData.addAll(orders);
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to load orders", e.getMessage());
        }
    }
    
    private void showCreateOrderDialog() {
        // TODO: Implement order creation dialog
        InventoryManagementApp.showInfo("Create Order", "Order Creation", "Order creation dialog will be implemented next.");
    }
    
    private void showProcessSaleDialog() {
        // TODO: Implement direct sale dialog
        InventoryManagementApp.showInfo("Process Sale", "Direct Sale", "Direct sale dialog will be implemented next.");
    }
    
    public VBox getRoot() {
        return root;
    }
}
