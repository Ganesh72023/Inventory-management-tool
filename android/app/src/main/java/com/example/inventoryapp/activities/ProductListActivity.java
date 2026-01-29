package com.example.inventoryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryapp.R;
import com.example.inventoryapp.adapter.ProductAdapter;
import com.example.inventoryapp.database.DatabaseHelper;
import com.example.inventoryapp.model.Product;

import java.util.List;

/**
 * ProductListActivity Class
 * Displays all products in a RecyclerView
 * Handles edit and delete operations for products
 */
public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {
    private RecyclerView productsRecyclerView;
    private ProductAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Product> productList;
    private Button addProductBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        addProductBtn = findViewById(R.id.addProductBtn);

        // Setup RecyclerView
        setupRecyclerView();

        // Set add button listener
        addProductBtn.setOnClickListener(v -> openAddProductActivity());

        // Load products
        loadProducts();
    }

    /**
     * Setup RecyclerView with layout manager and adapter
     */
    private void setupRecyclerView() {
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = dbHelper.getAllProducts();
        adapter = new ProductAdapter(productList, this, this);
        productsRecyclerView.setAdapter(adapter);
    }

    /**
     * Load all products from database and refresh adapter
     */
    private void loadProducts() {
        productList = dbHelper.getAllProducts();
        if (adapter != null) {
            adapter.updateList(productList);
        }
    }

    /**
     * Open AddProductActivity to add new product
     */
    private void openAddProductActivity() {
        Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Open UpdateProductActivity to edit existing product
     */
    private void openUpdateProductActivity(Product product) {
        Intent intent = new Intent(ProductListActivity.this, UpdateProductActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("productName", product.getName());
        intent.putExtra("productCategory", product.getCategory());
        intent.putExtra("productPrice", product.getPrice());
        intent.putExtra("productQuantity", product.getQuantity());
        startActivityForResult(intent, 2);
    }

    /**
     * Callback when edit button is clicked on a product
     */
    @Override
    public void onEditProduct(Product product) {
        openUpdateProductActivity(product);
    }

    /**
     * Callback when delete button is clicked on a product
     * Shows confirmation dialog before deleting
     */
    @Override
    public void onDeleteProduct(int productId) {
        showDeleteConfirmationDialog(productId);
    }

    /**
     * Show confirmation dialog before deleting a product
     */
    private void showDeleteConfirmationDialog(int productId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deleteProduct(productId);
                    loadProducts(); // Refresh list
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Refresh products when returning to this activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }
}
