package com.inventory.models;

import java.io.Serializable;

public class Order implements Serializable {
    private String id;
    private String productId;
    private int quantity;
    private double totalAmount;
    
    public Order(String id, String productId, int quantity, double totalAmount) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
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
    
    @Override
    public String toString() {
        return String.format("Order{id='%s', productId='%s', quantity=%d, totalAmount=%.2f}", 
                           id, productId, quantity, totalAmount);
    }
}
