package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uni.R;
import com.example.uni.acts.groomServiceAct;
import com.example.uni.entities.Service;
import com.example.uni.fragments.Menu;
import com.example.uni.fragments.appAct;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;
import java.util.UUID;
import java.util.ArrayList;

public class serviceAdapt extends RecyclerView.Adapter<serviceAdapt.ServiceViewHolder> {
    private ArrayList<Service> appointments;

    private Context context;

    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    public serviceAdapt(ArrayList<Service> items){
        this.appointments = items;
        this.context = context;
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
        holder.name.setText(item.getName());
        Glide.with(holder.pic.getContext())
                .load(item.getImage())
                .into(holder.pic);
        holder.itemView.setOnClickListener(view -> {
            appAct dialogFragment = new appAct();
            dialogFragment.show(new groomServiceAct().getFragment(), "appointmentDialog");
    });
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
