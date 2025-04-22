package com.example.uni.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.acts.TechHome;
import com.example.uni.acts.TechnicianDashB;
import com.example.uni.acts.groomServiceAct;
import com.example.uni.acts.ownerDashB_setting;
import com.example.uni.acts.settingAct;
import com.example.uni.entities.Appointment;
import com.example.uni.helper.TempStorage;

import java.text.SimpleDateFormat;

public class Menu  extends DialogFragment {
    private static TempStorage temp = TempStorage.getInstance();

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu, container, false);
        TextView dashB = view.findViewById(R.id.username16);

        TextView home = view.findViewById(R.id.username14);

        TextView profile = view.findViewById(R.id.username2);

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

        return view;
    }

}
