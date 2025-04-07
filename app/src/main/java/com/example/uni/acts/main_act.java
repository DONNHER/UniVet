package com.example.uni.acts;

import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.ownerRegisterAct;
import com.example.uni.fragments.start_act;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
//import com.example.uni.management.SessionManager;

public class main_act extends AppCompatActivity {

    private FirebaseAuth myAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(myAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, OwnerDashboardAct.class); // Replace with actual target
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        start_act dialogFragment = new start_act();
        dialogFragment.show(getSupportFragmentManager(), "StartDialog");
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
    public void onMedClick(View view) {
        Intent intent = new Intent(this,  medServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onGroomClick(View view) {
        Intent intent = new Intent(this, groomServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onProductClick(View view) {
        Intent intent = new Intent(this, productServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onOtherClick(View view) {
        Intent intent = new Intent(this, otherServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onLogClick(View view) {
        ownerLoginAct dialogFragment = new ownerLoginAct();
        dialogFragment.show(getSupportFragmentManager(), "LogInDialog");
    }
    public void onResClick(View view) {
        ownerRegisterAct dialogFragment = new ownerRegisterAct();
        dialogFragment.show(getSupportFragmentManager(), "RegisterDialog");

    }

}
