package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;
import com.example.uni.management.SQLiteDB;
import com.example.uni.serviceType;
//import com.example.uni.management.SessionManager;

public class ownerDashB_setting extends AppCompatActivity {
    private ownerLoginAct ownerLogin;


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
        setContentView(R.layout.setting_act);
        Button btnGetStarted = findViewById(R.id.btn_edit_profile);

        // Set the action for the "Get Started" button
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to perform when "Get Started" is clicked
                // Redirect to Login or next activity, for example
                Intent intent = new Intent(ownerDashB_setting.this, ownerAct.class);
                startActivity(intent);
                finish();
            }
        });
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
    public void logoutClick(View view) {
        ownerLogin.setLoggedIn(null);
        Intent intent = new Intent(this, main_act.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
    public void editProfileClick(View view) {
        Intent intent = new Intent(this, ownerAct.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
    public void onProductClick(View view) {
        if(ownerLogin.isLoggedIn()==null){
            Intent intent = new Intent(this, ownerLoginAct.class); // Replace with actual target
            startActivity(intent);
            finish();
        }
        Intent intent = new Intent(this, productServiceAct.class); // Replace with actual target
        startActivity(intent);
        finish();
    }
    public void onOtherClick(View view) {
        if(ownerLogin.isLoggedIn()==null){
            Intent intent = new Intent(this, ownerLoginAct.class); // Replace with actual target
            startActivity(intent);
            finish();
        }
        Intent intent = new Intent(this, otherServiceAct.class); // Replace with actual target
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
