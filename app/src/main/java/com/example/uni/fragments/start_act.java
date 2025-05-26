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
import androidx.fragment.app.FragmentActivity;

import com.example.uni.R;

public class start_act extends DialogFragment {
    private TextView costumer;
    private TextView manager;
    private TextView guest;
    FragmentActivity fragmentActivity;
    private String role;
    public start_act(FragmentActivity fragmentActivity){
        this.fragmentActivity= fragmentActivity;
    }



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.start_act, container, false);

        Button btnGetStarted = view.findViewById(R.id.btnGetStarted);

        costumer = view.findViewById(R.id.costumer);
        manager = view.findViewById(R.id.manager);

        guest = view.findViewById(R.id.guest);

        // Set the action for the "Get Started" button
        costumer.setOnClickListener(v -> select(costumer));
        manager.setOnClickListener(v -> select(manager));
        guest.setOnClickListener(v -> dismiss());

        btnGetStarted.setOnClickListener(v -> {

            if(role == null){
                Toast.makeText(fragmentActivity,"Please select a role", Toast.LENGTH_SHORT).show();
            } else if (role.equals("I'm a Costumer.")) {
                Intent intent = new Intent(getContext(), ownerLoginAct.class);
                startActivity(intent);
                dismiss();
            }else if (role.equals("I'm a Manager.")) {
                TechnicianLogin dialogFragment = new TechnicianLogin();
                dialogFragment.show(getParentFragmentManager(), "Dialog");
                dismiss();
            }
        });
        return view;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void select(TextView view){
        costumer.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.blue,null));
        manager.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.blue,null));
        view.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.edit_background,null));
        role = view.getText().toString();
    }
}
