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

public class pemupukan extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText jenisPemupukan, deskripsiPersemaian, tanggalSemai, jumlahSemai;
    private Button btnSimpan, btnBatal;
    private TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemupukan);

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        jenisPemupukan = findViewById(R.id.JenisPemupukan);
        deskripsiPersemaian = findViewById(R.id.DeskripsiPersemaian);
        tanggalSemai = findViewById(R.id.TanggalSemai);
        jumlahSemai = findViewById(R.id.JumlahSemai);
        btnSimpan = findViewById(R.id.btnsimpan);
        btnBatal = findViewById(R.id.btnbatal);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

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
        String jenisPemupukanText = jenisPemupukan.getText().toString();
        String deskripsiText = deskripsiPersemaian.getText().toString();
        String tanggalText = tanggalSemai.getText().toString();
        String jumlahText = jumlahSemai.getText().toString();

        // Create JSON object with the data
        JSONObject postData = new JSONObject();
        try {
            postData.put("jenis_pemupukan", jenisPemupukanText);
            postData.put("deskripsi_persemaian", deskripsiText);
            postData.put("tanggal_semai", tanggalText);
            postData.put("jumlah_semai", jumlahText);
            // Add other fields if needed
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Replace YOUR_PHP_SCRIPT_URL with the actual URL of your PHP script
        String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/insert_pupuk.php";

        // Send a POST request using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            String message = response.getString("message");
                            Toast.makeText(pemupukan.this, message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(pemupukan.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
