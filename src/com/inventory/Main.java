package com.inventory;

import java.util.Scanner;
import com.inventory.managers.InventoryManager;
import com.inventory.managers.SupplierManager;
import com.inventory.managers.OrderManager;
import com.inventory.managers.ReportManager;
import com.inventory.DataStore;

public class Main {
    private static InventoryManager inventoryManager = new InventoryManager();
    private static SupplierManager supplierManager = new SupplierManager();
    private static OrderManager orderManager = new OrderManager(inventoryManager);
    private static ReportManager reportManager = new ReportManager(inventoryManager, orderManager);
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("=== Inventory Management System - Phase 7 ===");
        
        while (running) {
            displayMenu();
            System.out.print("Enter your choice (1-7): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        productManagementMenu(scanner);
                        break;
                    case 2:
                        stockManagementMenu(scanner);
                        break;
                    case 3:
                        supplierManagementMenu(scanner);
                        break;
                    case 4:
                        salesAndOrdersMenu(scanner);
                        break;
                    case 5:
                        reportsAndAnalyticsMenu(scanner);
                        break;
                    case 6:
                        dataManagementMenu(scanner);
                        break;
                    case 7:
                        System.out.println("Exiting Inventory Management System...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                        break;
                }
                
                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        
        // Save all data before exiting
        System.out.println("\nSaving data before exit...");
        saveAllData();
        
        scanner.close();
        System.out.println("Thank you for using Inventory Management System!");
    }
    
    private static void displayMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Product Management");
        System.out.println("2. Stock Management");
        System.out.println("3. Supplier Management");
        System.out.println("4. Sales & Orders");
        System.out.println("5. Reports & Analytics");
        System.out.println("6. Data Management");
        System.out.println("7. Exit");
        System.out.println("=================");
    }
    
    private static void productManagementMenu(Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice (1-5): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        inventoryManager.addProduct(scanner);
                        break;
                    case 2:
                        inventoryManager.viewAllProducts();
                        break;
                    case 3:
                        inventoryManager.updateProduct(scanner);
                        break;
                    case 4:
                        inventoryManager.deleteProduct(scanner);
                        break;
                    case 5:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                        break;
                }
                
                if (inMenu) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    private static void stockManagementMenu(Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== Stock Management ===");
            System.out.println("1. Check Stock Levels");
            System.out.println("2. Add Stock");
            System.out.println("3. Remove Stock");
            System.out.println("4. Update Reorder Level");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice (1-5): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        inventoryManager.checkStockLevels();
                        break;
                    case 2:
                        inventoryManager.addStock(scanner);
                        break;
                    case 3:
                        inventoryManager.removeStock(scanner);
                        break;
                    case 4:
                        inventoryManager.updateReorderLevel(scanner);
                        break;
                    case 5:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                        break;
                }
                
                if (inMenu) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    private static void supplierManagementMenu(Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== Supplier Management ===");
            System.out.println("1. Add New Supplier");
            System.out.println("2. View All Suppliers");
            System.out.println("3. Update Supplier");
            System.out.println("4. Delete Supplier");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice (1-5): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        supplierManager.addSupplier(scanner);
                        break;
                    case 2:
                        supplierManager.viewSuppliers();
                        break;
                    case 3:
                        supplierManager.updateSupplier(scanner);
                        break;
                    case 4:
                        supplierManager.deleteSupplier(scanner);
                        break;
                    case 5:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                        break;
                }
                
                if (inMenu) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    private static void salesAndOrdersMenu(Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== Sales & Orders ===");
            System.out.println("1. Create New Order");
            System.out.println("2. View Order History");
            System.out.println("3. Process Direct Sale");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice (1-4): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        orderManager.createOrder(scanner);
                        break;
                    case 2:
                        orderManager.viewOrderHistory();
                        break;
                    case 3:
                        orderManager.processDirectSale(scanner);
                        break;
                    case 4:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                        break;
                }
                
                if (inMenu) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    private static void reportsAndAnalyticsMenu(Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== Reports & Analytics ===");
            System.out.println("1. View Low Stock Products");
            System.out.println("2. View Total Inventory Value");
            System.out.println("3. View Sales Summary");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice (1-4): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        reportManager.viewLowStockProducts();
                        break;
                    case 2:
                        reportManager.viewTotalInventoryValue();
                        break;
                    case 3:
                        reportManager.viewSalesSummary();
                        break;
                    case 4:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                        break;
                }
                
                if (inMenu) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    private static void dataManagementMenu(Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== Data Management ===");
            System.out.println("1. Save All Data");
            System.out.println("2. View Data File Information");
            System.out.println("3. Delete All Data Files");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice (1-4): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        saveAllData();
                        break;
                    case 2:
                        System.out.println(DataStore.getDataFileInfo());
                        break;
                    case 3:
                        deleteAllDataFiles();
                        break;
                    case 4:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                        break;
                }
                
                if (inMenu) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    private static void saveAllData() {
        System.out.println("\n=== Saving All Data ===");
        inventoryManager.saveProducts();
        supplierManager.saveSuppliers();
        orderManager.saveOrders();
        System.out.println("✓ All data saved successfully!");
    }
    
    private static void deleteAllDataFiles() {
        System.out.println("\n=== Delete All Data Files ===");
        System.out.print("Are you sure you want to delete ALL data files? This cannot be undone! (y/n): ");
        Scanner confirmScanner = new Scanner(System.in);
        String confirm = confirmScanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            DataStore.deleteDataFile(DataStore.PRODUCTS_FILE);
            DataStore.deleteDataFile(DataStore.SUPPLIERS_FILE);
            DataStore.deleteDataFile(DataStore.ORDERS_FILE);
            System.out.println("✓ All data files deleted successfully!");
        } else {
            System.out.println("Data file deletion cancelled.");
        }
    }
}
