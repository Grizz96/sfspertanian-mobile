package com.example.sfspertanian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SemaiMainActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private RecyclerView recyclerView;
    private adapter_card_semai adapter;
    private List<ModelCardSemai> dataItemList;

    private int idSawah; // Declare a variable to store the id_sawah

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semai_main);

        // Get the id_sawah from the Intent

        btnBack = findViewById(R.id.backToHalamanBefore);
        recyclerView = findViewById(R.id.recyclesemai);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataItemList = new ArrayList<>();
        adapter = new adapter_card_semai(this, dataItemList);
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        FloatingActionButton addButton = findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil bottom sheet saat tombol "Add" ditekan
                showBottomSheet();
            }
        });

        makeVolleyRequest();
    }

    private void showBottomSheet() {
        BottomSheetLayout bottomSheet = new BottomSheetLayout();
        bottomSheet.setOnDataAddedListener(new BottomSheetLayout.OnDataAddedListener() {
            @Override
            public void onDataAdded() {
                // Update your data in SemaiMainActivity
                makeVolleyRequest();
            }
        });
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }

    private void makeVolleyRequest() {
        int idSawah = getIntent().getIntExtra("id_sawah", 39);
        // Modify the URL to include id_sawah as a parameter
        String url = "https://jejakpadi.com/app/Http/mobileController/get_data_semai.php?id_sawah=" + idSawah;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataItemList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String titleCard = jsonObject.getString("jenis_semai");
                                String subtitleCard = jsonObject.getString("deskripsi");
                                String timeCard = jsonObject.getString("waktu");
                                String dateCard = jsonObject.getString("tanggal");

                                // Create a new ModelCardSemai object
                                ModelCardSemai dataItem = new ModelCardSemai(titleCard, subtitleCard, timeCard, dateCard);
                                dataItemList.add(dataItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SemaiMainActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}