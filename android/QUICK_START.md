# Quick Start Guide - Android Inventory App

## Installation & First Run (5 minutes)

### Step 1: Open Project
1. Launch Android Studio
2. File > Open > Select `InventoryApp` folder
3. Wait for Gradle sync to complete

### Step 2: Create Emulator or Connect Device
**Using Emulator:**
- Tools > Device Manager > Create Device
- Select Pixel 5, Android 14, click Finish
- Start the emulator

**Using Physical Device:**
- Connect via USB
- Enable Developer Options (tap Build Number 7 times)
- Enable USB Debugging
- Allow USB debugging prompt

### Step 3: Run App
1. Click the green **Run** button (or press Shift+F10)
2. Select your device/emulator
3. Click OK
4. Wait for app to build and install (~30 seconds)

### Step 4: Test Features
The app opens with the Dashboard:

**Dashboard Screen:**
- Shows total products (currently 0)
- Shows low stock count (currently 0)
- Two buttons: "Add Product" and "View All Products"

**Try This:**
1. Tap "+ Add Product"
2. Fill in the form:
   - Name: "Laptop"
   - Category: "Electronics"
   - Price: "45000"
   - Quantity: "3"
3. Tap "Save" → Toast shows "Product added successfully!"
4. You're back at dashboard → Total Products now shows 1, Low Stock shows 1 (qty 3 < 5)

5. Tap "View All Products"
6. See your product with RED background (low stock warning)
7. Tap Edit button to modify
8. Tap Delete button to remove (confirms first)

**Test Low Stock:**
1. Add product with qty 10 → Normal (white) background
2. Edit to qty 4 → Changes to RED background
3. Edit to qty 5+ → Changes back to normal

## File Structure Explained

```
InventoryApp/
├── app/                              # Main application module
│   ├── src/main/java/.../
│   │   ├── activities/               # 4 Activity screens
│   │   ├── adapter/                  # RecyclerView adapter
│   │   ├── model/                    # Product class
│   │   └── database/                 # DatabaseHelper (CRUD)
│   ├── src/main/res/
│   │   ├── layout/                   # 5 XML layout files
│   │   ├── values/                   # Colors, strings
│   │   └── drawable/                 # Gradient background
│   ├── AndroidManifest.xml           # App configuration
│   └── build.gradle                  # Dependencies
├── build.gradle                      # Project config
├── settings.gradle                   # Module settings
├── README.md                         # Full documentation
└── BUILD_AND_SIGN.md                # APK generation guide
```

## Key Concepts

### Activities (4 Screens)
1. **MainActivity** - Dashboard with stats
2. **AddProductActivity** - Create new product form
3. **ProductListActivity** - RecyclerView list
4. **UpdateProductActivity** - Edit product form

### Database
- SQLite database: `inventory.db`
- Table: `products` with 5 columns (id, name, category, price, quantity)
- Automatically created on first run

### Data Flow
```
User Input Form
    ↓
Input Validation (no empty, no negative)
    ↓
DatabaseHelper CRUD Operation
    ↓
SQLite Database
    ↓
Load from Database
    ↓
ProductAdapter Updates RecyclerView
    ↓
UI Refresh (product list updates)
```

## Common Tasks

### Add Product
1. Dashboard > "+ Add Product"
2. Fill all fields
3. Click Save
4. Confirmation toast appears

### Edit Product
1. View Products list
2. Find product, click Edit button
3. Modify fields
4. Click Update
5. Returns to list

### Delete Product
1. View Products list
2. Find product, click Delete button
3. Confirmation dialog appears
4. Confirm deletion
5. Product removed from list

### Search Products (Future Feature)
Currently not implemented - good for extending!

### Low Stock Alert
- Automatic: any product with quantity < 5
- Visual: red background on list item
- Text: "⚠️ LOW STOCK" warning appears
- Dashboard: low stock count increases

## Troubleshooting

### App Won't Run
**Problem:** Gradle sync failed
**Solution:**
1. File > Sync Now
2. Build > Clean Project
3. Build > Rebuild Project

**Problem:** Can't find emulator
**Solution:**
1. Tools > Device Manager
2. Create new virtual device
3. Select device before running app

### Database Issues
**Problem:** Products not saving
**Solution:**
- App creates database automatically
- Check Android Device Monitor > File Explorer
- Database location: `/data/data/com.example.inventoryapp/databases/inventory.db`

**Problem:** Quantity shows as 0
**Solution:**
- Ensure you enter a number in quantity field
- No letters allowed in quantity field

### UI Issues
**Problem:** Buttons look wrong
**Solution:**
- Update build.gradle dependencies
- File > Sync Now
- Rebuild project

## Performance Tips
- Adding products < 1 second
- Editing products < 1 second
- Deleting products < 1 second
- Loading 100+ products < 2 seconds

## Next Steps

### Learn the Code
1. Start with `MainActivity.java` - entry point
2. Look at `DatabaseHelper.java` - all database logic
3. Check `ProductAdapter.java` - how list displays
4. Study `UpdateProductActivity.java` - edit logic

### Modify the App
1. Add search functionality
2. Add product images
3. Add stock history
4. Add export to CSV
5. Add dark mode

### Build Release APK
1. Follow BUILD_AND_SIGN.md
2. Generate signed APK
3. Install on real device
4. Share with others

## Database Queries (Advanced)

If you want to debug:
```sql
-- View all products
SELECT * FROM products;

-- Get low stock items
SELECT * FROM products WHERE quantity < 5;

-- Count products by category
SELECT category, COUNT(*) FROM products GROUP BY category;

-- Total inventory value
SELECT SUM(price * quantity) FROM products;
```

Access via Android Device Monitor (Tools > Android > Android Device Monitor)

## Performance Benchmarks
- APK Size: ~5-10 MB
- Database Size: < 1 MB (for 1000 products)
- Memory Usage: ~50 MB
- Battery Usage: Minimal (local storage only)

## Build APK for Release
```bash
# Takes about 2-3 minutes
Build > Generate Signed Bundle / APK
Select APK > Next
Choose keystore > Next
Select release > Finish

# APK location: app/release/app-release.apk
```

## Installation on Real Device
```bash
adb install -r app/release/app-release.apk
```

## Delete App Data (Reset)
```bash
adb shell pm clear com.example.inventoryapp
```

## View App Logs
```bash
adb logcat | grep InventoryApp
```

---

## Get Help
1. Check README.md for detailed documentation
2. Check BUILD_AND_SIGN.md for APK generation
3. Review source code comments
4. Android Studio's built-in Help
5. Android Developer Documentation: developer.android.com

---

**Ready to start? Open Android Studio and load the InventoryApp folder!**
