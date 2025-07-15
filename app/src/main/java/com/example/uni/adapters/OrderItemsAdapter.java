package com.example.uni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.entities.CartItem;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder> {
    
    private List<CartItem> items;

    public OrderItemsAdapter() {
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_item, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<CartItem> newItems) {
        this.items = newItems != null ? newItems : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private MaterialTextView itemName, itemPrice, quantityText, totalPrice;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            quantityText = itemView.findViewById(R.id.quantity_text);
            totalPrice = itemView.findViewById(R.id.total_price);
        }

        public void bind(CartItem item) {
            itemName.setText(item.getItemName());
            itemPrice.setText(String.format("$%.2f", item.getItemPrice()));
            quantityText.setText("Qty: " + item.getQuantity());
            totalPrice.setText(String.format("$%.2f", item.getTotalPrice()));

            // Load image
            if (item.getItemImage() != null && !item.getItemImage().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(item.getItemImage())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(itemImage);
            } else {
                itemImage.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }
    }
}