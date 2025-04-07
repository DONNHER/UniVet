package com.example.uni.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;

public class start_act extends DialogFragment {



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view = inflater.inflate(R.layout.start_act, container, false);

        Button btnGetStarted = view.findViewById(R.id.btnGetStarted);

        // Set the action for the "Get Started" button
        btnGetStarted.setOnClickListener(v -> {
            // Action to perform when "Get Started" is clicked
            // Redirect to Login or next activity, for example
            dismiss();
        });
        return view;
    }
}
