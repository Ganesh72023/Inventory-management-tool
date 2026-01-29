package com.example.inventoryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryapp.R;
import com.example.inventoryapp.model.Product;

import java.util.List;

/**
 * ProductAdapter Class
 * Adapter for RecyclerView to display list of products
 * Handles item layout inflation and data binding
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;
    private OnProductActionListener listener;

    /**
     * Interface for handling product actions (edit, delete)
     */
    public interface OnProductActionListener {
        void onEditProduct(Product product);
        void onDeleteProduct(int productId);
    }

    /**
     * Constructor
     */
    public ProductAdapter(List<Product> productList, Context context, OnProductActionListener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Create new ViewHolder instances
     */
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);
        return new ProductViewHolder(view);
    }

    /**
     * Bind product data to ViewHolder
     * Highlights low stock items with red background
     */
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productCategory.setText("Category: " + product.getCategory());
        holder.productPrice.setText(String.format("Price: ₹%.2f", product.getPrice()));
        holder.productQuantity.setText("Qty: " + product.getQuantity());

        // Highlight low stock items
        if (product.isLowStock()) {
            holder.productContainer.setBackgroundColor(context.getResources().getColor(R.color.lowStockColor));
            holder.lowStockWarning.setVisibility(View.VISIBLE);
        } else {
            holder.productContainer.setBackgroundColor(context.getResources().getColor(R.color.normalColor));
            holder.lowStockWarning.setVisibility(View.GONE);
        }

        // Edit button click listener
        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditProduct(product);
            }
        });

        // Delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteProduct(product.getId());
            }
        });
    }

    /**
     * Return total number of items
     */
    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    /**
     * Update the product list (for filtering or refreshing)
     */
    public void updateList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class
     * Holds references to views in each list item
     */
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productCategory, productPrice, productQuantity, lowStockWarning;
        ImageButton editButton, deleteButton;
        LinearLayout productContainer;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            lowStockWarning = itemView.findViewById(R.id.lowStockWarning);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            productContainer = itemView.findViewById(R.id.productContainer);
        }
    }
}
