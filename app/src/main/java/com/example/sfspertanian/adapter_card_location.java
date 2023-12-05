package com.example.sfspertanian;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class adapter_card_location extends RecyclerView.Adapter<adapter_card_location.LocationViewHolder> {

    private Context context;
    private List<LocationItem> locationList;

    public adapter_card_location(Context context, List<LocationItem> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_model, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        LocationItem location = locationList.get(position);

        // Bind data to views
        holder.locationNameTextView.setText(location.getLocationName());
        holder.startDateTextView.setText(location.getStartDate());
        holder.locationDescriptionTextView.setText(location.getLocationDescription());
        holder.locationAddressTextView.setText(location.getLocationAddress());
        holder.locationSizeTextView.setText(location.getLocationSize());

        // Set click listeners or any other operations if needed
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View view) {
                    // Handle item click
                    // Contoh: Buka PencatatanKetelusuranActivity dan kirim data "sawah" yang diklik
                Intent intent = new Intent(context, PencatatanKetelusuranActivity.class);
                intent.putExtra("id_sawah", location.getIdSawah());
                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle delete button click
            }
        });
    }

    public void setData(List<LocationItem> locationList) {
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView locationNameTextView, startDateTextView, locationDescriptionTextView, locationAddressTextView, locationSizeTextView;
        ImageButton detailButton, deleteButton;

        public LocationViewHolder(View itemView) {
            super(itemView);

            // cardView = itemView.findViewById(R.id.lokasi_sawah);
            locationNameTextView = itemView.findViewById(R.id.locationName);
            startDateTextView = itemView.findViewById(R.id.tanggal_mulai);
            locationDescriptionTextView = itemView.findViewById(R.id.deskripsimapTextView);
            locationAddressTextView = itemView.findViewById(R.id.luasTV);
            locationSizeTextView = itemView.findViewById(R.id.luasTV);
            detailButton = itemView.findViewById(R.id.detailLokasi);
            deleteButton = itemView.findViewById(R.id.hapussawah);
        }
    }
}
