package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.helper.TempStorage;

public class appAct extends DialogFragment {
    private CalendarView calendarView;
    private Button scheduleButton;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.app_act, container, false); // Ensure this matches your XML file name
//        recyclerView = view.findViewById(R.id.recycleView);
//        recyclerView2 = view.findViewById(R.id.recycleView2);
        // Initialize UI elements
        calendarView = view.findViewById(R.id.calendarView);
        scheduleButton = view.findViewById(R.id.scheduleButton);
        EditText time = view.findViewById(R.id.time);
        TextView cost = view.findViewById(R.id.cost);
        scheduleButton.setOnClickListener(v -> {
            // Handle button click to schedule appointment
            if (time == null || time.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid time.", Toast.LENGTH_SHORT).show();
                return;
            }
            String dateTime = calendarView.getDate() + " " + time.getText().toString();
            Appointment newAppointment = new Appointment(groomServiceAct.getServiceType(),dateTime);
            main_act.getTemp().addAppointment(newAppointment);
            Toast.makeText(getContext(), "Successful Appointment", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        return view;
    }

}
