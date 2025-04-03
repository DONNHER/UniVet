package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class ownerRegisterAct extends AppCompatActivity {
    private static TempStorage temp = TempStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_register_act);

        EditText passwordEditText = findViewById(R.id.pass);
        EditText conpasswordEditText = findViewById(R.id.conPass);
        CheckBox showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        Button btnGetStarted = findViewById(R.id.btnSignUp);

        // Set the action for the "Get Started" button
        btnGetStarted.setOnClickListener(v -> {
            boolean res = register();
            if (res) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ownerRegisterAct.this, ownerLoginAct.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Toggle password visibility for both fields simultaneously
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show both passwords
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                conpasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Hide both passwords
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                conpasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            // Set the cursor at the end of the text after toggle
            passwordEditText.post(() -> passwordEditText.setSelection(passwordEditText.getText().length()));
            conpasswordEditText.post(() -> conpasswordEditText.setSelection(conpasswordEditText.getText().length()));
        });
    }


    public boolean register(){
        EditText name =  findViewById(R.id.username);
        String username = name.getText().toString().trim();
        EditText pass = findViewById(R.id.pass);
        String password = pass.getText().toString().trim();
        EditText confirm = findViewById(R.id.conPass);
        String confirmation = confirm.getText().toString().trim();
        if (password.equals(confirmation)){
            owner newUser = new owner(username, password);
            return temp.addUser(newUser);

        }else {
            return false;
        }

    }
    private void addUser(String username, String role) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.putString("role", role); // Store user role
        editor.apply();
    }

    public static TempStorage getTemp() {
        return temp;
    }

    public void setTemp(TempStorage temp) {
        ownerRegisterAct.temp = temp;
    }
}
