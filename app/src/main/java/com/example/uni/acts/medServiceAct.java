package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.google.firebase.auth.FirebaseAuth;

public class medServiceAct extends AppCompatActivity {
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load Main UI for the logged-in user
        setContentView(R.layout.med_service);
        if (myAuth.getCurrentUser() != null) {
            TextView name = findViewById(R.id.name);//
            name.setText( "Hi, " + myAuth.getCurrentUser().getDisplayName());
            return;
        }
        TextView name = findViewById(R.id.name);//
        name.setText( "Hi, ");
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
            startActivity(intent);
            finish(); // Close MainActivity after redirection

        }
    }

    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
    }
}
