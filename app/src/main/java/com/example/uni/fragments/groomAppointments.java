package com.example.uni.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

public class groomAppointments extends DialogFragment {
    private Button scheduleButton;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private String Service_name;
    private String description;
    private double price;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id;

    public groomAppointments(String id, String name, String description, double price) {
        this.id = id;
        this.Service_name = name;
        this.description = description;
        this.price = price;
    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_act, container, false);

        TextView serviceName = view.findViewById(R.id.serviceName);
        TextView serviceDescription = view.findViewById(R.id.serviceDescription);
        TextView price = view.findViewById(R.id.price9);
        serviceName.setText(Service_name);
        serviceDescription.setText(description);
        price.setText(String.valueOf(this.price));

        EditText editTextMonth = view.findViewById(R.id.editTextMonth);
        EditText editTextDay = view.findViewById(R.id.editTextDay);
        EditText editTextYear = view.findViewById(R.id.editTextYear);
        EditText Patient = view.findViewById(R.id.namePatient);
        TextView timeTextView = view.findViewById(R.id.time);
        ImageView imageViewCalendar = view.findViewById(R.id.imageViewCalendar);
        ImageView imageViewTime = view.findViewById(R.id.imageViewTime);
        scheduleButton = view.findViewById(R.id.scheduleButton);

        // Date text watcher for toast (optional feedback)
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String mm = editTextMonth.getText().toString();
                String dd = editTextDay.getText().toString();
                String yyyy = editTextYear.getText().toString();

                if (mm.length() == 2 && dd.length() == 2 && yyyy.length() == 4) {
                    String fullDate = mm + "/" + dd + "/" + yyyy;
                    Toast.makeText(getContext(), "Date Entered: " + fullDate, Toast.LENGTH_SHORT).show();
                }
            }
        };
        editTextMonth.addTextChangedListener(watcher);
        editTextDay.addTextChangedListener(watcher);
        editTextYear.addTextChangedListener(watcher);

        // Date Picker
        imageViewCalendar.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    v.getContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate currentDate = LocalDate.now();
                            try {
                                LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay);

                                if (selectedDate.isBefore(currentDate)) {
                                    Toast.makeText(getContext(), "Please select a future date.", Toast.LENGTH_SHORT).show();
                                    editTextMonth.setText("");
                                    editTextDay.setText("");
                                    editTextYear.setText("");
                                    return;
                                }

                                editTextMonth.setText(String.format("%02d", selectedMonth + 1));
                                editTextDay.setText(String.format("%02d", selectedDay));
                                editTextYear.setText(String.valueOf(selectedYear));

                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Invalid date selected.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Time Picker
        imageViewTime.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (view12, hourOfDay, minute1) -> {
                        String formattedTime = String.format("%02d:%02d", hourOfDay, minute1);
                        timeTextView.setText(formattedTime);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        // Schedule Button Logic
        scheduleButton.setOnClickListener(v -> {
            String mm = editTextMonth.getText().toString();
            String dd = editTextDay.getText().toString();
            String yyyy = editTextYear.getText().toString();
            String time = timeTextView.getText().toString();
            String namePatient = Patient.getText().toString();

            if (mm.isEmpty() || dd.isEmpty() || yyyy.isEmpty()) {
                Toast.makeText(getContext(), "Please select a valid date.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (time.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid time.", Toast.LENGTH_SHORT).show();
                return;
            }

            String Date = mm + "/" + dd + "/" + yyyy;
            String docId = UUID.randomUUID().toString();

            Appointment appointment = new Appointment(
                    myAuth.getCurrentUser().getDisplayName(),
                    Date,
                    time,
                    namePatient
            );

            appointment.setId(docId);
            appointment.setStatus("Pending");
            appointment.setUserID(myAuth.getCurrentUser().getUid());
            appointment.setServiceID(id);

            db.collection("Appointments").document(docId).set(appointment)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Appointment scheduled successfully!", Toast.LENGTH_SHORT).show();
                        requireActivity().recreate();
                        dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        return view;
    }
}
