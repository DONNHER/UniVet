package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class ownerRegisterAct extends AppCompatActivity {
    private static final ArrayList<owner> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_register_act);

        Button btnGetStarted = findViewById(R.id.btnSignUp);

        // Set the action for the "Get Started" button
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Redirect to Login or next activity, for example
                if (register()) {
                    Intent intent = new Intent(ownerRegisterAct.this, ownerLoginAct.class);
                    startActivity(intent);
                    finish();
                }
                TextView pass = findViewById(R.id.pass);
                TextView conPass = findViewById(R.id.conPass);
                pass.setText("Incorrect password");
                conPass.setText("Incorrect password");
            }
        });
    }


    public boolean register(){
        String name = findViewById(R.id.username).toString().trim();
        String password = findViewById(R.id.pass).toString().trim();
        String confirmation = findViewById(R.id.conPass).toString().trim();
        if (password.equals(confirmation)){
            owner newUser = new owner(name, password);
            int index = findIndexInsertion(newUser);
            users.add(index,newUser);
            return true;
        }
        return false;
    }
    private void addUser(String username, String role) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("role", role); // Store user role
        editor.apply();
    }
    public static owner getUser(owner user){
        int l = 0;
        int r = users.size() -1;
        while (l <= r){
            int m = l + (l-r) /2;
            int find = users.get(m).getEmail().compareTo(user.getEmail());
            if (find == 0){
                return users.get(m);
            } else if (find < 0){
                l = m + 1;
            }else {
                r = m - 1;
            }
        }
        return null;
    }
    public static int findIndexInsertion(owner o) {
        int left = 0, right = users.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (users.get(mid).getEmail().compareTo(o.getEmail()) >= 0) {
                right = mid;  // Search in the left half
            } else {
                left = mid + 1;  // Search in the right half
            }
        }
        return left; // Returns the correct index for insertion
    }
}
