package com.example.sfspertanian;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class penyemprotan extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText jenisPenyemprotan, deskripsiPenyemprotan, tanggalSemprot, jumlahSemprot;
    private Button btnSimpan, btnBatal;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyemprotan);

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        jenisPenyemprotan = findViewById(R.id.jenisPenyemprotanEditText);
        deskripsiPenyemprotan = findViewById(R.id.deskripsiPenyemprotanEditText);
        tanggalSemprot = findViewById(R.id.tanggalSemprotEditText);
        jumlahSemprot = findViewById(R.id.jumlahSemprotEditText);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);
        titleTextView = findViewById(R.id.titleTextView);

        // Set click listener for the "Simpan" button
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToServer();
            }
        });
    }

    private void sendDataToServer() {
        // Get data from EditText fields
        String jenisPenyemprotanText = jenisPenyemprotan.getText().toString();
        String deskripsiText = deskripsiPenyemprotan.getText().toString();
        String tanggalText = tanggalSemprot.getText().toString();
        String jumlahText = jumlahSemprot.getText().toString();

        // Replace these values with the actual user and sawah IDs
        int userId = 14;
        int sawahId = 39;
        int idsemprotan = 2;

        // Create JSON object with the data
        JSONObject postData = new JSONObject();
        try {
            postData.put("jenis_penyemprotan", jenisPenyemprotanText);
            postData.put("deskripsi_penyemprotan", deskripsiText);
            postData.put("tanggal_semprot", tanggalText);
            postData.put("jumlah_semprot", jumlahText);
            postData.put("id_user", userId);
            postData.put("id_semprotan",  idsemprotan);
            postData.put("id_sawah", sawahId);
            // Add other fields if needed
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Replace YOUR_PHP_SCRIPT_URL with the actual URL of your PHP script
        String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/insert_semprot.php";

        // Send a POST request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            String message = response.getString("message");
                            Toast.makeText(penyemprotan.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("Volley Error", "Error: " + error.toString());
                        Toast.makeText(penyemprotan.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
