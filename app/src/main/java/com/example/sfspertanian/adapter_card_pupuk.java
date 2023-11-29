package com.example.sfspertanian;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class adapter_card_pupuk extends RecyclerView.Adapter<adapter_card_pupuk.ViewHolder> {

    private List<DataItem> dataItemList;
    private OnItemClickListener mListener;

    // Define an interface for the click listener
    public interface OnItemClickListener {
        void onItemClick(String bibitName);
    }

    // Constructor with the listener
    public adapter_card_pupuk(List<DataItem> dataItemList, OnItemClickListener listener) {
        this.dataItemList = dataItemList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your item layout here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem currentItem = dataItemList.get(position);

        // Bind data to your item view
        // Assuming getNamaBibit() and getDeskripsiSingkat() are methods in DataItem
        holder.cardTitle.setText(currentItem.getNamaBibit());
        holder.cardDescription.setText(currentItem.getDeskripsiSingkat());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(currentItem.getNamaBibit());
                }
            }
        });
        Glide.with(holder.itemView.getContext())
                .load(currentItem.getGambarPathMain())

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Handle error
                        Log.e("Glide", "Error loading image", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Image loaded successfully
                        return false;
                    }
                })
                .into(holder.cardImage);


    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    // ViewHolder class with updated IDs, replace with your actual implementation
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardTitle;
        TextView cardDescription;
        RelativeLayout cardDetailButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your item views here
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            cardDescription = itemView.findViewById(R.id.cardDescription);

        }
    }
}
