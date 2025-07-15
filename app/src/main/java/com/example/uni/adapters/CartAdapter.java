package com.example.uni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.entities.CartItem;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    
    private List<CartItem> cartItems;
    private CartItemListener listener;

    public interface CartItemListener {
        void onQuantityChanged(CartItem item, int newQuantity);
        void onItemRemoved(CartItem item);
    }

    public CartAdapter(List<CartItem> cartItems, CartItemListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateItems(List<CartItem> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private MaterialTextView itemName, itemPrice, quantityText, totalPrice;
        private ImageButton decreaseButton, increaseButton, removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            quantityText = itemView.findViewById(R.id.quantity_text);
            totalPrice = itemView.findViewById(R.id.total_price);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
            increaseButton = itemView.findViewById(R.id.increase_button);
            removeButton = itemView.findViewById(R.id.remove_button);
        }

        public void bind(CartItem item, CartItemListener listener) {
            itemName.setText(item.getItemName());
            itemPrice.setText(String.format("$%.2f", item.getItemPrice()));
            quantityText.setText(String.valueOf(item.getQuantity()));
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

            // Set click listeners
            decreaseButton.setOnClickListener(v -> {
                int newQuantity = item.getQuantity() - 1;
                if (newQuantity >= 0) {
                    listener.onQuantityChanged(item, newQuantity);
                }
            });

            increaseButton.setOnClickListener(v -> {
                int newQuantity = item.getQuantity() + 1;
                listener.onQuantityChanged(item, newQuantity);
            });

            removeButton.setOnClickListener(v -> listener.onItemRemoved(item));
        }
    }
}