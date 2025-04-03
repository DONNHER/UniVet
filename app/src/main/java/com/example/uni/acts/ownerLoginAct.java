package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class ownerLoginAct extends AppCompatActivity {
    private static TempStorage temp = ownerRegisterAct.getTemp();

    private static owner isLoggedIn = null;


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
        EditText name =  findViewById(R.id.Email);
        String username = name.getText().toString().trim();
        EditText pass =  findViewById(R.id.Pass);
        String password = pass.getText().toString().trim();
        owner newUser =   new owner(username, password);
        int n= temp.getUsers().size();
        Toast.makeText(getApplicationContext(),"Locessful" + n,Toast.LENGTH_SHORT).show();
        owner logUser = temp.getUser(newUser, temp.getUsers());

        if (logUser != null) {
//            saveSession(username, getU+serRole(username));
            ownerLoginAct.setLoggedIn(logUser);
            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,    OwnerDashboardAct.class);
            startActivity(intent);
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
    }


    private String getUserRole(String username) {
        // Replace this with real logic to fetch user role
        return "owner";
    }

    public static owner isLoggedIn() {
        return isLoggedIn;
    }

    public static void setLoggedIn(owner loggedIn) {
        isLoggedIn = loggedIn;
    }

    public TempStorage getTemp() {
        return temp;
    }

    public void setTemp(TempStorage temp) {
        ownerLoginAct.temp = temp;
    }
}
