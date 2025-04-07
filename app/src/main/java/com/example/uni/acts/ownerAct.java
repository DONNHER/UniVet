package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.ownerRegisterAct;
import com.google.firebase.auth.FirebaseAuth;

public class ownerAct extends AppCompatActivity {

    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        if (!isSessionActive()) {
//            // Redirect to Login if session is not found
//            Intent intent = new Intent(this, ownerLoginAct.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//            return; // Prevent further execution
//        }

        // Load Main UI for the logged-in user
        setContentView(R.layout.owner_act);

    }

    private void initializeServices() {

//        com.univet.service.PetService petService = new com.univet.service.PetService(dbHelper);
//        com.univet.service.OwnerService ownerService = new com.univet.service.OwnerService(dbHelper);
//        com.univet.service.ServiceService serviceService = new com.univet.service.ServiceService(dbHelper);
//        com.univet.service.AppointmentService appointmentService = new com.univet.service.AppointmentService(dbHelper);
//
//        // Initialize SessionManager
//        SessionManager.initialize(petService, ownerService, serviceService, appointmentService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SessionManager.getInstance().saveSessionData();
    }

    // Check if a session exists
    private boolean isSessionActive() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void loadUserRoleUI() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");

        Intent intent;
        if ("admin".equals(role)) {
            intent = new Intent(this, OwnerDashboardAct.class);
//        else if ("technician".equals(role)) {
//            intent = new Intent(this, TechnicianDashboardActivity.class);
//        } else {
//            intent = new Intent(this, OwnerDashboardActivity.class);
//        }
            startActivity(intent);
            finish(); // Close MainActivity after redirection

        }
    }
    public void back(View view) {
        finish();
    }
    public void onGroomClick(View view) {
        if(myAuth.getCurrentUser() ==null){
            Intent intent = new Intent(this, ownerLoginAct.class); // Replace with actual target
            startActivity(intent);
        }
        Intent intent = new Intent(this, medServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onProductClick(View view) {
        if(myAuth.getCurrentUser() ==null){
            Intent intent = new Intent(this, ownerLoginAct.class); // Replace with actual target
            startActivity(intent);
            finish();
        }
        Intent intent = new Intent(this, productServiceAct.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
    public void onOtherClick(View view) {
        if(myAuth.getCurrentUser() ==null){
            Intent intent = new Intent(this, ownerLoginAct.class); // Replace with actual target
            startActivity(intent);
            finish();
        }
        Intent intent = new Intent(this, otherServiceAct.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
    public void onLogClick(View view) {
        Intent intent = new Intent(this, ownerLoginAct.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
    public void onResClick(View view) {
        Intent intent = new Intent(this, ownerRegisterAct.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
}
