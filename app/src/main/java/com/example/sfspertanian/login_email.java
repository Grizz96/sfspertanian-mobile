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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login_email extends AppCompatActivity {
    private TextView txtRegister;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ToggleButton toggleShowPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        toggleShowPassword = findViewById(R.id.toggleShowPassword);


        TextView txtRegister = findViewById(R.id.txtRegister);

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
                            if (response.equals("Selamat Datang")) {
                                Toast.makeText(login_email.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login_email.this, MainActivity.class));
                            } else {
                                Toast.makeText(login_email.this, "Email atau Kata Sandi anda tidak sesuai", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(login_email.this, "Email dan Kata Sandi harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
