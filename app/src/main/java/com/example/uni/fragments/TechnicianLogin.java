package com.example.uni.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.acts.OwnerDashboardAct;
import com.example.uni.acts.TechnicianDashB;
import com.example.uni.entities.owner;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

public class TechnicianLogin extends DialogFragment {
    private OwnerDashboardAct ownerDashboardAct;
    private EditText passwordEditText;
    private com.example.uni.viewModel.ownerVModel ownerVModel;
    private TempStorage temp = TempStorage.getInstance();

    private FirebaseAuth myAuth= FirebaseAuth.getInstance();;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.tech_login, container, false);
        TextView btm_signup = view.findViewById(R.id.bot_signUp);
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

        btm_signup.setOnClickListener(v -> {
            TechRegister dialogFragment = new TechRegister();
            dialogFragment.show(getParentFragmentManager(), "RegisterDialog");
            dismiss();
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
        owner logUser = temp.getUser(newUser, temp.getUsers());
        if (logUser != null) {
            temp.setIsLoggedIn(logUser);
            Intent intent = new Intent(getContext(), TechnicianDashB.class); // Replace with actual target
            startActivity(intent);
            return;
        }
        myAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(requireActivity(), task -> {
            if(task.isSuccessful()){
                Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), TechnicianDashB.class); // Replace with actual target
                startActivity(intent);
                dismiss();
                return;
            }
            Toast.makeText( getContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
        });
    }

    private String getUserRole(String username) {
        // Replace this with real logic to fetch user role
        return "owner";
    }

}
