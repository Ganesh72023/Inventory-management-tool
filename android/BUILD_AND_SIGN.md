# APK Generation and Deployment Guide

## Quick Start: Generate Signed APK in 5 Steps

### Step 1: Prepare Keystore
If you don't have a keystore file yet:
```bash
keytool -genkey -v -keystore inventory-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias inventory-alias
```

### Step 2: Open Android Studio
- Load the InventoryApp project

### Step 3: Generate Signed APK
- Menu: **Build > Generate Signed Bundle / APK**
- Select **APK** → **Next**
- Choose keystore file → Enter passwords → **Next**
- Select **release** build variant
- Check both signature versions → **Finish**

### Step 4: Locate APK
Your signed APK is at:
```
InventoryApp/app/release/app-release.apk
```

### Step 5: Install on Device
```bash
adb install -r app/release/app-release.apk
```

---

## Detailed Build Instructions

### Prerequisites
- Android Studio 2022.3 or newer
- Android SDK (API 34)
- Java Development Kit (JDK 11+)
- Minimum 2GB free disk space

### Complete Build Process

1. **Open Project in Android Studio**
   ```
   File > Open > Select InventoryApp folder
   ```

2. **Wait for Gradle Sync**
   - Android Studio automatically syncs gradle
   - Check for errors in Build > Clean Project

3. **Build Release APK**
   ```
   Build > Generate Signed Bundle / APK > APK > Next
   ```

4. **Select Keystore**
   - Choose existing keystore or create new
   - Enter keystore password
   - Select key alias
   - Enter key password

5. **Choose Build Variant**
   - Select "release" from dropdown
   - Enable signature versions (V1 and V2)
   - Click Finish

6. **Wait for Build to Complete**
   - Monitor the Build Output panel
   - Success message appears when done

### Using Command Line (Advanced)

```bash
# Navigate to project directory
cd C:\path\to\InventoryApp

# Build release APK
.\gradlew.bat assembleRelease

# Output: app\build\outputs\apk\release\app-release-unsigned.apk
```

### Signing with Command Line

```bash
# Sign the unsigned APK
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 ^
  -keystore inventory-key.jks ^
  app\build\outputs\apk\release\app-release-unsigned.apk ^
  inventory-alias

# Verify APK signature
jarsigner -verify -verbose app\build\outputs\apk\release\app-release-unsigned.apk
```

---

## Troubleshooting Build Issues

### Issue: "Build failed"
- Clean project: `Build > Clean Project`
- Rebuild: `Build > Rebuild Project`
- Invalidate cache: `File > Invalidate Caches > Invalidate and Restart`

### Issue: "Cannot find SDK"
Create `local.properties` in project root:
```properties
sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
```

### Issue: "Keystore not found"
Ensure keystore file path is correct and accessible:
```bash
# Test if file exists
dir C:\path\to\inventory-key.jks
```

### Issue: "Manifest merger failed"
Check for duplicate activity declarations in AndroidManifest.xml. Ensure each activity listed once.

### Issue: "Gradle sync failed"
- Update Gradle: `Tools > SDK Manager > SDK Tools > Update Gradle`
- Clear cache: `rm -rf .gradle` (Windows: `rmdir /s .gradle`)
- Sync again: `File > Sync Now`

---

## Installation Methods

### Method 1: Via ADB (Recommended)
```bash
# Connect device via USB, then:
adb install -r app/release/app-release.apk

# View installation progress
adb logcat | grep "Installing"

# Verify installation
adb shell pm list packages | grep inventoryapp
```

### Method 2: File Explorer
1. Copy APK to device storage
2. Open file manager on device
3. Navigate to APK location
4. Tap to install
5. Allow unknown sources if prompted

### Method 3: Android Studio
- With device connected
- **Run > Run 'app'**
- Select your device
- Android Studio builds and installs automatically

### Method 4: Google Play Store
For production deployment:
1. Create Google Play Developer account
2. Create app listing
3. Upload signed APK
4. Fill app details, screenshots, description
5. Submit for review
6. Publish once approved

---

## APK Optimization

### Reduce File Size
```gradle
android {
    buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

### Enable Multi-APK by Screen Density
```gradle
android {
    bundle {
        density {
            enableSplit = true
        }
    }
}
```

---

## APK Testing Checklist

**Before Distribution:**
- [ ] APK size < 50MB
- [ ] All features tested on device
- [ ] No console errors
- [ ] Database operations working
- [ ] Low stock alerts functional
- [ ] All buttons responsive
- [ ] Toast messages appear
- [ ] Data persists after restart
- [ ] No crashes observed
- [ ] Performance acceptable

---

## Version Management

Update version for new releases:

In `app/build.gradle`:
```gradle
defaultConfig {
    versionCode 2          // Increment by 1
    versionName "1.1"      // Update version string
}
```

Version naming convention:
- Major.Minor.Patch
- Example: 1.0.0 (first release)
- Example: 1.1.0 (minor update)
- Example: 1.0.1 (bug fix)

---

## Security Best Practices

1. **Protect Keystore:**
   - Keep `inventory-key.jks` in secure location
   - Never commit to version control
   - Add to `.gitignore`:
     ```
     *.jks
     *.keystore
     ```

2. **Use ProGuard:**
   - Obfuscates code
   - Reduces APK size
   - Protects against reverse engineering

3. **Enable Permissions:**
   - Only request necessary permissions
   - Explain why each permission is needed

4. **Update Dependencies:**
   - Keep libraries updated
   - Monitor security advisories
   - Test updates before release

---

## Distribution Channels

### 1. Direct APK Distribution
- Email APK to users
- Host on website
- Share via file transfer

### 2. Google Play Store
- Largest Android app store
- Requires developer account ($25 one-time)
- Professional distribution channel

### 3. F-Droid
- Open source app store
- Free listing
- Community driven

### 4. Galaxy Store (Samsung)
- For Samsung devices
- Alternative to Google Play

### 5. Alternative App Stores
- Amazon Appstore
- Huawei AppGallery
- Requires separate APK uploads

---

## Post-Launch Support

- Monitor crash reports
- Respond to user reviews
- Fix bugs promptly
- Add features based on feedback
- Update dependencies regularly
- Maintain changelog

---

## Useful Commands

```bash
# List connected devices
adb devices

# Install APK
adb install app-release.apk

# Uninstall app
adb uninstall com.example.inventoryapp

# View logs
adb logcat

# Get app info
adb shell dumpsys package com.example.inventoryapp

# Clear app data
adb shell pm clear com.example.inventoryapp

# Get APK file from device
adb pull /data/app/com.example.inventoryapp-xxx/app-release.apk
```

---

## Support
For issues during build or deployment, check:
1. Android Studio logs
2. Gradle sync output
3. Java version compatibility (Java 11+)
4. SDK version (34+)
5. Disk space available
