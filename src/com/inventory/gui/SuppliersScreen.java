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

public class SuppliersScreen {
    
    private final InventoryManagementApp app;
    private final VBox root;
    private TableView<Supplier> suppliersTable;
    private ObservableList<Supplier> suppliersData;
    private final SupplierManager supplierManager;
    private final UserManager userManager;
    
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
        
        // Create main container
        root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
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
    }
    
    private void createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));
        
        Text title = new Text("Suppliers Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setStyle("-fx-fill: #2c3e50;");
        
        Text subtitle = new Text("Manage supplier information and relationships");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitle.setStyle("-fx-fill: #7f8c8d;");
        
        header.getChildren().addAll(title, subtitle);
        root.getChildren().add(header);
    }
    
    private void createSuppliersTable() {
        VBox tableContainer = new VBox(15);
        tableContainer.setAlignment(Pos.CENTER);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text tableTitle = new Text("Suppliers Table");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableTitle.setStyle("-fx-fill: #2c3e50;");
        
        // Create table
        suppliersTable = new TableView<>();
        suppliersTable.setItems(suppliersData);
        suppliersTable.setPrefHeight(400);
        suppliersTable.setPlaceholder(new Label("No suppliers found"));
        
        // Create columns
        TableColumn<Supplier, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(100);
        
        TableColumn<Supplier, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(250);
        
        TableColumn<Supplier, String> contactCol = new TableColumn<>("Contact Information");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        contactCol.setPrefWidth(300);
        
        // Add columns to table
        suppliersTable.getColumns().addAll(idCol, nameCol, contactCol);
        
        // Handle row selection
        suppliersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
        
        tableContainer.getChildren().addAll(tableTitle, suppliersTable);
        root.getChildren().add(tableContainer);
    }
    
    private void createFormSection() {
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text formTitle = new Text("Add/Edit Supplier");
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
        nameField.setPromptText("Enter supplier name");
        nameField.setPrefWidth(300);
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        
        // Contact info field
        Label contactLabel = new Label("Contact Information:");
        contactInfoField = new TextField();
        contactInfoField.setPromptText("Enter contact information");
        contactInfoField.setPrefWidth(300);
        formGrid.add(contactLabel, 0, 1);
        formGrid.add(contactInfoField, 1, 1);
        
        formContainer.getChildren().addAll(formTitle, formGrid);
        root.getChildren().add(formContainer);
    }
    
    private void createActionButtons() {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));
        
        // Add button
        Button addBtn = new Button("Add Supplier");
        addBtn.setPrefSize(120, 40);
        addBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        addBtn.setOnAction(e -> addSupplier());
        
        // Update button
        Button updateBtn = new Button("Update Supplier");
        updateBtn.setPrefSize(120, 40);
        updateBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        updateBtn.setOnAction(e -> updateSupplier());
        
        // Delete button (admin only)
        Button deleteBtn = new Button("Delete Supplier");
        deleteBtn.setPrefSize(120, 40);
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        deleteBtn.setOnAction(e -> deleteSupplier());
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
    
    private void loadSuppliersData() {
        try {
            // TODO: Get suppliers from SupplierManager
            // For now, create some sample data
            suppliersData.clear();
            // suppliersData.addAll(supplierManager.getSuppliers());
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
            
            // TODO: Call supplierManager.addSupplier() with the form data
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Add Supplier", "Supplier Added", "Supplier will be added when integration is complete.");
            
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
            
            // TODO: Call supplierManager.updateSupplier() with the form data
            // For now, show a placeholder message
            InventoryManagementApp.showInfo("Update Supplier", "Supplier Updated", "Supplier will be updated when integration is complete.");
            
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
                    // TODO: Call supplierManager.deleteSupplier() with the selected supplier
                    // For now, show a placeholder message
                    InventoryManagementApp.showInfo("Delete Supplier", "Supplier Deleted", "Supplier will be deleted when integration is complete.");
                    
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
}
