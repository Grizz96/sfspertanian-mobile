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
public class DetailBibitActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnPilih;
    TextView bibitNameTextView, hargaTextView, deskripsiTextView, jenistanahtv, estimasipanentv, cuacatv;
    ImageView gambarPathMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bibit);
        gambarPathMain = findViewById(R.id.gambar_main);
        bibitNameTextView = findViewById(R.id.bibitNameTextView);
        hargaTextView = findViewById(R.id.hargaTextView);
        deskripsiTextView = findViewById(R.id.deskripsiTextView);
        estimasipanentv = findViewById(R.id.estimasipanentv);
        cuacatv = findViewById(R.id.cuacatv);
        jenistanahtv = findViewById(R.id.jenistanahtv);

        btnBack = findViewById(R.id.backToHalamanBefore);
        btnBack.setOnClickListener(v -> finish());

        btnPilih = findViewById(R.id.btnPilihBibit);


        // Fetch data from PHP script
        handleDataFromSQL();
    }

    private void handleDataFromSQL() {
        // Retrieve bibit name from intent
        String namaBibit = getIntent().getStringExtra("nama_bibit");

        // Continue with the existing code to fetch data using Volley
        String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/get_detail_bibit.php?nama_bibit=" + namaBibit;

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
                            String bibitName = bibitData.getString("nama_bibit");
                            String harga = bibitData.getString("harga");
                            String deskripsi = bibitData.getString("deskripsi");
                            String tanah = bibitData.getString("jenis_tanah");
                            String cuaca = bibitData.getString("cuaca");
                            String estimasi = bibitData.getString("estimasi_panen");

                                    ;

                            // Update UI components with the retrieved data
                            Glide.with(DetailBibitActivity.this)
                                    .load(gambarPath)
                                    .into(gambarPathMain);
                            bibitNameTextView.setText(bibitName);
                            hargaTextView.setText(harga);
                            deskripsiTextView.setText(deskripsi);
                            cuacatv.setText(cuaca);
                            jenistanahtv.setText(tanah);
                            estimasipanentv.setText(estimasi + " bulan") ;

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
