package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.acts.GroomPackage;
import com.example.uni.acts.MedPackage;
import com.example.uni.acts.OwnerDashboardAct;
import com.example.uni.acts.TechHome;
import com.example.uni.acts.groomServiceAct;
import com.example.uni.acts.medServiceAct;
import com.example.uni.entities.Service;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.groomAppointments;
import com.example.uni.fragments.ownerLoginAct;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class packageAdapt  extends RecyclerView.Adapter<packageAdapt.manageServiceTypeHolder> {
    private ArrayList<Service> appointments;
    private FragmentActivity fragmentActivity;
    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("NotifyDataSetChanged")
    public packageAdapt(ArrayList<Service> items,FragmentActivity activity){
        this.appointments = items;
        this.fragmentActivity = activity;
    }


    @NonNull
    @Override
    public manageServiceTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.service_package),parent,false);
        return new manageServiceTypeHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull manageServiceTypeHolder holder, int position) {
        Service item = appointments.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(""+item.getDescription());
        holder.price.setText(""+item.getPrice());
        Glide.with(holder.pic.getContext())
                .load(item.getImage())
                .into(holder.pic);
        if(myAuth.getCurrentUser() == null){
            holder.itemView.setOnClickListener(view -> {
            ownerLoginAct dialogFragment = new ownerLoginAct();
            dialogFragment.show(fragmentActivity.getSupportFragmentManager(), "AppointmentDialog");
            });
        }else {
            holder.itemView.setOnClickListener(view -> {
                groomAppointments dialogFragment = new groomAppointments(item.getId().toString(), item.getName());
                dialogFragment.show(fragmentActivity.getSupportFragmentManager(), "AppointmentDialog");
            });
        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class manageServiceTypeHolder extends RecyclerView.ViewHolder {
        public ImageView pic;
        public TextView name;
        public TextView description;
        public TextView price;
        public manageServiceTypeHolder(@NonNull View itemView){
            super(itemView);
            description = itemView.findViewById(R.id.textView5);
            pic = itemView.findViewById(R.id.image2);
            name = itemView.findViewById(R.id.service_name);
            price = itemView.findViewById(R.id.price2);
        }
    }
}
