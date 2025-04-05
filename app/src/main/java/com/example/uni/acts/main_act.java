package com.example.uni.acts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.uni.helper.TempStorage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uni.R;
import com.example.uni.entities.owner;
import com.example.uni.management.SQLiteDB;
import com.example.uni.serviceType;

import java.util.ArrayList;
//import com.example.uni.management.SessionManager;

public class main_act extends AppCompatActivity {
    private static owner ownerLogin;

    private static final TempStorage temp = TempStorage.getInstance();
    public static owner getOwnerLogin() {
        return ownerLogin;
    }

    public static void setOwnerLogin(owner ownerLogin) {
        main_act.ownerLogin = ownerLogin;
    }

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
        if(ownerLogin != null){
            Intent intent = new Intent(this, OwnerDashboardAct.class); // Replace with actual target
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        start_act dialogFragment = new start_act();
        dialogFragment.show(getSupportFragmentManager(), "StartDialog");
    }
    public Context getContext(){
        return this.getApplicationContext();
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
    public static TempStorage getTemp(){
        return temp;
    }

}
