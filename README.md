# Inventory Management System - Phase 8: Advanced Features

A comprehensive Java console-based Inventory Management System with advanced features including user authentication, search & filtering, and report export functionality.

## 🚀 Features Overview

### ✅ Completed Phases (1-7)
- **Phase 1**: Product Management (Add, View, Update, Delete)
- **Phase 2**: Stock Management (Check Levels, Add/Remove Stock, Reorder Levels)
- **Phase 3**: Supplier Management (Add, View, Update, Delete)
- **Phase 4**: Sales & Orders (Create Orders, Process Sales, Order History)
- **Phase 5**: Reports & Analytics (Low Stock, Inventory Value, Sales Summary)
- **Phase 6**: Data Persistence (Automatic Save/Load, File Management)
- **Phase 7**: Enhanced UI & Validation (Input Validation, Error Handling)

### 🆕 Phase 8: Advanced Features
- **Search & Filtering**: Advanced product search capabilities
- **User Authentication**: Role-based access control (Admin/Staff)
- **Report Export**: CSV export functionality for reports
- **Enhanced Security**: Permission-based operations

## 🔐 User Authentication System

### User Roles
- **Admin**: Full access to all features
- **Staff**: Restricted access (cannot delete products/suppliers, no data management access)

### Default Credentials
- **Username**: `admin`
- **Password**: `admin123`

### Security Features
- Login required before accessing any menu
- Role-based permission system
- Maximum 3 login attempts
- Session management

## 🔍 Search & Filtering Features

### Product Search Options
1. **Name Search**: Case-insensitive partial matching
2. **Price Range**: Search by minimum and maximum price
3. **Supplier ID**: Find products by specific supplier
4. **View by Supplier**: Display all products from a specific supplier

### Search Results
- Clean, formatted table display
- Total result count
- Comprehensive product information including supplier details

## 📊 Report Export Functionality

### Exportable Reports
1. **Low Stock Report**: Products below reorder level
2. **Inventory Value Report**: Complete inventory with calculated values

### Export Features
- CSV format output
- Timestamped filenames
- Automatic exports directory creation
- Success confirmation messages

### Export Location
- Files saved in `/exports` folder
- Naming convention: `report_type_YYYYMMDD_HHMMSS.csv`

## 🏗️ System Architecture

### Core Components
```
src/com/inventory/
├── Main.java                 # Main application entry point
├── DataStore.java            # Data persistence layer
├── managers/                 # Business logic managers
│   ├── InventoryManager.java # Product & stock management
│   ├── SupplierManager.java  # Supplier operations
│   ├── OrderManager.java     # Sales & order processing
│   ├── ReportManager.java    # Reporting & analytics
│   └── UserManager.java      # User authentication & management
└── models/                   # Data models
    ├── Product.java          # Product entity
    ├── Supplier.java         # Supplier entity
    ├── Order.java            # Order entity
    └── User.java             # User entity
```

### Data Files
- `products.dat` - Product inventory data
- `suppliers.dat` - Supplier information
- `orders.dat` - Order history
- `users.dat` - User accounts and credentials

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Windows/Linux/macOS

### Compilation
```bash
javac -cp src src/com/inventory/Main.java
```

### Execution
```bash
java -cp src com.inventory.Main
```

## 🧪 Testing Instructions

### 1. User Authentication Testing

#### Login Test
1. Run the application
2. Enter credentials: `admin` / `admin123`
3. Verify successful login message
4. Test invalid credentials (should show remaining attempts)

#### Role-Based Access Test
1. Login as admin
2. Navigate to Product Management → Delete Product
3. Verify deletion is allowed
4. Create a staff account via User Management
5. Logout and login as staff
6. Try to delete a product (should be denied)

### 2. Search & Filtering Testing

#### Product Search Test
1. Add several products with different names, prices, and suppliers
2. Test name search with partial matches
3. Test price range search with various ranges
4. Test supplier ID search
5. Verify search results display correctly

#### Enhanced Search Test
1. Navigate to Product Management → Search Products
2. Test all search options (1-4)
3. Verify "View Products by Supplier" functionality
4. Check that empty results are handled gracefully

### 3. Report Export Testing

#### Low Stock Report Export
1. Create products with low stock levels
2. Navigate to Reports & Analytics → Export Low Stock Report
3. Verify exports directory is created
4. Check CSV file is generated with correct data
5. Verify success confirmation message

#### Inventory Value Report Export
1. Navigate to Reports & Analytics → Export Inventory Value Report
2. Verify CSV file is generated
3. Check that all products are included
4. Verify calculated values are correct

### 4. Data Persistence Testing

#### Save/Load Test
1. Add products, suppliers, and users
2. Exit the application
3. Restart and verify data is loaded
4. Check that all information is preserved

#### File Management Test
1. Navigate to Data Management (admin only)
2. View data file information
3. Test save all data functionality
4. Verify file sizes and existence

### 5. User Management Testing

#### Create User Test
1. Login as admin
2. Navigate to User Management → Create New User
3. Create a staff account
4. Verify user is saved and can be viewed

#### Password Change Test
1. Login as any user
2. Navigate to User Management → Change Password
3. Enter current password correctly
4. Set new password
5. Logout and verify new password works

### 6. Integration Testing

#### Complete Workflow Test
1. Login as admin
2. Add suppliers
3. Add products with supplier assignments
4. Create orders and process sales
5. Generate and export reports
6. Verify all data persists between sessions

## 🐛 Troubleshooting

### Common Issues

#### Compilation Errors
- Ensure Java 8+ is installed
- Check all source files are in correct package structure
- Verify import statements are correct

#### Runtime Errors
- Check file permissions for data files
- Ensure exports directory can be created
- Verify input validation is working correctly

#### Data Persistence Issues
- Check if data files are corrupted
- Verify file paths are correct
- Ensure sufficient disk space

### Debug Mode
- Check console output for detailed error messages
- Verify data file existence and permissions
- Test individual components in isolation

## 📝 Code Quality Features

### Input Validation
- Comprehensive error checking
- User-friendly error messages
- Input sanitization

### Error Handling
- Graceful degradation
- Informative error messages
- Data integrity protection

### Code Organization
- Modular design
- Clear separation of concerns
- Consistent naming conventions

## 🔮 Future Enhancements

### Potential Phase 9 Features
- Database integration
- Web-based interface
- Advanced analytics
- Barcode scanning
- Email notifications
- Multi-warehouse support

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

---

**Note**: This system is designed for educational and demonstration purposes. For production use, consider implementing additional security measures such as password hashing, database encryption, and audit logging.
