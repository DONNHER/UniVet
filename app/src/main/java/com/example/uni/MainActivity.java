package com.example.uni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.uni.fragments.DashboardFragment;
import com.example.uni.fragments.ServicesFragment;
import com.example.uni.fragments.ProfileFragment;
import com.example.uni.fragments.WelcomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    // Firebase
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    // UI Components
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    // Session management
    private SharedPreferences preferences;

    // Fragment management
    private FragmentManager fragmentManager;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        initializeUI();

        // Initialize session preferences
        preferences = getSharedPreferences("UniVetPrefs", MODE_PRIVATE);

        // Set up fragment manager
        fragmentManager = getSupportFragmentManager();

        // Check user authentication status
        checkUserAuthentication();
    }

    private void initializeUI() {
        // Set up toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Set up bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Set default fragment
        loadFragment(new WelcomeFragment(), false);
    }

    private void checkUserAuthentication() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        handleUserRole(role);
                    } else {
                        // User not found, show welcome screen
                        loadFragment(new WelcomeFragment(), false);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    loadFragment(new WelcomeFragment(), false);
                });
        } else {
            // No user logged in, show welcome screen
            loadFragment(new WelcomeFragment(), false);
        }
    }

    private void handleUserRole(String role) {
        if ("user".equals(role)) {
            loadFragment(new DashboardFragment(), false);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else if ("technician".equals(role)) {
            // Load technician dashboard fragment
            loadFragment(new DashboardFragment(), false);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else if ("admin".equals(role)) {
            // Load admin dashboard fragment
            loadFragment(new DashboardFragment(), false);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        );
        
        transaction.replace(R.id.fragment_container, fragment);
        
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        
        transaction.commit();
        currentFragment = fragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            selectedFragment = new DashboardFragment();
        } else if (itemId == R.id.nav_services) {
            selectedFragment = new ServicesFragment();
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment, false);
            return true;
        }
        return false;
    }

    public void showBottomNavigation() {
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    public void hideBottomNavigation() {
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void logout() {
        firebaseAuth.signOut();
        preferences.edit().clear().apply();
        loadFragment(new WelcomeFragment(), false);
        hideBottomNavigation();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }
}