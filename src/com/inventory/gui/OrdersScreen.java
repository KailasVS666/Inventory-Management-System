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
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Priority;

public class OrdersScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private final TableView<Order> orderTable;
    private final ComboBox<Product> productComboBox;
    private final TextField quantityField, 
    customerField;
    private final Label statusLabel;
    private final ObservableList<Order> ordersData;
    private final OrderManager orderManager;
    private final InventoryManager inventoryManager;
    
    public OrdersScreen(InventoryManagementApp app) {
        this.app = app;
        this.orderManager = InventoryManagementApp.getOrderManager();
        this.inventoryManager = InventoryManagementApp.getInventoryManager();
        
        // Initialize data
        ordersData = FXCollections.observableArrayList();
        
        // Create root layout with beautiful background
        root = new VBox(25);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #FFA07A 0%, #98D8C8 100%); -fx-background-radius: 0;");
        
        // Initialize UI components
        orderTable = new TableView<>();
        productComboBox = new ComboBox<>();
        quantityField = new TextField();
        customerField = new TextField();
        statusLabel = new Label();
        
        // Create UI sections
        createHeader();
        createOrdersTable();
        createFormSection();
        createButtons();
        
        // Apply initial theme
        applyCurrentTheme();
        
        // Load orders and products
        loadOrders();
        loadProducts();
    }
    
    private void createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: linear-gradient(to right, #FFA07A 0%, #98D8C8 100%); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        
        Text title = new Text("📋 Orders Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        Text subtitle = new Text("Manage customer orders and sales");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-fill: rgba(255,255,255,0.9); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 0);");
        
        VBox titleBox = new VBox(8);
        titleBox.getChildren().addAll(title, subtitle);
        
        header.getChildren().add(titleBox);
        VBox.setMargin(header, new Insets(0, 0, 30, 0));
        root.getChildren().add(header);
    }
    
    private void createOrdersTable() {
        VBox tableContainer = new VBox(20);
        tableContainer.setPadding(new Insets(25));
        tableContainer.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, new CornerRadii(20), Insets.EMPTY)));
        Label tableTitle = new Label("📋 Order List");
        tableTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        tableTitle.setTextFill(Color.DARKSLATEGRAY);

        // Use class-level orderTable and ordersData
        orderTable.setPrefHeight(350);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderTable.setPlaceholder(new Label("No orders available."));

        TableColumn<Order, String> idCol = new TableColumn<>("🆔 Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);
        TableColumn<Order, String> customerCol = new TableColumn<>("👤 Customer");
        customerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        customerCol.setPrefWidth(150);
        TableColumn<Order, String> productCol = new TableColumn<>("📦 Product");
        productCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        productCol.setPrefWidth(150);
        TableColumn<Order, Integer> quantityCol = new TableColumn<>("📊 Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(100);
        TableColumn<Order, Double> totalCol = new TableColumn<>("💰 Total");
        totalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());
        totalCol.setPrefWidth(120);
        orderTable.getColumns().setAll(idCol, customerCol, productCol, quantityCol, totalCol);

        // Bind class-level ordersData
        orderTable.setItems(ordersData);

        // Row highlight
        orderTable.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected && !row.isEmpty()) {
                    row.setStyle("-fx-background-color: #e0f7fa;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        tableContainer.getChildren().addAll(tableTitle, orderTable);
        root.getChildren().add(tableContainer);
    }
    
    private void createFormSection() {
        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(25));
        formContainer.setStyle("-fx-background-color: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label formTitle = new Label("✨ Create New Order");
        formTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        // Form fields with enhanced styling
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(20));
        
        // Style form fields
        styleFormField(productComboBox, "Choose a product...");
        styleFormField(customerField, "Customer Name");
        styleFormField(quantityField, "Quantity");
        
        // Add form fields to grid
        formGrid.add(createFormLabel("📦 Product:"), 0, 0);
        formGrid.add(productComboBox, 1, 0);
        
        formGrid.add(createFormLabel("👤 Customer:"), 0, 1);
        formGrid.add(customerField, 1, 1);
        
        formGrid.add(createFormLabel("📊 Quantity:"), 0, 2);
        formGrid.add(quantityField, 1, 2);
        
        formContainer.getChildren().addAll(formTitle, formGrid);
        root.getChildren().add(formContainer);
    }
    
    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 2, 0, 0, 0);");
        return label;
    }
    
    private void styleFormField(TextField field, String prompt) {
        field.setPromptText(prompt);
        field.setPrefHeight(40);
        field.setPrefWidth(250);
        field.setStyle("-fx-font-size: 14; -fx-background-radius: 20; -fx-background-color: rgba(255,255,255,0.9); -fx-border-radius: 20; -fx-border-color: white; -fx-border-width: 2; -fx-padding: 8 15;");
    }
    
    private void styleFormField(ComboBox<Product> comboBox, String prompt) {
        comboBox.setPromptText(prompt);
        comboBox.setPrefHeight(40);
        comboBox.setPrefWidth(250);
        comboBox.setStyle("-fx-font-size: 14; -fx-background-radius: 20; -fx-background-color: rgba(255,255,255,0.9); -fx-border-radius: 20; -fx-border-color: white; -fx-border-width: 2; -fx-padding: 8 15;");
    }
    
    private void createButtons() {
        VBox buttonContainer = new VBox(20);
        buttonContainer.setPadding(new Insets(25));
        buttonContainer.setStyle("-fx-background-color: linear-gradient(135deg, #fa709a 0%, #fee140 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label buttonTitle = new Label("🎯 Order Actions");
        buttonTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button createOrderBtn = new Button("➕ Create Order");
        createOrderBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        createOrderBtn.setOnAction(e -> createOrder());
        
        Button processSaleBtn = new Button("💳 Process Sale");
        processSaleBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        processSaleBtn.setOnAction(e -> processSale());
        
        Button backBtn = new Button("🏠 Back to Dashboard");
        backBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        backBtn.setOnAction(e -> app.showDashboard());
        
        // Add hover effects for all buttons
        addHoverEffects(createOrderBtn);
        addHoverEffects(processSaleBtn);
        addHoverEffects(backBtn);
        
        buttonBox.getChildren().addAll(createOrderBtn, processSaleBtn, backBtn);
        
        // Status label with enhanced styling
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 2, 0, 0, 0);");
        statusLabel.setAlignment(Pos.CENTER);
        
        buttonContainer.getChildren().addAll(buttonTitle, buttonBox, statusLabel);
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
    
    private void applyCurrentTheme() {
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
    
    private void loadOrders() {
        try {
            List<Order> orders = orderManager.getOrders();
            ordersData.clear();
            ordersData.addAll(orders);
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to load orders", e.getMessage());
        }
    }
    
    private void loadProducts() {
        try {
        List<Product> products = inventoryManager.getProducts();
            productComboBox.getItems().clear();
            productComboBox.getItems().addAll(products);
            productComboBox.setPromptText("Choose a product...");
            productComboBox.setCellFactory(param -> new ListCell<Product>() {
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
            productComboBox.setButtonCell(productComboBox.getCellFactory().call(null));
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to load products", e.getMessage());
        }
    }
    
    private void loadOrderToForm(Order order) {
        if (order != null) {
            // Load basic order information - adjust based on actual Order model
            quantityField.setText(String.valueOf(order.getQuantity()));
        }
    }
    
    private void createOrder() {
        try {
            Product selectedProduct = productComboBox.getValue();
            if (selectedProduct == null) {
                InventoryManagementApp.showError("Validation Error", "No product selected", "Please select a product.");
                return;
            }
            String customerName = customerField.getText().trim();
            if (customerName.isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Customer name required", "Please enter a customer name.");
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
                InventoryManagementApp.showError("Validation Error", "Insufficient stock", "Available stock: " + selectedProduct.getQuantity());
                return;
            }
            double totalAmount = selectedProduct.getPrice() * quantity;
            String orderId = "O" + String.format("%03d", orderManager.getOrders().size() + 1);
            Order order = new Order(orderId, selectedProduct.getId(), quantity, totalAmount, customerField.getText().trim());
            orderManager.addOrder(order);
            int newQuantity = selectedProduct.getQuantity() - quantity;
            selectedProduct.setQuantity(newQuantity);
            inventoryManager.updateProduct(selectedProduct);
            loadOrders();
            loadProducts();
            statusLabel.setText("Order created successfully!");
            statusLabel.setStyle("-fx-text-fill: #27ae60;");
            if (newQuantity <= selectedProduct.getReorderLevel()) {
                InventoryManagementApp.showWarning("Low Stock Alert", "Stock is Low", String.format("Product %s is now low on stock (%d remaining). Consider reordering.", selectedProduct.getName(), newQuantity));
            }
        } catch (NumberFormatException ex) {
            InventoryManagementApp.showError("Validation Error", "Invalid quantity", "Please enter a valid number for quantity.");
        } catch (Exception ex) {
            InventoryManagementApp.showError("Error", "Failed to create order", ex.getMessage());
        }
    }
    
    private void processSale() {
        try {
            Product selectedProduct = productComboBox.getValue();
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
                
            // Process direct sale - adjust based on actual implementation
            String orderId = "O" + String.format("%03d", orderManager.getOrders().size() + 1);
            Order order = new Order(orderId, selectedProduct.getId(), quantity, selectedProduct.getPrice() * quantity, "Guest");
            orderManager.addOrder(order);
            
            // Refresh orders and products
            loadOrders();
            loadProducts();
            
            statusLabel.setText("Sale processed successfully!");
            statusLabel.setStyle("-fx-text-fill: #27ae60;");
                
            } catch (NumberFormatException ex) {
                InventoryManagementApp.showError("Validation Error", "Invalid quantity", "Please enter a valid number for quantity.");
            } catch (Exception ex) {
                InventoryManagementApp.showError("Error", "Failed to process sale", ex.getMessage());
            }
    }
    
    private void createOrder(Product product, int quantity) {
        try {
            // Calculate total amount
            double totalAmount = product.getPrice() * quantity;
            
            // Generate order ID (using OrderManager's pattern)
            String orderId = "O" + String.format("%03d", orderManager.getOrders().size() + 1);
            
            // Create order
            Order order = new Order(orderId, product.getId(), quantity, totalAmount, customerField.getText().trim());
            
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
}
