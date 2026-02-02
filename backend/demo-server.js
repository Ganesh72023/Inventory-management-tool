const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors());
app.use(express.json());

// In-memory database
let products = [
  { id: 1, name: 'Laptop', category: 'Electronics', price: 50000, quantity: 3, createdAt: new Date() },
  { id: 2, name: 'Mouse', category: 'Electronics', price: 500, quantity: 15, createdAt: new Date() },
  { id: 3, name: 'Keyboard', category: 'Electronics', price: 2000, quantity: 8, createdAt: new Date() },
  { id: 4, name: 'Monitor', category: 'Electronics', price: 15000, quantity: 2, createdAt: new Date() },
  { id: 5, name: 'USB Cable', category: 'Accessories', price: 200, quantity: 50, createdAt: new Date() }
];

let nextId = 6;

// GET all products
app.get('/api/products', (req, res) => {
  res.json(products);
});

// POST - Add product
app.post('/api/products', (req, res) => {
  const newProduct = {
    id: nextId++,
    name: req.body.name,
    category: req.body.category,
    price: parseFloat(req.body.price),
    quantity: parseInt(req.body.quantity),
    createdAt: new Date()
  };
  products.push(newProduct);
  res.status(201).json(newProduct);
});

// PUT - Update product
app.put('/api/products/:id', (req, res) => {
  const product = products.find(p => p.id === parseInt(req.params.id));
  if (!product) return res.status(404).json({ error: 'Product not found' });
  
  product.name = req.body.name || product.name;
  product.category = req.body.category || product.category;
  product.price = req.body.price ? parseFloat(req.body.price) : product.price;
  product.quantity = req.body.quantity ? parseInt(req.body.quantity) : product.quantity;
  
  res.json(product);
});

// DELETE - Remove product
app.delete('/api/products/:id', (req, res) => {
  products = products.filter(p => p.id !== parseInt(req.params.id));
  res.json({ message: 'Deleted' });
});

// Search
app.get('/api/products/search/:keyword', (req, res) => {
  const filtered = products.filter(p => 
    p.name.toLowerCase().includes(req.params.keyword.toLowerCase())
  );
  res.json(filtered);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`✅ Demo Backend Running on port ${PORT}`);
});
