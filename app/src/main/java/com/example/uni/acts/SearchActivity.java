package com.example.uni.acts;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.adapters.SearchResultsAdapter;
import com.example.uni.entities.Item;
import com.example.uni.helper.CartManager;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements SearchResultsAdapter.SearchItemListener {
    
    private TextInputEditText searchInput;
    private RecyclerView searchResultsRecyclerView;
    private MaterialTextView noResultsText;
    private CircularProgressIndicator progressIndicator;
    
    private SearchResultsAdapter searchAdapter;
    private FirebaseFirestore db;
    private CartManager cartManager;
    private List<Item> allItems;
    private List<Item> filteredItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        initViews();
        setupRecyclerView();
        setupSearch();
        loadItems();
        
        db = FirebaseFirestore.getInstance();
        cartManager = CartManager.getInstance();
        allItems = new ArrayList<>();
        filteredItems = new ArrayList<>();
    }

    private void initViews() {
        searchInput = findViewById(R.id.search_input);
        searchResultsRecyclerView = findViewById(R.id.search_results_recycler_view);
        noResultsText = findViewById(R.id.no_results_text);
        progressIndicator = findViewById(R.id.progress_indicator);
        
        // Set up back button
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        searchAdapter = new SearchResultsAdapter(filteredItems, this);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultsRecyclerView.setAdapter(searchAdapter);
    }

    private void setupSearch() {
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItems(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    private void loadItems() {
        showLoading(true);
        
        // Load all items from different collections
        loadItemsFromCollection("items");
        loadItemsFromCollection("services");
        loadItemsFromCollection("products");
        loadItemsFromCollection("groomingServices");
        loadItemsFromCollection("medicalServices");
    }

    private void loadItemsFromCollection(String collectionName) {
        db.collection(collectionName)
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                Item item = document.toObject(Item.class);
                                if (item != null) {
                                    item.setId(document.getId());
                                    allItems.add(item);
                                }
                            } catch (Exception e) {
                                // Skip invalid items
                            }
                        }
                        updateFilteredItems();
                    }
                    showLoading(false);
                })
                .addOnFailureListener(e -> {
                    showLoading(false);
                    Toast.makeText(this, "Error loading items: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void filterItems(String query) {
        filteredItems.clear();
        
        if (query.isEmpty()) {
            filteredItems.addAll(allItems);
        } else {
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());
            
            for (Item item : allItems) {
                if (matchesSearchQuery(item, lowerCaseQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        
        updateFilteredItems();
    }

    private boolean matchesSearchQuery(Item item, String query) {
        // Search in item name
        if (item.getName() != null && 
            item.getName().toLowerCase(Locale.getDefault()).contains(query)) {
            return true;
        }
        
        // Search in service type
        if (item.getServiceType() != null && 
            item.getServiceType().toLowerCase(Locale.getDefault()).contains(query)) {
            return true;
        }
        
        // Search by price range
        try {
            double queryPrice = Double.parseDouble(query);
            if (Math.abs(item.getPrice() - queryPrice) < 1.0) {
                return true;
            }
        } catch (NumberFormatException e) {
            // Not a price query, continue with text search
        }
        
        return false;
    }

    private void updateFilteredItems() {
        searchAdapter.updateItems(filteredItems);
        
        if (filteredItems.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
            searchResultsRecyclerView.setVisibility(View.GONE);
        } else {
            noResultsText.setVisibility(View.GONE);
            searchResultsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showLoading(boolean show) {
        if (show) {
            progressIndicator.setVisibility(View.VISIBLE);
            searchResultsRecyclerView.setVisibility(View.GONE);
            noResultsText.setVisibility(View.GONE);
        } else {
            progressIndicator.setVisibility(View.GONE);
            updateFilteredItems();
        }
    }

    @Override
    public void onItemClicked(Item item) {
        // You can implement item detail view here
        Toast.makeText(this, "Item: " + item.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToCartClicked(Item item) {
        cartManager.addToCart(
            item.getId(),
            item.getName(),
            item.getImage(),
            item.getPrice(),
            item.getServiceType()
        );
        Toast.makeText(this, item.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up any listeners if needed
    }
}