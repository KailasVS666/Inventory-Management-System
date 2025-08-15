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

public class ProductsScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private final TableView<Product> productsTable;
    private final ObservableList<Product> productsData;
    private final InventoryManager inventoryManager;
    private final UserManager userManager;
    
    // Form fields
    private final TextField nameField;
    private final TextField priceField;
    private final TextField quantityField;
    private final TextField reorderLevelField;
    private final TextField supplierIdField;
    
    public ProductsScreen(InventoryManagementApp app) {
        this.app = app;
        this.inventoryManager = InventoryManagementApp.getInventoryManager();
        this.userManager = InventoryManagementApp.getUserManager();
        
        // Initialize data
        productsData = FXCollections.observableArrayList();
        loadProductsData();
        
        // Create main container
        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Create header
        createHeader();
        
        // Create search section
        createSearchSection();
        
        // Create table
        createProductsTable();
        
        // Create form section
        createFormSection();
        
        // Create action buttons
        createActionButtons();
        
        // Create back button
        createBackButton();
    }
    
    private void createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));
        
        Text title = new Text("Products Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: #2c3e50;");
        
        Text subtitle = new Text("Manage inventory items, stock levels, and product information");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitle.setStyle("-fx-fill: #7f8c8d;");
        
        header.getChildren().addAll(title, subtitle);
        root.getChildren().add(header);
    }
    
    private void createSearchSection() {
        VBox searchContainer = new VBox(15);
        searchContainer.setAlignment(Pos.CENTER);
        searchContainer.setPadding(new Insets(20));
        searchContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text searchTitle = new Text("Search & Filter Products");
        searchTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        searchTitle.setStyle("-fx-fill: #2c3e50;");
        
        HBox searchFields = new HBox(15);
        searchFields.setAlignment(Pos.CENTER);
        
        // Search by name
        VBox nameSearch = new VBox(5);
        Label nameLabel = new Label("Product Name:");
        TextField nameSearchField = new TextField();
        nameSearchField.setPromptText("Enter product name");
        nameSearchField.setPrefWidth(200);
        nameSearch.getChildren().addAll(nameLabel, nameSearchField);
        
        // Search by price range
        VBox priceSearch = new VBox(5);
        Label priceLabel = new Label("Price Range:");
        HBox priceRange = new HBox(10);
        TextField minPriceField = new TextField();
        minPriceField.setPromptText("Min");
        minPriceField.setPrefWidth(80);
        TextField maxPriceField = new TextField();
        maxPriceField.setPromptText("Max");
        maxPriceField.setPrefWidth(80);
        priceRange.getChildren().addAll(minPriceField, new Label("-"), maxPriceField);
        priceSearch.getChildren().addAll(priceLabel, priceRange);
        
        // Search by supplier
        VBox supplierSearch = new VBox(5);
        Label supplierLabel = new Label("Supplier ID:");
        TextField supplierSearchField = new TextField();
        supplierSearchField.setPromptText("Enter supplier ID");
        supplierSearchField.setPrefWidth(150);
        supplierSearch.getChildren().addAll(supplierLabel, supplierSearchField);
        
        // Search button
        Button searchBtn = new Button("Search");
        searchBtn.setPrefSize(100, 35);
        searchBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        searchBtn.setOnAction(e -> performSearch(nameSearchField.getText(), minPriceField.getText(), maxPriceField.getText(), supplierSearchField.getText()));
        
        // Clear search button
        Button clearBtn = new Button("Clear");
        clearBtn.setPrefSize(100, 35);
        clearBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        clearBtn.setOnAction(e -> {
            nameSearchField.clear();
            minPriceField.clear();
            maxPriceField.clear();
            supplierSearchField.clear();
            loadProductsData();
        });
        
        HBox searchButtons = new HBox(10);
        searchButtons.setAlignment(Pos.CENTER);
        searchButtons.getChildren().addAll(searchBtn, clearBtn);
        
        searchFields.getChildren().addAll(nameSearch, priceSearch, supplierSearch);
        
        searchContainer.getChildren().addAll(searchTitle, searchFields, searchButtons);
        root.getChildren().add(searchContainer);
    }
    
    private void createProductsTable() {
        VBox tableContainer = new VBox(15);
        tableContainer.setAlignment(Pos.CENTER);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text tableTitle = new Text("Products Table");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableTitle.setStyle("-fx-fill: #2c3e50;");
        
        // Create table
        productsTable = new TableView<>();
        productsTable.setItems(productsData);
        productsTable.setPrefHeight(400);
        productsTable.setPlaceholder(new Label("No products found"));
        
        // Create columns
        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(80);
        
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        
        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);
        priceCol.setCellFactory(col -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });
        
        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(100);
        quantityCol.setCellFactory(col -> new TableCell<Product, Integer>() {
            @Override
            protected void updateItem(Integer quantity, boolean empty) {
                super.updateItem(quantity, empty);
                if (empty || quantity == null) {
                    setText(null);
                } else {
                    setText(quantity.toString());
                    // Highlight low stock
                    if (quantity <= 5) {
                        setStyle("-fx-background-color: #ffebee; -fx-text-fill: #d32f2f; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        
        TableColumn<Product, Integer> reorderCol = new TableColumn<>("Reorder Level");
        reorderCol.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        reorderCol.setPrefWidth(120);
        
        TableColumn<Product, String> supplierCol = new TableColumn<>("Supplier ID");
        supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        supplierCol.setPrefWidth(120);
        supplierCol.setCellFactory(col -> new TableCell<Product, String>() {
            @Override
            protected void updateItem(String supplierId, boolean empty) {
                super.updateItem(supplierId, empty);
                if (empty || supplierId == null || supplierId.isEmpty()) {
                    setText("N/A");
                } else {
                    setText(supplierId);
                }
            }
        });
        
        // Add columns to table
        productsTable.getColumns().addAll(idCol, nameCol, priceCol, quantityCol, reorderCol, supplierCol);
        
        // Handle row selection
        productsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
        
        tableContainer.getChildren().addAll(tableTitle, productsTable);
        root.getChildren().add(tableContainer);
    }
    
    private void createFormSection() {
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text formTitle = new Text("Add/Edit Product");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        formTitle.setStyle("-fx-fill: #2c3e50;");
        
        // Create form fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setAlignment(Pos.CENTER);
        
        // Name field
        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter product name");
        nameField.setPrefWidth(200);
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        
        // Price field
        Label priceLabel = new Label("Price:");
        priceField = new TextField();
        priceField.setPromptText("Enter price");
        priceField.setPrefWidth(200);
        formGrid.add(priceLabel, 0, 1);
        formGrid.add(priceField, 1, 1);
        
        // Quantity field
        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        quantityField.setPrefWidth(200);
        formGrid.add(quantityLabel, 0, 2);
        formGrid.add(quantityField, 1, 2);
        
        // Reorder level field
        Label reorderLabel = new Label("Reorder Level:");
        reorderLevelField = new TextField();
        reorderLevelField.setPromptText("Enter reorder level");
        reorderLevelField.setPrefWidth(200);
        formGrid.add(reorderLabel, 0, 3);
        formGrid.add(reorderLevelField, 1, 3);
        
        // Supplier ID field
        Label supplierLabel = new Label("Supplier ID:");
        supplierIdField = new TextField();
        supplierIdField.setPromptText("Enter supplier ID (optional)");
        supplierIdField.setPrefWidth(200);
        formGrid.add(supplierLabel, 0, 4);
        formGrid.add(supplierIdField, 1, 4);
        
        formContainer.getChildren().addAll(formTitle, formGrid);
        root.getChildren().add(formContainer);
    }
    
    private void createActionButtons() {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));
        
        // Add button
        Button addBtn = new Button("Add Product");
        addBtn.setPrefSize(120, 40);
        addBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        addBtn.setOnAction(e -> addProduct());
        
        // Update button
        Button updateBtn = new Button("Update Product");
        updateBtn.setPrefSize(120, 40);
        updateBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        updateBtn.setOnAction(e -> updateProduct());
        
        // Delete button (admin only)
        Button deleteBtn = new Button("Delete Product");
        deleteBtn.setPrefSize(120, 40);
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        deleteBtn.setOnAction(e -> deleteProduct());
        deleteBtn.setVisible(userManager.getCurrentUser() != null && userManager.getCurrentUser().canDelete());
        
        // Clear form button
        Button clearBtn = new Button("Clear Form");
        clearBtn.setPrefSize(120, 40);
        clearBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        clearBtn.setOnAction(e -> clearForm());
        
        buttonContainer.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn);
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
    
    private void loadProductsData() {
        try {
            List<Product> products = inventoryManager.getProducts();
            productsData.clear();
            productsData.addAll(products);
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to load products", e.getMessage());
        }
    }
    
    private void performSearch(String name, String minPrice, String maxPrice, String supplierId) {
        try {
            List<Product> products = inventoryManager.getProducts();
            List<Product> filteredProducts = products.stream()
                .filter(product -> name.isEmpty() || product.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(product -> minPrice.isEmpty() || product.getPrice() >= Double.parseDouble(minPrice))
                .filter(product -> maxPrice.isEmpty() || product.getPrice() <= Double.parseDouble(maxPrice))
                .filter(product -> supplierId.isEmpty() || product.getSupplierId().equals(supplierId))
                .toList();
            
            productsData.clear();
            productsData.addAll(filteredProducts);
            
        } catch (NumberFormatException e) {
            InventoryManagementApp.showError("Search Error", "Invalid price format", "Please enter valid numbers for price range.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Search Error", "Failed to perform search", e.getMessage());
        }
    }
    
    private void populateForm(Product product) {
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
        productsTable.getSelectionModel().clearSelection();
    }
    
    private void addProduct() {
        try {
            // Validate input
            if (nameField.getText().trim().isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Name is required", "Please enter a product name.");
                return;
            }
            
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            int reorderLevel = Integer.parseInt(reorderLevelField.getText().trim());
            String supplierId = supplierIdField.getText().trim();
            
            if (price <= 0 || quantity < 0 || reorderLevel < 0) {
                InventoryManagementApp.showError("Validation Error", "Invalid values", "Price must be positive, quantity and reorder level must be non-negative.");
                return;
            }
            
            // TODO: Call inventoryManager.addProduct() with the form data
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Add Product", "Product Added", "Product will be added when integration is complete.");
            
            clearForm();
            loadProductsData();
            
        } catch (NumberFormatException e) {
            InventoryManagementApp.showError("Validation Error", "Invalid number format", "Please enter valid numbers for price, quantity, and reorder level.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to add product", e.getMessage());
        }
    }
    
    private void updateProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            InventoryManagementApp.showWarning("No Selection", "No product selected", "Please select a product to update.");
            return;
        }
        
        try {
            // Validate input
            if (nameField.getText().trim().isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Name is required", "Please enter a product name.");
                return;
            }
            
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            int reorderLevel = Integer.parseInt(reorderLevelField.getText().trim());
            String supplierId = supplierIdField.getText().trim();
            
            if (price <= 0 || quantity < 0 || reorderLevel < 0) {
                InventoryManagementApp.showError("Validation Error", "Invalid values", "Price must be positive, quantity and reorder level must be non-negative.");
                return;
            }
            
            // TODO: Call inventoryManager.updateProduct() with the form data
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Update Product", "Product Updated", "Product will be updated when integration is complete.");
            
            clearForm();
            loadProductsData();
            
        } catch (NumberFormatException e) {
            InventoryManagementApp.showError("Validation Error", "Invalid number format", "Please enter valid numbers for price, quantity, and reorder level.");
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to update product", e.getMessage());
        }
    }
    
    private void deleteProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            InventoryManagementApp.showWarning("No Selection", "No product selected", "Please select a product to delete.");
            return;
        }
        
        InventoryManagementApp.showConfirmation("Delete Product", "Confirm Deletion", 
            "Are you sure you want to delete '" + selectedProduct.getName() + "'?", () -> {
                try {
                    // TODO: Call inventoryManager.deleteProduct() with the selected product
                    // For now, show a placeholder message
                    InventoryManagementApp.showInfo("Delete Product", "Product Deleted", "Product will be deleted when integration is complete.");
                    
                    clearForm();
                    loadProductsData();
                    
                } catch (Exception e) {
                    InventoryManagementApp.showError("Error", "Failed to delete product", e.getMessage());
                }
            });
    }
    
    public VBox getRoot() {
        return root;
    }
}
