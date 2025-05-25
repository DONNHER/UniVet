package com.example.uni.fragments;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ownerRegisterAct extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_register_act);

        db = FirebaseFirestore.getInstance();
        myAuth = FirebaseAuth.getInstance();

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.pass);
        EditText conPasswordEditText = findViewById(R.id.conPass);
        TextView login = findViewById(R.id.num_sign);
        CheckBox showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        login.setOnClickListener(v -> {
            // Launch Login Activity (you should implement this)
            finish(); // closes current activity
        });

        btnSignUp.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmation = conPasswordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmation)) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else if (Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                myAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = myAuth.getCurrentUser();
                                Map<String, Object> data = new HashMap<>();
                                data.put("uid", user.getUid());
                                data.put("username", user.getEmail());
                                data.put("role", "user");

                                db.collection("users").document(user.getUid()).set(data);
                                Toast.makeText(this, "Successfully Registered.", Toast.LENGTH_SHORT).show();
                                finish(); // or redirect to login
                            } else {
                                Exception e = task.getException();
                                if (e instanceof FirebaseNetworkException) {
                                    Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            }
        });

        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                conPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                conPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
            conPasswordEditText.setSelection(conPasswordEditText.getText().length());
        });
    }
}
