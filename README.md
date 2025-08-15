# Inventory Management System - Phase 4

A Java console application for managing inventory with complete Product Management CRUD, Stock Management, and Supplier Management functionality.

## Phase 4 Features

### 1. Product Management CRUD Operations ✅
- **Add Product**: Auto-generate IDs (P001, P002, ...), input validation, success confirmation
- **View Products**: Formatted table display with all product details
- **Update Product**: Selective field updates with current value display
- **Delete Product**: Confirmation-based deletion with safety checks

### 2. Stock Management Operations ✅
- **Check Stock Levels**: Formatted table with status indicators (OK/LOW STOCK)
- **Add Stock**: Restock products with validation and confirmation
- **Remove Stock**: Sales/damage stock removal with insufficient stock checks
- **Update Reorder Level**: Modify reorder thresholds with validation

### 3. Supplier Management Operations ✅
- **Add New Supplier**: Auto-generate IDs (S001, S002, ...), input validation
- **View All Suppliers**: Formatted table display with supplier details
- **Update Supplier**: Selective field updates with current value display
- **Delete Supplier**: Confirmation-based deletion with safety checks

### 4. Enhanced Menu System
- **Main Menu**: 6 options with Product, Stock, and Supplier Management fully functional
- **Product Sub-menu**: 5 options for complete CRUD operations
- **Stock Sub-menu**: 5 options for complete stock management
- **Supplier Sub-menu**: 5 options for complete supplier management
- **Input Validation**: Comprehensive error handling and user-friendly messages
- **Navigation**: Seamless menu navigation with back options

### 5. Data Management
- **In-Memory Storage**: ArrayList<Product> and ArrayList<Supplier> for data
- **Auto-ID Generation**: Sequential IDs with P prefix for products, S prefix for suppliers
- **Data Validation**: Comprehensive validation for all inputs
- **User-Friendly Input**: Current values shown in brackets for updates

## Project Structure

```
src/
 └── com/
     └── inventory/
         ├── Main.java                 # Application entry point with menus
         ├── models/
         │    ├── Product.java         # Product model with full CRUD support
         │    └── Supplier.java        # Supplier model with full CRUD support
         └── managers/
              ├── InventoryManager.java # Complete product & stock management logic
              └── SupplierManager.java  # Complete supplier management logic
```

## How to Run

### Compile the Application
```bash
javac -d . src/com/inventory/Main.java src/com/inventory/models/*.java src/com/inventory/managers/*.java
```

### Run the Application
```bash
java com.inventory.Main
```

## Menu Options

### Main Menu
1. **Product Management** - ✅ Fully functional CRUD operations
2. **Stock Management** - ✅ Fully functional stock operations
3. **Supplier Management** - ✅ Fully functional CRUD operations
4. **Sales & Orders** - Placeholder functionality
5. **Reports** - Placeholder functionality
6. **Exit** - Exits the application

### Product Management Sub-menu
1. **Add Product** - Create new products with validation
2. **View Products** - Display all products in formatted table
3. **Update Product** - Modify existing product details
4. **Delete Product** - Remove products with confirmation
5. **Back to Main Menu** - Return to main menu

### Stock Management Sub-menu
1. **Check Stock Levels** - View stock status with OK/LOW STOCK indicators
2. **Add Stock** - Restock products with quantity validation
3. **Remove Stock** - Remove stock for sales/damage with safety checks
4. **Update Reorder Level** - Modify reorder thresholds
5. **Back to Main Menu** - Return to main menu

### Supplier Management Sub-menu
1. **Add New Supplier** - Create new suppliers with validation
2. **View All Suppliers** - Display all suppliers in formatted table
3. **Update Supplier** - Modify existing supplier details
4. **Delete Supplier** - Remove suppliers with confirmation
5. **Back to Main Menu** - Return to main menu

## Product Management Features

### Add Product
- **Auto-ID Generation**: P001, P002, P003, etc.
- **Input Validation**: 
  - Name: Cannot be empty
  - Price: Must be greater than 0
  - Quantity: Must be 0 or greater
  - Reorder Level: Must be 0 or greater
- **Error Handling**: Clear error messages for invalid inputs
- **Success Confirmation**: Shows generated product ID

### View Products
- **Formatted Table**: Clean display with headers
- **Complete Information**: ID, Name, Price ($), Quantity, Reorder Level
- **Empty State**: "No products found" when list is empty
- **Currency Formatting**: Prices displayed with dollar signs

### Update Product
- **Product Selection**: Choose by product ID
- **Current Value Display**: Shows existing values in brackets
- **Selective Updates**: Update only specific fields
- **Skip Option**: Press Enter to keep current values
- **Validation**: Ensures valid data for each field

### Delete Product
- **Product Selection**: Choose by product ID
- **Confirmation**: "Are you sure?" prompt
- **Safety Check**: Prevents accidental deletions
- **Success Feedback**: Clear confirmation messages

## Stock Management Features

### Check Stock Levels
- **Status Indicators**: Shows "OK" or "LOW STOCK" for each product
- **Threshold Monitoring**: Compares current quantity vs reorder level
- **Formatted Display**: Clean table with ID, Name, Quantity, Reorder Level, Status
- **Empty State**: "No products found" when no products exist

### Add Stock (Restocking)
- **Product Selection**: Choose product by ID
- **Quantity Validation**: Must be greater than 0
- **Real-time Updates**: Stock levels updated immediately
- **Success Confirmation**: Shows new total quantity
- **Error Handling**: Clear messages for invalid inputs

### Remove Stock (Sales/Damage)
- **Product Selection**: Choose product by ID
- **Quantity Validation**: Must be positive and ≤ current quantity
- **Insufficient Stock Check**: Prevents removing more than available
- **Real-time Updates**: Stock reduced immediately
- **Success Confirmation**: Shows new total quantity

### Update Reorder Level
- **Product Selection**: Choose product by ID
- **Threshold Validation**: Must be 0 or greater
- **Immediate Application**: Changes applied instantly
- **Success Confirmation**: Shows new reorder level

## Supplier Management Features

### Add New Supplier
- **Auto-ID Generation**: S001, S002, S003, etc.
- **Input Validation**: 
  - Name: Cannot be empty
  - Contact Information: Cannot be empty
- **Error Handling**: Clear error messages for invalid inputs
- **Success Confirmation**: Shows generated supplier ID

### View All Suppliers
- **Formatted Table**: Clean display with headers
- **Complete Information**: ID, Name, Contact Information
- **Empty State**: "No suppliers found" when list is empty
- **Professional Layout**: Well-organized table format

### Update Supplier
- **Supplier Selection**: Choose by supplier ID
- **Current Value Display**: Shows existing values in brackets
- **Selective Updates**: Update only specific fields
- **Skip Option**: Press Enter to keep current values
- **Validation**: Ensures valid data for each field

### Delete Supplier
- **Supplier Selection**: Choose by supplier ID
- **Confirmation**: "Are you sure?" prompt
- **Safety Check**: Prevents accidental deletions
- **Success Feedback**: Clear confirmation messages

## Data Models

### Product.java
- **Fields**: id, name, price, quantity, reorderLevel
- **Full CRUD Support**: Constructor, getters, and all setters
- **Serializable**: Ready for future persistence
- **Formatted toString()**: Clean object representation

### Supplier.java
- **Fields**: id, name, contactInfo
- **Full CRUD Support**: Constructor, getters, and all setters
- **Serializable**: Ready for future persistence
- **Formatted toString()**: Clean object representation

## Technical Details

- **Language**: Java
- **Data Storage**: In-memory (ArrayList for both products and suppliers)
- **Input Validation**: Comprehensive validation for all inputs
- **Error Handling**: Try-catch blocks with user-friendly messages
- **Menu Navigation**: Hierarchical menu system with proper flow control

## Example Usage

### Adding a Product
```
=== Add New Product ===
Enter product name: Gaming Laptop
Enter product price: $1299.99
Enter initial quantity: 5
Enter reorder level: 2
Product added successfully! Product ID: P001
```

### Adding a Supplier
```
=== Add New Supplier ===
Enter supplier name: Tech Solutions Inc.
Enter contact information: contact@techsolutions.com
Supplier added successfully! Supplier ID: S001
```

### Checking Stock Levels
```
=== Stock Levels ===
ID         Name                 Quantity   Reorder Level   Status
------------------------------------------------------------
P001       Gaming Laptop        5          2               OK
P002       Wireless Mouse       3          5               LOW STOCK
```

### Viewing Suppliers
```
=== All Suppliers ===
ID         Name                           Contact Information
------------------------------------------------------------
S001       Tech Solutions Inc.            contact@techsolutions.com
S002       Global Electronics             sales@globalelectronics.com
```

### Updating a Supplier
```
=== Update Supplier ===
[Supplier list displayed]
Enter supplier ID to update: S001
Current supplier details: Supplier{id='S001', name='Tech Solutions Inc.', contactInfo='contact@techsolutions.com'}

Enter new details (press Enter to keep current value):
Name [Tech Solutions Inc.]: Premium Tech Solutions
Contact Information [contact@techsolutions.com]: info@premiumtech.com
Supplier updated successfully!
```

## Future Phases

This Phase 4 provides the foundation for:
- **Phase 5**: Add Sales & Orders functionality
- **Phase 6**: Implement Reports and Analytics
- **Phase 7**: Data persistence and file storage
- **Phase 8**: Advanced features and integrations
- **Phase 9**: User authentication and multi-user support
