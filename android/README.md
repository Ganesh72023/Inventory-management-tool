# Inventory Management App

A complete Android Inventory Management Application built with Java and SQLite database.

## Features

✅ **Add Product** - Add new products with name, category, price, and quantity  
✅ **View Products** - Display all products in a scrollable RecyclerView list  
✅ **Update Product** - Edit existing product details  
✅ **Delete Product** - Remove products with confirmation dialog  
✅ **Low Stock Alert** - Visual warning for products with quantity < 5  
✅ **Input Validation** - Prevents empty fields and invalid data  
✅ **Toast Notifications** - Success and error messages  
✅ **Offline Storage** - All data stored locally using SQLite  

## Project Structure

```
InventoryApp/
├── src/main/
│   ├── java/com/example/inventoryapp/
│   │   ├── MainActivity.java              # Dashboard with navigation
│   │   ├── AddProductActivity.java        # Add new product form
│   │   ├── ProductListActivity.java       # Display products in RecyclerView
│   │   ├── UpdateProductActivity.java     # Update/Delete product
│   │   ├── DatabaseHelper.java            # SQLite CRUD operations
│   │   ├── Product.java                   # Product model class
│   │   └── ProductAdapter.java            # RecyclerView adapter
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml          # Dashboard layout
│   │   │   ├── activity_add_product.xml   # Add product form
│   │   │   ├── activity_product_list.xml  # Product list view
│   │   │   ├── activity_update_product.xml# Update product form
│   │   │   └── row_product.xml            # Product list item layout
│   │   └── values/
│   │       └── strings.xml                # String resources
│   └── AndroidManifest.xml                # App manifest
├── build.gradle                           # Module build configuration
└── settings.gradle                        # Project settings
```

## Database Schema

**Table: products**

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER (Primary Key, Auto-increment) | Unique product identifier |
| name | TEXT | Product name (required) |
| category | TEXT | Product category (required) |
| price | REAL | Product price (required) |
| quantity | INTEGER | Stock quantity (required) |

## Classes Overview

### 1. **Product.java**
Model class representing a product with getters/setters and a method to check low stock status.

### 2. **DatabaseHelper.java**
Extends SQLiteOpenHelper and handles all database operations:
- `onCreate()` - Creates the products table
- `addProduct()` - Insert new product
- `getAllProducts()` - Retrieve all products
- `getProductById()` - Get specific product
- `updateProduct()` - Update existing product
- `deleteProduct()` - Remove product
- `getLowStockProducts()` - Get products with quantity < 5
- `getTotalProducts()` - Get count of all products

### 3. **MainActivity.java**
Dashboard screen with:
- Display total product count
- Navigation buttons for Add, View, and Low Stock products
- Real-time product count update

### 4. **AddProductActivity.java**
Form to add new products with:
- Input fields for name, category, price, quantity
- Input validation (no empty fields)
- Price and quantity validation (no negative values)
- Toast notifications for success/errors
- Cancel button to return to dashboard

### 5. **ProductListActivity.java**
Displays products in RecyclerView with:
- All products view or low stock products view
- Empty state message
- Click to edit/delete product
- Auto-refresh on activity resume

### 6. **UpdateProductActivity.java**
Edit and delete product with:
- Pre-filled form with current product data
- Update button to save changes
- Delete button with confirmation dialog
- Input validation same as AddProductActivity
- Cancel button

### 7. **ProductAdapter.java**
RecyclerView adapter that:
- Displays product information
- Shows low stock warning (red background) for products with quantity < 5
- Handles item click to open UpdateProductActivity
- Provides updateList() method to refresh data

## Setup Instructions

### Prerequisites
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK 21 or higher

### Steps to Run

1. **Clone/Import Project**
   - Open Android Studio
   - File → Open → Select InventoryApp folder

2. **Build the Project**
   - Build → Make Project (Ctrl+F9)
   - Wait for Gradle build to complete

3. **Run on Emulator or Device**
   - Click Run button (Shift+F10)
   - Select emulator or connected device
   - App will launch with MainActivity

### First Time Setup
- App creates SQLite database automatically on first run
- Database file: `inventory.db`
- Location: `/data/data/com.example.inventoryapp/databases/inventory.db`

## Usage Guide

### Adding a Product
1. Click **+ Add Product** button on dashboard
2. Fill in all fields:
   - Product Name (required)
   - Category (required)
   - Price (required, positive number)
   - Quantity (required, positive number)
3. Click **Save Product**
4. Success message appears, returns to dashboard

### Viewing Products
1. Click **View All Products** button on dashboard
2. RecyclerView displays all products
3. Product count updates automatically
4. Click on any product to edit/delete it

### Checking Low Stock
1. Click **⚠ Low Stock Products** button on dashboard
2. Shows only products with quantity < 5
3. Red background highlights low stock items
4. Click on product to restock/update quantity

### Updating a Product
1. Click on any product from the list
2. UpdateProductActivity opens with current data
3. Modify any field
4. Click **Update Product**
5. Success message appears, returns to list

### Deleting a Product
1. Open product for editing
2. Click **Delete Product**
3. Confirmation dialog appears
4. Confirm deletion
5. Product removed from database, returns to list

## Input Validation

The app validates all inputs:
- ✓ No empty fields allowed
- ✓ Price must be a valid decimal number (≥ 0)
- ✓ Quantity must be a valid integer (≥ 0)
- ✓ All errors shown via Toast messages

## Low Stock Alert

Products are marked as low stock when:
- Quantity < 5

Visual indicators:
- Light red background (#FFEBEE) on product card
- "⚠ LOW STOCK" warning text displayed
- Low Stock Products view to see all low-stock items

## UI/UX Features

- **Clean, Material Design** - Simple and intuitive interface
- **Color-coded buttons** - Green (Add), Blue (View), Orange (Low Stock), Red (Delete)
- **Responsive layouts** - Works on various screen sizes
- **Toast notifications** - User feedback for all actions
- **Empty state handling** - Shows message when no products exist
- **Elevation/shadows** - Visual depth with material design elevation

## Dependencies

```gradle
androidx.appcompat:appcompat:1.6.1
androidx.constraintlayout:constraintlayout:2.1.4
androidx.recyclerview:recyclerview:1.3.1
com.google.android.material:material:1.9.0
```

## Testing the App

### Test Scenarios

1. **Add Product**
   - Add product with all fields → Success
   - Try adding with empty name → Error message
   - Try adding negative price → Error message

2. **View Products**
   - View empty list → Shows "No products available"
   - View with products → All products display correctly
   - Product count updates → Count matches database

3. **Low Stock Alert**
   - Add product with quantity < 5 → Appears in low stock list
   - Add product with quantity ≥ 5 → Doesn't appear in low stock list
   - Red background shows for low stock → Visual indicator works

4. **Update Product**
   - Click product → Form pre-filled correctly
   - Update values → Values save correctly
   - Invalid input → Error message shown

5. **Delete Product**
   - Click delete → Confirmation dialog appears
   - Confirm delete → Product removed
   - Cancel delete → Product remains

## Troubleshooting

### Database Issues
- **Problem**: Database not loading
- **Solution**: Clear app data → Settings → Apps → Inventory Management → Storage → Clear Data

### Activity Crashes
- **Problem**: Activity crashes on open
- **Solution**: Check logcat for exact error, ensure all findViewById IDs match XML

### RecyclerView Not Showing
- **Problem**: Products don't display
- **Solution**: Ensure database has products, check ProductAdapter implementation

## Future Enhancements

- Search/Filter products by name or category
- Export product list to CSV
- Product images
- Barcode scanning
- Inventory history/analytics
- Multiple warehouses
- User authentication
- Cloud sync

## Code Quality

- ✓ Full comments on all important methods
- ✓ Proper exception handling
- ✓ Input validation throughout
- ✓ Clean code structure
- ✓ Following Android best practices
- ✓ Proper resource management (database closing)

## License

This project is provided as-is for educational purposes.

## Support

For issues or questions, check:
1. Android logcat for error messages
2. Database schema in DatabaseHelper.java
3. Intent extras in activity code
4. RecyclerView adapter implementation

---

**Version**: 1.0  
**Author**: Android Developer  
**Language**: Java  
**Database**: SQLite  
**Min SDK**: 21  
**Target SDK**: 33
