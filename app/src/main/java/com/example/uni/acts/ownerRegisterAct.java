package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class ownerRegisterAct extends DialogFragment {
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.owner_register_act, container, false);

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
                ownerLoginAct dialogFragment = new ownerLoginAct();
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


    public boolean register(View view){
        EditText name =  view.findViewById(R.id.username);
        String username = name.getText().toString().trim();
        EditText pass = view.findViewById(R.id.pass);
        String password = pass.getText().toString().trim();
        EditText confirm = view.findViewById(R.id.conPass);
        String confirmation = confirm.getText().toString().trim();
        if (password.equals(confirmation)){
            owner newUser = new owner(username, password);
            TempStorage.getInstance().addUser(newUser);
            return true;
        }
            return false;
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
