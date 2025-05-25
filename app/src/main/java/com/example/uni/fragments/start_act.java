package com.example.uni.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.uni.R;

public class start_act extends DialogFragment {
    private TextView costumer;
    private TextView manager;
    private TextView admin;
    private TextView guest;

    private String role;



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.start_act, container, false);

        Button btnGetStarted = view.findViewById(R.id.btnGetStarted);

        costumer = view.findViewById(R.id.costumer);
        manager = view.findViewById(R.id.manager);
        admin = view.findViewById(R.id.admin);

        guest = view.findViewById(R.id.guest);

        // Set the action for the "Get Started" button
        costumer.setOnClickListener(v -> select(costumer));
        manager.setOnClickListener(v -> select(manager));
        admin.setOnClickListener(v -> select(admin));
        guest.setOnClickListener(v -> select(guest));

        btnGetStarted.setOnClickListener(v -> {
            Toast.makeText(getContext(),role, Toast.LENGTH_SHORT).show();

            if(role == null){
                Toast.makeText(getContext(),"Please select a role", Toast.LENGTH_SHORT).show();
            } else if (role.equals("I'm a Costumer.")) {
                Intent intent = new Intent(getContext(), ownerLoginAct.class);
                startActivity(intent);
                dismiss();
            }else if (role.equals("I'm a Manager.")) {
                TechnicianLogin dialogFragment = new TechnicianLogin();
                dialogFragment.show(getParentFragmentManager(), "Dialog");
                dismiss();
            }
            else if (role.equals("I'm a Admin.")) {
                AdminLogin dialogFragment = new AdminLogin();
                dialogFragment.show(getParentFragmentManager(), "LogIn");
                dismiss();
            }
            dismiss();
        });
        return view;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void select(TextView view){
        costumer.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.blue,null));
        manager.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.blue,null));
        admin.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.blue,null));
        view.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.edit_background,null));
        role = view.getText().toString();
    }
}
