package com.example.inventoryapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * AddProductActivity Class
 * Form to add new products to the inventory
 */
public class AddProductActivity extends AppCompatActivity {
    private EditText etProductName, etCategory, etPrice, etQuantity;
    private Button btnSaveProduct, btnCancel;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI components
        etProductName = findViewById(R.id.et_product_name);
        etCategory = findViewById(R.id.et_category);
        etPrice = findViewById(R.id.et_price);
        etQuantity = findViewById(R.id.et_quantity);
        btnSaveProduct = findViewById(R.id.btn_save_product);
        btnCancel = findViewById(R.id.btn_cancel);

        // Save button click listener
        btnSaveProduct.setOnClickListener(v -> saveProduct());

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> finish());
    }

    /**
     * Validate and save the product to database
     */
    private void saveProduct() {
        // Get input values
        String name = etProductName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();

        // Input validation
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter product name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (category.isEmpty()) {
            Toast.makeText(this, "Please enter category", Toast.LENGTH_SHORT).show();
            return;
        }

        if (priceStr.isEmpty()) {
            Toast.makeText(this, "Please enter price", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantityStr.isEmpty()) {
            Toast.makeText(this, "Please enter quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Parse price and quantity
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            // Validate price and quantity values
            if (price < 0) {
                Toast.makeText(this, "Price cannot be negative", Toast.LENGTH_SHORT).show();
                return;
            }

            if (quantity < 0) {
                Toast.makeText(this, "Quantity cannot be negative", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert product into database
            boolean isInserted = dbHelper.addProduct(name, category, price, quantity);

            if (isInserted) {
                Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close activity and return to previous screen
            } else {
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid price and quantity", Toast.LENGTH_SHORT).show();
        }
    }
}
