package com.example.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper Class
 * Handles all SQLite database operations (CRUD)
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create products table
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COLUMN_PRICE + " REAL NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER NOT NULL)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if exists and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    /**
     * Add a new product to the database
     */
    public boolean addProduct(String name, String category, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();

        // Return true if insert was successful (result != -1)
        return result != -1;
    }

    /**
     * Get all products from the database
     */
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));

                Product product = new Product(id, name, category, price, quantity);
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }

    /**
     * Get a specific product by ID
     */
    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + " = " + id, null);

        Product product = null;
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));

            product = new Product(id, name, category, price, quantity);
        }

        cursor.close();
        db.close();

        return product;
    }

    /**
     * Update an existing product
     */
    public boolean updateProduct(int id, String name, String category, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);

        int result = db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();

        // Return true if update was successful (result > 0)
        return result > 0;
    }

    /**
     * Delete a product by ID
     */
    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();

        // Return true if delete was successful (result > 0)
        return result > 0;
    }

    /**
     * Get products with low stock (quantity < 5)
     */
    public List<Product> getLowStockProducts() {
        List<Product> lowStockList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_QUANTITY + " < 5", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));

                Product product = new Product(id, name, category, price, quantity);
                lowStockList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lowStockList;
    }

    /**
     * Get the total number of products
     */
    public int getTotalProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCTS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }
}
