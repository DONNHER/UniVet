package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class ownerLoginAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_login_act);
    }


    private void saveSession(String username, String role) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("role", role); // Store user role
        editor.apply();
    }

    public void loginUser() {
        String username = findViewById(R.id.username).toString().trim();
        String password = findViewById(R.id.pass).toString().trim();

        if (authenticateUser(username, password)) {
            saveSession(username, getUserRole(username));

            Intent intent = new Intent(this, main_act.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    // Dummy authentication logic (replace with real one)
    private boolean authenticateUser(String username, String password) {
        owner user =  new ownerRegisterAct().register();
        // Replace this with your actual authentication logic
        return user.getName().equals(username) && user.getPassword().equals(password);
    }

    private String getUserRole(String username) {
        // Replace this with real logic to fetch user role
        return "owner";
    }
}
