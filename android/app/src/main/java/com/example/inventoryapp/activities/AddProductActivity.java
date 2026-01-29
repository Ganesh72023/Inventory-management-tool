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
 * AddProductActivity Class
 * Screen for adding new products to the inventory
 * Validates user input before inserting into database
 */
public class AddProductActivity extends AppCompatActivity {
    private EditText productNameInput, categoryInput, priceInput, quantityInput;
    private Button saveButton, cancelButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize input fields
        productNameInput = findViewById(R.id.productNameInput);
        categoryInput = findViewById(R.id.categoryInput);
        priceInput = findViewById(R.id.priceInput);
        quantityInput = findViewById(R.id.quantityInput);

        // Initialize buttons
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Set button click listeners
        saveButton.setOnClickListener(v -> saveProduct());
        cancelButton.setOnClickListener(v -> finish());
    }

    /**
     * Validate and save product to database
     * Checks for empty fields and invalid values
     */
    private void saveProduct() {
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

            // Create new product and insert into database
            Product product = new Product(name, category, price, quantity);
            long result = dbHelper.addProduct(product);

            if (result != -1) {
                Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Return to previous activity
            } else {
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid price and quantity", Toast.LENGTH_SHORT).show();
        }
    }
}
