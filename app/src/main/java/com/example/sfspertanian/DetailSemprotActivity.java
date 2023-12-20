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
import android.util.Log;

public class DetailSemprotActivity extends AppCompatActivity {
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
        // Retrieve semprotan name from intent
        String namaSemprotan = getIntent().getStringExtra("nama_semprot");

        // Continue with the existing code to fetch data using Volley
        String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/get_detail_semprotan.php?nama_semprotan=" + namaSemprotan;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            // Assuming only one result for the given semprotan name
                            JSONObject semprotanData = jsonArray.getJSONObject(0);
                            String gambarPath = semprotanData.getString("gambar_path_main");
                            String imagePath = "https://jejakpadi.com/public/assets/img/semprotan/" + gambarPath;

                            String pupukName = semprotanData.getString("nama_semprotan");
                            String harga = semprotanData.getString("harga");
                            String deskripsi = semprotanData.getString("detail_semprotan");
                            String kegunaan = semprotanData.getString("kegunaan");

                            // Update UI components with the retrieved data
                            Glide.with(DetailSemprotActivity.this)
                                    .load(imagePath)
                                    .into(gambarPathMain);
                            pupukNameTextView.setText(pupukName);
                            hargaTextView.setText(harga + "/kg");
                            deskripsitv.setText(deskripsi);
                            kegunaantv.setText(kegunaan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        error.printStackTrace();

                        // Print the error message
                        Log.e("Volley Error", error.toString());
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
