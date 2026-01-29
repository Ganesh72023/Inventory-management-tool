// Real-time API Configuration
const API_URL = 'https://inventory-api.railway.app' || 'http://localhost:5000';

// Get all products from database
async function loadProducts() {
  try {
    const response = await fetch(`${API_URL}/api/products`);
    const products = await response.json();
    render(products);
  } catch (error) {
    console.error('Error loading products:', error);
    // Fallback to localStorage
    loadFromLocalStorage();
  }
}

// Add product to database
async function addProduct(name, category, price, quantity) {
  if (!name || !price || !quantity) {
    alert('Please fill all fields!');
    return;
  }

  try {
    const response = await fetch(`${API_URL}/api/products`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, category, price: parseFloat(price), quantity: parseInt(quantity) })
    });
    
    const product = await response.json();
    
    // Clear form
    document.getElementById('productName').value = '';
    document.getElementById('productCategory').value = '';
    document.getElementById('productPrice').value = '';
    document.getElementById('productQuantity').value = '';
    
    // Reload products
    loadProducts();
  } catch (error) {
    console.error('Error adding product:', error);
    alert('Error adding product. Check backend connection.');
  }
}

// Update product
async function updateProduct(id, name, category, price, quantity) {
  try {
    const response = await fetch(`${API_URL}/api/products/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, category, price: parseFloat(price), quantity: parseInt(quantity) })
    });
    
    await response.json();
    loadProducts();
  } catch (error) {
    console.error('Error updating product:', error);
  }
}

// Delete product
async function deleteProduct(id) {
  if (!confirm('Are you sure you want to delete this product?')) return;
  
  try {
    await fetch(`${API_URL}/api/products/${id}`, {
      method: 'DELETE'
    });
    loadProducts();
  } catch (error) {
    console.error('Error deleting product:', error);
  }
}

// Search products
async function searchProducts(keyword) {
  if (!keyword) {
    loadProducts();
    return;
  }
  
  try {
    const response = await fetch(`${API_URL}/api/products/search/${keyword}`);
    const products = await response.json();
    render(products);
  } catch (error) {
    console.error('Error searching:', error);
  }
}

// Render products table
function render(products) {
  const table = document.getElementById('productTable');
  table.innerHTML = products.map(p => `
    <tr style="${p.quantity < 5 ? 'background-color: #ffebee;' : ''}">
      <td>${p.name}</td>
      <td>${p.category || 'N/A'}</td>
      <td>₹${p.price.toFixed(2)}</td>
      <td><strong>${p.quantity}</strong> ${p.quantity < 5 ? '⚠️ Low Stock' : ''}</td>
      <td>
        <button onclick="editProduct('${p._id}', '${p.name}', '${p.category}', '${p.price}', '${p.quantity}')" class="edit-btn">Edit</button>
        <button onclick="deleteProduct('${p._id}')" class="delete-btn">Delete</button>
      </td>
    </tr>
  `).join('');
}

// Edit product
function editProduct(id, name, category, price, quantity) {
  const newName = prompt('Product Name:', name);
  if (newName === null) return;
  
  const newCategory = prompt('Category:', category);
  if (newCategory === null) return;
  
  const newPrice = prompt('Price:', price);
  if (newPrice === null) return;
  
  const newQuantity = prompt('Quantity:', quantity);
  if (newQuantity === null) return;
  
  updateProduct(id, newName, newCategory, newPrice, newQuantity);
}

// Fallback: Load from localStorage
function loadFromLocalStorage() {
  const saved = localStorage.getItem('inventoryProducts');
  const products = saved ? JSON.parse(saved) : [];
  render(products);
}

// Attach form event
document.getElementById('productForm')?.addEventListener('submit', (e) => {
  e.preventDefault();
  addProduct(
    document.getElementById('productName').value,
    document.getElementById('productCategory').value,
    document.getElementById('productPrice').value,
    document.getElementById('productQuantity').value
  );
});

// Attach search event
document.getElementById('searchInput')?.addEventListener('keyup', (e) => {
  searchProducts(e.target.value);
});

// Load products on page load
window.addEventListener('load', loadProducts);

// Auto-refresh every 5 seconds (real-time)
setInterval(loadProducts, 5000);
