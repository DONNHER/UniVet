package com.example.uni.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.acts.TechHome;
import com.example.uni.acts.TechnicianDashB;
import com.example.uni.acts.manager_inventory;
import com.example.uni.acts.ownerDashB_setting;

public class Menu  extends DialogFragment {
   @Override
    public void onStart(){
        super.onStart();
        if(getDialog() == null&& getDialog().getWindow() == null){
            dismiss();
        }else {
            Window window = getDialog().getWindow();
            window.setLayout(600,ViewGroup.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.START);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu, container, false);
        TextView dashB = view.findViewById(R.id.username16);

        TextView home = view.findViewById(R.id.username14);

        TextView profile = view.findViewById(R.id.username6);
        TextView inventory = view.findViewById(R.id.username5);

//        TextView reports = view.findViewById(R.id.username15);
        dashB.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TechnicianDashB.class);
            startActivity(intent);
            dismiss();
        });
        home.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TechHome.class);
            startActivity(intent);
            dismiss();
        });
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ownerDashB_setting.class);
            startActivity(intent);
            dismiss();
        });
        inventory.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), manager_inventory.class);
            startActivity(intent);
            dismiss();
        });

        return view;
    }

}
