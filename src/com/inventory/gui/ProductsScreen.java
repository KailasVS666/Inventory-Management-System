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
import com.inventory.models.Product;
import com.inventory.managers.InventoryManager;
import com.inventory.managers.UserManager;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Priority;

public class ProductsScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private final InventoryManager inventoryManager;
    private final TableView<Product> productTable;
    private final TextField searchField;
    private final TextField nameField, priceField, quantityField, reorderLevelField, supplierIdField;
    private final Label statusLabel;
    
    public ProductsScreen(InventoryManagementApp app) {
        this.app = app;
        this.inventoryManager = InventoryManagementApp.getInventoryManager();
        
        // Create root layout with beautiful dark background
        root = new VBox(25);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e 0%, #16213e 100%); -fx-background-radius: 0;");
        
        // Initialize UI components
        productTable = new TableView<>();
        searchField = new TextField();
        nameField = new TextField();
        priceField = new TextField();
        quantityField = new TextField();
        reorderLevelField = new TextField();
        supplierIdField = new TextField();
        statusLabel = new Label();
        
        // Create UI sections
        createHeader();
        createSearchSection();
        createTableSection();
        createFormSection(); // This method now includes validation
        createButtons();
        
        // Apply initial theme
        applyCurrentTheme();
        
        // Load products
        loadProducts();
    }
    
    private void createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: linear-gradient(to right, #2c3e50 0%, #34495e 100%); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0); -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 15;");
        
        Text title = new Text("📦 Products Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        Text subtitle = new Text("Manage your product inventory");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-fill: #e8e8e8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 2, 0, 0, 0);");
        
        VBox titleBox = new VBox(8);
        titleBox.getChildren().addAll(title, subtitle);
        
        header.getChildren().add(titleBox);
        VBox.setMargin(header, new Insets(0, 0, 30, 0));
        root.getChildren().add(header);
    }
    
    private void createSearchSection() {
        VBox searchContainer = new VBox(20);
        searchContainer.setPadding(new Insets(25));
        searchContainer.setStyle("-fx-background-color: linear-gradient(135deg, #34495e 0%, #2c3e50 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label searchTitle = new Label("🔍 Search Products");
        searchTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        HBox searchBox = new HBox(15);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        
        Label searchLabel = new Label("Search:");
        searchLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 2, 0, 0, 0);");
        
        searchField.setPromptText("Enter product name or description");
        searchField.setPrefWidth(300);
        searchField.setPrefHeight(40);
        searchField.setStyle("-fx-font-size: 14; -fx-background-radius: 20; -fx-background-color: rgba(255,255,255,0.9); -fx-border-radius: 20; -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-text-fill: #2c3e50;");
        
        Button searchBtn = new Button("🔍 Search");
        searchBtn.setStyle("-fx-background-color: rgba(74, 144, 226, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        
        Button clearBtn = new Button("🧹 Clear");
        clearBtn.setStyle("-fx-background-color: rgba(52, 73, 94, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #34495e; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        
        // Hover effects for buttons
        searchBtn.setOnMouseEntered(e -> searchBtn.setStyle("-fx-background-color: rgba(74, 144, 226, 1.0); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #4a90e2; -fx-border-width: 3; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 0);"));
        searchBtn.setOnMouseExited(e -> searchBtn.setStyle("-fx-background-color: rgba(74, 144, 226, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);"));
        
        clearBtn.setOnMouseEntered(e -> clearBtn.setStyle("-fx-background-color: rgba(52, 73, 94, 1.0); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #34495e; -fx-border-width: 3; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 0);"));
        clearBtn.setOnMouseExited(e -> clearBtn.setStyle("-fx-background-color: rgba(52, 73, 94, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-border-color: #34495e; -fx-border-width: 2; -fx-border-radius: 20; -fx-padding: 12 24; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);"));
        
        searchBox.getChildren().addAll(searchLabel, searchField, searchBtn, clearBtn);
        
        searchContainer.getChildren().addAll(searchTitle, searchBox);
        root.getChildren().add(searchContainer);
        
        // Search functionality
        searchBtn.setOnAction(e -> {
            String searchTerm = searchField.getText().toLowerCase();
            if (searchTerm.isEmpty()) {
                loadProducts();
            } else {
                // Filter products based on search term
                List<Product> filteredProducts = inventoryManager.getProducts().stream()
                    .filter(p -> p.getName().toLowerCase().contains(searchTerm) || 
                                p.getId().toLowerCase().contains(searchTerm))
                    .collect(java.util.stream.Collectors.toList());
                
                productTable.getItems().clear();
                productTable.getItems().addAll(filteredProducts);
            }
        });
        
        clearBtn.setOnAction(e -> {
            searchField.clear();
            loadProducts();
        });
    }
    
    private void createTableSection() {
        VBox tableContainer = new VBox(20);
        tableContainer.setPadding(new Insets(25));
        tableContainer.setStyle("-fx-background-color: linear-gradient(135deg, #2c3e50 0%, #34495e 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label tableTitle = new Label("📋 Product List");
        tableTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        // Style the table
        productTable.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 0);");
        productTable.setPrefHeight(350);
        
        // Create table columns with better styling
        TableColumn<Product, String> idCol = new TableColumn<>("🆔 ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(100);
        idCol.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50 0%, #34495e 100%); -fx-text-fill: white;");
        
        TableColumn<Product, String> nameCol = new TableColumn<>("📝 Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        nameCol.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50 0%, #34495e 100%); -fx-text-fill: white;");
        
        TableColumn<Product, Double> priceCol = new TableColumn<>("💰 Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(120);
        priceCol.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50 0%, #34495e 100%); -fx-text-fill: white;");
        
        TableColumn<Product, Integer> quantityCol = new TableColumn<>("📦 Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(120);
        quantityCol.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50 0%, #34495e 100%); -fx-text-fill: white;");
        
        TableColumn<Product, Integer> reorderLevelCol = new TableColumn<>("⚠️ Reorder Level");
        reorderLevelCol.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        reorderLevelCol.setPrefWidth(140);
        reorderLevelCol.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50 0%, #34495e 100%); -fx-text-fill: white;");
        
        TableColumn<Product, String> supplierIdCol = new TableColumn<>("🏢 Supplier ID");
        supplierIdCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        supplierIdCol.setPrefWidth(140);
        supplierIdCol.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50 0%, #34495e 100%); -fx-text-fill: white;");
        
        productTable.getColumns().addAll(idCol, nameCol, priceCol, quantityCol, reorderLevelCol, supplierIdCol);
        
        // Row selection with enhanced styling
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadProductToForm(newSelection);
                // Highlight selected row
                productTable.setStyle("-fx-background-color: rgba(255,255,255,0.98); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0);");
            }
        });
        
        tableContainer.getChildren().addAll(tableTitle, productTable);
        root.getChildren().add(tableContainer);
    }
    
    private void createFormSection() {
        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(25));
        formContainer.setStyle("-fx-background-color: linear-gradient(135deg, #34495e 0%, #2c3e50 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label formTitle = new Label("✏️ Add/Edit Product");
        formTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        // Form fields with enhanced styling
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(20));
        
        // Style all form fields
        styleFormField(nameField, "Product Name");
        styleFormField(priceField, "Price");
        styleFormField(quantityField, "Quantity");
        styleFormField(reorderLevelField, "Reorder Level");
        styleFormField(supplierIdField, "Supplier ID");
        
        // Add form fields to grid
        formGrid.add(createFormLabel("📝 Product Name:"), 0, 0);
        formGrid.add(nameField, 1, 0);
        
        formGrid.add(createFormLabel("💰 Price:"), 0, 1);
        formGrid.add(priceField, 1, 1);
        
        formGrid.add(createFormLabel("📦 Quantity:"), 0, 2);
        formGrid.add(quantityField, 1, 2);
        
        formGrid.add(createFormLabel("⚠️ Reorder Level:"), 0, 3);
        formGrid.add(reorderLevelField, 1, 3);
        
        formGrid.add(createFormLabel("🏢 Supplier ID:"), 0, 4);
        formGrid.add(supplierIdField, 1, 4);
        
        formContainer.getChildren().addAll(formTitle, formGrid);
        root.getChildren().add(formContainer);

        // --- NEW: REAL-TIME INPUT VALIDATION ---
        addInputValidation();
    }

    private void addInputValidation() {
        // Validation for Price Field (allows numbers and one decimal point)
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                priceField.setText(oldValue);
            }
            // Visual feedback for validation
            if (newValue.isEmpty() || !isValidDouble(newValue)) {
                priceField.setStyle(getInvalidFieldStyle());
            } else {
                priceField.setStyle(getValidFieldStyle());
            }
        });

        // Validation for Quantity Field (only allows whole numbers)
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityField.setText(newValue.replaceAll("[^\\d]", ""));
            }
             if (newValue.isEmpty() || !isValidInteger(newValue)) {
                quantityField.setStyle(getInvalidFieldStyle());
            } else {
                quantityField.setStyle(getValidFieldStyle());
            }
        });

        // Validation for Reorder Level Field (only allows whole numbers)
        reorderLevelField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                reorderLevelField.setText(newValue.replaceAll("[^\\d]", ""));
            }
             if (newValue.isEmpty() || !isValidInteger(newValue)) {
                reorderLevelField.setStyle(getInvalidFieldStyle());
            } else {
                reorderLevelField.setStyle(getValidFieldStyle());
            }
        });
    }

    private boolean isValidDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String getValidFieldStyle() {
        return "-fx-font-size: 14; -fx-background-radius: 20; -fx-background-color: rgba(255,255,255,0.95); -fx-border-radius: 20; -fx-border-color: #4a90e2; -fx-border-width: 2; -fx-padding: 8 15; -fx-text-fill: #2c3e50;";
    }

    private String getInvalidFieldStyle() {
        return "-fx-font-size: 14; -fx-background-radius: 20; -fx-background-color: rgba(255,255,255,0.95); -fx-border-radius: 20; -fx-border-color: red; -fx-border-width: 2; -fx-padding: 8 15; -fx-text-fill: #2c3e50;";
    }
    
    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 2, 0, 0, 0);");
        return label;
    }
    
    private void styleFormField(TextField field, String prompt) {
        field.setPromptText(prompt);
        field.setPrefHeight(40);
        field.setPrefWidth(250);
        field.setStyle(getValidFieldStyle()); // Use the standard valid style
    }
    
    private void createButtons() {
        VBox buttonContainer = new VBox(20);
        buttonContainer.setPadding(new Insets(25));
        buttonContainer.setStyle("-fx-background-color: linear-gradient(135deg, #2c3e50 0%, #34495e 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 0); -fx-border-color: rgba(74, 144, 226, 0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label buttonTitle = new Label("🎯 Product Actions");
        buttonTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 3, 0, 0, 0);");
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("➕ Add Product");
        addBtn.setStyle("-fx-background-color: rgba(46, 204, 113, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: #2ecc71; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        addBtn.setOnAction(e -> addProduct());
        
        Button updateBtn = new Button("✏️ Update Product");
        updateBtn.setStyle("-fx-background-color: rgba(243, 156, 18, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: #f39c12; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        updateBtn.setOnAction(e -> updateProduct());
        
        Button deleteBtn = new Button("🗑️ Delete Product");
        deleteBtn.setStyle("-fx-background-color: rgba(231, 76, 60, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: #e74c3c; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        deleteBtn.setOnAction(e -> deleteProduct());
        
        Button clearBtn = new Button("🧹 Clear Form");
        clearBtn.setStyle("-fx-background-color: rgba(149, 165, 166, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: #95a5a6; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        clearBtn.setOnAction(e -> clearForm());
        
        Button backBtn = new Button("🏠 Back to Dashboard");
        backBtn.setStyle("-fx-background-color: rgba(52, 73, 94, 0.8); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: #34495e; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
        backBtn.setOnAction(e -> app.showDashboard());
        
        // Add hover effects for all buttons
        addHoverEffects(addBtn);
        addHoverEffects(updateBtn);
        addHoverEffects(deleteBtn);
        addHoverEffects(clearBtn);
        addHoverEffects(backBtn);
        
        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn, backBtn);
        
        // Status label with enhanced styling
        statusLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 2, 0, 0, 0);");
        statusLabel.setAlignment(Pos.CENTER);
        
        buttonContainer.getChildren().addAll(buttonTitle, buttonBox, statusLabel);
        root.getChildren().add(buttonContainer);
    }
    
    private void addHoverEffects(Button button) {
        button.setOnMouseEntered(e -> {
            String currentStyle = button.getStyle();
            if (currentStyle.contains("0.8")) {
                button.setStyle(currentStyle.replace("0.8", "1.0").replace("2", "3"));
            }
        });
        button.setOnMouseExited(e -> {
            String currentStyle = button.getStyle();
            if (currentStyle.contains("1.0")) {
                button.setStyle(currentStyle.replace("1.0", "0.8").replace("3", "2"));
            }
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
    
    private void loadProducts() {
        try {
            List<Product> products = inventoryManager.getProducts();
            ObservableList<Product> productsData = FXCollections.observableArrayList();
            productsData.addAll(products);
            productTable.setItems(productsData);
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to load products", e.getMessage());
        }
    }
    
    private void performSearch() {
        try {
            List<Product> products = inventoryManager.getProducts();
            ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
            String searchTerm = searchField.getText().trim();
            
            filteredProducts.addAll(products.stream()
                .filter(product -> searchTerm.isEmpty() || product.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .filter(product -> searchTerm.isEmpty() || String.valueOf(product.getPrice()).contains(searchTerm))
                .filter(product -> searchTerm.isEmpty() || String.valueOf(product.getQuantity()).contains(searchTerm))
                .collect(Collectors.toList()));
            
            productTable.setItems(filteredProducts);
            
        } catch (Exception e) {
            InventoryManagementApp.showError("Search Error", "Failed to perform search", e.getMessage());
        }
    }
    
    private void loadProductToForm(Product product) {
        if (product != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getQuantity()));
            reorderLevelField.setText(String.valueOf(product.getReorderLevel()));
            supplierIdField.setText(product.getSupplierId());
        }
    }
    
    private void clearForm() {
        nameField.clear();
        priceField.clear();
        quantityField.clear();
        reorderLevelField.clear();
        supplierIdField.clear();
        productTable.getSelectionModel().clearSelection();
    }
    
    private void addProduct() {
        try {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String reorderLevelText = reorderLevelField.getText().trim();
            String supplierId = supplierIdField.getText().trim();
            
            if (name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || reorderLevelText.isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Missing fields", "Please fill in all required fields.");
                return;
            }
            
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);
            int reorderLevel = Integer.parseInt(reorderLevelText);
            
            if (price <= 0 || quantity < 0 || reorderLevel < 0) {
                InventoryManagementApp.showError("Validation Error", "Invalid values", "Price must be positive, quantity and reorder level must be non-negative.");
                return;
            }
            
            // Generate a simple ID for new products
            String id = "P" + System.currentTimeMillis();
            
            Product newProduct = new Product(
                id,
                name,
                price,
                quantity,
                reorderLevel,
                supplierId
            );
            
            inventoryManager.addProduct(newProduct);
            InventoryManagementApp.showInfo("Add Product", "Product Added", "Product successfully added.");
            
            clearForm();
            loadProducts();
            
        } catch (NumberFormatException e) {
            InventoryManagementApp.showError("Validation Error", "Invalid number format", "Please enter valid numbers for price, quantity, and reorder level.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to add product", e.getMessage());
        }
    }
    
    private void updateProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            InventoryManagementApp.showWarning("No Selection", "No product selected", "Please select a product to update.");
            return;
        }
        
        try {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String reorderLevelText = reorderLevelField.getText().trim();
            String supplierId = supplierIdField.getText().trim();
            
            if (name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || reorderLevelText.isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Missing fields", "Please fill in all required fields.");
                return;
            }
            
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);
            int reorderLevel = Integer.parseInt(reorderLevelText);
            
            if (price <= 0 || quantity < 0 || reorderLevel < 0) {
                InventoryManagementApp.showError("Validation Error", "Invalid values", "Price must be positive, quantity and reorder level must be non-negative.");
                return;
            }
            
            selectedProduct.setName(name);
            selectedProduct.setPrice(price);
            selectedProduct.setQuantity(quantity);
            selectedProduct.setReorderLevel(reorderLevel);
            selectedProduct.setSupplierId(supplierId);
            
            inventoryManager.updateProduct(selectedProduct);
            InventoryManagementApp.showInfo("Update Product", "Product Updated", "Product successfully updated.");
            
            clearForm();
            loadProducts();
            
        } catch (NumberFormatException e) {
            InventoryManagementApp.showError("Validation Error", "Invalid number format", "Please enter valid numbers for price, quantity, and reorder level.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to update product", e.getMessage());
        }
    }
    
    private void deleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            InventoryManagementApp.showWarning("No Selection", "No product selected", "Please select a product to delete.");
            return;
        }
        
        InventoryManagementApp.showConfirmation("Delete Product", "Confirm Deletion", 
            "Are you sure you want to delete '" + selectedProduct.getName() + "'?", () -> {
                try {
                    inventoryManager.deleteProduct(selectedProduct.getId());
                    InventoryManagementApp.showInfo("Delete Product", "Product Deleted", "Product successfully deleted.");
                    
                    clearForm();
                    loadProducts();
                    
                } catch (Exception e) {
                    InventoryManagementApp.showError("Error", "Failed to delete product", e.getMessage());
                }
            });
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

        // This makes the ScrollPane grow to fill all available vertical space
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return container;
    }
}

