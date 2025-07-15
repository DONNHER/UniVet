package com.example.uni.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uni.MainActivity;
import com.example.uni.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private TextView userNameText, userEmailText, userRoleText;
    private MaterialCardView cardAppointments, cardSettings, cardSupport, cardAbout;
    private Button btnLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        initializeViews(view);
        loadUserInfo();
        setupClickListeners();
        
        // Show bottom navigation
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigation();
        }
        
        return view;
    }

    private void initializeViews(View view) {
        userNameText = view.findViewById(R.id.user_name_text);
        userEmailText = view.findViewById(R.id.user_email_text);
        userRoleText = view.findViewById(R.id.user_role_text);
        cardAppointments = view.findViewById(R.id.card_appointments);
        cardSettings = view.findViewById(R.id.card_settings);
        cardSupport = view.findViewById(R.id.card_support);
        cardAbout = view.findViewById(R.id.card_about);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

    private void loadUserInfo() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            String email = user.getEmail();
            
            userEmailText.setText(email != null ? email : "No email available");
            
            db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && isAdded()) {
                        String name = documentSnapshot.getString("name");
                        String role = documentSnapshot.getString("role");
                        
                        userNameText.setText(name != null ? name : "Unknown User");
                        userRoleText.setText(role != null ? role.toUpperCase() : "USER");
                    }
                })
                .addOnFailureListener(e -> {
                    if (isAdded()) {
                        userNameText.setText("Unknown User");
                        userRoleText.setText("USER");
                        Toast.makeText(getContext(), "Error loading profile", Toast.LENGTH_SHORT).show();
                    }
                });
        } else {
            userNameText.setText("Guest User");
            userEmailText.setText("Not logged in");
            userRoleText.setText("GUEST");
        }
    }

    private void setupClickListeners() {
        cardAppointments.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Appointments feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        cardSettings.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Settings feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        cardSupport.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Support feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        cardAbout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "About UniVet - Your trusted veterinary partner", Toast.LENGTH_LONG).show();
        });

        btnLogout.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).logout();
            }
        });
    }
}