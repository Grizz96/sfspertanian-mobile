package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login_email extends AppCompatActivity {
    private TextView;
    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        TextView txtRegister = findViewById(R.id.txtRegister);

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
                            if (response.equals("Selamat Datang")) {
                                Toast.makeText(login_email.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login_email.this, MainActivity.class));
                            } else {
                                Toast.makeText(login_email.this, "Password atau Email anda tidak sesuai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(login_email.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                            // Log the error for debugging
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
                    Toast.makeText(login_email.this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
