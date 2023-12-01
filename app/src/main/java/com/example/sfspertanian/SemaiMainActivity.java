package com.example.sfspertanian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Fade;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semai_main);

        btnBack = findViewById(R.id.backToHalamanBefore); // Assuming you have an ImageButton with this ID
        recyclerView = findViewById(R.id.recyclesemai);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataItemList = new ArrayList<>();
        adapter = new adapter_card_semai(this,dataItemList);
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        makeVolleyRequest();
    }

    private void makeVolleyRequest() {
        String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/get_data_semai.php";

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
