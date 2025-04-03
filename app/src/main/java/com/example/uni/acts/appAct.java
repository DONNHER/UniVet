package com.example.uni.acts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.uni.R;

public class appAct extends DialogFragment {

    private ImageView doctorImage;
    private TextView doctorInfo;
    private CalendarView calendarView;
    private GridLayout morningSlots;
    private GridLayout eveningSlots;
    private Button scheduleButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.app_act, container, false); // Ensure this matches your XML file name

        // Initialize UI elements
         doctorImage = view.findViewById(R.id.doctorImage);
        calendarView = view.findViewById(R.id.calendarView);
        morningSlots = view.findViewById(R.id.morningSlots);
        eveningSlots = view.findViewById(R.id.eveningSlots);
        scheduleButton = view.findViewById(R.id.scheduleButton);

        // Set up listeners or actions
        setupSlots(morningSlots, "Morning");
        setupSlots(eveningSlots, "Evening");

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click to schedule appointment
                scheduleAppointment();
            }
        });

        return view;
    }

    private void setupSlots(GridLayout gridLayout, String timeOfDay) {
        // You can dynamically populate time slots based on the grid layout for morning or evening
        // Example: Add buttons for time slots dynamically
        for (int i = 0; i < 4; i++) {
            Button slotButton = new Button(getActivity());
            slotButton.setText(timeOfDay + " Slot " + (i + 1));
            // Set up button listeners if needed
            gridLayout.addView(slotButton);
        }
    }

    private void scheduleAppointment() {
        // Logic to schedule the appointment based on user selection
        // Example: Get the selected date and time slot, then proceed with scheduling
    }
}
