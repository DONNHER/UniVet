package com.example.uni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.entities.Item;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder> {
    
    private List<Item> items;
    private SearchItemListener listener;

    public interface SearchItemListener {
        void onItemClicked(Item item);
        void onAddToCartClicked(Item item);
    }

    public SearchResultsAdapter(List<Item> items, SearchItemListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Item> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private MaterialTextView itemName, itemPrice, serviceType;
        private MaterialButton addToCartButton;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            serviceType = itemView.findViewById(R.id.service_type);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }

        public void bind(Item item, SearchItemListener listener) {
            itemName.setText(item.getName());
            itemPrice.setText(String.format("$%.2f", item.getPrice()));
            
            if (item.getServiceType() != null && !item.getServiceType().isEmpty()) {
                serviceType.setText(item.getServiceType());
                serviceType.setVisibility(View.VISIBLE);
            } else {
                serviceType.setVisibility(View.GONE);
            }

            // Load image
            if (item.getImage() != null && !item.getImage().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(item.getImage())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(itemImage);
            } else {
                itemImage.setImageResource(R.drawable.ic_launcher_foreground);
            }

            // Set click listeners
            itemView.setOnClickListener(v -> listener.onItemClicked(item));
            addToCartButton.setOnClickListener(v -> listener.onAddToCartClicked(item));
        }
    }
}