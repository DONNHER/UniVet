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
import com.example.uni.acts.ManageServiceType;
import com.example.uni.acts.OwnerDashboardAct;
import com.example.uni.acts.TechHome;
import com.example.uni.acts.TechnicianDashB;
import com.example.uni.acts.ownerDashB_setting;
import com.example.uni.acts.settingAct;
import com.example.uni.acts.shop;
import com.example.uni.entities.Service;

public class userMenu extends DialogFragment {
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
        View view = inflater.inflate(R.layout.user_menu, container, false);
        TextView dashB = view.findViewById(R.id.user_dashB);

        TextView shop2 = view.findViewById(R.id.userPetShop);

        TextView user_profile = view.findViewById(R.id.userProfile);
        TextView booking = view.findViewById(R.id.userBookService);

        dashB.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OwnerDashboardAct.class);
            startActivity(intent);
            dismiss();
        });
        shop2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), shop.class);
            startActivity(intent);
            dismiss();
        });
        user_profile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), settingAct.class);
            startActivity(intent);
            dismiss();
        });
        booking.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Service.class);
            startActivity(intent);
            dismiss();
        });

        return view;
    }

}
