package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class ownerRegisterAct extends AppCompatActivity {
    private ArrayList<owner> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_register_act);
    }

    public owner register(){
        String name = findViewById(R.id.username).toString().trim();
        String password = findViewById(R.id.pass).toString().trim();
        String confirmation = findViewById(R.id.conPass).toString().trim();
        if (password.equals(confirmation)){
            owner newUser = new owner(name, password);
            users.add(newUser);
            return newUser;
        }
        return null;
    }
    private void addUser(String username, String role) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("role", role); // Store user role
        editor.apply();
    }
}
