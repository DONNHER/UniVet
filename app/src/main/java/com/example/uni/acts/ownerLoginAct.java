package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class ownerLoginAct extends AppCompatActivity {
    private owner isLoggedIn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_login_act);
        Button btnGetStarted = findViewById(R.id.btnLogin);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Login or next activity, for example
                loginUser();
            }
        });
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
        owner logUser = ownerRegisterAct.getUser(new owner(username, password));

        if (logUser != null) {
//            saveSession(username, getUserRole(username));
            setLoggedIn(logUser);
            Intent intent = new Intent(this, OwnerDashboardAct.class);
            startActivity(intent);
        }
    }


    private String getUserRole(String username) {
        // Replace this with real logic to fetch user role
        return "owner";
    }

    public owner isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(owner loggedIn) {
        isLoggedIn = loggedIn;
    }
}
