package com.example.uni.acts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class ownerLoginAct extends AppCompatActivity {
    private static TempStorage temp = ownerRegisterAct.getTemp();

    private static owner isLoggedIn;
    private EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_login_act); // Ensure this is correct

        Button btnGetStarted = findViewById(R.id.btnLogin);
        EditText passwordEditText = findViewById(R.id.Pass);
        CheckBox showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

        btnGetStarted.setOnClickListener(v -> loginUser());

        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            passwordEditText.post(() -> passwordEditText.setSelection(passwordEditText.getText().length()));
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

    protected void loginUser() {
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
