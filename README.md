# Inventory Management System

A robust inventory management application built in Java with a modern JavaFX GUI. Designed for small businesses and organizations to efficiently manage products, suppliers, orders, sales, and reporting.

## Features

- Product Management: Add, edit, delete, and search products. Track stock, pricing, and reorder levels.
- Supplier Management: Manage supplier details and relationships.
- Order & Sales Management: Create orders, process direct sales, and update inventory in real time.
- Reports & Analytics: Generate low stock, inventory value, and sales summary reports. Export reports to CSV.
- User Authentication: Secure login with role-based access (admin/staff).
- Data Persistence: All data is saved using Java serialization (.dat files).
- Modern JavaFX GUI: Clean, responsive, and intuitive interface.
- Table Views: All data is presented in sortable, scrollable tables.
- CSV Export: Export reports for business analysis.
- Validation & Error Handling: Prevents invalid data entry and provides clear feedback.
- Low Stock Alerts: Warnings when products fall below reorder level.

## Getting Started

### Requirements

- Java 21 or higher
- JavaFX 21 SDK (download from GluonHQ)
- Windows, Mac, or Linux

### Setup

1. Clone the repository:
   ```
   git clone <your-repo-url>
   cd Inventory-Management-System
   ```

2. Download and extract JavaFX SDK.
   Place it somewhere convenient (e.g., C:/Users/yourname/Documents/openjfx-21.0.2_windows-x64_bin-sdk/).

3. Compile the project:
   ```
   javac --module-path "C:/path/to/javafx-sdk-21.0.2/lib" --add-modules javafx.controls,javafx.fxml -cp "src" src/com/inventory/gui/*.java src/com/inventory/managers/*.java src/com/inventory/models/*.java src/com/inventory/*.java
   ```

4. Run the application:
   ```
   java --module-path "C:/path/to/javafx-sdk-21.0.2/lib" --add-modules javafx.controls,javafx.fxml -cp src com.inventory.gui.InventoryManagementApp
   ```

## Usage

- Login with your credentials (admin/staff).
- Use the dashboard to navigate to Products, Suppliers, Orders, and Reports.
- Manage inventory, suppliers, and orders.
- Generate and export business reports.
- Logout or exit the application when done.

## Project Structure

src/
  com/inventory/
    DataStore.java
    Main.java
    models/
      Product.java
      Supplier.java
      Order.java
      User.java
    managers/
      InventoryManager.java
      SupplierManager.java
      OrderManager.java
      ReportManager.java
      UserManager.java
    gui/
      InventoryManagementApp.java
      DashboardScreen.java
      ProductsScreen.java
      SuppliersScreen.java
      OrdersScreen.java
      ReportsScreen.java
      LoginScreen.java

data files: products.dat, orders.dat, suppliers.dat, users.dat  
exports: CSV files are saved in the exports/ directory.

## License

This project is licensed under the MIT License.
