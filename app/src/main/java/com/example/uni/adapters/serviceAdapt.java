package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.Item;
import com.example.uni.entities.Service;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class serviceAdapt extends RecyclerView.Adapter<serviceAdapt.ServiceViewHolder> {
    private final ArrayList<Service> appointments;

    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    public serviceAdapt(ArrayList<Service> items){
        this.appointments = items;
    }


    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.service),parent,false);
        return new ServiceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service item = appointments.get(position);
        holder.price.setText(""+ item.getPrice());
        holder.pic.setImageResource(item.getImage());
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView price;
        public ImageView pic;
        public TextView name;
        public ServiceViewHolder(@NonNull View itemView){
            super(itemView);
            price = itemView.findViewById(R.id.cost2);
            pic = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.service_name);
        }
    }
}
