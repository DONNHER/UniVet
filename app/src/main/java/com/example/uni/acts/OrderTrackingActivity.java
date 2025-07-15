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
import com.example.uni.adapters.OrderItemsAdapter;
import com.example.uni.entities.Order;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderTrackingActivity extends DialogFragment {
    
    private static final String ARG_ORDER_ID = "order_id";
    
    private String orderId;
    private FirebaseFirestore db;
    private ListenerRegistration orderListener;
    
    private MaterialTextView trackingNumberText, orderStatusText, customerNameText, 
                            customerPhoneText, deliveryAddressText, orderDateText, 
                            estimatedDeliveryText, totalAmountText, notesText;
    private RecyclerView itemsRecyclerView;
    private OrderItemsAdapter itemsAdapter;
    private View statusProgressBar;
    
    public static OrderTrackingActivity newInstance(String orderId) {
        OrderTrackingActivity fragment = new OrderTrackingActivity();
        Bundle args = new Bundle();
        args.putString(ARG_ORDER_ID, orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Uni);
        
        if (getArguments() != null) {
            orderId = getArguments().getString(ARG_ORDER_ID);
        }
        
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_order_tracking, container, false);
        
        initViews(view);
        setupRecyclerView();
        setupClickListeners();
        loadOrderData();
        
        return view;
    }

    private void initViews(View view) {
        trackingNumberText = view.findViewById(R.id.tracking_number_text);
        orderStatusText = view.findViewById(R.id.order_status_text);
        customerNameText = view.findViewById(R.id.customer_name_text);
        customerPhoneText = view.findViewById(R.id.customer_phone_text);
        deliveryAddressText = view.findViewById(R.id.delivery_address_text);
        orderDateText = view.findViewById(R.id.order_date_text);
        estimatedDeliveryText = view.findViewById(R.id.estimated_delivery_text);
        totalAmountText = view.findViewById(R.id.total_amount_text);
        notesText = view.findViewById(R.id.notes_text);
        itemsRecyclerView = view.findViewById(R.id.items_recycler_view);
        statusProgressBar = view.findViewById(R.id.status_progress_bar);
        
        MaterialButton closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dismiss());
    }

    private void setupRecyclerView() {
        itemsAdapter = new OrderItemsAdapter();
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsRecyclerView.setAdapter(itemsAdapter);
    }

    private void setupClickListeners() {
        // Add any additional click listeners here if needed
    }

    private void loadOrderData() {
        if (orderId == null) {
            Toast.makeText(getContext(), "Invalid order ID", Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }

        // Listen for real-time updates
        orderListener = db.collection("orders").document(orderId)
                .addSnapshotListener((documentSnapshot, error) -> {
                    if (error != null) {
                        Toast.makeText(getContext(), "Error loading order: " + error.getMessage(), 
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Order order = documentSnapshot.toObject(Order.class);
                        if (order != null) {
                            order.setId(documentSnapshot.getId());
                            updateUI(order);
                        }
                    } else {
                        Toast.makeText(getContext(), "Order not found", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
    }

    private void updateUI(Order order) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault());
        
        trackingNumberText.setText("Tracking #: " + order.getTrackingNumber());
        orderStatusText.setText(order.getStatusDisplayText());
        customerNameText.setText(order.getCustomerName());
        customerPhoneText.setText(order.getCustomerPhone());
        deliveryAddressText.setText(order.getDeliveryAddress());
        totalAmountText.setText(String.format("$%.2f", order.getTotalAmount()));
        
        if (order.getOrderDate() != null) {
            orderDateText.setText("Ordered: " + dateFormat.format(order.getOrderDate()));
        }
        
        if (order.getEstimatedDeliveryDate() != null) {
            estimatedDeliveryText.setText("Estimated Delivery: " + dateFormat.format(order.getEstimatedDeliveryDate()));
        }
        
        if (order.getNotes() != null && !order.getNotes().isEmpty()) {
            notesText.setText("Notes: " + order.getNotes());
            notesText.setVisibility(View.VISIBLE);
        } else {
            notesText.setVisibility(View.GONE);
        }
        
        // Update status color
        updateStatusColor(order.getStatus());
        
        // Update items list
        if (order.getItems() != null) {
            itemsAdapter.updateItems(order.getItems());
        }
        
        // Update progress bar based on status
        updateProgressBar(order.getStatus());
    }

    private void updateStatusColor(Order.OrderStatus status) {
        int colorResId;
        switch (status) {
            case PENDING:
                colorResId = R.color.status_pending;
                break;
            case CONFIRMED:
                colorResId = R.color.status_confirmed;
                break;
            case PROCESSING:
                colorResId = R.color.status_processing;
                break;
            case OUT_FOR_DELIVERY:
                colorResId = R.color.status_out_for_delivery;
                break;
            case DELIVERED:
                colorResId = R.color.status_delivered;
                break;
            case CANCELLED:
                colorResId = R.color.status_cancelled;
                break;
            default:
                colorResId = R.color.design_default_color_primary;
        }
        
        orderStatusText.setTextColor(getResources().getColor(colorResId, null));
    }

    private void updateProgressBar(Order.OrderStatus status) {
        // You can implement a custom progress bar here to show order progress
        // For now, we'll just show/hide based on completion
        if (status == Order.OrderStatus.DELIVERED || status == Order.OrderStatus.CANCELLED) {
            statusProgressBar.setVisibility(View.GONE);
        } else {
            statusProgressBar.setVisibility(View.VISIBLE);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderListener != null) {
            orderListener.remove();
        }
    }
}