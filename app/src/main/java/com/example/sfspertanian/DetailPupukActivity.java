package com.example.sfspertanian;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DetailPupukActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnPilih;
    TextView pupukNameTextView, hargaTextView, deskripsitv, kegunaantv;
    ImageView gambarPathMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pupuk);
        gambarPathMain = findViewById(R.id.gambar_mainpupuk);
        pupukNameTextView = findViewById(R.id.pupukNameTextView);
        hargaTextView = findViewById(R.id.hargapupukTextView);
        deskripsitv = findViewById(R.id.deskripsitv);
        kegunaantv = findViewById(R.id.kegunaantv);


        btnBack = findViewById(R.id.backToHalamanBefore);
        btnBack.setOnClickListener(v -> finish());

        btnPilih = findViewById(R.id.btnPilihPupuk);


        // Fetch data from PHP script
        handleDataFromSQL();
    }

    private void handleDataFromSQL() {
        // Retrieve bibit name from intent
        String namaPupuk = getIntent().getStringExtra("nama_pupuk");

        // Continue with the existing code to fetch data using Volley
        String url = "https://jejakpadi.com/app/Http/mobileController/get_detail_pupuk.php?nama_pupuk=" + namaPupuk;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            // Assuming only one result for the given bibit name
                            JSONObject bibitData = jsonArray.getJSONObject(0);
                            String gambarPath = bibitData.getString("gambar_path_main");
                            String pupukName = bibitData.getString("nama_pupuk");
                            String harga = bibitData.getString("harga");
                            String deskripsi = bibitData.getString("detail_pupuk");
                            String kegunaan = bibitData.getString("kegunaan");


                            ;

                            // Update UI components with the retrieved data
                            Glide.with(DetailPupukActivity.this)
                                    .load(gambarPath)
                                    .into(gambarPathMain);
                            pupukNameTextView.setText(pupukName);
                            hargaTextView.setText(harga+"/kg");
                            deskripsitv.setText(deskripsi);
                            kegunaantv.setText(kegunaan) ;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
