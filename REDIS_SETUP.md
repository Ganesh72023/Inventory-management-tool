# 🔴 Redis Database Deployment Guide

## Your Redis Database Configuration

**Host:** `redis-15095.c212.ap-south-1-1.ec2.cloud.redislabs.com`
**Port:** `15095`
**Username:** `default`
**Password:** `iwbRulXXenH5r2CSFCAgG7ID934XaadZ`

**Connection String:**
```
redis://default:iwbRulXXenH5r2CSFCAgG7ID934XaadZ@redis-15095.c212.ap-south-1-1.ec2.cloud.redislabs.com:15095
```

---

## 📋 Step-by-Step Deployment

### **Step 1: Deploy Backend to Railway**

1. **Go to:** https://railway.app
2. **Login** with GitHub
3. **New Project** → "Deploy from GitHub"
4. **Select:** `Ganesh72023/Inventory-management-tool`
5. **Configure:**
   - Service: Select the `backend` folder
   - Scroll to "Variables" section
   - Add these environment variables:
   ```
   REDIS_HOST=redis-15095.c212.ap-south-1-1.ec2.cloud.redislabs.com
   REDIS_PORT=15095
   REDIS_USERNAME=default
   REDIS_PASSWORD=iwbRulXXenH5r2CSFCAgG7ID934XaadZ
   NODE_ENV=production
   ```
6. **Deploy!** ✅
7. **Get your Railway URL** (example: `https://inventory-redis-api.railway.app`)

### **Step 2: Update Frontend API URL**

1. **Edit file:** `web/redis-live.html`
2. **Find line:** `const API_URL = ...`
3. **Replace with your Railway URL:**
   ```javascript
   const API_URL = 'https://your-railway-url.up.railway.app';
   ```
4. **Save and commit:**
   ```bash
   git add web/redis-live.html
   git commit -m "Update Redis API URL to production"
   git push origin main
   ```

### **Step 3: Test Your Live App**

1. **Open:** https://Ganesh72023.github.io/Inventory-management-tool/web/redis-live.html
2. **Add a product**
3. **Refresh page** → Data persists! ✨
4. **Open in another tab** → See real-time sync! 🔄

---

## 🎯 Features

✅ **Real Redis Database** - Cloud-hosted Redis
✅ **Persistent Storage** - Data survives app restart
✅ **Real-time Sync** - Auto-refresh every 3 seconds
✅ **Global Access** - Works worldwide
✅ **Fast Performance** - Redis is super-fast
✅ **Scalable** - Handles millions of products

---

## 📊 Architecture

```
GitHub Pages Frontend
        ↓
    API Calls
        ↓
Railway Backend (Node.js)
        ↓
Redis Database (Cloud)
```

---

## 🆘 Troubleshooting

### **"Cannot connect to database"**
- Check Railway deployment status
- Verify Redis credentials in .env
- Wait 2 minutes after deployment

### **"Data not persisting"**
- Check Redis connection in backend logs
- Verify REDIS_HOST and REDIS_PORT

### **"Real-time sync not working"**
- Backend might be sleeping (Railway free tier)
- Refresh page manually
- Check browser console for errors

---

## 📝 Files Used

- **Backend:** `backend/redis-server.js`
- **Frontend:** `web/redis-live.html`
- **Config:** `backend/.env`

---

## 🚀 Your Live URLs

**Frontend (with instructions):**
```
https://Ganesh72023.github.io/Inventory-management-tool/web/redis-live.html
```

**Backend API:** (Set after Railway deployment)
```
https://your-railway-url.up.railway.app
```

---

## ✨ Next Steps

1. Deploy backend to Railway
2. Get your Railway URL
3. Update API_URL in redis-live.html
4. Push to GitHub
5. Your live app is ready! 🎉

**Questions?** Check Railway dashboard for logs if something fails.
