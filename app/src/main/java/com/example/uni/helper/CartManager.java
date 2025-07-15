package com.example.uni.helper;

import com.example.uni.entities.CartItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private FirebaseFirestore db;
    private String currentUserId;
    private List<CartItem> cartItems;
    private CartUpdateListener cartUpdateListener;

    public interface CartUpdateListener {
        void onCartUpdated(List<CartItem> items, double totalAmount);
        void onCartError(String error);
    }

    private CartManager() {
        db = FirebaseFirestore.getInstance();
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void setCurrentUserId(String userId) {
        this.currentUserId = userId;
        loadCartItems();
    }

    public void setCartUpdateListener(CartUpdateListener listener) {
        this.cartUpdateListener = listener;
    }

    public void addToCart(String itemId, String itemName, String itemImage, 
                         double itemPrice, String serviceType) {
        if (currentUserId == null) {
            if (cartUpdateListener != null) {
                cartUpdateListener.onCartError("User not logged in");
            }
            return;
        }

        // Check if item already exists in cart
        for (CartItem existingItem : cartItems) {
            if (existingItem.getItemId().equals(itemId)) {
                updateCartItemQuantity(existingItem.getId(), existingItem.getQuantity() + 1);
                return;
            }
        }

        // Create new cart item
        CartItem cartItem = new CartItem(itemId, itemName, itemImage, itemPrice, 1, currentUserId, serviceType);
        
        db.collection("cart")
                .add(cartItem)
                .addOnSuccessListener(documentReference -> {
                    cartItem.setId(documentReference.getId());
                    cartItems.add(cartItem);
                    notifyCartUpdate();
                })
                .addOnFailureListener(e -> {
                    if (cartUpdateListener != null) {
                        cartUpdateListener.onCartError("Failed to add item to cart: " + e.getMessage());
                    }
                });
    }

    public void removeFromCart(String cartItemId) {
        db.collection("cart").document(cartItemId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    cartItems.removeIf(item -> item.getId().equals(cartItemId));
                    notifyCartUpdate();
                })
                .addOnFailureListener(e -> {
                    if (cartUpdateListener != null) {
                        cartUpdateListener.onCartError("Failed to remove item: " + e.getMessage());
                    }
                });
    }

    public void updateCartItemQuantity(String cartItemId, int newQuantity) {
        if (newQuantity <= 0) {
            removeFromCart(cartItemId);
            return;
        }

        db.collection("cart").document(cartItemId)
                .update("quantity", newQuantity)
                .addOnSuccessListener(aVoid -> {
                    for (CartItem item : cartItems) {
                        if (item.getId().equals(cartItemId)) {
                            item.setQuantity(newQuantity);
                            break;
                        }
                    }
                    notifyCartUpdate();
                })
                .addOnFailureListener(e -> {
                    if (cartUpdateListener != null) {
                        cartUpdateListener.onCartError("Failed to update quantity: " + e.getMessage());
                    }
                });
    }

    public void clearCart() {
        if (currentUserId == null) return;

        db.collection("cart")
                .whereEqualTo("userId", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().delete();
                        }
                        cartItems.clear();
                        notifyCartUpdate();
                    }
                });
    }

    public void loadCartItems() {
        if (currentUserId == null) return;

        db.collection("cart")
                .whereEqualTo("userId", currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cartItems.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CartItem item = document.toObject(CartItem.class);
                            item.setId(document.getId());
                            cartItems.add(item);
                        }
                        notifyCartUpdate();
                    } else {
                        if (cartUpdateListener != null) {
                            cartUpdateListener.onCartError("Failed to load cart items");
                        }
                    }
                });
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public int getCartItemCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }

    private void notifyCartUpdate() {
        if (cartUpdateListener != null) {
            cartUpdateListener.onCartUpdated(cartItems, getTotalAmount());
        }
    }
}