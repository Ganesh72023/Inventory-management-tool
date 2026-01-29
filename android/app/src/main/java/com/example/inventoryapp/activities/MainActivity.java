package com.example.inventoryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoryapp.R;
import com.example.inventoryapp.database.DatabaseHelper;

/**
 * MainActivity Class
 * Dashboard screen showing inventory statistics
 * Navigation to add and view products
 */
public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView totalProductsText, lowStockText;
    private Button addProductBtn, viewProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        totalProductsText = findViewById(R.id.totalProductsText);
        lowStockText = findViewById(R.id.lowStockText);
        addProductBtn = findViewById(R.id.addProductBtn);
        viewProductsBtn = findViewById(R.id.viewProductsBtn);

        // Set button click listeners
        addProductBtn.setOnClickListener(v -> openAddProductActivity());
        viewProductsBtn.setOnClickListener(v -> openProductListActivity());

        // Update statistics
        updateStatistics();
    }

    /**
     * Update the statistics displayed on the dashboard
     */
    private void updateStatistics() {
        int totalProducts = dbHelper.getProductCount();
        int lowStockCount = dbHelper.getLowStockProducts().size();

        totalProductsText.setText(String.valueOf(totalProducts));
        lowStockText.setText(String.valueOf(lowStockCount));
    }

    /**
     * Open AddProductActivity
     */
    private void openAddProductActivity() {
        Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
        startActivity(intent);
    }

    /**
     * Open ProductListActivity
     */
    private void openProductListActivity() {
        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
        startActivity(intent);
    }

    /**
     * Refresh statistics when returning to this activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateStatistics();
    }
}
