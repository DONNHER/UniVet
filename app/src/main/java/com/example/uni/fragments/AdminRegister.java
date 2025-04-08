package com.example.uni.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.entities.owner;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

public class AdminRegister extends DialogFragment {
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.admin_register, container, false);

        EditText passwordEditText = view.findViewById(R.id.pass);
        EditText conpasswordEditText = view.findViewById(R.id.conPass);
        CheckBox showPasswordCheckBox = view.findViewById(R.id.showPasswordCheckBox);
        Button btnGetStarted = view.findViewById(R.id.btnSignUp);

        // Set the action for the "Get Started" button
        btnGetStarted.setOnClickListener(v -> {
            boolean res = register( view);
            if (res) {
                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                dismiss();
                AdminLogin dialogFragment = new AdminLogin();
                dialogFragment.show(getParentFragmentManager(), "LogInDialog");

            } else {
                Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
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
        return view;
    }


    public boolean register(View view) {
        EditText name = view.findViewById(R.id.username);
        String username = name.getText().toString().trim();
        EditText pass = view.findViewById(R.id.pass);
        String password = pass.getText().toString().trim();
        EditText confirm = view.findViewById(R.id.conPass);
        String confirmation = confirm.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(getContext(), "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(confirmation)) {
            Toast.makeText(getContext(), "Password do not match.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            myAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(requireActivity(), task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Successfully Registered.", Toast.LENGTH_SHORT).show();
                    dismiss();
                    AdminLogin dialogFragment = new AdminLogin();
                    dialogFragment.show(getParentFragmentManager(), "LogInDialog");
                }
            });
        } else {
            owner newUser = new owner(username, password);
            TempStorage.getInstance().addUser(newUser);
            Toast.makeText(getContext(), "Successfully Registered.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }
//    private void addUser(String username, String role) {
//        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("isLoggedIn", true);
//        editor.putString("username", username);
//        editor.putString("role", role); // Store user role
//        editor.apply();
//    }

}
