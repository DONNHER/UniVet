package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.entities.Item;

import java.util.ArrayList;

public class recycler extends RecyclerView.Adapter<recycler.ViewHolder> {
    private final Context context;
    private final ArrayList<Item> items;

    public recycler(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public recycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = LayoutInflater.from(context).inflate((R.id.appointments),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycler.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.button.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
//            button = itemView.findViewById(R.id.itemBtn);
//            button = itemView.findViewById(R.id.itemBtn2);
        }
    }
}
