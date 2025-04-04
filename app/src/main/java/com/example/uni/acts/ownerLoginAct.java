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
import com.example.uni.viewModel.*;

public class ownerLoginAct extends DialogFragment {
    private OwnerDashboardAct ownerDashboardAct;
    private EditText passwordEditText;
    private ownerVModel ownerVModel;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.owner_login_act, container, false);

        Button btn= view.findViewById(R.id.btnLogin);
        EditText passwordEditText = view.findViewById(R.id.pass);
        CheckBox showPasswordCheckBox = view.findViewById(R.id.showPasswordCheckBox);
        btn.setOnClickListener(v -> {
            loginUser(view);
        });

        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            passwordEditText.post(() -> passwordEditText.setSelection(passwordEditText.getText().length()));
        });
        return view;
    }
//
//
//    private void saveSession(String username, String role) {
//        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("isLoggedIn", true);
//        editor.putString("username", username);
//        editor.putString("role", role); // Store user role
//        editor.apply();
//    }

    protected void loginUser(View btn) {
        EditText name =  btn.findViewById(R.id.username);
        String username = name.getText().toString().trim();
        EditText pass =  btn.findViewById(R.id.pass);
        String password = pass.getText().toString().trim();
        owner newUser =   new owner(username, password);
        owner logUser = main_act.getTemp().getUser(newUser, main_act.getTemp().getUsers());

        if (logUser != null) {
//            saveSession(username, getU+serRole(username));
            main_act.setOwnerLogin(logUser);
            Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), OwnerDashboardAct.class); // Replace with actual target
            startActivity(intent);
            dismiss();
            return;
        }
        Toast.makeText( getContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
    }

    private String getUserRole(String username) {
        // Replace this with real logic to fetch user role
        return "owner";
    }

}
