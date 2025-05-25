package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.adapters.ownerAdapt;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.ownerRegisterAct;
import com.example.uni.fragments.start_act;
import com.example.uni.fragments.userMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class main_act extends AppCompatActivity {

    // Firebase
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth myAuth = FirebaseAuth.getInstance();

    // UI
    private Button btn1, btn2;
    private RecyclerView recyclerView;

    // Data
    private ArrayList<ServiceType> list;
    private ownerAdapt owner_adapt;

    // Session
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show welcome/start dialog
        start_act dialogFragment = new start_act();
        dialogFragment.show(getSupportFragmentManager(), "StartDialog");

        // Initialize UI elements
        recyclerView = findViewById(R.id.appointmentsView);
        btn1 = findViewById(R.id.appoint1);
        btn2 = findViewById(R.id.appoint2);

        // Initialize adapter and list
        list = new ArrayList<>();
        owner_adapt = new ownerAdapt(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(owner_adapt);

        // Load services to show
        loadServices();

        // Check if a user is already logged in
        FirebaseUser user = myAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String role = documentSnapshot.getString("role");
                    if ("user".equals(role)) {
                        startActivity(new Intent(this, OwnerDashboardAct.class));
                        finish();
                    } else if ("manager".equals(role)) {
                        startActivity(new Intent(this, TechnicianDashB.class));
                        finish();
                    } else if ("admin".equals(role)) {
                        startActivity(new Intent(this, AdminDashB.class));
                        finish();
                    }
                } else {
                    Toast.makeText(this, "User record not found", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error loading user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });
        }
    }

    public void onMenuClick2(View view) {
        userMenu menu = new userMenu();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }

    private void loadServices() {
        db.collection("serviceType").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    ServiceType serviceType = doc.toObject(ServiceType.class);
                    list.add(serviceType);
                }
                owner_adapt.notifyDataSetChanged();
            } else {
                Exception e = task.getException();
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Firestore Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown Firestore error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onLogClick(View view) {
        start_act menu = new start_act();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }

    public void onResClick(View view) {
        start_act menu = new start_act();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }
}
