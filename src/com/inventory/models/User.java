package com.inventory.models;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String role; // "ADMIN" or "STAFF"
    
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getRole() {
        return role;
    }
    
    // Setters
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    // Helper methods
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }
    
    public boolean isStaff() {
        return "STAFF".equalsIgnoreCase(role);
    }
    
    public boolean canDelete() {
        return isAdmin();
    }
    
    public boolean canAccessDataManagement() {
        return isAdmin();
    }
    
    @Override
    public String toString() {
        return String.format("User{username='%s', role='%s'}", username, role);
    }
}

