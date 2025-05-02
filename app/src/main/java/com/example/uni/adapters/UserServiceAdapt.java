package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.acts.TechHome;
import com.example.uni.entities.Service;
import com.example.uni.fragments.groomAppointments;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserServiceAdapt extends   RecyclerView.Adapter<UserServiceAdapt.ViewHolder> {
    private ArrayList<Service> appointments;

    private Context context;

    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    public UserServiceAdapt(ArrayList<Service> items){
        this.appointments = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.service_package),parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service item = appointments.get(position);
        holder.price.setText(""+ item.getPrice());
        holder.name.setText(item.getName());
        Glide.with(holder.pic.getContext())
                .load(item.getImage())
                .into(holder.pic);
        holder.itemView.setOnClickListener(view -> {
            groomAppointments dialogFragment = new groomAppointments(item.getId().toString(),item.getName(),item.getDescription(),item.getPrice());
            dialogFragment.show(new TechHome().getFragment(), "appointmentDialog");
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView price;
        public ImageView pic;
        public TextView name;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
//            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.image2);
            name = itemView.findViewById(R.id.service_name);
        }
    }
}
