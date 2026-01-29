package com.example.inventoryapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.inventoryapp.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper Class
 * Handles all SQLite database operations for products
 * Implements CRUD operations: Create, Read, Update, Delete
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database constants
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column constants
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";

    /**
     * Constructor
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when database is created for the first time
     * Creates the products table with all necessary columns
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COLUMN_PRICE + " REAL NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER NOT NULL)";

        db.execSQL(createTableSQL);
    }

    /**
     * Called when database needs to be upgraded
     * Currently drops old table and recreates it
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    /**
     * Add a new product to the database
     * @param product Product object to be added
     * @return row ID of inserted product, or -1 if insertion failed
     */
    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_CATEGORY, product.getCategory());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result;
    }

    /**
     * Retrieve a product by ID
     * @param id Product ID
     * @return Product object if found, null otherwise
     */
    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Product product = null;

        try {
            cursor = db.query(TABLE_PRODUCTS,
                    new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_PRICE, COLUMN_QUANTITY},
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                product = new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getInt(4)
                );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return product;
    }

    /**
     * Retrieve all products from the database
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_PRODUCTS,
                    new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_PRICE, COLUMN_QUANTITY},
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Product product = new Product(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getInt(4)
                    );
                    products.add(product);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return products;
    }

    /**
     * Get all products with low stock (quantity < 5)
     * @return List of low stock products
     */
    public List<Product> getLowStockProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_PRODUCTS,
                    new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_PRICE, COLUMN_QUANTITY},
                    COLUMN_QUANTITY + " < 5",
                    null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Product product = new Product(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getInt(4)
                    );
                    products.add(product);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return products;
    }

    /**
     * Get total number of products
     * @return Count of all products
     */
    public int getProductCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCTS, null);
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return count;
    }

    /**
     * Update an existing product
     * @param product Product object with updated values
     * @return Number of rows updated
     */
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_CATEGORY, product.getCategory());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        int result = db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
        return result;
    }

    /**
     * Delete a product by ID
     * @param id Product ID to delete
     * @return Number of rows deleted
     */
    public int deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    /**
     * Delete all products (useful for clearing inventory)
     * @return Number of rows deleted
     */
    public int deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, null, null);
        db.close();
        return result;
    }

    /**
     * Search products by name (case-insensitive)
     * @param searchTerm Search keyword
     * @return List of products matching the search term
     */
    public List<Product> searchProducts(String searchTerm) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_PRODUCTS,
                    new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_PRICE, COLUMN_QUANTITY},
                    COLUMN_NAME + " LIKE ?",
                    new String[]{"%" + searchTerm + "%"},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Product product = new Product(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getInt(4)
                    );
                    products.add(product);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return products;
    }
}
