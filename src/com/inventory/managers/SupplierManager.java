package com.inventory.managers;

import java.util.*;
import java.util.Scanner;
import com.inventory.models.Supplier;
import com.inventory.DataStore;

public class SupplierManager {
    private List<Supplier> suppliers;
    private int supplierIdCounter;
    
    public SupplierManager() {
        this.suppliers = new ArrayList<>();
        this.supplierIdCounter = 1;
        loadSuppliers();
    }
    
    // Supplier Management Methods
    public void addSupplier(Scanner scanner) {
        System.out.println("\n=== Add New Supplier ===");
        
        // Get supplier name
        System.out.print("Enter supplier name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Supplier name cannot be empty.");
            return;
        }
        
        // Get contact information
        System.out.print("Enter contact information: ");
        String contactInfo = scanner.nextLine().trim();
        
        if (contactInfo.isEmpty()) {
            System.out.println("Error: Contact information cannot be empty.");
            return;
        }
        
        // Generate supplier ID
        String id = "S" + String.format("%03d", supplierIdCounter++);
        
        // Create and add supplier
        Supplier supplier = new Supplier(id, name, contactInfo);
        suppliers.add(supplier);
        
        System.out.println("Supplier added successfully! Supplier ID: " + id);
    }
    
    public void viewSuppliers() {
        System.out.println("\n=== All Suppliers ===");
        
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found.");
            return;
        }
        
        // Print header
        System.out.printf("%-10s %-30s %-30s%n", 
                         "ID", "Name", "Contact Information");
        System.out.println("------------------------------------------------------------");
        
        // Print each supplier
        for (Supplier supplier : suppliers) {
            System.out.printf("%-10s %-30s %-30s%n",
                             supplier.getId(), 
                             supplier.getName(), 
                             supplier.getContactInfo());
        }
    }
    
    public void updateSupplier(Scanner scanner) {
        System.out.println("\n=== Update Supplier ===");
        
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found to update.");
            return;
        }
        
        // Show current suppliers
        viewSuppliers();
        
        // Get supplier ID
        System.out.print("\nEnter supplier ID to update: ");
        String id = scanner.nextLine().trim();
        
        Supplier supplier = findSupplierById(id);
        if (supplier == null) {
            System.out.println("Supplier not found.");
            return;
        }
        
        System.out.println("Current supplier details: " + supplier);
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        // Update name
        System.out.print("Name [" + supplier.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            supplier.setName(name);
            System.out.println("Name updated to: " + name);
        }
        
        // Update contact information
        System.out.print("Contact Information [" + supplier.getContactInfo() + "]: ");
        String contactInfo = scanner.nextLine().trim();
        if (!contactInfo.isEmpty()) {
            supplier.setContactInfo(contactInfo);
            System.out.println("Contact information updated to: " + contactInfo);
        }
        
        System.out.println("Supplier updated successfully!");
    }
    
    public void deleteSupplier(Scanner scanner, boolean canDelete) {
        System.out.println("\n=== Delete Supplier ===");
        
        if (!canDelete) {
            System.out.println("✗ Access denied. You do not have permission to delete suppliers.");
            return;
        }
        
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found to delete.");
            return;
        }
        
        // Show current suppliers
        viewSuppliers();
        
        // Get supplier ID
        System.out.print("\nEnter supplier ID to delete: ");
        String id = scanner.nextLine().trim();
        
        Supplier supplier = findSupplierById(id);
        if (supplier == null) {
            System.out.println("Supplier not found.");
            return;
        }
        
        // Confirm deletion
        System.out.print("Are you sure you want to delete " + supplier.getName() + " (y/n)? ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            suppliers.remove(supplier);
            System.out.println("Supplier deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    // Helper method to find supplier by ID
    private Supplier findSupplierById(String id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(id)) {
                return supplier;
            }
        }
        return null;
    }
    
    // Data Persistence Methods
    private void loadSuppliers() {
        List<Supplier> loadedSuppliers = DataStore.loadData(DataStore.SUPPLIERS_FILE);
        if (loadedSuppliers != null) {
            this.suppliers = loadedSuppliers;
            // Update supplier ID counter to avoid conflicts
            updateSupplierIdCounter();
            System.out.println("✓ Loaded " + suppliers.size() + " suppliers from storage");
        } else {
            System.out.println("ℹ Starting with empty supplier list");
        }
    }
    
    public void saveSuppliers() {
        boolean success = DataStore.saveData(DataStore.SUPPLIERS_FILE, suppliers);
        if (success) {
            System.out.println("✓ Supplier data saved successfully");
        } else {
            System.err.println("✗ Failed to save supplier data");
        }
    }
    
    private void updateSupplierIdCounter() {
        if (!suppliers.isEmpty()) {
            // Find the highest supplier ID number and set counter accordingly
            int maxId = 0;
            for (Supplier supplier : suppliers) {
                try {
                    String idStr = supplier.getId().substring(1); // Remove 'S' prefix
                    int idNum = Integer.parseInt(idStr);
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
            this.supplierIdCounter = maxId + 1;
        }
    }
}
