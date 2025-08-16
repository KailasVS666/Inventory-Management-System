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
import com.inventory.models.Product;
import com.inventory.managers.OrderManager;
import com.inventory.managers.InventoryManager;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;

public class OrdersScreen {
    
    private final InventoryManagementApp app;
    private VBox root;
    private TableView<Order> ordersTable;
    private final ObservableList<Order> ordersData;
    private final OrderManager orderManager;
    private final InventoryManager inventoryManager;
    
    public OrdersScreen(InventoryManagementApp app) {
        this.app = app;
        this.orderManager = InventoryManagementApp.getOrderManager();
        this.inventoryManager = InventoryManagementApp.getInventoryManager();
        
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
        
        // Create scrollable container
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(800);
        
        // Set the scroll pane as the root
        VBox scrollRoot = new VBox();
        scrollRoot.getChildren().add(scrollPane);
        this.root = scrollRoot;
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
        Button createOrderBtn = new Button("📋 Create New Order");
        createOrderBtn.setPrefSize(150, 40);
        createOrderBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        createOrderBtn.setOnAction(e -> showCreateOrderDialog());
        createOrderBtn.setTooltip(new Tooltip("Create a new order (Ctrl+O)"));
        
        // Process sale button
        Button processSaleBtn = new Button("💰 Process Direct Sale");
        processSaleBtn.setPrefSize(150, 40);
        processSaleBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        processSaleBtn.setOnAction(e -> showProcessSaleDialog());
        processSaleBtn.setTooltip(new Tooltip("Process a direct sale (Ctrl+S)"));
        
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
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(app.getPrimaryStage());
        dialog.setTitle("Create New Order");
        dialog.setResizable(false);
        
        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);
        dialogContent.setPadding(new Insets(30));
        dialogContent.setStyle("-fx-background-color: #f5f5f5;");
        
        // Title
        Text title = new Text("Create New Order");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");
        
        // Product selection
        VBox productSection = new VBox(10);
        productSection.setAlignment(Pos.CENTER);
        
        Label productLabel = new Label("Select Product:");
        productLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        ComboBox<Product> productCombo = new ComboBox<>();
        productCombo.setPromptText("Choose a product...");
        productCombo.setPrefWidth(300);
        
        // Load products into combo box
        List<Product> products = inventoryManager.getProducts();
        productCombo.getItems().addAll(products);
        
        // Display format for products
        productCombo.setCellFactory(param -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s (Stock: %d, Price: $%.2f)", 
                        product.getId(), product.getName(), product.getQuantity(), product.getPrice()));
                }
            }
        });
        
        productCombo.setButtonCell(productCombo.getCellFactory().call(null));
        
        // Product info display
        VBox productInfo = new VBox(5);
        productInfo.setAlignment(Pos.CENTER);
        productInfo.setPadding(new Insets(10));
        productInfo.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
        
        Label infoLabel = new Label("Product Information");
        infoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        infoLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        Label stockLabel = new Label("Available Stock: ");
        Label priceLabel = new Label("Unit Price: ");
        
        productInfo.getChildren().addAll(infoLabel, stockLabel, priceLabel);
        productInfo.setVisible(false);
        
        // Update product info when selection changes
        productCombo.setOnAction(e -> {
            Product selected = productCombo.getValue();
            if (selected != null) {
                stockLabel.setText("Available Stock: " + selected.getQuantity());
                priceLabel.setText("Unit Price: $" + String.format("%.2f", selected.getPrice()));
                productInfo.setVisible(true);
            } else {
                productInfo.setVisible(false);
            }
        });
        
        // Quantity input
        VBox quantitySection = new VBox(10);
        quantitySection.setAlignment(Pos.CENTER);
        
        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        quantityField.setPrefWidth(200);
        
        // Total amount display
        VBox totalSection = new VBox(10);
        totalSection.setAlignment(Pos.CENTER);
        totalSection.setPadding(new Insets(10));
        totalSection.setStyle("-fx-background-color: #e8f5e8; -fx-background-radius: 5;");
        
        Label totalLabel = new Label("Total Amount: $0.00");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        totalLabel.setStyle("-fx-text-fill: #27ae60;");
        
        totalSection.getChildren().add(totalLabel);
        totalSection.setVisible(false);
        
        // Calculate total when quantity changes
        quantityField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                Product selected = productCombo.getValue();
                if (selected != null && !newVal.isEmpty()) {
                    int quantity = Integer.parseInt(newVal);
                    double total = selected.getPrice() * quantity;
                    totalLabel.setText(String.format("Total Amount: $%.2f", total));
                    totalSection.setVisible(true);
                } else {
                    totalSection.setVisible(false);
                }
            } catch (NumberFormatException ex) {
                totalSection.setVisible(false);
            }
        });
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button createBtn = new Button("Create Order");
        createBtn.setPrefSize(120, 40);
        createBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefSize(120, 40);
        cancelBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        
        buttonBox.getChildren().addAll(createBtn, cancelBtn);
        
        // Create order action
        createBtn.setOnAction(e -> {
            try {
                Product selectedProduct = productCombo.getValue();
                if (selectedProduct == null) {
                    InventoryManagementApp.showError("Validation Error", "No product selected", "Please select a product.");
                    return;
                }
                
                String quantityText = quantityField.getText().trim();
                if (quantityText.isEmpty()) {
                    InventoryManagementApp.showError("Validation Error", "Quantity required", "Please enter a quantity.");
                    return;
                }
                
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    InventoryManagementApp.showError("Validation Error", "Invalid quantity", "Quantity must be greater than 0.");
                    return;
                }
                
                if (quantity > selectedProduct.getQuantity()) {
                    InventoryManagementApp.showError("Validation Error", "Insufficient stock", 
                        "Available stock: " + selectedProduct.getQuantity());
                    return;
                }
                
                // Create order using OrderManager
                createOrder(selectedProduct, quantity);
                
                dialog.close();
                loadOrdersData(); // Refresh the orders table
                
            } catch (NumberFormatException ex) {
                InventoryManagementApp.showError("Validation Error", "Invalid quantity", "Please enter a valid number for quantity.");
            } catch (Exception ex) {
                InventoryManagementApp.showError("Error", "Failed to create order", ex.getMessage());
            }
        });
        
        cancelBtn.setOnAction(e -> dialog.close());
        
        // Add all components
        productSection.getChildren().addAll(productLabel, productCombo, productInfo);
        quantitySection.getChildren().addAll(quantityLabel, quantityField);
        
        dialogContent.getChildren().addAll(title, productSection, quantitySection, totalSection, buttonBox);
        
        Scene dialogScene = new Scene(dialogContent);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }
    
    private void showProcessSaleDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(app.getPrimaryStage());
        dialog.setTitle("Process Direct Sale");
        dialog.setResizable(false);
        
        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);
        dialogContent.setPadding(new Insets(30));
        dialogContent.setStyle("-fx-background-color: #f5f5f5;");
        
        // Title
        Text title = new Text("Process Direct Sale");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-fill: #2c3e50;");
        
        // Product selection
        VBox productSection = new VBox(10);
        productSection.setAlignment(Pos.CENTER);
        
        Label productLabel = new Label("Select Product:");
        productLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        ComboBox<Product> productCombo = new ComboBox<>();
        productCombo.setPromptText("Choose a product...");
        productCombo.setPrefWidth(300);
        
        // Load products into combo box
        List<Product> products = inventoryManager.getProducts();
        productCombo.getItems().addAll(products);
        
        // Display format for products
        productCombo.setCellFactory(param -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s (Stock: %d, Price: $%.2f)", 
                        product.getId(), product.getName(), product.getQuantity(), product.getPrice()));
                }
            }
        });
        
        productCombo.setButtonCell(productCombo.getCellFactory().call(null));
        
        // Product info display
        VBox productInfo = new VBox(5);
        productInfo.setAlignment(Pos.CENTER);
        productInfo.setPadding(new Insets(10));
        productInfo.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
        
        Label infoLabel = new Label("Product Information");
        infoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        infoLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        Label stockLabel = new Label("Available Stock: ");
        Label priceLabel = new Label("Unit Price: ");
        
        productInfo.getChildren().addAll(infoLabel, stockLabel, priceLabel);
        productInfo.setVisible(false);
        
        // Update product info when selection changes
        productCombo.setOnAction(e -> {
            Product selected = productCombo.getValue();
            if (selected != null) {
                stockLabel.setText("Available Stock: " + selected.getQuantity());
                priceLabel.setText("Unit Price: $" + String.format("%.2f", selected.getPrice()));
                productInfo.setVisible(true);
            } else {
                productInfo.setVisible(false);
            }
        });
        
        // Quantity input
        VBox quantitySection = new VBox(10);
        quantitySection.setAlignment(Pos.CENTER);
        
        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        quantityField.setPrefWidth(200);
        
        // Total amount display
        VBox totalSection = new VBox(10);
        totalSection.setAlignment(Pos.CENTER);
        totalSection.setPadding(new Insets(10));
        totalSection.setStyle("-fx-background-color: #e8f5e8; -fx-background-radius: 5;");
        
        Label totalLabel = new Label("Total Amount: $0.00");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        totalLabel.setStyle("-fx-text-fill: #27ae60;");
        
        totalSection.getChildren().add(totalLabel);
        totalSection.setVisible(false);
        
        // Calculate total when quantity changes
        quantityField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                Product selected = productCombo.getValue();
                if (selected != null && !newVal.isEmpty()) {
                    int quantity = Integer.parseInt(newVal);
                    double total = selected.getPrice() * quantity;
                    totalLabel.setText(String.format("Total Amount: $%.2f", total));
                    totalSection.setVisible(true);
                } else {
                    totalSection.setVisible(false);
                }
            } catch (NumberFormatException ex) {
                totalSection.setVisible(false);
            }
        });
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button processBtn = new Button("Process Sale");
        processBtn.setPrefSize(120, 40);
        processBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefSize(120, 40);
        cancelBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        
        buttonBox.getChildren().addAll(processBtn, cancelBtn);
        
        // Process sale action
        processBtn.setOnAction(e -> {
            try {
                Product selectedProduct = productCombo.getValue();
                if (selectedProduct == null) {
                    InventoryManagementApp.showError("Validation Error", "No product selected", "Please select a product.");
                    return;
                }
                
                String quantityText = quantityField.getText().trim();
                if (quantityText.isEmpty()) {
                    InventoryManagementApp.showError("Validation Error", "Quantity required", "Please enter a quantity.");
                    return;
                }
                
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    InventoryManagementApp.showError("Validation Error", "Invalid quantity", "Quantity must be greater than 0.");
                    return;
                }
                
                if (quantity > selectedProduct.getQuantity()) {
                    InventoryManagementApp.showError("Validation Error", "Insufficient stock", 
                        "Available stock: " + selectedProduct.getQuantity());
                    return;
                }
                
                // Process direct sale
                processDirectSale(selectedProduct, quantity);
                
                dialog.close();
                
            } catch (NumberFormatException ex) {
                InventoryManagementApp.showError("Validation Error", "Invalid quantity", "Please enter a valid number for quantity.");
            } catch (Exception ex) {
                InventoryManagementApp.showError("Error", "Failed to process sale", ex.getMessage());
            }
        });
        
        cancelBtn.setOnAction(e -> dialog.close());
        
        // Add all components
        productSection.getChildren().addAll(productLabel, productCombo, productInfo);
        quantitySection.getChildren().addAll(quantityLabel, quantityField);
        
        dialogContent.getChildren().addAll(title, productSection, quantitySection, totalSection, buttonBox);
        
        Scene dialogScene = new Scene(dialogContent);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }
    
    private void createOrder(Product product, int quantity) {
        try {
            // Calculate total amount
            double totalAmount = product.getPrice() * quantity;
            
            // Generate order ID (using OrderManager's pattern)
            String orderId = "O" + String.format("%03d", orderManager.getOrders().size() + 1);
            
            // Create order
            Order order = new Order(orderId, product.getId(), quantity, totalAmount);
            
            // Add order to OrderManager
            orderManager.addOrder(order);
            
            // Update stock in InventoryManager
            int newQuantity = product.getQuantity() - quantity;
            product.setQuantity(newQuantity);
            inventoryManager.updateProduct(product);
            
            // Show success message
            InventoryManagementApp.showInfo("Order Created", "Success", 
                String.format("Order %s created successfully!\nProduct: %s\nQuantity: %d\nTotal: $%.2f\nRemaining Stock: %d", 
                    orderId, product.getName(), quantity, totalAmount, newQuantity));
            
            // Check if stock is now low
            if (newQuantity <= product.getReorderLevel()) {
                InventoryManagementApp.showWarning("Low Stock Alert", "Stock is Low", 
                    String.format("Product %s is now low on stock (%d remaining). Consider reordering.", 
                        product.getName(), newQuantity));
            }
            
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to create order", e.getMessage());
        }
    }
    
    private void processDirectSale(Product product, int quantity) {
        try {
            // Calculate total amount
            double totalAmount = product.getPrice() * quantity;
            
            // Update stock directly (no order created)
            int newQuantity = product.getQuantity() - quantity;
            product.setQuantity(newQuantity);
            inventoryManager.updateProduct(product);
            
            // Show success message
            InventoryManagementApp.showInfo("Sale Processed", "Success", 
                String.format("Sale processed successfully!\nProduct: %s\nQuantity: %d\nUnit Price: $%.2f\nTotal: $%.2f\nRemaining Stock: %d", 
                    product.getName(), quantity, product.getPrice(), totalAmount, newQuantity));
            
            // Check if stock is now low
            if (newQuantity <= product.getReorderLevel()) {
                InventoryManagementApp.showWarning("Low Stock Alert", "Stock is Low", 
                    String.format("Product %s is now low on stock (%d remaining). Consider reordering.", 
                        product.getName(), newQuantity));
            }
            
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to process sale", e.getMessage());
        }
    }
    
    public VBox getRoot() {
        return root;
    }
}
