package com.example.uni.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.acts.OwnerDashboardAct;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;

public class ownerLoginAct extends AppCompatActivity {

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_login_act);

        myAuth = FirebaseAuth.getInstance();

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.pass);
        Button btnLogin = findViewById(R.id.btnLogin);
        CheckBox showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        TextView signup = findViewById(R.id.num_sign);

        signup.setOnClickListener(v -> {
            // Go to Register Activity
            Intent intent = new Intent(ownerLoginAct.this, ownerRegisterAct.class);
            startActivity(intent);
            finish(); // Close login screen
        });

        btnLogin.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            myAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, OwnerDashboardAct.class);
                            startActivity(intent);
                            finish(); // Close login screen
                        } else {
                            Exception e = task.getException();
                            if (e instanceof FirebaseNetworkException) {
                                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });

        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
        });
    }
}
