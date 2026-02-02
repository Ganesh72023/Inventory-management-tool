const express = require('express');
const cors = require('cors');
const redis = require('redis');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

// Redis Client
const redisClient = redis.createClient({
  host: process.env.REDIS_HOST || 'redis-15095.c212.ap-south-1-1.ec2.cloud.redislabs.com',
  port: process.env.REDIS_PORT || 15095,
  password: process.env.REDIS_PASSWORD || 'iwbRulXXenH5r2CSFCAgG7ID934XaadZ',
  username: process.env.REDIS_USERNAME || 'default',
  retryStrategy: (options) => {
    if (options.error && options.error.code === 'ECONNREFUSED') {
      return new Error('Redis Server refused the connection');
    }
    if (options.total_retry_time > 1000 * 60 * 60) {
      return new Error('Retry time exhausted');
    }
    if (options.attempt > 10) {
      return undefined;
    }
    return Math.min(options.attempt * 100, 3000);
  }
});

redisClient.on('connect', () => {
  console.log('✅ Connected to Redis Database');
});

redisClient.on('error', (err) => {
  console.error('❌ Redis Error:', err);
});

// Helper: Get all products
function getAllProducts(callback) {
  redisClient.hgetall('products', (err, products) => {
    if (err) {
      callback(err, []);
      return;
    }
    const result = Object.values(products).map(p => JSON.parse(p));
    callback(null, result.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)));
  });
}

// Helper: Get next ID
function getNextId(callback) {
  redisClient.incr('product:id:counter', callback);
}

// GET all products
app.get('/api/products', (req, res) => {
  getAllProducts((err, products) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    res.json(products);
  });
});

// POST - Add product
app.post('/api/products', (req, res) => {
  getNextId((err, id) => {
    if (err) return res.status(500).json({ error: err.message });

    const product = {
      id: id,
      name: req.body.name,
      category: req.body.category || '',
      price: parseFloat(req.body.price),
      quantity: parseInt(req.body.quantity),
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString()
    };

    redisClient.hset('products', `product:${id}`, JSON.stringify(product), (err) => {
      if (err) return res.status(500).json({ error: err.message });
      res.status(201).json(product);
    });
  });
});

// PUT - Update product
app.put('/api/products/:id', (req, res) => {
  const id = req.params.id;
  redisClient.hget('products', `product:${id}`, (err, data) => {
    if (err || !data) {
      return res.status(404).json({ error: 'Product not found' });
    }

    const product = JSON.parse(data);
    product.name = req.body.name || product.name;
    product.category = req.body.category || product.category;
    product.price = req.body.price ? parseFloat(req.body.price) : product.price;
    product.quantity = req.body.quantity ? parseInt(req.body.quantity) : product.quantity;
    product.updatedAt = new Date().toISOString();

    redisClient.hset('products', `product:${id}`, JSON.stringify(product), (err) => {
      if (err) return res.status(500).json({ error: err.message });
      res.json(product);
    });
  });
});

// DELETE - Remove product
app.delete('/api/products/:id', (req, res) => {
  const id = req.params.id;
  redisClient.hdel('products', `product:${id}`, (err) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json({ message: 'Product deleted successfully' });
  });
});

// Search products
app.get('/api/products/search/:keyword', (req, res) => {
  getAllProducts((err, products) => {
    if (err) return res.status(500).json({ error: err.message });
    
    const keyword = req.params.keyword.toLowerCase();
    const filtered = products.filter(p => 
      p.name.toLowerCase().includes(keyword) || 
      p.category.toLowerCase().includes(keyword)
    );
    res.json(filtered);
  });
});

// Health check
app.get('/api/health', (req, res) => {
  res.json({ 
    status: 'ok', 
    database: 'Redis',
    endpoint: `${process.env.REDIS_HOST || 'redis-15095.c212.ap-south-1-1.ec2.cloud.redislabs.com'}:${process.env.REDIS_PORT || 15095}`
  });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 Server running on port ${PORT}`);
  console.log(`📡 Redis Database: Connecting...`);
});
