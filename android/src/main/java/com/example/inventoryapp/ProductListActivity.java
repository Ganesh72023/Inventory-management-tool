package com.example.inventoryapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * ProductListActivity Class
 * Displays all products in a RecyclerView
 */
public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private DatabaseHelper dbHelper;
    private Button btnBack;
    private TextView tvEmptyState;
    private boolean showLowStockOnly = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI components
        recyclerViewProducts = findViewById(R.id.rv_products);
        btnBack = findViewById(R.id.btn_back);
        tvEmptyState = findViewById(R.id.tv_empty_state);

        // Check if we should show only low stock products
        if (getIntent().hasExtra("SHOW_LOW_STOCK")) {
            showLowStockOnly = getIntent().getBooleanExtra("SHOW_LOW_STOCK", false);
        }

        // Setup RecyclerView
        setupRecyclerView();

        // Back button click listener
        btnBack.setOnClickListener(v -> finish());
    }

    /**
     * Setup RecyclerView with products
     */
    private void setupRecyclerView() {
        // Get products from database
        List<Product> productList;
        if (showLowStockOnly) {
            productList = dbHelper.getLowStockProducts();
        } else {
            productList = dbHelper.getAllProducts();
        }

        // Check if product list is empty
        if (productList.isEmpty()) {
            recyclerViewProducts.setVisibility(android.view.View.GONE);
            tvEmptyState.setVisibility(android.view.View.VISIBLE);
            if (showLowStockOnly) {
                tvEmptyState.setText("No products with low stock");
            } else {
                tvEmptyState.setText("No products available. Add some products!");
            }
        } else {
            recyclerViewProducts.setVisibility(android.view.View.VISIBLE);
            tvEmptyState.setVisibility(android.view.View.GONE);

            // Create adapter and set it to RecyclerView
            productAdapter = new ProductAdapter(this, productList);
            recyclerViewProducts.setAdapter(productAdapter);

            // Set layout manager
            recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the product list when returning to this activity
        setupRecyclerView();
    }
}
