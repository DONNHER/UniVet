package com.example.uni.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;

public class AppointmentDetails extends DialogFragment {
    private String date, time, name, services;
    private double totalCost;

    public static AppointmentDetails newInstance(String date, String time, double totalCost, String name, String services) {
        AppointmentDetails fragment = new AppointmentDetails();
        Bundle args = new Bundle();
        args.putString("date", date);
        args.putString("time", time);
        args.putDouble("totalCost", totalCost);
        args.putString("name", name);
        args.putString("services", services);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.appointmen_detials, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            date = getArguments().getString("date");
            time = getArguments().getString("time");
            totalCost = getArguments().getDouble("totalCost");
            name = getArguments().getString("name");
            services = getArguments().getString("services");
        }

        TextView Date = view.findViewById(R.id.Date);
        TextView Time = view.findViewById(R.id.Time);
        TextView tCost = view.findViewById(R.id.cost);
        TextView Name = view.findViewById(R.id.r);
        TextView service = view.findViewById(R.id.service);

        Date.setText(date);
        Time.setText(time);
        tCost.setText(String.format("%.2f", totalCost));
        service.setText(services);
    }
}
