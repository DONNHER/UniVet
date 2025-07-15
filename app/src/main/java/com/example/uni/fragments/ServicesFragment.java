package com.example.uni.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.MainActivity;
import com.example.uni.R;
import com.example.uni.adapters.ownerAdapt;
import com.example.uni.entities.ServiceType;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ServicesFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    
    private FirebaseFirestore db;
    private RecyclerView servicesRecyclerView;
    private ChipGroup categoryChipGroup;
    private ArrayList<ServiceType> servicesList;
    private ArrayList<ServiceType> filteredServicesList;
    private ownerAdapt servicesAdapter;
    private String selectedCategory;

    public static ServicesFragment newInstance(String category) {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        servicesList = new ArrayList<>();
        filteredServicesList = new ArrayList<>();
        
        if (getArguments() != null) {
            selectedCategory = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        
        initializeViews(view);
        setupRecyclerView();
        setupCategoryChips();
        loadServices();
        
        // Show bottom navigation
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigation();
        }
        
        return view;
    }

    private void initializeViews(View view) {
        servicesRecyclerView = view.findViewById(R.id.services_recycler_view);
        categoryChipGroup = view.findViewById(R.id.category_chip_group);
    }

    private void setupRecyclerView() {
        servicesAdapter = new ownerAdapt(filteredServicesList);
        servicesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        servicesRecyclerView.setAdapter(servicesAdapter);
    }

    private void setupCategoryChips() {
        String[] categories = {"All", "Grooming", "Medical", "Products", "Other"};
        
        for (String category : categories) {
            Chip chip = new Chip(getContext());
            chip.setText(category);
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((button, isChecked) -> {
                if (isChecked) {
                    // Uncheck other chips
                    for (int i = 0; i < categoryChipGroup.getChildCount(); i++) {
                        Chip otherChip = (Chip) categoryChipGroup.getChildAt(i);
                        if (otherChip != chip) {
                            otherChip.setChecked(false);
                        }
                    }
                    filterServicesByCategory(category);
                }
            });
            
            categoryChipGroup.addView(chip);
            
            // Select the category if specified
            if (selectedCategory != null && category.toLowerCase().equals(selectedCategory.toLowerCase())) {
                chip.setChecked(true);
            } else if (selectedCategory == null && category.equals("All")) {
                chip.setChecked(true);
            }
        }
    }

    private void loadServices() {
        db.collection("serviceType").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && isAdded()) {
                servicesList.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    ServiceType serviceType = doc.toObject(ServiceType.class);
                    servicesList.add(serviceType);
                }
                
                // Apply initial filter
                if (selectedCategory != null) {
                    filterServicesByCategory(selectedCategory);
                } else {
                    filterServicesByCategory("All");
                }
            } else {
                if (isAdded()) {
                    Toast.makeText(getContext(), "Error loading services", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void filterServicesByCategory(String category) {
        filteredServicesList.clear();
        
        if ("All".equals(category)) {
            filteredServicesList.addAll(servicesList);
        } else {
            for (ServiceType service : servicesList) {
                if (service.getType() != null && 
                    service.getType().toLowerCase().contains(category.toLowerCase())) {
                    filteredServicesList.add(service);
                }
            }
        }
        
        if (servicesAdapter != null) {
            servicesAdapter.notifyDataSetChanged();
        }
    }
}