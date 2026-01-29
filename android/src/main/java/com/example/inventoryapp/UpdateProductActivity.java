package com.example.inventoryapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * UpdateProductActivity Class
 * Update existing product details and delete product
 */
public class UpdateProductActivity extends AppCompatActivity {
    private EditText etProductName, etCategory, etPrice, etQuantity;
    private Button btnUpdateProduct, btnDeleteProduct, btnCancel;
    private DatabaseHelper dbHelper;
    private int productId;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI components
        etProductName = findViewById(R.id.et_update_product_name);
        etCategory = findViewById(R.id.et_update_category);
        etPrice = findViewById(R.id.et_update_price);
        etQuantity = findViewById(R.id.et_update_quantity);
        btnUpdateProduct = findViewById(R.id.btn_update_product);
        btnDeleteProduct = findViewById(R.id.btn_delete_product);
        btnCancel = findViewById(R.id.btn_cancel_update);

        // Get product ID from intent
        productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        if (productId != -1) {
            loadProductDetails();
        }

        // Update button click listener
        btnUpdateProduct.setOnClickListener(v -> updateProduct());

        // Delete button click listener
        btnDeleteProduct.setOnClickListener(v -> confirmDelete());

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> finish());
    }

    /**
     * Load product details from database
     */
    private void loadProductDetails() {
        product = dbHelper.getProductById(productId);

        if (product != null) {
            etProductName.setText(product.getName());
            etCategory.setText(product.getCategory());
            etPrice.setText(String.valueOf(product.getPrice()));
            etQuantity.setText(String.valueOf(product.getQuantity()));
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Validate and update the product
     */
    private void updateProduct() {
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

            // Update product in database
            boolean isUpdated = dbHelper.updateProduct(productId, name, category, price, quantity);

            if (isUpdated) {
                Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update product", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid price and quantity", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Confirm delete operation with dialog
     */
    private void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure you want to delete this product?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Delete the product from database
     */
    private void deleteProduct() {
        boolean isDeleted = dbHelper.deleteProduct(productId);

        if (isDeleted) {
            Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to delete product", Toast.LENGTH_SHORT).show();
        }
    }
}
