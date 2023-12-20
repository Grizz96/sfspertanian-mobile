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
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;



public class PilihSemprotActivity extends AppCompatActivity {
    ImageButton btnBack;
    private RecyclerView recyclerView;
    private adapter_card_pupuk adapter;
    private List<DataItem> dataItemList;
    RelativeLayout btnDetailPupuk;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_semprot);

        btnBack = findViewById(R.id.btnBackBefore);


        btnBack.setOnClickListener(v->{
            finish();
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataItemList = new ArrayList<>();
        adapter = new adapter_card_pupuk(dataItemList, new adapter_card_pupuk.OnItemClickListener() {
            @Override
            public void onItemClick(String pupukName) {
                Intent intent = new Intent(PilihSemprotActivity.this, DetailSemprotActivity.class);
                intent.putExtra("nama_semprot", pupukName);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        makeVolleyRequest();


    }

    private void makeVolleyRequest() {
        String url = Db_Contract.urlGetSemprot;

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
                                String namaPupuk = jsonObject.getString("nama_semprotan");
                                String deskripsiSingkat = jsonObject.getString("deskripsi_singkat");
                                String gambarPathMain = jsonObject.getString("gambar_path_main"); // Ambil path gambar
                                String imagePath = "https://jejakpadi.com/public/assets/img/semprotan/" + gambarPathMain;
                                DataItem dataItem = new DataItem(namaPupuk, deskripsiSingkat, imagePath);

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
                        Toast.makeText(PilihSemprotActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
