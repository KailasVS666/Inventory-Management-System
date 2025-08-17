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
import com.inventory.models.Supplier;
import com.inventory.managers.SupplierManager;
import com.inventory.managers.UserManager;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class SuppliersScreen {
    
    private InventoryManagementApp app;
    private VBox root;
    private TableView<Supplier> suppliersTable;
    private ObservableList<Supplier> suppliersData;
    private SupplierManager supplierManager;
    private UserManager userManager;
    
    // Form fields
    private TextField nameField;
    private TextField contactInfoField;
    
    public SuppliersScreen(InventoryManagementApp app) {
        this.app = app;
        this.supplierManager = InventoryManagementApp.getSupplierManager();
        this.userManager = InventoryManagementApp.getUserManager();
        
        // Initialize data
        suppliersData = FXCollections.observableArrayList();
        loadSuppliersData();
        
        // Create main container with beautiful background
        root = new VBox(25);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #45B7D1 0%, #96CEB4 100%); -fx-background-radius: 0;");
        
        // Create header
        createHeader();
        
        // Create table
        createSuppliersTable();
        
        // Create form section
        createFormSection();
        
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
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        // Improve scroll pane behavior
        scrollPane.setPannable(true);
        scrollPane.setMinViewportHeight(400);
        scrollPane.setMinViewportWidth(600);
        
        // Set the scroll pane as the root
        VBox scrollRoot = new VBox();
        scrollRoot.getChildren().add(scrollPane);
        this.root = scrollRoot;
    }
    
    private void createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: linear-gradient(to right, #45B7D1 0%, #96CEB4 100%); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        
        Text title = new Text("🏢 Suppliers Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        Text subtitle = new Text("Manage your supplier relationships");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        subtitle.setStyle("-fx-fill: rgba(255,255,255,0.9); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 0);");
        
        VBox titleBox = new VBox(8);
        titleBox.getChildren().addAll(title, subtitle);
        
        header.getChildren().add(titleBox);
        VBox.setMargin(header, new Insets(0, 0, 30, 0));
        root.getChildren().add(header);
    }
    
    private void createSuppliersTable() {
        VBox tableContainer = new VBox(20);
        tableContainer.setPadding(new Insets(25));
        tableContainer.setStyle("-fx-background-color: linear-gradient(135deg, #667eea 0%, #764ba2 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label tableTitle = new Label("📋 Suppliers List");
        tableTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        // Style the table
        suppliersTable.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        suppliersTable.setPrefHeight(350);
        
        // Create table columns with better styling
        TableColumn<Supplier, String> idCol = new TableColumn<>("🆔 Supplier ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);
        idCol.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6B6B 0%, #4ECDC4 100%); -fx-text-fill: white;");
        
        TableColumn<Supplier, String> nameCol = new TableColumn<>("🏢 Company Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        nameCol.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6B6B 0%, #4ECDC4 100%); -fx-text-fill: white;");
        
        TableColumn<Supplier, String> contactCol = new TableColumn<>("📞 Contact Info");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        contactCol.setPrefWidth(250);
        contactCol.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6B6B 0%, #4ECDC4 100%); -fx-text-fill: white;");
        
        suppliersTable.getColumns().addAll(idCol, nameCol, contactCol);
        
        // Row selection with enhanced styling
        suppliersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadSupplierToForm(newSelection);
                // Highlight selected row
                suppliersTable.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0);");
            }
        });
        
        tableContainer.getChildren().addAll(tableTitle, suppliersTable);
        root.getChildren().add(tableContainer);
    }
    
    private void createFormSection() {
        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(25));
        formContainer.setStyle("-fx-background-color: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label formTitle = new Label("✏️ Add/Edit Supplier");
        formTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        // Form fields with enhanced styling
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(20));
        
        // Style form fields
        styleFormField(nameField, "Company Name");
        styleFormField(contactInfoField, "Contact Information");
        
        // Add form fields to grid
        formGrid.add(createFormLabel("🏢 Company Name:"), 0, 0);
        formGrid.add(nameField, 1, 0);
        
        formGrid.add(createFormLabel("📞 Contact Info:"), 0, 1);
        formGrid.add(contactInfoField, 1, 1);
        
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
        field.setPrefWidth(300);
        field.setStyle("-fx-font-size: 14; -fx-background-radius: 20; -fx-background-color: rgba(255,255,255,0.9); -fx-border-radius: 20; -fx-border-color: white; -fx-border-width: 2; -fx-padding: 8 15;");
    }
    
    private void createActionButtons() {
        VBox buttonContainer = new VBox(20);
        buttonContainer.setPadding(new Insets(25));
        buttonContainer.setStyle("-fx-background-color: linear-gradient(135deg, #fa709a 0%, #fee140 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label buttonTitle = new Label("🎯 Supplier Actions");
        buttonTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("➕ Add Supplier");
        addBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        addBtn.setOnAction(e -> addSupplier());
        
        Button updateBtn = new Button("✏️ Update Supplier");
        updateBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        updateBtn.setOnAction(e -> updateSupplier());
        
        Button deleteBtn = new Button("🗑️ Delete Supplier");
        deleteBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        deleteBtn.setOnAction(e -> deleteSupplier());
        
        Button clearBtn = new Button("🧹 Clear Form");
        clearBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        clearBtn.setOnAction(e -> clearForm());
        
        // Add hover effects for all buttons
        addHoverEffects(addBtn);
        addHoverEffects(updateBtn);
        addHoverEffects(deleteBtn);
        addHoverEffects(clearBtn);
        
        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn);
        
        buttonContainer.getChildren().addAll(buttonTitle, buttonBox);
        root.getChildren().add(buttonContainer);
    }
    
    private void createBackButton() {
        VBox backContainer = new VBox(20);
        backContainer.setPadding(new Insets(25));
        backContainer.setStyle("-fx-background-color: linear-gradient(135deg, #667eea 0%, #764ba2 100%); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 0); -fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label backTitle = new Label("🏠 Navigation");
        backTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 0);");
        
        HBox backBox = new HBox(20);
        backBox.setAlignment(Pos.CENTER);
        
        Button backBtn = new Button("🏠 Back to Dashboard");
        backBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        backBtn.setOnAction(e -> app.showDashboard());
        
        // Add hover effects for back button
        addHoverEffects(backBtn);
        
        backBox.getChildren().add(backBtn);
        backContainer.getChildren().addAll(backTitle, backBox);
        root.getChildren().add(backContainer);
    }
    
    private void addHoverEffects(Button button) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 3; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 0);");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25; -fx-padding: 15 30; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0);");
        });
    }
    
    private void loadSuppliersData() {
        try {
            List<Supplier> suppliers = supplierManager.getSuppliers();
            suppliersData.clear();
            suppliersData.addAll(suppliers);
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to load suppliers", e.getMessage());
        }
    }
    
    private void populateForm(Supplier supplier) {
        if (supplier != null) {
            nameField.setText(supplier.getName());
            contactInfoField.setText(supplier.getContactInfo());
        }
    }

    private void loadSupplierToForm(Supplier supplier) {
        if (supplier != null) {
            nameField.setText(supplier.getName());
            contactInfoField.setText(supplier.getContactInfo());
        }
    }
    
    private void clearForm() {
        nameField.clear();
        contactInfoField.clear();
        suppliersTable.getSelectionModel().clearSelection();
    }
    
    private void addSupplier() {
        try {
            // Validate input
            if (nameField.getText().trim().isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Name is required", "Please enter a supplier name.");
                return;
            }
            
            if (contactInfoField.getText().trim().isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Contact information is required", "Please enter contact information.");
                return;
            }
            
            Supplier newSupplier = new Supplier(
                supplierManager.generateSupplierId(),
                nameField.getText().trim(),
                contactInfoField.getText().trim()
            );
            
            supplierManager.addSupplier(newSupplier);
            InventoryManagementApp.showInfo("Add Supplier", "Supplier Added", "Supplier successfully added.");
            
            clearForm();
            loadSuppliersData();
            
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to add supplier", e.getMessage());
        }
    }
    
    private void updateSupplier() {
        Supplier selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            InventoryManagementApp.showWarning("No Selection", "No supplier selected", "Please select a supplier to update.");
            return;
        }
        
        try {
            // Validate input
            if (nameField.getText().trim().isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Name is required", "Please enter a supplier name.");
                return;
            }
            
            if (contactInfoField.getText().trim().isEmpty()) {
                InventoryManagementApp.showError("Validation Error", "Contact information is required", "Please enter contact information.");
                return;
            }
            
            selectedSupplier.setName(nameField.getText().trim());
            selectedSupplier.setContactInfo(contactInfoField.getText().trim());
            
            supplierManager.updateSupplier(selectedSupplier);
            InventoryManagementApp.showInfo("Update Supplier", "Supplier Updated", "Supplier successfully updated.");
            
            clearForm();
            loadSuppliersData();
            
        } catch (Exception e) {
            InventoryManagementApp.showError("Error", "Failed to update supplier", e.getMessage());
        }
    }
    
    private void deleteSupplier() {
        Supplier selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            InventoryManagementApp.showWarning("No Selection", "No supplier selected", "Please select a supplier to delete.");
            return;
        }
        
        InventoryManagementApp.showConfirmation("Delete Supplier", "Confirm Deletion", 
            "Are you sure you want to delete '" + selectedSupplier.getName() + "'?", () -> {
                try {
                    supplierManager.deleteSupplier(selectedSupplier.getId());
                    InventoryManagementApp.showInfo("Delete Supplier", "Supplier Deleted", "Supplier successfully deleted.");
                    
                    clearForm();
                    loadSuppliersData();
                    
                } catch (Exception e) {
                    InventoryManagementApp.showError("Error", "Failed to delete supplier", e.getMessage());
                }
            });
    }
    
    public VBox getRoot() {
        return root;
    }

    private void applyCurrentTheme() {
        String theme = InventoryManagementApp.getCurrentTheme();
        
        if ("dark".equals(theme)) {
            // Dark theme
            root.setStyle("-fx-background-color: #2c3e50;");
            
            // Header styling
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof HBox) {
                    HBox header = (HBox) node;
                    for (javafx.scene.Node child : header.getChildren()) {
                        if (child instanceof javafx.scene.text.Text) {
                            javafx.scene.text.Text text = (javafx.scene.text.Text) child;
                            if (text.getText().contains("Suppliers Management")) {
                                text.setFill(Color.WHITE);
                            } else {
                                text.setFill(Color.web("#bdc3c7"));
                            }
                        }
                    }
                }
            }
            
            // Table container
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof VBox && ((VBox) node).getChildren().size() > 0) {
                    VBox container = (VBox) node;
                    if (container.getChildren().get(0) instanceof Label && 
                        ((Label) container.getChildren().get(0)).getText().contains("Supplier List")) {
                        container.setStyle("-fx-background-color: #34495e; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
                        
                        for (javafx.scene.Node child : container.getChildren()) {
                            if (child instanceof Label) {
                                Label label = (Label) child;
                                label.setStyle("-fx-text-fill: #ecf0f1;");
                            }
                        }
                    }
                }
            }
            
            // Form container
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof VBox && ((VBox) node).getChildren().size() > 0) {
                    VBox container = (VBox) node;
                    if (container.getChildren().get(0) instanceof Label && 
                        ((Label) container.getChildren().get(0)).getText().contains("Add/Edit Supplier")) {
                        container.setStyle("-fx-background-color: #34495e; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 0);");
                        
                        for (javafx.scene.Node child : container.getChildren()) {
                            if (child instanceof Label) {
                                Label label = (Label) child;
                                label.setStyle("-fx-text-fill: #ecf0f1;");
                            }
                        }
                    }
                }
            }
            
            // Buttons
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof HBox) {
                    HBox buttonBox = (HBox) node;
                    for (javafx.scene.Node child : buttonBox.getChildren()) {
                        if (child instanceof Button) {
                            Button button = (Button) child;
                            if (button.getText().contains("Add")) {
                                button.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Update")) {
                                button.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Delete")) {
                                button.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Clear")) {
                                button.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Back")) {
                                button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            }
                        }
                    }
                }
            }
            
        } else {
            // Light theme
            root.setStyle("-fx-background-color: #f5f5f5;");
            
            // Header styling
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof HBox) {
                    HBox header = (HBox) node;
                    for (javafx.scene.Node child : header.getChildren()) {
                        if (child instanceof javafx.scene.text.Text) {
                            javafx.scene.text.Text text = (javafx.scene.text.Text) child;
                            if (text.getText().contains("Suppliers Management")) {
                                text.setFill(Color.web("#2c3e50"));
                            } else {
                                text.setFill(Color.web("#7f8c8d"));
                            }
                        }
                    }
                }
            }
            
            // Table container
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof VBox && ((VBox) node).getChildren().size() > 0) {
                    VBox container = (VBox) node;
                    if (container.getChildren().get(0) instanceof Label && 
                        ((Label) container.getChildren().get(0)).getText().contains("Supplier List")) {
                        container.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
                        
                        for (javafx.scene.Node child : container.getChildren()) {
                            if (child instanceof Label) {
                                Label label = (Label) child;
                                label.setStyle("-fx-text-fill: #2c3e50;");
                            }
                        }
                    }
                }
            }
            
            // Form container
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof VBox && ((VBox) node).getChildren().size() > 0) {
                    VBox container = (VBox) node;
                    if (container.getChildren().get(0) instanceof Label && 
                        ((Label) container.getChildren().get(0)).getText().contains("Add/Edit Supplier")) {
                        container.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
                        
                        for (javafx.scene.Node child : container.getChildren()) {
                            if (child instanceof Label) {
                                Label label = (Label) child;
                                label.setStyle("-fx-text-fill: #2c3e50;");
                            }
                        }
                    }
                }
            }
            
            // Buttons
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof HBox) {
                    HBox buttonBox = (HBox) node;
                    for (javafx.scene.Node child : buttonBox.getChildren()) {
                        if (child instanceof Button) {
                            Button button = (Button) child;
                            if (button.getText().contains("Add")) {
                                button.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Update")) {
                                button.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Delete")) {
                                button.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Clear")) {
                                button.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            } else if (button.getText().contains("Back")) {
                                button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                            }
                        }
                    }
                }
            }
        }
    }
}
