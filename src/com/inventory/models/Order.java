package com.inventory.models;

import java.io.Serializable;

public class Order implements Serializable {
    private String id;
    private String productId;
    private int quantity;
    private double totalAmount;
    private String customerName;

    public Order(String id, String productId, int quantity, double totalAmount, String customerName) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.customerName = customerName;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Helper to get product name from InventoryManager
    public String getProductName() {
        Product product = com.inventory.gui.InventoryManagementApp.getInventoryManager().findProductById(productId);
        return product != null ? product.getName() : "Unknown";
    }

    public double getTotal() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return String.format("Order{id='%s', productId='%s', quantity=%d, totalAmount=%.2f}", 
                           id, productId, quantity, totalAmount);
    }
}
