package com.example.uni.acts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.adapters.CartAdapter;
import com.example.uni.entities.CartItem;
import com.example.uni.helper.CartManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AddToCart extends DialogFragment implements CartManager.CartUpdateListener {
    
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private MaterialTextView totalAmountText, emptyCartText;
    private MaterialButton checkoutButton;
    private CartManager cartManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Uni);
        cartManager = CartManager.getInstance();
        cartManager.setCartUpdateListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cart, container, false);
        
        initViews(view);
        setupRecyclerView();
        setupClickListeners();
        
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.cart_recycler_view);
        totalAmountText = view.findViewById(R.id.total_amount_text);
        emptyCartText = view.findViewById(R.id.empty_cart_text);
        checkoutButton = view.findViewById(R.id.checkout_button);
        
        MaterialButton closeButton = view.findViewById(R.id.close_button);
        MaterialButton clearCartButton = view.findViewById(R.id.clear_cart_button);
        
        closeButton.setOnClickListener(v -> dismiss());
        clearCartButton.setOnClickListener(v -> clearCart());
    }

    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(cartManager.getCartItems(), new CartAdapter.CartItemListener() {
            @Override
            public void onQuantityChanged(CartItem item, int newQuantity) {
                cartManager.updateCartItemQuantity(item.getId(), newQuantity);
            }

            @Override
            public void onItemRemoved(CartItem item) {
                cartManager.removeFromCart(item.getId());
            }
        });
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);
    }

    private void setupClickListeners() {
        checkoutButton.setOnClickListener(v -> {
            if (cartManager.getCartItems().isEmpty()) {
                Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Navigate to checkout
            checkout checkoutDialog = new checkout();
            checkoutDialog.show(getParentFragmentManager(), "checkout");
            dismiss();
        });
    }

    private void clearCart() {
        cartManager.clearCart();
        Toast.makeText(getContext(), "Cart cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartUpdated(List<CartItem> items, double totalAmount) {
        if (cartAdapter != null) {
            cartAdapter.updateItems(items);
        }
        
        updateUI(items, totalAmount);
    }

    @Override
    public void onCartError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void updateUI(List<CartItem> items, double totalAmount) {
        if (items.isEmpty()) {
            emptyCartText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            checkoutButton.setEnabled(false);
            totalAmountText.setText("Total: $0.00");
        } else {
            emptyCartText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            checkoutButton.setEnabled(true);
            totalAmountText.setText(String.format("Total: $%.2f", totalAmount));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}
