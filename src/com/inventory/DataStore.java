package com.inventory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    
    // File name constants for easy configuration
    public static final String PRODUCTS_FILE = "products.dat";
    public static final String SUPPLIERS_FILE = "suppliers.dat";
    public static final String ORDERS_FILE = "orders.dat";
    public static final String USERS_FILE = "users.dat"; // Added for Phase 8
    
    /**
     * Save data to a file using Java Serialization
     * @param fileName The name of the file to save to
     * @param data The data object to serialize
     * @return true if save was successful, false otherwise
     */
    public static boolean saveData(String fileName, Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
            System.out.println("✓ Data saved successfully to " + fileName);
            return true;
        } catch (IOException e) {
            System.err.println("✗ Error saving data to " + fileName + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load data from a file using Java Deserialization
     * @param fileName The name of the file to load from
     * @param <T> The type of data to load
     * @return The loaded data object, or null if loading failed
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadData(String fileName) {
        File file = new File(fileName);
        
        // If file doesn't exist, return null (will be handled by caller)
        if (!file.exists()) {
            System.out.println("ℹ No existing data file found: " + fileName + " (will start with empty data)");
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            T data = (T) ois.readObject();
            System.out.println("✓ Data loaded successfully from " + fileName);
            return data;
        } catch (IOException e) {
            System.err.println("✗ Error reading data from " + fileName + ": " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: Data format in " + fileName + " is incompatible: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Check if a data file exists
     * @param fileName The name of the file to check
     * @return true if file exists, false otherwise
     */
    public static boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }
    
    /**
     * Delete a data file (useful for testing or resetting data)
     * @param fileName The name of the file to delete
     * @return true if deletion was successful, false otherwise
     */
    public static boolean deleteDataFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("✓ Data file deleted: " + fileName);
            } else {
                System.err.println("✗ Failed to delete data file: " + fileName);
            }
            return deleted;
        } else {
            System.out.println("ℹ Data file does not exist: " + fileName);
            return true;
        }
    }
    
    /**
     * Get information about all data files
     * @return A string containing information about data files
     */
    public static String getDataFileInfo() {
        StringBuilder info = new StringBuilder();
        info.append("\n=== Data File Information ===\n");
        
        String[] files = {PRODUCTS_FILE, SUPPLIERS_FILE, ORDERS_FILE, USERS_FILE};
        for (String fileName : files) {
            File file = new File(fileName);
            if (file.exists()) {
                long sizeInBytes = file.length();
                String size = sizeInBytes < 1024 ? sizeInBytes + " B" : 
                             sizeInBytes < 1024 * 1024 ? String.format("%.1f KB", sizeInBytes / 1024.0) :
                             String.format("%.1f MB", sizeInBytes / (1024.0 * 1024.0));
                info.append(String.format("%-15s: %s (%s)\n", fileName, "EXISTS", size));
            } else {
                info.append(String.format("%-15s: %s\n", fileName, "NOT FOUND"));
            }
        }
        
        return info.toString();
    }
}
