package com.example.sfspertanian;

import android.os.Bundle;
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

public class DetailPupukActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnPilih;
    TextView pupukNameTextView, hargaTextView, deskripsiTextView, kegunaanTextView;
    ImageView gambarPathMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pupuk);
        gambarPathMain = findViewById(R.id.gambar_mainpupuk);
        pupukNameTextView = findViewById(R.id.pupukNameTextView);
        hargaTextView = findViewById(R.id.hargapupukTextView);
        deskripsiTextView = findViewById(R.id.deskripsitv);
        kegunaanTextView = findViewById(R.id.kegunaantv);

        btnBack = findViewById(R.id.backToHalamanBefore);
        btnBack.setOnClickListener(v -> finish());

        btnPilih = findViewById(R.id.btnPilihPupuk);

        // Fetch data from PHP script
        handleDataFromSQL();
    }

    private void handleDataFromSQL() {
        // Retrieve pupuk name from intent
        String namaPupuk = getIntent().getStringExtra("nama_pupuk");

        // Continue with the existing code to fetch data using Volley
        String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/get_detail_pupuk.php?nama_pupuk=" + namaPupuk;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            // Assuming only one result for the given pupuk name
                            JSONObject pupukData = jsonArray.getJSONObject(0);
                            String gambarPath = pupukData.getString("gambar_path_main");
                            String imagePath = "https://jejakpadi-develop.000webhostapp.com/img/pupuk/" + gambarPath;
                            String pupukName = pupukData.getString("nama_pupuk");
                            String harga = pupukData.getString("harga");
                            String deskripsi = pupukData.getString("detail_pupuk");
                            String kegunaan = pupukData.getString("kegunaan");

                            // Update UI components with the retrieved data
                            Glide.with(DetailPupukActivity.this)
                                    .load(imagePath)
                                    .into(gambarPathMain);
                            pupukNameTextView.setText(pupukName);
                            hargaTextView.setText(harga + "/kg");
                            deskripsiTextView.setText(deskripsi);
                            kegunaanTextView.setText(kegunaan);

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
