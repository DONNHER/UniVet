package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.acts.TechHome;
import com.example.uni.acts.groomServiceAct;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.Item;
import com.example.uni.fragments.AppointmentDetails;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class appAdapt extends RecyclerView.Adapter<appAdapt.AppViewHolder> {
    private List<Appointment> appointments;
    FragmentManager fragmentManager;

    @SuppressLint("NotifyDataSetChanged")
    public void setAppointments(ArrayList<Appointment> newList,FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        appointments = newList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.appointment),parent,false);
        return new AppViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Appointment item = appointments.get(position);
        holder.date.setText(item.getAppointmentDate());
        holder.time.setText(item.getEmail()+ "     " + item.getStatus()+ "      "+item.getAppointmentTime());
        holder.itemView.setOnClickListener(v -> {
            AppointmentDetails dialogFragment = AppointmentDetails.newInstance(
                    item.getAppointmentDate(),
                    item.getAppointmentTime(),
                    item.getTotalCost(),
                    item.getEmail(), // or name
                    "" // or service string
            );
            dialogFragment.show(fragmentManager, "appointmentDialog");
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class AppViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView time;
        public AppViewHolder(@NonNull View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
