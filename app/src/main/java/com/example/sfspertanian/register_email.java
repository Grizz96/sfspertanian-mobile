package com.example.sfspertanian;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.InputType;
import android.widget.ToggleButton;
import android.widget.CompoundButton;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register_email extends AppCompatActivity {

    private EditText etEmail, etPassword, etPasswordVerifikasi;
    private Button btnRegister;
    private ToggleButton toggleShowPassword, toggleShowPasswordVerifikasi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordVerifikasi = findViewById(R.id.etPasswordVerifikasi);

        btnRegister = findViewById(R.id.btnDaftar);
        toggleShowPassword = findViewById(R.id.toggleShowPassword);
        toggleShowPasswordVerifikasi = findViewById(R.id.toggleShowPasswordVerifikasi);



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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordVerifikasi = etPasswordVerifikasi.getText().toString();


                if (!(email.isEmpty() || password.isEmpty())){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegister, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), login_email.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override
                        protected HashMap<String, String> getParams() throws AuthFailureError{
                            HashMap<String, String> params = new HashMap<>();

                            params.put("email", email);
                            params.put("password", password);


                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }else{
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}