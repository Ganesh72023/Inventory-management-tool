package com.example.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity Class
 * Dashboard screen with options to add and view products
 */
public class MainActivity extends AppCompatActivity {
    private Button btnAddProduct, btnViewProducts, btnLowStock;
    private TextView tvTotalProducts;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI components
        btnAddProduct = findViewById(R.id.btn_add_product);
        btnViewProducts = findViewById(R.id.btn_view_products);
        btnLowStock = findViewById(R.id.btn_low_stock);
        tvTotalProducts = findViewById(R.id.tv_total_products);

        // Update product count on activity resume
        updateProductCount();

        // Button click listeners
        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        btnViewProducts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        btnLowStock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            intent.putExtra("SHOW_LOW_STOCK", true);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh product count when returning to this activity
        updateProductCount();
    }

    /**
     * Update the total product count display
     */
    private void updateProductCount() {
        int totalProducts = dbHelper.getTotalProducts();
        tvTotalProducts.setText("Total Products: " + totalProducts);
    }
}
