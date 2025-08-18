package com.inventory.managers;

import java.util.*;
import java.util.Scanner;
import com.inventory.models.Order;
import com.inventory.models.Product;
import com.inventory.DataStore;

public class OrderManager {
    private List<Order> orders;
    private int orderIdCounter;
    private InventoryManager inventoryManager;
    
    public OrderManager(InventoryManager inventoryManager) {
        this.orders = new ArrayList<>();
        this.orderIdCounter = 1;
        this.inventoryManager = inventoryManager;
        loadOrders();
    }
    
    // Sales & Orders Methods
    public void createOrder(Scanner scanner) {
        System.out.println("\n=== Create New Order ===");
        
        // Show available products
        inventoryManager.viewAllProducts();
        
        // Get product ID
        System.out.print("\nEnter product ID: ");
        String productId = scanner.nextLine().trim();
        
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        // Check if product has stock
        if (product.getQuantity() <= 0) {
            System.out.println("Error: Product is out of stock.");
            return;
        }
        
        // Get quantity
        int quantity = -1;
        while (quantity <= 0 || quantity > product.getQuantity()) {
            try {
                System.out.print("Enter quantity (max " + product.getQuantity() + "): ");
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("Error: Quantity must be greater than 0.");
                } else if (quantity > product.getQuantity()) {
                    System.out.println("Error: Insufficient stock. Available: " + product.getQuantity());
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for quantity.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Calculate total amount
        double totalAmount = product.getPrice() * quantity;
        
        // Generate order ID
        String orderId = "O" + String.format("%03d", orderIdCounter++);
        
        // Create order
        Order order = new Order(orderId, productId, quantity, totalAmount, "Guest");
        orders.add(order);
        
        // Update stock
        int newQuantity = product.getQuantity() - quantity;
        product.setQuantity(newQuantity);
        
        // Display confirmation
        System.out.println("\n=== Order Created Successfully ===");
        System.out.println("Order ID: " + orderId);
        System.out.println("Product: " + product.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Remaining Stock: " + newQuantity);
    }
    
    public void viewOrderHistory() {
        System.out.println("\n=== Order History ===");
        
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        
        // Print header
        System.out.printf("%-10s %-10s %-10s %-15s%n", 
                         "Order ID", "Product ID", "Quantity", "Total Amount");
        System.out.println("------------------------------------------------");
        
        // Print each order
        for (Order order : orders) {
            System.out.printf("%-10s %-10s %-10d $%-14.2f%n",
                             order.getId(), 
                             order.getProductId(), 
                             order.getQuantity(), 
                             order.getTotalAmount());
        }
        
        // Show summary
        System.out.println("\n=== Order Summary ===");
        System.out.println("Total Orders: " + orders.size());
        
        double totalSales = 0;
        int totalItems = 0;
        for (Order order : orders) {
            totalSales += order.getTotalAmount();
            totalItems += order.getQuantity();
        }
        
        System.out.println("Total Items Sold: " + totalItems);
        System.out.println("Total Sales: $" + totalSales);
        if (orders.size() > 0) {
            System.out.println("Average Order Value: $" + (totalSales / orders.size()));
        }
    }
    
    public void processDirectSale(Scanner scanner) {
        System.out.println("\n=== Process Direct Sale ===");
        
        // Show available products
        inventoryManager.viewAllProducts();
        
        // Get product ID
        System.out.print("\nEnter product ID: ");
        String productId = scanner.nextLine().trim();
        
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        // Check if product has stock
        if (product.getQuantity() <= 0) {
            System.out.println("Error: Product is out of stock.");
            return;
        }
        
        // Get quantity
        int quantity = -1;
        while (quantity <= 0 || quantity > product.getQuantity()) {
            try {
                System.out.print("Enter quantity (max " + product.getQuantity() + "): ");
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("Error: Quantity must be greater than 0.");
                } else if (quantity > product.getQuantity()) {
                    System.out.println("Error: Insufficient stock. Available: " + product.getQuantity());
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for quantity.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Calculate total amount
        double totalAmount = product.getPrice() * quantity;
        
        // Update stock
        int newQuantity = product.getQuantity() - quantity;
        product.setQuantity(newQuantity);
        
        // Display sale confirmation
        System.out.println("\n=== Sale Processed Successfully ===");
        System.out.println("Product: " + product.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Unit Price: $" + product.getPrice());
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Remaining Stock: " + newQuantity);
        
        // Check if stock is now low
        if (newQuantity <= product.getReorderLevel()) {
            System.out.println("⚠️  WARNING: Stock is now low! Consider reordering.");
        }
    }
    
    // Helper method to find product by ID (delegates to InventoryManager)
    private Product findProductById(String productId) {
        // We need to access the products list from InventoryManager
        // For now, we'll create a simple implementation
        // In a real application, you might want to create a shared data access layer
        return inventoryManager.findProductById(productId);
    }
    
    // Getter method to access orders list for reports
    public List<Order> getOrders() {
        return orders;
    }
    
    // GUI-friendly method to add order
    public void addOrder(Order order) {
        orders.add(order);
        saveOrders(); // Auto-save when order is added
    }
    
    // Data Persistence Methods
    private void loadOrders() {
        List<Order> loadedOrders = DataStore.loadData(DataStore.ORDERS_FILE);
        if (loadedOrders != null) {
            this.orders = loadedOrders;
            // Update order ID counter to avoid conflicts
            updateOrderIdCounter();
            System.out.println("✓ Loaded " + orders.size() + " orders from storage");
        } else {
            System.out.println("ℹ Starting with empty order history");
        }
    }
    
    public void saveOrders() {
        boolean success = DataStore.saveData(DataStore.ORDERS_FILE, orders);
        if (success) {
            System.out.println("✓ Order data saved successfully");
        } else {
            System.err.println("✗ Failed to save order data");
        }
    }
    
    private void updateOrderIdCounter() {
        if (!orders.isEmpty()) {
            // Find the highest order ID number and set counter accordingly
            int maxId = 0;
            for (Order order : orders) {
                try {
                    String idStr = order.getId().substring(1); // Remove 'O' prefix
                    int idNum = Integer.parseInt(idStr);
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
            this.orderIdCounter = maxId + 1;
        }
    }
}
