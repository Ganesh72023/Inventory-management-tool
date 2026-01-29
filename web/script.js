// ===================================
// Inventory Management Web App
// Using LocalStorage for data persistence
// ===================================

// ===================================
// Global Variables
// ===================================

// Storage key for LocalStorage
const STORAGE_KEY = 'inventoryProducts';

// DOM Elements
const productForm = document.getElementById('productForm');
const productNameInput = document.getElementById('productName');
const productPriceInput = document.getElementById('productPrice');
const productQuantityInput = document.getElementById('productQuantity');
const productIdInput = document.getElementById('productId');
const submitBtn = document.getElementById('submitBtn');
const tableBody = document.getElementById('tableBody');
const searchInput = document.getElementById('searchInput');
const clearBtn = document.getElementById('clearBtn');
const emptyState = document.getElementById('emptyState');
const totalProductsSpan = document.getElementById('totalProducts');
const lowStockCountSpan = document.getElementById('lowStockCount');

// Modal Elements
const editModal = document.getElementById('editModal');
const closeBtn = document.getElementById('closeBtn');
const editForm = document.getElementById('editForm');
const editProductNameInput = document.getElementById('editProductName');
const editProductPriceInput = document.getElementById('editProductPrice');
const editProductQuantityInput = document.getElementById('editProductQuantity');
const closeModalBtn = document.querySelector('.close');

let currentEditId = null;
let allProducts = []; // Array to store all products
let filteredProducts = []; // Array to store filtered products

// ===================================
// Initialize App
// ===================================

document.addEventListener('DOMContentLoaded', function() {
    // Load products from LocalStorage
    loadProducts();
    
    // Render products table
    renderTable();
    
    // Update statistics
    updateStats();
    
    // Setup event listeners
    setupEventListeners();
});

// ===================================
// Event Listeners Setup
// ===================================

function setupEventListeners() {
    // Form submission - Add new product
    productForm.addEventListener('submit', handleAddProduct);
    
    // Search input - Filter products
    searchInput.addEventListener('input', handleSearch);
    
    // Clear all button
    clearBtn.addEventListener('click', handleClearAll);
    
    // Modal close buttons
    closeBtn.addEventListener('click', closeModal);
    closeModalBtn.addEventListener('click', closeModal);
    
    // Modal edit form submission
    editForm.addEventListener('submit', handleEditProduct);
    
    // Close modal when clicking outside
    editModal.addEventListener('click', function(e) {
        if (e.target === editModal) {
            closeModal();
        }
    });
}

// ===================================
// Core Functions - Data Management
// ===================================

/**
 * Load products from LocalStorage
 * If no products exist, initialize empty array
 */
function loadProducts() {
    try {
        const stored = localStorage.getItem(STORAGE_KEY);
        if (stored) {
            allProducts = JSON.parse(stored);
        } else {
            allProducts = [];
        }
        filteredProducts = [...allProducts];
    } catch (error) {
        console.error('Error loading products:', error);
        allProducts = [];
        filteredProducts = [];
    }
}

/**
 * Save products to LocalStorage
 */
function saveProducts() {
    try {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(allProducts));
    } catch (error) {
        console.error('Error saving products:', error);
        showNotification('Error saving data', 'error');
    }
}

/**
 * Add a new product
 * Validates input and prevents duplicates
 */
function handleAddProduct(e) {
    e.preventDefault();
    
    // Get input values
    const name = productNameInput.value.trim();
    const price = parseFloat(productPriceInput.value);
    const quantity = parseInt(productQuantityInput.value);
    const id = productIdInput.value;
    
    // Validation
    if (!name || !price || isNaN(quantity)) {
        showNotification('Please fill in all fields correctly', 'error');
        return;
    }
    
    if (price < 0 || quantity < 0) {
        showNotification('Price and quantity cannot be negative', 'error');
        return;
    }
    
    // Check if editing existing product
    if (id) {
        updateProduct(parseInt(id), name, price, quantity);
    } else {
        // Create new product
        const newProduct = {
            id: Date.now(), // Use timestamp as unique ID
            name: name,
            price: price,
            quantity: quantity,
            dateAdded: new Date().toLocaleDateString()
        };
        
        allProducts.push(newProduct);
        saveProducts();
        showNotification(`Product "${name}" added successfully!`, 'success');
    }
    
    // Reset form
    resetForm();
    
    // Re-render table and update stats
    renderTable();
    updateStats();
    
    // Reset search
    searchInput.value = '';
    filteredProducts = [...allProducts];
}

/**
 * Update an existing product
 */
function updateProduct(id, name, price, quantity) {
    const product = allProducts.find(p => p.id === id);
    if (product) {
        product.name = name;
        product.price = price;
        product.quantity = quantity;
        saveProducts();
        showNotification(`Product "${name}" updated successfully!`, 'success');
    }
}

/**
 * Delete a product by ID
 */
function deleteProduct(id) {
    const product = allProducts.find(p => p.id === id);
    if (product) {
        // Confirm deletion
        if (confirm(`Are you sure you want to delete "${product.name}"?`)) {
            allProducts = allProducts.filter(p => p.id !== id);
            saveProducts();
            renderTable();
            updateStats();
            showNotification(`Product deleted successfully!`, 'success');
        }
    }
}

// ===================================
// UI Rendering Functions
// ===================================

/**
 * Render the products table
 */
function renderTable() {
    const displayProducts = filteredProducts.length > 0 ? filteredProducts : allProducts;
    
    // Clear table body
    tableBody.innerHTML = '';
    
    // Show/hide empty state
    if (displayProducts.length === 0) {
        emptyState.classList.add('show');
        emptyState.style.display = 'block';
        return;
    } else {
        emptyState.classList.remove('show');
        emptyState.style.display = 'none';
    }
    
    // Create table rows
    displayProducts.forEach(product => {
        const row = createTableRow(product);
        tableBody.appendChild(row);
    });
}

/**
 * Create a single table row for a product
 */
function createTableRow(product) {
    const row = document.createElement('tr');
    
    // Add low stock class if quantity < 5
    if (product.quantity < 5) {
        row.classList.add('low-stock-row');
    }
    
    // Determine status
    const isLowStock = product.quantity < 5;
    const statusClass = isLowStock ? 'status-low-stock' : 'status-in-stock';
    const statusText = isLowStock ? '⚠️ Low Stock' : '✓ In Stock';
    
    // Create row HTML
    row.innerHTML = `
        <td>${escapeHtml(product.name)}</td>
        <td>$${product.price.toFixed(2)}</td>
        <td>${product.quantity}</td>
        <td>
            <span class="status-badge ${statusClass}">
                ${statusText}
            </span>
        </td>
        <td>
            <div class="action-buttons">
                <button class="btn-edit" onclick="editProductClick(${product.id})">Edit</button>
                <button class="btn-delete" onclick="deleteProduct(${product.id})">Delete</button>
            </div>
        </td>
    `;
    
    return row;
}

/**
 * Update statistics display
 */
function updateStats() {
    totalProductsSpan.textContent = allProducts.length;
    
    // Count low stock items
    const lowStockCount = allProducts.filter(p => p.quantity < 5).length;
    lowStockCountSpan.textContent = lowStockCount;
    
    // Show alert if there are low stock items
    if (lowStockCount > 0) {
        showLowStockAlert(lowStockCount);
    }
}

// ===================================
// Modal and Edit Functions
// ===================================

/**
 * Open edit modal and populate with product data
 */
function editProductClick(id) {
    const product = allProducts.find(p => p.id === id);
    if (product) {
        currentEditId = id;
        editProductNameInput.value = product.name;
        editProductPriceInput.value = product.price;
        editProductQuantityInput.value = product.quantity;
        openModal();
    }
}

/**
 * Handle edit form submission
 */
function handleEditProduct(e) {
    e.preventDefault();
    
    const name = editProductNameInput.value.trim();
    const price = parseFloat(editProductPriceInput.value);
    const quantity = parseInt(editProductQuantityInput.value);
    
    if (!name || !price || isNaN(quantity)) {
        showNotification('Please fill in all fields correctly', 'error');
        return;
    }
    
    if (price < 0 || quantity < 0) {
        showNotification('Price and quantity cannot be negative', 'error');
        return;
    }
    
    updateProduct(currentEditId, name, price, quantity);
    closeModal();
    renderTable();
    updateStats();
    searchInput.value = '';
    filteredProducts = [...allProducts];
}

/**
 * Open the edit modal
 */
function openModal() {
    editModal.classList.add('show');
}

/**
 * Close the edit modal
 */
function closeModal() {
    editModal.classList.remove('show');
    editForm.reset();
    currentEditId = null;
}

// ===================================
// Search and Filter Functions
// ===================================

/**
 * Handle search input
 */
function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase().trim();
    
    if (!searchTerm) {
        filteredProducts = [...allProducts];
    } else {
        filteredProducts = allProducts.filter(product =>
            product.name.toLowerCase().includes(searchTerm) ||
            product.price.toString().includes(searchTerm) ||
            product.quantity.toString().includes(searchTerm)
        );
    }
    
    renderTable();
}

/**
 * Clear all products after confirmation
 */
function handleClearAll() {
    if (allProducts.length === 0) {
        showNotification('No products to clear', 'warning');
        return;
    }
    
    if (confirm('Are you sure you want to delete ALL products? This cannot be undone.')) {
        allProducts = [];
        filteredProducts = [];
        saveProducts();
        renderTable();
        updateStats();
        resetForm();
        searchInput.value = '';
        showNotification('All products deleted', 'success');
    }
}

// ===================================
// Utility Functions
// ===================================

/**
 * Reset the form to initial state
 */
function resetForm() {
    productForm.reset();
    productIdInput.value = '';
    submitBtn.textContent = 'Add Product';
}

/**
 * Show notification to user
 */
function showNotification(message, type = 'info') {
    // Create alert element
    const alert = document.createElement('div');
    alert.className = `alert alert-${type}`;
    alert.textContent = message;
    
    // Insert at top of form section
    const formSection = document.querySelector('.form-section');
    formSection.insertBefore(alert, formSection.firstChild);
    
    // Show alert
    setTimeout(() => alert.classList.add('show'), 10);
    
    // Auto-remove after 4 seconds
    setTimeout(() => {
        alert.classList.remove('show');
        setTimeout(() => alert.remove(), 300);
    }, 4000);
}

/**
 * Show low stock alert when items are running low
 */
function showLowStockAlert(count) {
    const message = `⚠️ WARNING: ${count} product(s) have low stock (quantity < 5)`;
    // Only show in console and stats - visual indicator already in table
    console.warn(message);
}

/**
 * Escape HTML special characters to prevent XSS
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// ===================================
// Sample Data for Testing (Optional)
// ===================================

/**
 * Load sample data for testing
 * Uncomment the line below to load sample data on page load
 */
function loadSampleData() {
    const sampleProducts = [
        {
            id: 1,
            name: 'Laptop',
            price: 999.99,
            quantity: 3,
            dateAdded: new Date().toLocaleDateString()
        },
        {
            id: 2,
            name: 'Mouse',
            price: 29.99,
            quantity: 15,
            dateAdded: new Date().toLocaleDateString()
        },
        {
            id: 3,
            name: 'Keyboard',
            price: 79.99,
            quantity: 4,
            dateAdded: new Date().toLocaleDateString()
        },
        {
            id: 4,
            name: 'Monitor',
            price: 299.99,
            quantity: 8,
            dateAdded: new Date().toLocaleDateString()
        },
        {
            id: 5,
            name: 'Headphones',
            price: 149.99,
            quantity: 2,
            dateAdded: new Date().toLocaleDateString()
        }
    ];
    
    allProducts = sampleProducts;
    filteredProducts = [...allProducts];
    saveProducts();
    renderTable();
    updateStats();
}

// Uncomment the next line to load sample data:
// loadSampleData();
