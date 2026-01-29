# Deploy Backend to Railway (Free Hosting)

## Step 1: Create MongoDB Database (Free)

1. Go to: https://www.mongodb.com/cloud/atlas
2. Sign up for free account
3. Create a free cluster
4. Get connection string: `mongodb+srv://username:password@cluster.mongodb.net/inventory`
5. Copy this and save it

## Step 2: Deploy Backend to Railway

1. Go to: https://railway.app
2. Click "New Project"
3. Select "Deploy from GitHub"
4. Choose your repository: `Ganesh72023/Inventory-management-tool`
5. Select the `backend` folder
6. Add environment variables:
   ```
   MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/inventory
   PORT=5000
   NODE_ENV=production
   ```
7. Click Deploy
8. Get your API URL (e.g., `https://inventory-api-production.up.railway.app`)

## Step 3: Update Frontend API URL

Update in `web/script-api.js`:
```javascript
const API_URL = 'https://your-railway-url.up.railway.app';
```

## Step 4: Push to GitHub

```bash
git add .
git commit -m "Add Node.js backend with MongoDB for real-time data"
git push origin main
```

## Features

✅ Real-time data updates across all users
✅ MongoDB cloud database
✅ REST API endpoints
✅ Auto-refresh every 5 seconds
✅ Scalable and production-ready
✅ Free hosting on Railway

## How it Works

1. Frontend connects to backend API
2. All data stored in MongoDB cloud
3. Multiple users see same data instantly
4. Changes sync automatically every 5 seconds
5. Works offline (fallback to localStorage)
