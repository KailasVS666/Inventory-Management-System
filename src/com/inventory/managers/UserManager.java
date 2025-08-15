package com.inventory.managers;

import java.util.*;
import java.util.Scanner;
import com.inventory.models.User;
import com.inventory.DataStore;

public class UserManager {
    private List<User> users;
    private User currentUser;
    private static final String USERS_FILE = "users.dat";
    
    public UserManager() {
        this.users = new ArrayList<>();
        loadUsers();
        initializeDefaultAdmin();
    }
    
    // User Authentication Methods
    public boolean login(Scanner scanner) {
        System.out.println("\n=== User Authentication ===");
        
        int maxAttempts = 3;
        int attempts = 0;
        
        while (attempts < maxAttempts) {
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            
            User user = authenticateUser(username, password);
            if (user != null) {
                this.currentUser = user;
                System.out.println("✓ Login successful! Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
                return true;
            } else {
                attempts++;
                int remaining = maxAttempts - attempts;
                if (remaining > 0) {
                    System.out.println("✗ Invalid username or password. " + remaining + " attempts remaining.");
                } else {
                    System.out.println("✗ Maximum login attempts exceeded. Exiting...");
                }
            }
        }
        
        return false;
    }
    
    public void logout() {
        if (currentUser != null) {
            System.out.println("✓ Logged out: " + currentUser.getUsername());
            currentUser = null;
        }
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    // User Management Methods
    public void createUser(Scanner scanner) {
        if (!isLoggedIn() || !currentUser.isAdmin()) {
            System.out.println("✗ Access denied. Admin privileges required.");
            return;
        }
        
        System.out.println("\n=== Create New User ===");
        
        // Get username
        String username = "";
        while (username.isEmpty()) {
            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();
            
            if (username.isEmpty()) {
                System.out.println("Error: Username cannot be empty.");
            } else if (userExists(username)) {
                System.out.println("Error: Username already exists.");
                username = "";
            }
        }
        
        // Get password
        String password = "";
        while (password.isEmpty()) {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();
            
            if (password.isEmpty()) {
                System.out.println("Error: Password cannot be empty.");
            }
        }
        
        // Get role
        String role = "";
        while (role.isEmpty()) {
            System.out.print("Enter role (ADMIN/STAFF): ");
            role = scanner.nextLine().trim().toUpperCase();
            
            if (!role.equals("ADMIN") && !role.equals("STAFF")) {
                System.out.println("Error: Role must be ADMIN or STAFF.");
                role = "";
            }
        }
        
        // Create and add user
        User newUser = new User(username, password, role);
        users.add(newUser);
        saveUsers();
        
        System.out.println("✓ User created successfully: " + username + " (" + role + ")");
    }
    
    public void viewUsers() {
        if (!isLoggedIn() || !currentUser.isAdmin()) {
            System.out.println("✗ Access denied. Admin privileges required.");
            return;
        }
        
        System.out.println("\n=== All Users ===");
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        
        // Print header
        System.out.printf("%-20s %-15s %-10s%n", "Username", "Role", "Status");
        System.out.println("----------------------------------------");
        
        // Print each user
        for (User user : users) {
            String status = user.getUsername().equals(currentUser.getUsername()) ? "CURRENT" : "ACTIVE";
            System.out.printf("%-20s %-15s %-10s%n",
                             user.getUsername(), 
                             user.getRole(), 
                             status);
        }
    }
    
    public void changePassword(Scanner scanner) {
        if (!isLoggedIn()) {
            System.out.println("✗ You must be logged in to change password.");
            return;
        }
        
        System.out.println("\n=== Change Password ===");
        
        // Verify current password
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine().trim();
        
        if (!currentUser.getPassword().equals(currentPassword)) {
            System.out.println("✗ Current password is incorrect.");
            return;
        }
        
        // Get new password
        String newPassword = "";
        while (newPassword.isEmpty()) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine().trim();
            
            if (newPassword.isEmpty()) {
                System.out.println("Error: New password cannot be empty.");
            }
        }
        
        // Update password
        currentUser.setPassword(newPassword);
        saveUsers();
        
        System.out.println("✓ Password changed successfully!");
    }
    
    // Helper Methods
    private User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    private boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    private void initializeDefaultAdmin() {
        if (users.isEmpty()) {
            User admin = new User("admin", "admin123", "ADMIN");
            users.add(admin);
            saveUsers();
            System.out.println("ℹ Default admin account created: admin/admin123");
        }
    }
    
    // Data Persistence Methods
    private void loadUsers() {
        List<User> loadedUsers = DataStore.loadData(USERS_FILE);
        if (loadedUsers != null) {
            this.users = loadedUsers;
            System.out.println("✓ Loaded " + users.size() + " users from storage");
        } else {
            System.out.println("ℹ Starting with default user list");
        }
    }
    
    public void saveUsers() {
        boolean success = DataStore.saveData(USERS_FILE, users);
        if (success) {
            System.out.println("✓ User data saved successfully");
        } else {
            System.err.println("✗ Failed to save user data");
        }
    }
}

