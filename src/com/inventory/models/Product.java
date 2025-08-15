package com.inventory.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private int reorderLevel;
    private String supplierId; // Added for Phase 8
    
    public Product(String id, String name, double price, int quantity, int reorderLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.supplierId = ""; // Default empty supplier ID
    }
    
    public Product(String id, String name, double price, int quantity, int reorderLevel, String supplierId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.supplierId = supplierId;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public int getReorderLevel() {
        return reorderLevel;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
        public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
    
    public String getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    
    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f, quantity=%d, reorderLevel=%d, supplierId='%s'}",
                           id, name, price, quantity, reorderLevel, supplierId);
    }
}
