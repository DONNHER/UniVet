package com.example.uni.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private TextView welcomeText, userNameText;
    private RecyclerView servicesRecyclerView, upcomingRecyclerView;
    private MaterialCardView cardGrooming, cardMedical, cardProducts, cardEmergency;
    private ArrayList<ServiceType> servicesList;
    private ownerAdapt servicesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        servicesList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        initializeViews(view);
        setupRecyclerViews();
        loadUserInfo();
        loadServices();
        setupClickListeners();
        
        // Show bottom navigation
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigation();
        }
        
        return view;
    }

    private void initializeViews(View view) {
        welcomeText = view.findViewById(R.id.welcome_text);
        userNameText = view.findViewById(R.id.user_name_text);
        servicesRecyclerView = view.findViewById(R.id.services_recycler_view);
        upcomingRecyclerView = view.findViewById(R.id.upcoming_recycler_view);
        cardGrooming = view.findViewById(R.id.card_grooming);
        cardMedical = view.findViewById(R.id.card_medical);
        cardProducts = view.findViewById(R.id.card_products);
        cardEmergency = view.findViewById(R.id.card_emergency);
    }

    private void setupRecyclerViews() {
        // Services RecyclerView
        servicesAdapter = new ownerAdapt(servicesList);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        servicesRecyclerView.setAdapter(servicesAdapter);
        
        // Upcoming appointments RecyclerView
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadUserInfo() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && isAdded()) {
                        String name = documentSnapshot.getString("name");
                        String role = documentSnapshot.getString("role");
                        
                        if (name != null) {
                            userNameText.setText(name);
                            welcomeText.setText("Welcome back,");
                        } else {
                            userNameText.setText("User");
                            welcomeText.setText("Welcome,");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (isAdded()) {
                        userNameText.setText("User");
                        welcomeText.setText("Welcome,");
                    }
                });
        } else {
            userNameText.setText("Guest");
            welcomeText.setText("Welcome,");
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
                servicesAdapter.notifyDataSetChanged();
            } else {
                if (isAdded()) {
                    Toast.makeText(getContext(), "Error loading services", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupClickListeners() {
        cardGrooming.setOnClickListener(v -> openServiceCategory("grooming"));
        cardMedical.setOnClickListener(v -> openServiceCategory("medical"));
        cardProducts.setOnClickListener(v -> openServiceCategory("products"));
        cardEmergency.setOnClickListener(v -> handleEmergencyCall());
    }

    private void openServiceCategory(String category) {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            ServicesFragment servicesFragment = ServicesFragment.newInstance(category);
            mainActivity.loadFragment(servicesFragment, true);
        }
    }

    private void handleEmergencyCall() {
        Toast.makeText(getContext(), "Emergency feature coming soon!", Toast.LENGTH_SHORT).show();
    }
}