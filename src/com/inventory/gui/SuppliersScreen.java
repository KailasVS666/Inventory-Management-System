package com.inventory.gui;

import javafx.scene.layout.VBox;

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

        // Initialize UI components
        suppliersTable = new TableView<>();
        suppliersTable.setItems(suppliersData);
        nameField = new TextField();
        contactInfoField = new TextField();

        // Create main container
        root = new VBox(25);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(25));
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

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
        scrollPane.setStyle("-fx-background: transparent;");
        scrollPane.setPannable(true);
        scrollPane.setMinViewportHeight(400);
        scrollPane.setMinViewportWidth(600);
        VBox scrollRoot = new VBox();
        scrollRoot.getChildren().add(scrollPane);
        this.root = scrollRoot;

        applyCurrentTheme();
    }
    
    private void createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setBackground(new Background(new BackgroundFill(Color.DARKCYAN, new CornerRadii(15), Insets.EMPTY)));
        Text title = new Text("🏢 Suppliers Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setFill(Color.WHITE);
        Text subtitle = new Text("Manage your supplier relationships");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        subtitle.setFill(Color.LIGHTGRAY);
        VBox titleBox = new VBox(8);
        titleBox.getChildren().addAll(title, subtitle);
        header.getChildren().add(titleBox);
        VBox.setMargin(header, new Insets(0, 0, 30, 0));
        root.getChildren().add(header);
    }
    
    private void createSuppliersTable() {
        VBox tableContainer = new VBox(20);
        tableContainer.setPadding(new Insets(25));
        tableContainer.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, new CornerRadii(20), Insets.EMPTY)));
        Label tableTitle = new Label("📋 Suppliers List");
        tableTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        tableTitle.setTextFill(Color.DARKSLATEGRAY);
        suppliersTable.setPrefHeight(350);
        suppliersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Supplier, String> idCol = new TableColumn<>("🆔 Supplier ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);
        TableColumn<Supplier, String> nameCol = new TableColumn<>("🏢 Company Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        TableColumn<Supplier, String> contactCol = new TableColumn<>("📞 Contact Info");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        contactCol.setPrefWidth(250);
        suppliersTable.getColumns().addAll(idCol, nameCol, contactCol);
        suppliersTable.setRowFactory(tv -> {
            TableRow<Supplier> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected && !row.isEmpty()) {
                    row.setStyle("-fx-background-color: #e0f7fa;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });
        suppliersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadSupplierToForm(newSelection);
            }
        });
        tableContainer.getChildren().addAll(tableTitle, suppliersTable);
        root.getChildren().add(tableContainer);
    }
    
    private void createFormSection() {
        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(25));
        formContainer.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, new CornerRadii(20), Insets.EMPTY)));
        Label formTitle = new Label("✏️ Add/Edit Supplier");
        formTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        formTitle.setTextFill(Color.DARKSLATEGRAY);
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(20));
        styleFormField(nameField, "Company Name");
        styleFormField(contactInfoField, "Contact Information");
        formGrid.add(createFormLabel("🏢 Company Name:"), 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(createFormLabel("📞 Contact Info:"), 0, 1);
        formGrid.add(contactInfoField, 1, 1);
        formContainer.getChildren().addAll(formTitle, formGrid);
        root.getChildren().add(formContainer);
    }
    
    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        label.setTextFill(Color.DARKSLATEGRAY);
        return label;
    }
    
    private void styleFormField(TextField field, String prompt) {
        field.setPromptText(prompt);
        field.setPrefHeight(40);
        field.setPrefWidth(300);
        field.setFont(Font.font("Segoe UI", 14));
        field.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY)));
        field.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2))));
    }
    
    private void createActionButtons() {
        VBox buttonContainer = new VBox(20);
        buttonContainer.setPadding(new Insets(25));
        buttonContainer.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, new CornerRadii(20), Insets.EMPTY)));
        Label buttonTitle = new Label("🎯 Supplier Actions");
        buttonTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        buttonTitle.setTextFill(Color.DARKSLATEGRAY);
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button addBtn = new Button("➕ Add Supplier");
        addBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        addBtn.setTextFill(Color.DARKSLATEGRAY);
        addBtn.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(25), Insets.EMPTY)));
        addBtn.setOnAction(e -> addSupplier());
        Button updateBtn = new Button("✏️ Update Supplier");
        updateBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        updateBtn.setTextFill(Color.DARKSLATEGRAY);
        updateBtn.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(25), Insets.EMPTY)));
        updateBtn.setOnAction(e -> updateSupplier());
        Button deleteBtn = new Button("🗑️ Delete Supplier");
        deleteBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        deleteBtn.setTextFill(Color.DARKSLATEGRAY);
        deleteBtn.setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, new CornerRadii(25), Insets.EMPTY)));
        deleteBtn.setOnAction(e -> deleteSupplier());
        Button clearBtn = new Button("🧹 Clear Form");
        clearBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        clearBtn.setTextFill(Color.DARKSLATEGRAY);
        clearBtn.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(25), Insets.EMPTY)));
        clearBtn.setOnAction(e -> clearForm());
        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn);
        buttonContainer.getChildren().addAll(buttonTitle, buttonBox);
        root.getChildren().add(buttonContainer);
    }
    
    private void createBackButton() {
        VBox backContainer = new VBox(20);
        backContainer.setPadding(new Insets(25));
        backContainer.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(20), Insets.EMPTY)));
        Label backTitle = new Label("🏠 Navigation");
        backTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        backTitle.setTextFill(Color.DARKSLATEGRAY);
        HBox backBox = new HBox(20);
        backBox.setAlignment(Pos.CENTER);
        Button backBtn = new Button("🏠 Back to Dashboard");
        backBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        backBtn.setTextFill(Color.DARKSLATEGRAY);
        backBtn.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, new CornerRadii(25), Insets.EMPTY)));
        backBtn.setOnAction(e -> app.showDashboard());
        backBox.getChildren().add(backBtn);
        backContainer.getChildren().addAll(backTitle, backBox);
        root.getChildren().add(backContainer);
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

        // Set root background only once
        root.setStyle("-fx-background-color: " + bg + ";");

        // Header styling
        if (root.getChildren().size() > 0 && root.getChildren().get(0) instanceof HBox) {
            HBox headerBox = (HBox) root.getChildren().get(0);
            headerBox.setStyle("-fx-background-color: " + header + "; -fx-background-radius: 15;");
            // Only set style for title and subtitle (first VBox)
            if (headerBox.getChildren().size() > 0 && headerBox.getChildren().get(0) instanceof VBox) {
                VBox titleBox = (VBox) headerBox.getChildren().get(0);
                for (javafx.scene.Node innerNode : titleBox.getChildren()) {
                    if (innerNode instanceof Text) {
                        innerNode.setStyle("-fx-fill: " + textHeader + ";");
                    }
                }
            }
        }

        // Style VBoxes (cards) and their children
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
    
    public VBox getRoot() {
        return root;
    }
}
