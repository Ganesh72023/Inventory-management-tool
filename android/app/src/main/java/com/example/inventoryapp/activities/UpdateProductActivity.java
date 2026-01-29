package com.example.inventoryapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoryapp.R;
import com.example.inventoryapp.database.DatabaseHelper;
import com.example.inventoryapp.model.Product;

/**
 * UpdateProductActivity Class
 * Screen for editing existing products
 * Validates user input before updating in database
 */
public class UpdateProductActivity extends AppCompatActivity {
    private EditText productNameInput, categoryInput, priceInput, quantityInput;
    private Button updateButton, cancelButton;
    private DatabaseHelper dbHelper;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize input fields
        productNameInput = findViewById(R.id.productNameInput);
        categoryInput = findViewById(R.id.categoryInput);
        priceInput = findViewById(R.id.priceInput);
        quantityInput = findViewById(R.id.quantityInput);

        // Initialize buttons
        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Set button click listeners
        updateButton.setOnClickListener(v -> updateProduct());
        cancelButton.setOnClickListener(v -> finish());

        // Load product details from intent
        loadProductDetails();
    }

    /**
     * Load product details from intent and populate input fields
     */
    private void loadProductDetails() {
        productId = getIntent().getIntExtra("productId", -1);
        String name = getIntent().getStringExtra("productName");
        String category = getIntent().getStringExtra("productCategory");
        double price = getIntent().getDoubleExtra("productPrice", 0);
        int quantity = getIntent().getIntExtra("productQuantity", 0);

        productNameInput.setText(name);
        categoryInput.setText(category);
        priceInput.setText(String.valueOf(price));
        quantityInput.setText(String.valueOf(quantity));
    }

    /**
     * Validate and update product in database
     * Checks for empty fields and invalid values
     */
    private void updateProduct() {
        String name = productNameInput.getText().toString().trim();
        String category = categoryInput.getText().toString().trim();
        String priceStr = priceInput.getText().toString().trim();
        String quantityStr = quantityInput.getText().toString().trim();

        // Validation: Check for empty fields
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter product name", Toast.LENGTH_SHORT).show();
            productNameInput.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            Toast.makeText(this, "Please enter category", Toast.LENGTH_SHORT).show();
            categoryInput.requestFocus();
            return;
        }

        if (priceStr.isEmpty()) {
            Toast.makeText(this, "Please enter price", Toast.LENGTH_SHORT).show();
            priceInput.requestFocus();
            return;
        }

        if (quantityStr.isEmpty()) {
            Toast.makeText(this, "Please enter quantity", Toast.LENGTH_SHORT).show();
            quantityInput.requestFocus();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            // Validation: Check for negative values
            if (price < 0) {
                Toast.makeText(this, "Price cannot be negative", Toast.LENGTH_SHORT).show();
                priceInput.requestFocus();
                return;
            }

            if (quantity < 0) {
                Toast.makeText(this, "Quantity cannot be negative", Toast.LENGTH_SHORT).show();
                quantityInput.requestFocus();
                return;
            }

            // Create updated product and update database
            Product product = new Product(productId, name, category, price, quantity);
            int result = dbHelper.updateProduct(product);

            if (result > 0) {
                Toast.makeText(this, "Product updated successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Return to previous activity
            } else {
                Toast.makeText(this, "Failed to update product", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid price and quantity", Toast.LENGTH_SHORT).show();
        }
    }
}
