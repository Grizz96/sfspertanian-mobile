package com.example.sfspertanian;

import static android.content.ContentValues.TAG;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfilActivity extends AppCompatActivity {
    private EditText etNamaDepan, etNamaBelakang, etEmail, etNoHp, etTtl, etAlamat;
    private Button btnSubmit, btnCancel;
    private RequestQueue requestQueue;
    // Untuk mengambil kembali ID pengguna nanti (misalnya, di activity lain)
    SessionManager sessionManager;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        etNamaDepan=findViewById(R.id.TextNama1);
        etNamaBelakang=findViewById(R.id.TextNama2);
        etEmail=findViewById(R.id.TextEmail);
        etNoHp=findViewById(R.id.NomorTelepon);
        etTtl=findViewById(R.id.ttl);
        etAlamat=findViewById(R.id.alamat);
        btnSubmit=findViewById(R.id.btnsimpan);
        btnCancel=findViewById(R.id.btnbatal);

        sessionManager = new SessionManager(getApplicationContext());
        idUser = sessionManager.getUserId();
        readProfil();

        btnSubmit.setOnClickListener(v->{
            updateProfile();
        });
        btnCancel.setOnClickListener(v->{
            finish();

        });

    }
    private void updateProfile() {
        String namaDepan = etNamaDepan.getText().toString();
        String namaBelakang = etNamaBelakang.getText().toString();
        String email = etEmail.getText().toString();
        String noHp = etNoHp.getText().toString();
        String ttl = etTtl.getText().toString();
        String alamat = etAlamat.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlUpdateUserData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        Toast.makeText(EditProfilActivity.this, "Profile berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(EditProfilActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", idUser);
                params.put("nama_depan", namaDepan);
                params.put("nama_belakang", namaBelakang);
                params.put("email", email);
                params.put("no_handphone", noHp);
                params.put("tanggal_lahir", ttl);
                params.put("alamat", alamat);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void readProfil() {
        String url = Db_Contract.urlGetUserData+ "?id_user=" + idUser;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response
                            String namaDepan = response.getString("nama_depan");
                            String namaBelakang = response.getString("nama_belakang");
                            String alamat = response.getString("alamat");
                            String email = response.getString("email");
                            String noHP = response.getString("no_handphone");
                            String ttl = response.getString("tanggal_lahir");

                            idUser= response.getString("id_user");
                            // Set the data to the respective EditText fields
                            etNamaDepan.setText(namaDepan);
                            etNamaBelakang.setText(namaBelakang);
                            etEmail.setText(email);
                            etNoHp.setText(noHP);
                            etTtl.setText(ttl);
                            etAlamat.setText(alamat);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditProfilActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(EditProfilActivity.this, "Error fetching data"+idUser, Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
