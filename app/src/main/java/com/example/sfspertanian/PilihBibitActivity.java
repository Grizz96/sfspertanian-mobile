package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
public class PilihBibitActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private adapter_card_bibit adapter;
    private List<DataItem> dataItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_bibit);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataItemList = new ArrayList<>();
        adapter = new adapter_card_bibit(dataItemList, new adapter_card_bibit.OnItemClickListener() {
            @Override
            public void onItemClick(String bibitName) {
                Intent intent = new Intent(PilihBibitActivity.this, DetailBibitActivity.class);
                intent.putExtra("nama_bibit", bibitName);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        makeVolleyRequest();
    }

    private void makeVolleyRequest() {
        String url = "https://jejakpadi.com/app/Http/mobileController/get_data_bibit.php";

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
                                String namaBibit = jsonObject.getString("nama_bibit");
                                String deskripsiSingkat = jsonObject.getString("deskripsi_singkat");
                                String gambarPathMain = jsonObject.getString("gambar_path_main"); // Ambil path gambar

                                // Buat objek DataItem dengan atribut baru
                                DataItem dataItem = new DataItem(namaBibit, deskripsiSingkat, gambarPathMain);
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
                        Toast.makeText(PilihBibitActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
