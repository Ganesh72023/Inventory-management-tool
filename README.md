# Inventory Management Tool

A complete inventory management solution with Web and Android implementations. Manage products, track stock levels, and get low-stock alerts across multiple platforms.

## 📁 Project Structure

```
Inventory-management-tool/
├── /web              - Web application (HTML5, CSS3, JavaScript)
│   ├── index.html    - Main desktop interface
│   ├── mobile-app.html - Mobile responsive version
│   ├── script.js     - CRUD operations & LocalStorage
│   ├── style.css     - Styling & responsive design
│   └── README.md     - Web app documentation
│
└── /android          - Android native application (Java, SQLite)
    ├── app/
    ├── build.gradle  - Gradle build configuration
    ├── gradlew.bat   - Gradle wrapper (Windows)
    ├── gradlew       - Gradle wrapper (Unix)
    └── [Java classes, XML layouts, resources]
```

## 🚀 Quick Start

### Web Application
1. Navigate to `/web` directory
2. Open `index.html` in a web browser
3. Or deploy to Vercel for live hosting

### Android Application
1. Navigate to `/android` directory
2. Open in Android Studio
3. Build APK using `Shift+F10` or `Build > Build Bundle(s) / APK(s)`

## ✨ Features

- **Product Management** - Add, view, update, delete products
- **Low Stock Alerts** - Automatic alerts for items with quantity < 5
- **Search & Filter** - Find products by name or category
- **Data Persistence** - Local storage for web, SQLite for Android
- **Responsive Design** - Works on desktop, tablet, and mobile
- **Material Design** - Modern UI/UX with purple gradient theme

## 🛠️ Tech Stack

### Web
- HTML5, CSS3, JavaScript (ES6+)
- LocalStorage for data persistence
- Responsive CSS Grid & Flexbox

### Android
- Java 11
- SQLite Database
- Android SDK 21-34
- Material Design 3
- AndroidX Libraries
- Gradle 8.0

## 📊 Database Schema

**Products Table**
```sql
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    category TEXT,
    price REAL,
    quantity INTEGER
)
```

## 🔄 CRUD Operations

All implementations support:
- **Create** - Add new products
- **Read** - View all products & search
- **Update** - Edit product details
- **Delete** - Remove products

## 📱 Deployment

### Web App (Vercel)
- Live URL available after Vercel deployment
- Automatic CI/CD with GitHub integration
- Staging & production environments

### Android App
- Build debug APK for testing
- Generate signed APK for Google Play Store
- Distribute via direct download or app store

## 📖 Documentation

- [Web App README](./web/README.md) - Web implementation details
- [Android BUILD Instructions](./android/BUILD_APK_INSTRUCTIONS.md) - APK building guide
- [BUILD & SIGN Guide](./android/BUILD_AND_SIGN.md) - Release signing instructions
- [Quick Start](./android/QUICK_START.md) - Getting started with Android

## 👨‍💻 Developer Info

- **Git Repository** - https://github.com/Ganesh72023/Inventory-management-tool
- **Platform Support** - Web (All browsers), Android 5.0+
- **Status** - Production Ready ✅

## 📝 License

Open source project - Feel free to use and modify

---

**Last Updated:** January 29, 2026
