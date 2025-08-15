# Phase 8 Testing Script

## Quick Test Guide for Phase 8 Features

### Prerequisites
- Java 8+ installed
- All source files compiled successfully
- Exports directory created

### Test 1: User Authentication
```bash
# Compile and run
javac -cp src src/com/inventory/Main.java
java -cp src com.inventory.Main

# Login with default admin credentials
Username: admin
Password: admin123

# Expected: Successful login with "Welcome, admin (ADMIN)" message
```

### Test 2: Create Staff User
1. Navigate to User Management (Option 7)
2. Select "Create New User" (Option 1)
3. Enter details:
   - Username: staff1
   - Password: staff123
   - Role: STAFF
4. Verify user creation success message

### Test 3: Test Role-Based Access
1. Logout (User Management → Option 4)
2. Login as staff1/staff123
3. Try to delete a product (should be denied)
4. Try to access Data Management (should be denied)

### Test 4: Product Search & Filtering
1. Login as admin
2. Add several test products with different names and prices
3. Navigate to Product Management → Search Products
4. Test each search option:
   - Name search (partial match)
   - Price range search
   - Supplier ID search
   - View by supplier

### Test 5: Report Export
1. Create products with low stock levels
2. Navigate to Reports & Analytics
3. Test export functions:
   - Export Low Stock Report (Option 4)
   - Export Inventory Value Report (Option 5)
4. Verify CSV files are created in exports/ directory

### Test 6: Data Persistence
1. Add various data (products, suppliers, users)
2. Exit application
3. Restart and verify data is loaded
4. Check that all information is preserved

### Expected Results
- All Phase 8 features should work correctly
- User authentication should enforce role-based permissions
- Search functionality should return accurate results
- Export functionality should create timestamped CSV files
- Data should persist between application sessions

### Troubleshooting
- If compilation fails, check Java version and package structure
- If runtime errors occur, verify file permissions and directory access
- If authentication fails, check users.dat file integrity
