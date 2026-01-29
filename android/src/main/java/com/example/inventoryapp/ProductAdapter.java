package com.example.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * ProductAdapter Class
 * Adapter for RecyclerView to display list of products
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the row_product layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Get product at current position
        Product product = productList.get(position);

        // Set product details
        holder.nameTextView.setText("Product: " + product.getName());
        holder.categoryTextView.setText("Category: " + product.getCategory());
        holder.priceTextView.setText("Price: $" + String.format("%.2f", product.getPrice()));
        holder.quantityTextView.setText("Quantity: " + product.getQuantity());

        // Check if product is low on stock and highlight it
        if (product.isLowStock()) {
            holder.lowStockWarning.setVisibility(View.VISIBLE);
            holder.productContainer.setBackgroundColor(Color.parseColor("#FFEBEE")); // Light red background
        } else {
            holder.lowStockWarning.setVisibility(View.GONE);
            holder.productContainer.setBackgroundColor(Color.WHITE);
        }

        // Handle item click - open update activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateProductActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * Update the product list and notify adapter
     */
    public void updateList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for product items
     */
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, categoryTextView, priceTextView, quantityTextView, lowStockWarning;
        LinearLayout productContainer;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_product_name);
            categoryTextView = itemView.findViewById(R.id.tv_product_category);
            priceTextView = itemView.findViewById(R.id.tv_product_price);
            quantityTextView = itemView.findViewById(R.id.tv_product_quantity);
            lowStockWarning = itemView.findViewById(R.id.tv_low_stock_warning);
            productContainer = itemView.findViewById(R.id.ll_product_container);
        }
    }
}
