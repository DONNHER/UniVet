package com.example.uni.acts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.entities.CartItem;
import com.example.uni.entities.Order;
import com.example.uni.helper.CartManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class checkout extends DialogFragment {
    
    private TextInputEditText customerNameEdit, customerPhoneEdit, deliveryAddressEdit, notesEdit;
    private MaterialTextView totalAmountText, paymentMethodText;
    private MaterialButton placeOrderButton;
    private CartManager cartManager;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Uni);
        cartManager = CartManager.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_checkout, container, false);
        
        initViews(view);
        setupClickListeners();
        loadData();
        
        return view;
    }

    private void initViews(View view) {
        customerNameEdit = view.findViewById(R.id.customer_name_edit);
        customerPhoneEdit = view.findViewById(R.id.customer_phone_edit);
        deliveryAddressEdit = view.findViewById(R.id.delivery_address_edit);
        notesEdit = view.findViewById(R.id.notes_edit);
        totalAmountText = view.findViewById(R.id.total_amount_text);
        paymentMethodText = view.findViewById(R.id.payment_method_text);
        placeOrderButton = view.findViewById(R.id.place_order_button);
        
        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> dismiss());
        
        // Set payment method as Cash on Delivery (non-changeable)
        paymentMethodText.setText("Cash on Delivery");
    }

    private void setupClickListeners() {
        placeOrderButton.setOnClickListener(v -> placeOrder());
    }

    private void loadData() {
        // Display total amount
        double totalAmount = cartManager.getTotalAmount();
        totalAmountText.setText(String.format("Total: $%.2f", totalAmount));
        
        // Pre-fill user data if available
        if (auth.getCurrentUser() != null) {
            String displayName = auth.getCurrentUser().getDisplayName();
            if (displayName != null) {
                customerNameEdit.setText(displayName);
            }
        }
    }

    private void placeOrder() {
        String customerName = customerNameEdit.getText().toString().trim();
        String customerPhone = customerPhoneEdit.getText().toString().trim();
        String deliveryAddress = deliveryAddressEdit.getText().toString().trim();
        String notes = notesEdit.getText().toString().trim();

        // Validation
        if (customerName.isEmpty()) {
            customerNameEdit.setError("Name is required");
            return;
        }
        if (customerPhone.isEmpty()) {
            customerPhoneEdit.setError("Phone number is required");
            return;
        }
        if (deliveryAddress.isEmpty()) {
            deliveryAddressEdit.setError("Delivery address is required");
            return;
        }

        List<CartItem> cartItems = cartManager.getCartItems();
        if (cartItems.isEmpty()) {
            Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button to prevent multiple submissions
        placeOrderButton.setEnabled(false);
        placeOrderButton.setText("Placing Order...");

        // Create order
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "guest";
        double totalAmount = cartManager.getTotalAmount();
        
        Order order = new Order(userId, customerName, customerPhone, deliveryAddress, cartItems, totalAmount);
        order.setNotes(notes);
        
        // Set estimated delivery date (3 days from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        order.setEstimatedDeliveryDate(calendar.getTime());

        // Save order to Firestore
        db.collection("orders")
                .add(order)
                .addOnSuccessListener(documentReference -> {
                    order.setId(documentReference.getId());
                    
                    // Clear cart after successful order
                    cartManager.clearCart();
                    
                    // Show success message
                    Toast.makeText(getContext(), 
                        "Order placed successfully! Tracking number: " + order.getTrackingNumber(), 
                        Toast.LENGTH_LONG).show();
                    
                    // Navigate to order tracking
                    showOrderTracking(order.getId());
                    dismiss();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to place order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    placeOrderButton.setEnabled(true);
                    placeOrderButton.setText("Place Order");
                });
    }

    private void showOrderTracking(String orderId) {
        OrderTrackingActivity trackingActivity = OrderTrackingActivity.newInstance(orderId);
        trackingActivity.show(getParentFragmentManager(), "order_tracking");
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
