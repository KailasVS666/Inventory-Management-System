package com.inventory.managers;

import java.util.*;
import java.util.Scanner;
import com.inventory.models.Product;
import com.inventory.DataStore;

public class InventoryManager {
    private List<Product> products;
    private int productIdCounter;
    
    public InventoryManager() {
        this.products = new ArrayList<>();
        this.productIdCounter = 1;
        loadProducts();
    }
    
    // Product Management Methods
    public void addProduct(Scanner scanner) {
        System.out.println("\n=== Add New Product ===");
        
        // Get product name
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Product name cannot be empty.");
            return;
        }
        
        // Get product price
        double price = 0;
        while (true) {
            try {
                System.out.print("Enter product price: $");
                price = scanner.nextDouble();
                if (price > 0) {
                    break;
                } else {
                    System.out.println("Error: Price must be greater than 0.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for price.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Get initial quantity
        int quantity = -1;
        while (quantity < 0) {
            try {
                System.out.print("Enter initial quantity: ");
                quantity = scanner.nextInt();
                if (quantity < 0) {
                    System.out.println("Error: Quantity must be 0 or greater.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for quantity.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Get reorder level
        int reorderLevel = -1;
        while (reorderLevel < 0) {
            try {
                System.out.print("Enter reorder level: ");
                reorderLevel = scanner.nextInt();
                if (reorderLevel < 0) {
                    System.out.println("Error: Reorder level must be 0 or greater.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for reorder level.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Generate product ID
        String id = "P" + String.format("%03d", productIdCounter++);
        
        // Get supplier ID (optional)
        System.out.print("Enter supplier ID (or press Enter to skip): ");
        String supplierId = scanner.nextLine().trim();
        
        // Create and add product
        Product product = new Product(id, name, price, quantity, reorderLevel, supplierId);
        products.add(product);
        
        System.out.println("Product added successfully! Product ID: " + id);
    }
    
    public void viewAllProducts() {
        System.out.println("\n=== All Products ===");
        
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        // Print header
        System.out.printf("%-10s %-20s %-12s %-10s %-15s%n", 
                         "ID", "Name", "Price", "Quantity", "Reorder Level");
        System.out.println("------------------------------------------------------------");
        
        // Print each product
        for (Product product : products) {
            System.out.printf("%-10s %-20s $%-11.2f %-10d %-15d%n",
                             product.getId(), 
                             product.getName(), 
                             product.getPrice(), 
                             product.getQuantity(), 
                             product.getReorderLevel());
        }
    }
    
    public void updateProduct(Scanner scanner) {
        System.out.println("\n=== Update Product ===");
        
        if (products.isEmpty()) {
            System.out.println("No products found to update.");
            return;
        }
        
        // Show current products
        viewAllProducts();
        
        // Get product ID
        System.out.print("\nEnter product ID to update: ");
        String id = scanner.nextLine().trim();
        
        Product product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        System.out.println("Current product details: " + product);
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        // Update name
        System.out.print("Name [" + product.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            product.setName(name);
            System.out.println("Name updated to: " + name);
        }
        
        // Update price
        System.out.print("Price [" + product.getPrice() + "]: ");
        String priceStr = scanner.nextLine().trim();
        if (!priceStr.isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                if (price > 0) {
                    product.setPrice(price);
                    System.out.println("Price updated to: " + price);
                } else {
                    System.out.println("Invalid price. Price must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format.");
            }
        }
        
        // Update quantity
        System.out.print("Quantity [" + product.getQuantity() + "]: ");
        String qtyStr = scanner.nextLine().trim();
        if (!qtyStr.isEmpty()) {
            try {
                int quantity = Integer.parseInt(qtyStr);
                if (quantity >= 0) {
                    product.setQuantity(quantity);
                    System.out.println("Quantity updated to: " + quantity);
                } else {
                    System.out.println("Invalid quantity. Quantity must be 0 or greater.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format.");
            }
        }
        
        // Update reorder level
        System.out.print("Reorder Level [" + product.getReorderLevel() + "]: ");
        String reorderStr = scanner.nextLine().trim();
        if (!reorderStr.isEmpty()) {
            try {
                int reorderLevel = Integer.parseInt(reorderStr);
                if (reorderLevel >= 0) {
                    product.setReorderLevel(reorderLevel);
                    System.out.println("Reorder level updated to: " + reorderLevel);
                } else {
                    System.out.println("Invalid reorder level. Reorder level must be 0 or greater.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid reorder level format.");
            }
        }
        
        // Update supplier ID
        System.out.print("Supplier ID [" + (product.getSupplierId().isEmpty() ? "N/A" : product.getSupplierId()) + "]: ");
        String supplierId = scanner.nextLine().trim();
        if (!supplierId.isEmpty()) {
            product.setSupplierId(supplierId);
            System.out.println("Supplier ID updated to: " + supplierId);
        }
        
        System.out.println("Product updated successfully!");
    }
    
    public void deleteProduct(Scanner scanner, boolean canDelete) {
        System.out.println("\n=== Delete Product ===");
        
        if (!canDelete) {
            System.out.println("✗ Access denied. You do not have permission to delete products.");
            return;
        }
        
        if (products.isEmpty()) {
            System.out.println("No products found to delete.");
            return;
        }
        
        // Show current products
        viewAllProducts();
        
        // Get product ID
        System.out.print("\nEnter product ID to delete: ");
        String id = scanner.nextLine().trim();
        
        Product product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        // Confirm deletion
        System.out.print("Are you sure you want to delete " + product.getName() + " (y/n)? ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            products.remove(product);
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    // Stock Management Methods
    public void checkStockLevels() {
        System.out.println("\n=== Stock Levels ===");
        
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        // Print header
        System.out.printf("%-10s %-20s %-10s %-15s %-10s%n", 
                         "ID", "Name", "Quantity", "Reorder Level", "Status");
        System.out.println("------------------------------------------------------------");
        
        // Print each product with status
        for (Product product : products) {
            String status = product.getQuantity() <= product.getReorderLevel() ? "LOW STOCK" : "OK";
            System.out.printf("%-10s %-20s %-10d %-15d %-10s%n",
                             product.getId(), 
                             product.getName(), 
                             product.getQuantity(), 
                             product.getReorderLevel(), 
                             status);
        }
    }
    
    public void addStock(Scanner scanner) {
        System.out.println("\n=== Add Stock ===");
        
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        // Show current products
        viewAllProducts();
        
        // Get product ID
        System.out.print("\nEnter product ID: ");
        String id = scanner.nextLine().trim();
        
        Product product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        // Get quantity to add
        int quantityToAdd = -1;
        while (quantityToAdd <= 0) {
            try {
                System.out.print("Enter quantity to add: ");
                quantityToAdd = scanner.nextInt();
                if (quantityToAdd <= 0) {
                    System.out.println("Error: Quantity must be greater than 0.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for quantity.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Update stock
        int newQuantity = product.getQuantity() + quantityToAdd;
        product.setQuantity(newQuantity);
        
        System.out.println("Stock updated successfully. New quantity: " + newQuantity);
    }
    
    public void removeStock(Scanner scanner) {
        System.out.println("\n=== Remove Stock ===");
        
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        // Show current products
        viewAllProducts();
        
        // Get product ID
        System.out.print("\nEnter product ID: ");
        String id = scanner.nextLine().trim();
        
        Product product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        // Get quantity to remove
        int quantityToRemove = -1;
        while (quantityToRemove <= 0) {
            try {
                System.out.print("Enter quantity to remove: ");
                quantityToRemove = scanner.nextInt();
                if (quantityToRemove <= 0) {
                    System.out.println("Error: Quantity must be greater than 0.");
                } else if (quantityToRemove > product.getQuantity()) {
                    System.out.println("Error: Insufficient stock. Current quantity: " + product.getQuantity());
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for quantity.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Update stock
        int newQuantity = product.getQuantity() - quantityToRemove;
        product.setQuantity(newQuantity);
        
        System.out.println("Stock updated successfully. New quantity: " + newQuantity);
    }
    
    public void updateReorderLevel(Scanner scanner) {
        System.out.println("\n=== Update Reorder Level ===");
        
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        // Show current products
        viewAllProducts();
        
        // Get product ID
        System.out.print("\nEnter product ID: ");
        String id = scanner.nextLine().trim();
        
        Product product = findProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        // Get new reorder level
        int newReorderLevel = -1;
        while (newReorderLevel < 0) {
            try {
                System.out.print("Enter new reorder level: ");
                newReorderLevel = scanner.nextInt();
                if (newReorderLevel < 0) {
                    System.out.println("Error: Reorder level must be 0 or greater.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for reorder level.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Update reorder level
        product.setReorderLevel(newReorderLevel);
        
        System.out.println("Reorder level updated successfully. New reorder level: " + newReorderLevel);
    }
    
    // Helper method to find product by ID
    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
    
    // Getter method to access products list for reports
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
    
    // Data Persistence Methods
    private void loadProducts() {
        List<Product> loadedProducts = DataStore.loadData(DataStore.PRODUCTS_FILE);
        if (loadedProducts != null) {
            this.products = loadedProducts;
            // Update product ID counter to avoid conflicts
            updateProductIdCounter();
            System.out.println("✓ Loaded " + products.size() + " products from storage");
        } else {
            System.out.println("ℹ Starting with empty product inventory");
        }
    }
    
    public void saveProducts() {
        boolean success = DataStore.saveData(DataStore.PRODUCTS_FILE, products);
        if (success) {
            System.out.println("✓ Product inventory saved successfully");
        } else {
            System.err.println("✗ Failed to save product inventory");
        }
    }
    
    private void updateProductIdCounter() {
        if (!products.isEmpty()) {
            // Find the highest product ID number and set counter accordingly
            int maxId = 0;
            for (Product product : products) {
                try {
                    String idStr = product.getId().substring(1); // Remove 'P' prefix
                    int idNum = Integer.parseInt(idStr);
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
            this.productIdCounter = maxId + 1;
        }
    }
    
    // Search & Filtering Methods (Phase 8)
    public void searchProducts(Scanner scanner) {
        System.out.println("\n=== Search Products ===");
        System.out.println("Search by:");
        System.out.println("1. Product Name (partial match)");
        System.out.println("2. Price Range");
        System.out.println("3. Supplier ID");
        System.out.println("4. View Products by Supplier");
        System.out.println("5. Back to Product Management");
        System.out.print("Enter your choice (1-5): ");
        
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    searchByName(scanner);
                    break;
                case 2:
                    searchByPriceRange(scanner);
                    break;
                case 3:
                    searchBySupplierId(scanner);
                    break;
                case 4:
                    viewProductsBySupplier(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear the invalid input
        }
    }
    
    private void searchByName(Scanner scanner) {
        System.out.print("Enter product name to search (partial match): ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            System.out.println("Error: Search term cannot be empty.");
            return;
        }
        
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(searchTerm)) {
                results.add(product);
            }
        }
        
        displaySearchResults(results, "Name Search: '" + searchTerm + "'");
    }
    
    private void searchByPriceRange(Scanner scanner) {
        double minPrice = -1;
        double maxPrice = -1;
        
        // Get minimum price
        while (minPrice < 0) {
            try {
                System.out.print("Enter minimum price: $");
                minPrice = scanner.nextDouble();
                if (minPrice < 0) {
                    System.out.println("Error: Minimum price must be 0 or greater.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for minimum price.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        // Get maximum price
        while (maxPrice < minPrice) {
            try {
                System.out.print("Enter maximum price: $");
                maxPrice = scanner.nextDouble();
                if (maxPrice < minPrice) {
                    System.out.println("Error: Maximum price must be greater than or equal to minimum price.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number for maximum price.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }
        
        displaySearchResults(results, "Price Range: $" + minPrice + " - $" + maxPrice);
    }
    
    private void searchBySupplierId(Scanner scanner) {
        System.out.print("Enter supplier ID to search: ");
        String supplierId = scanner.nextLine().trim();
        
        if (supplierId.isEmpty()) {
            System.out.println("Error: Supplier ID cannot be empty.");
            return;
        }
        
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getSupplierId().equals(supplierId)) {
                results.add(product);
            }
        }
        
        displaySearchResults(results, "Supplier ID: " + supplierId);
    }
    
    private void displaySearchResults(List<Product> results, String searchType) {
        System.out.println("\n=== Search Results: " + searchType + " ===");
        
        if (results.isEmpty()) {
            System.out.println("No products found matching the search criteria.");
            return;
        }
        
        // Print header
        System.out.printf("%-10s %-20s %-12s %-10s %-15s %-12s%n", 
                         "ID", "Name", "Price", "Quantity", "Reorder Level", "Supplier ID");
        System.out.println("------------------------------------------------------------------------");
        
        // Print each product
        for (Product product : results) {
            System.out.printf("%-10s %-20s $%-11.2f %-10d %-15d %-12s%n",
                             product.getId(), 
                             product.getName(), 
                             product.getPrice(), 
                             product.getQuantity(), 
                             product.getReorderLevel(),
                             product.getSupplierId().isEmpty() ? "N/A" : product.getSupplierId());
        }
        
        System.out.println("\nTotal results: " + results.size());
    }
    
    private void viewProductsBySupplier(Scanner scanner) {
        System.out.print("Enter supplier ID to view products: ");
        String supplierId = scanner.nextLine().trim();
        
        if (supplierId.isEmpty()) {
            System.out.println("Error: Supplier ID cannot be empty.");
            return;
        }
        
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getSupplierId().equals(supplierId)) {
                results.add(product);
            }
        }
        
        displaySearchResults(results, "Supplier: " + supplierId);
    }
}
