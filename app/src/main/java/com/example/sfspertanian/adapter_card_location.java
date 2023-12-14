package com.example.sfspertanian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class adapter_card_location extends RecyclerView.Adapter<adapter_card_location.LocationViewHolder> {
    private adapter_card_location locationAdapter;


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
                // Contoh: Buka QCActivity dan kirim data "id_sawah" yang diklik
                int id_sawah = location.getIdSawah();
                String id_sawahString = String.valueOf(id_sawah);

                Intent intent = new Intent(context, QCActivity.class);
                intent.putExtra("id_sawah", id_sawahString);
                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah Anda yakin ingin menghapus ini?");

                // Add buttons to the AlertDialog
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id_sawah = location.getIdSawah();
                        String id_sawahString = String.valueOf(id_sawah);
                        deleteSawah(id_sawahString);
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog
                    }
                });

                // Show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    private void deleteSawah(String id) {
        // You should perform the HTTP request to delete the item here
        String url = Db_Contract.urlDeleteSawah + "?id_sawah=" + id;

        // You can use Volley or any other HTTP library to make the request
        // Example using Volley
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        DataSawahFragment dataSawahFragment = new DataSawahFragment();
                        dataSawahFragment.fetchDataFromApi(context);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(context, "Error deleting item: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(context).add(request);
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
