package com.inventory.managers;

import java.util.*;
import java.util.Scanner;
import com.inventory.models.Product;
import com.inventory.models.Order;

public class ReportManager {
    private InventoryManager inventoryManager;
    private OrderManager orderManager;
    
    public ReportManager(InventoryManager inventoryManager, OrderManager orderManager) {
        this.inventoryManager = inventoryManager;
        this.orderManager = orderManager;
    }
    
    // Reports & Analytics Methods
    public void viewLowStockProducts() {
        System.out.println("\n=== Low Stock Products Report ===");
        
        // Get products from InventoryManager
        List<Product> products = getProductsFromInventory();
        
        if (products.isEmpty()) {
            System.out.println("No products found in inventory.");
            return;
        }
        
        // Filter low stock products
        List<Product> lowStockProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity() <= product.getReorderLevel()) {
                lowStockProducts.add(product);
            }
        }
        
        if (lowStockProducts.isEmpty()) {
            System.out.println("No low stock products found.");
            System.out.println("All products have sufficient stock levels.");
            return;
        }
        
        // Print header
        System.out.printf("%-10s %-20s %-10s %-15s%n", 
                         "ID", "Name", "Quantity", "Reorder Level");
        System.out.println("------------------------------------------------");
        
        // Print each low stock product
        for (Product product : lowStockProducts) {
            System.out.printf("%-10s %-20s %-10d %-15d%n",
                             product.getId(), 
                             product.getName(), 
                             product.getQuantity(), 
                             product.getReorderLevel());
        }
        
        // Show summary
        System.out.println("\n=== Summary ===");
        System.out.println("Total Low Stock Products: " + lowStockProducts.size());
        System.out.println("Total Products in Inventory: " + products.size());
        
        // Show critical items (quantity = 0)
        long criticalItems = lowStockProducts.stream()
                                           .filter(p -> p.getQuantity() == 0)
                                           .count();
        if (criticalItems > 0) {
            System.out.println("⚠️  CRITICAL: " + criticalItems + " products are OUT OF STOCK!");
        }
    }
    
    public void viewTotalInventoryValue() {
        System.out.println("\n=== Total Inventory Value Report ===");
        
        // Get products from InventoryManager
        List<Product> products = getProductsFromInventory();
        
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        
        // Calculate total value
        double totalValue = 0.0;
        int totalItems = 0;
        
        // Print header
        System.out.printf("%-10s %-20s %-12s %-10s %-15s%n", 
                         "ID", "Name", "Price", "Quantity", "Item Value");
        System.out.println("------------------------------------------------------------");
        
        // Print each product with its value
        for (Product product : products) {
            double itemValue = product.getPrice() * product.getQuantity();
            totalValue += itemValue;
            totalItems += product.getQuantity();
            
            System.out.printf("%-10s %-20s $%-11.2f %-10d $%-14.2f%n",
                             product.getId(), 
                             product.getName(), 
                             product.getPrice(), 
                             product.getQuantity(), 
                             itemValue);
        }
        
        // Show summary
        System.out.println("------------------------------------------------------------");
        System.out.println("\n=== Inventory Summary ===");
        System.out.println("Total Products: " + products.size());
        System.out.println("Total Items: " + totalItems);
        System.out.printf("Total Inventory Value: $%.2f%n", totalValue);
        
        // Show average values
        if (products.size() > 0) {
            double avgItemValue = totalValue / totalItems;
            double avgProductValue = totalValue / products.size();
            System.out.printf("Average Item Value: $%.2f%n", avgItemValue);
            System.out.printf("Average Product Value: $%.2f%n", avgProductValue);
        }
    }
    
    public void viewSalesSummary() {
        System.out.println("\n=== Sales Summary Report ===");
        
        // Get orders from OrderManager
        List<Order> orders = getOrdersFromOrderManager();
        
        if (orders.isEmpty()) {
            System.out.println("No sales found.");
            System.out.println("No orders have been processed yet.");
            return;
        }
        
        // Get current date info
        Calendar now = Calendar.getInstance();
        int currentDay = now.get(Calendar.DAY_OF_MONTH);
        int currentMonth = now.get(Calendar.MONTH);
        int currentYear = now.get(Calendar.YEAR);
        
        // Calculate daily and monthly totals
        double dailyTotal = 0.0;
        double monthlyTotal = 0.0;
        int dailyOrders = 0;
        int monthlyOrders = 0;
        
        // For this implementation, we'll use order IDs to simulate date-based filtering
        // In a real application, you'd have actual date fields in the Order class
        for (Order order : orders) {
            // Simulate daily sales (orders with even IDs for demo purposes)
            // In reality, you'd check actual order dates
            if (Integer.parseInt(order.getId().substring(1)) % 2 == 0) {
                dailyTotal += order.getTotalAmount();
                dailyOrders++;
            }
            
            // Simulate monthly sales (all orders for demo purposes)
            monthlyTotal += order.getTotalAmount();
            monthlyOrders++;
        }
        
        // Print order details
        System.out.println("=== Recent Orders ===");
        System.out.printf("%-10s %-10s %-10s %-15s%n", 
                         "Order ID", "Product ID", "Quantity", "Total Amount");
        System.out.println("------------------------------------------------");
        
        for (Order order : orders) {
            System.out.printf("%-10s %-10s %-10d $%-14.2f%n",
                             order.getId(), 
                             order.getProductId(), 
                             order.getQuantity(), 
                             order.getTotalAmount());
        }
        
        // Show summary
        System.out.println("\n=== Sales Summary ===");
        System.out.println("Total Orders: " + orders.size());
        System.out.printf("Total Sales Value: $%.2f%n", monthlyTotal);
        System.out.printf("Daily Sales (Simulated): $%.2f (%d orders)%n", dailyTotal, dailyOrders);
        System.out.printf("Monthly Sales (Simulated): $%.2f (%d orders)%n", monthlyTotal, monthlyOrders);
        
        // Show averages
        if (orders.size() > 0) {
            double avgOrderValue = monthlyTotal / orders.size();
            System.out.printf("Average Order Value: $%.2f%n", avgOrderValue);
        }
        
        // Note about simulation
        System.out.println("\nNote: Daily/Monthly filtering is simulated for demo purposes.");
        System.out.println("In a production system, orders would have actual date fields.");
    }
    
    // Helper methods to access data from other managers
    private List<Product> getProductsFromInventory() {
        // Access products from InventoryManager
        // We'll need to add a method to InventoryManager to get the products list
        return inventoryManager.getProducts();
    }
    
    private List<Order> getOrdersFromOrderManager() {
        // Access orders from OrderManager
        // We'll need to add a method to OrderManager to get the orders list
        return orderManager.getOrders();
    }
    
    // Export Methods (Phase 8)
    public void exportLowStockReport() {
        System.out.println("\n=== Export Low Stock Report ===");
        
        List<Product> products = getProductsFromInventory();
        if (products.isEmpty()) {
            System.out.println("No products found to export.");
            return;
        }
        
        // Filter low stock products
        List<Product> lowStockProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity() <= product.getReorderLevel()) {
                lowStockProducts.add(product);
            }
        }
        
        if (lowStockProducts.isEmpty()) {
            System.out.println("No low stock products found to export.");
            return;
        }
        
        // Create exports directory if it doesn't exist
        createExportsDirectory();
        
        // Generate filename with timestamp
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "exports/low_stock_report_" + timestamp + ".csv";
        
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
            // Write CSV header
            writer.println("ID,Name,Quantity,Reorder Level,Status,Supplier ID");
            
            // Write data rows
            for (Product product : lowStockProducts) {
                String status = product.getQuantity() <= product.getReorderLevel() ? "LOW STOCK" : "OK";
                String supplierId = product.getSupplierId().isEmpty() ? "N/A" : product.getSupplierId();
                
                writer.printf("%s,%s,%d,%d,%s,%s%n",
                             product.getId(),
                             product.getName(),
                             product.getQuantity(),
                             product.getReorderLevel(),
                             status,
                             supplierId);
            }
            
            System.out.println("✓ Low Stock Report exported successfully to: " + filename);
            System.out.println("Total products exported: " + lowStockProducts.size());
            
        } catch (java.io.IOException e) {
            System.err.println("✗ Error exporting report: " + e.getMessage());
        }
    }
    
    public void exportInventoryValueReport() {
        System.out.println("\n=== Export Inventory Value Report ===");
        
        List<Product> products = getProductsFromInventory();
        if (products.isEmpty()) {
            System.out.println("No products found to export.");
            return;
        }
        
        // Create exports directory if it doesn't exist
        createExportsDirectory();
        
        // Generate filename with timestamp
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "exports/inventory_value_report_" + timestamp + ".csv";
        
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
            // Write CSV header
            writer.println("ID,Name,Price,Quantity,Item Value,Supplier ID");
            
            // Write data rows
            for (Product product : products) {
                double itemValue = product.getPrice() * product.getQuantity();
                String supplierId = product.getSupplierId().isEmpty() ? "N/A" : product.getSupplierId();
                
                writer.printf("%s,%s,%.2f,%d,%.2f,%s%n",
                             product.getId(),
                             product.getName(),
                             product.getPrice(),
                             product.getQuantity(),
                             itemValue,
                             supplierId);
            }
            
            System.out.println("✓ Inventory Value Report exported successfully to: " + filename);
            System.out.println("Total products exported: " + products.size());
            
        } catch (java.io.IOException e) {
            System.err.println("✗ Error exporting report: " + e.getMessage());
        }
    }
    
    private void createExportsDirectory() {
        java.io.File exportsDir = new java.io.File("exports");
        if (!exportsDir.exists()) {
            if (exportsDir.mkdir()) {
                System.out.println("ℹ Created exports directory");
            } else {
                System.err.println("✗ Failed to create exports directory");
            }
        }
    }
}
