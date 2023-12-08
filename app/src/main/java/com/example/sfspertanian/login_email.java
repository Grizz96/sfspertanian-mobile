package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.ToggleButton;



import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login_email extends AppCompatActivity {
    private TextView txtRegister;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ToggleButton toggleShowPassword;
    private String idUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        toggleShowPassword = findViewById(R.id.toggleShowPassword);

        txtRegister = findViewById(R.id.txtRegister);

        toggleShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle the password visibility based on the ToggleButton state
                if (isChecked) {
                    // Show password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Hide password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Move the cursor to the end of the password field
                etPassword.setSelection(etPassword.length());
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent untuk membuka kelas RegisterEmail
                Intent intent = new Intent(login_email.this, register_email.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    // Instantiate a RequestQueue
                    RequestQueue requestQueue = Volley.newRequestQueue(login_email.this);

                    // Define the URL for login (adjust this based on your server)
                    String loginUrl = Db_Contract.urlLogin; // Use the URL from Db_Contract

                    // Make a POST request for secure login
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String status = jsonResponse.getString("status");

                                if (status.equals("success")) {
                                    Toast.makeText(login_email.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                                    // Mengambil id_user dari respons JSON
                                    String idUser = jsonResponse.getString("id_user");

                                    // Simpan idUser ke dalam sesi
                                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                                    sessionManager.setUserId(idUser);

                                    // Panggil fungsi readProfil
                                    readProfil(email);

                                    startActivity(new Intent(login_email.this, MainActivity.class));
                                } else {
                                    Toast.makeText(login_email.this, "Email atau Kata Sandi Anda tidak sesuai", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(login_email.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(login_email.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };

                    // Add the request to the RequestQueue
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(login_email.this, "Email dan Kata Sandi harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void readProfil(String gmail) {
        String url = Db_Contract.urlGetUserData;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            idUser = response.getString("id_user");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login_email.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(login_email.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
