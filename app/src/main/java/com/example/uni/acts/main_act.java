package com.example.uni.acts;

import com.example.uni.adapters.ownerAdapt;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.ownerRegisterAct;
import com.example.uni.fragments.start_act;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
//import com.example.uni.management.SessionManager;

public class main_act extends AppCompatActivity {
//    private RecyclerView appointmentsView ;
//    private appAdapt Adapt;
    private Button btn1, btn2;
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArrayList<ServiceType> list;
    private ownerAdapt owner_adapt;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    private SharedPreferences preferences;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = myAuth.getCurrentUser();
        if(preferences.getBoolean("isLoggedIn",false)){
            String uid = user.getUid();
            db.collection("users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists()) {
                    if("user".equals(documentSnapshot.getString("role"))){
                        Intent intent = new Intent(this, OwnerDashboardAct.class); // Replace with actual target
                        startActivity(intent);
                        finish();
                    } else if ("manager".equals(documentSnapshot.getString("role"))) {
                        Intent intent = new Intent(this, TechnicianDashB.class); // Replace with actual target
                        startActivity(intent);
                        finish();
                    } else if ("admin".equals(documentSnapshot.getString("role"))) {
                        Intent intent = new Intent(this, AdminDashB.class); // Replace with actual target
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_act dialogFragment = new start_act();
        dialogFragment.show(getSupportFragmentManager(), "StartDialog");
        recyclerView = findViewById(R.id.appointmentsView);
        list = new ArrayList<>();
        owner_adapt = new ownerAdapt(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(owner_adapt);
        loadServices();
        btn1 = findViewById(R.id.appoint1);
        btn2 = findViewById(R.id.appoint2);
    }
    public void onMenuClick2(View view) {
        Intent intent = new Intent(view.getContext(), settingAct.class);
        view.getContext().startActivity(intent);

    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadServices() {
        db.collection("serviceType").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot d : task.getResult()) {
                    ServiceType serviceType = d.toObject(ServiceType.class);
                    list.add(serviceType);
                    owner_adapt.notifyDataSetChanged();
                }
                owner_adapt = new ownerAdapt(list);
            } else {
                Toast.makeText(getApplicationContext(), "error:2", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onLogClick(View view) {
        Intent intent = new Intent(this, start_act.class); // Replace with actual target
        startActivity(intent);
    }
    public void onResClick(View view) {
        Intent intent = new Intent(this, start_act.class); // Replace with actual target
        startActivity(intent);
    }

}
