# Inventory Management Web App

A simple, clean, and responsive web-based inventory management application built with vanilla HTML, CSS, and JavaScript.

## Features

✅ **Add Products** - Add new products with name, price, and quantity  
✅ **View Inventory** - Display all products in a sortable table  
✅ **Edit Products** - Update existing product details via modal  
✅ **Delete Products** - Remove products with confirmation dialog  
✅ **Search/Filter** - Search products by name, price, or quantity  
✅ **Low Stock Alerts** - Visual warning when quantity < 5  
✅ **LocalStorage** - All data persists in browser storage  
✅ **Statistics** - Show total products and low stock count  
✅ **Responsive Design** - Works on desktop, tablet, and mobile  
✅ **No Backend Needed** - Pure client-side application  

## Files

- **index.html** - Main HTML file with form and table
- **style.css** - Complete styling with responsive design
- **script.js** - All JavaScript functionality and logic

## Quick Start

1. **Save the files** to your local machine
2. **Open index.html** in a web browser
3. **Start adding products**

### Using in VS Code

```bash
# Method 1: Right-click index.html → Open with Live Server
# Method 2: Install Live Server extension, then click "Go Live"
# Method 3: Just open index.html directly in browser
```

No server or installation needed - works immediately!

## How to Use

### Adding a Product

1. Enter product name (e.g., "Laptop")
2. Enter price (e.g., 999.99)
3. Enter quantity (e.g., 5)
4. Click **"Add Product"** button
5. Product appears in table below

### Viewing Products

- All products display in the table immediately after adding
- Table shows: Name, Price, Quantity, and Status
- **✓ In Stock** = Quantity ≥ 5
- **⚠️ Low Stock** = Quantity < 5 (highlighted in red)

### Searching Products

- Type in the search box to filter products
- Searches by: Name, Price, or Quantity
- Results update in real-time
- Clear search to see all products again

### Editing a Product

1. Click **"Edit"** button next to product
2. Modal form appears with current data
3. Modify the fields
4. Click **"Save Changes"**
5. Product updates in table

### Deleting a Product

1. Click **"Delete"** button next to product
2. Confirmation dialog appears
3. Click **"OK"** to confirm deletion
4. Product is removed from inventory

### Clearing All Products

1. Click **"Clear All"** button in search bar
2. Confirmation dialog appears
3. All products deleted permanently
4. Table returns to empty state

## Data Storage

### How LocalStorage Works

- All data stored in browser's LocalStorage
- Data persists between page refreshes
- Data stored locally (no server/cloud)
- Data only cleared when browser data is cleared

### Storage Location

- **Chrome**: Settings → Privacy → Clear Browsing Data → Cookies and site data
- **Firefox**: Settings → Privacy → Cookies and Site Data
- **Edge**: Settings → Privacy → Clear Browsing Data

### Storage Key

- LocalStorage key: `inventoryProducts`
- Stores as JSON array of product objects

## Product Object Structure

```javascript
{
    id: 1234567890,           // Unique timestamp ID
    name: "Laptop",           // Product name
    price: 999.99,            // Product price
    quantity: 3,              // Stock quantity
    dateAdded: "01/28/2026"   // Date added
}
```

## Code Structure

### HTML (index.html)

- **Form Section**: Input fields for adding products
- **Stats Card**: Display totals and low stock count
- **Table Section**: Display all products
- **Search Bar**: Filter products
- **Modal**: Edit form for updating products

### CSS (style.css)

- **Reset**: Base styles
- **Layout**: Grid-based two-column layout
- **Components**: Buttons, table, form, modal
- **Animations**: Fade-in, slide-in effects
- **Responsive**: Mobile-first design (768px, 480px breakpoints)
- **Colors**: Purple gradient theme with accent colors

### JavaScript (script.js)

- **Data Management**: Load, save, add, update, delete products
- **UI Rendering**: Create table rows, update stats
- **Event Handling**: Form submit, search, modal operations
- **Validation**: Input validation with error messages
- **Utilities**: Notifications, HTML escaping, sample data

## Features Explained

### 1. Add Product Form

```html
<form id="productForm">
    <input type="text" id="productName" required>
    <input type="number" id="productPrice" required>
    <input type="number" id="productQuantity" required>
</form>
```

- Validates all fields before submission
- Prevents negative values
- Clears form after successful add
- Shows success/error notifications

### 2. Products Table

```
| Name | Price | Quantity | Status | Actions |
|------|-------|----------|--------|---------|
```

- Displays all products with key info
- Color-coded status badges
- Edit/Delete buttons for each row
- Low stock rows highlighted in red

### 3. Low Stock Alert

When quantity < 5:
- Row background turns light red
- Status shows "⚠️ Low Stock" in red
- Warning count displayed in statistics
- Console log warning message

### 4. Search Functionality

- Real-time search as you type
- Searches: Product name, price, quantity
- Highlights matching results
- Clears when you clear search box

### 5. Edit Modal

- Opens when clicking Edit button
- Pre-fills form with current product data
- Validates input same as add form
- Saves changes and closes on submit
- Can cancel without saving

### 6. Statistics

**Total Products**
- Count of all products in inventory
- Updates when products added/deleted

**Low Stock Items**
- Count of products with quantity < 5
- Alert indicator in red
- Helps identify products to restock

## Input Validation

The app validates all inputs:

✓ Name field is required (cannot be empty)  
✓ Price must be a positive number  
✓ Quantity must be a positive whole number  
✓ No special characters in inputs  
✓ Price format: max 2 decimal places  

Error messages appear as toast notifications:
- "Please fill in all fields correctly"
- "Price and quantity cannot be negative"

## Notifications

Toast notifications appear for:
- ✅ Product added successfully
- ✅ Product updated successfully
- ✅ Product deleted successfully
- ❌ Validation errors
- ⚠️ Warning messages

Notifications auto-dismiss after 4 seconds.

## Responsive Design

### Desktop (1024px+)
- Two-column layout (form + table side-by-side)
- Full-size table with all columns
- Large buttons and text

### Tablet (768px - 1023px)
- Single column layout
- Stacked form and table
- Adjusted spacing and sizing

### Mobile (480px - 767px)
- Full-width layout
- Compact table with smaller text
- Vertical action buttons
- Simplified stats display

### Small Mobile (< 480px)
- Minimal padding
- Single-column action buttons
- Optimized table columns
- Touch-friendly button sizes

## Browser Compatibility

| Browser | Compatibility |
|---------|---------------|
| Chrome | ✅ Full support |
| Firefox | ✅ Full support |
| Safari | ✅ Full support |
| Edge | ✅ Full support |
| IE 11 | ⚠️ Limited support* |

*IE 11 may not support some CSS features like CSS Grid

## Testing

### Test Scenarios

**1. Add Product**
```
Input: Name="Laptop", Price=999.99, Quantity=5
Result: Product appears in table, stats update
```

**2. Low Stock Alert**
```
Input: Name="Mouse", Price=29.99, Quantity=3
Result: Row highlighted red, status shows "⚠️ Low Stock"
```

**3. Search**
```
Input: Search "Laptop"
Result: Only Laptop product shows in table
```

**4. Edit Product**
```
Input: Click Edit, change Quantity to 10
Result: Product updates, low stock alert removed if applicable
```

**5. Delete Product**
```
Input: Click Delete, confirm
Result: Product removed, table updates, stats decrease
```

**6. Page Refresh**
```
Input: Add products, refresh page
Result: All products still there (LocalStorage persists)
```

### Sample Data

Uncomment this line in script.js to load test data:

```javascript
// loadSampleData();
```

This creates 5 sample products for testing:
- Laptop ($999.99, qty 3) - Low stock
- Mouse ($29.99, qty 15) - In stock
- Keyboard ($79.99, qty 4) - Low stock
- Monitor ($299.99, qty 8) - In stock
- Headphones ($149.99, qty 2) - Low stock

## Code Examples

### Adding a Product Programmatically

```javascript
// This happens automatically in the form
const newProduct = {
    id: Date.now(),
    name: "Laptop",
    price: 999.99,
    quantity: 5,
    dateAdded: new Date().toLocaleDateString()
};
allProducts.push(newProduct);
saveProducts();
```

### Accessing Products in Console

```javascript
// View all products
console.log(allProducts);

// Find a specific product
const laptop = allProducts.find(p => p.name === "Laptop");

// Get low stock products
const lowStock = allProducts.filter(p => p.quantity < 5);

// Update localStorage directly
localStorage.setItem('inventoryProducts', JSON.stringify(allProducts));
```

## Customization

### Change Colors

Edit `style.css` to change the color scheme:

```css
/* Purple to blue gradient header */
background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

/* Primary button color */
.btn-primary {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

/* Status badge colors */
.status-in-stock {
    background-color: #d4edda; /* Green */
}

.status-low-stock {
    background-color: #f8d7da; /* Red */
}
```

### Change Low Stock Threshold

Edit `script.js` to change when products are marked low stock:

```javascript
// Current: quantity < 5
if (product.quantity < 5) {
    row.classList.add('low-stock-row');
}

// Change to: quantity < 10
if (product.quantity < 10) {
    row.classList.add('low-stock-row');
}
```

### Add More Fields

To add a category field:

1. Add input in HTML:
   ```html
   <input type="text" id="productCategory" placeholder="Category">
   ```

2. Update product object in JS:
   ```javascript
   const newProduct = {
       id: Date.now(),
       name: name,
       price: price,
       quantity: quantity,
       category: categoryInput.value, // Add this
       dateAdded: new Date().toLocaleDateString()
   };
   ```

3. Add table column in HTML:
   ```html
   <th>Category</th>
   ```

4. Add data in JS:
   ```javascript
   row.innerHTML = `
       <td>${product.category}</td>
       ...
   `;
   ```

## Troubleshooting

### Problem: Data not saving

**Solution**: Check if LocalStorage is enabled in browser
- Chrome: Settings → Privacy → Third-party cookies
- Firefox: Privacy → Enhanced Tracking Protection

### Problem: Products not showing in table

**Solution**: 
- Clear search box
- Refresh page (Ctrl+F5)
- Check browser console for errors (F12)

### Problem: Modal not opening when clicking Edit

**Solution**:
- Check if JavaScript is enabled
- Open browser console (F12) and look for errors
- Try refreshing page

### Problem: Can't clear data

**Solution**: Clear browser data manually
- Chrome: Settings → Privacy → Clear Browsing Data
- Firefox: Settings → Privacy → Clear Recent History

## Tips & Tricks

1. **Bulk Load Sample Data**
   - Uncomment `loadSampleData();` in script.js
   - Great for testing with realistic data

2. **Export Data to CSV**
   - Copy data from console: `console.table(allProducts)`
   - Paste into Excel/Google Sheets

3. **Backup Products**
   - Open DevTools (F12) → Application → LocalStorage
   - Copy entire `inventoryProducts` value
   - Paste somewhere safe as backup

4. **Clear Everything**
   - Click "Clear All" button, or
   - DevTools → Application → LocalStorage → Delete

5. **Debug Products**
   - Open console (F12)
   - Type: `allProducts` to see all data
   - Type: `filteredProducts` to see filtered data

## Performance

- No external libraries (pure JavaScript)
- Lightweight: All files < 50KB combined
- Fast load time: < 1 second on any connection
- No network requests (works offline)
- Efficient DOM updates

## Accessibility

- Semantic HTML elements
- Proper label associations
- Keyboard navigation support
- Focus indicators on buttons
- Error messages clearly displayed
- Good color contrast ratios

## Future Enhancements

- 📊 Charts and analytics
- 📥 Import/Export to CSV
- 📸 Product images
- 🔍 Advanced filtering
- 📱 PWA support
- 🎨 Dark mode
- 🌍 Multiple languages
- 📧 Email notifications
- 📊 Inventory reports
- 💾 Cloud sync

## License

This project is provided as-is for educational purposes.

## Support

For issues:
1. Check browser console (F12 → Console)
2. Look for error messages
3. Try clearing browser data
4. Refresh and try again

## Credits

Created as a simple, beginner-friendly inventory management application.

---

**Version**: 1.0  
**Type**: Vanilla JavaScript Web App  
**Storage**: LocalStorage (Browser)  
**Dependencies**: None (Pure HTML/CSS/JS)  
**Last Updated**: January 28, 2026
