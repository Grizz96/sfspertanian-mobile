package com.example.sfspertanian;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class register_email extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnRegister, btnDaftar;
    private TextView txtLogin;
    private ImageButton btnBackToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        edtEmail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        txtLogin = findViewById(R.id.txtLogin);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterIsiData1Activity.class));
        });


        btnBackToLogin.setOnClickListener(v->{
            // Trigger the transition defined in the transition XML
            Transition dissolveEaseOutTransition = TransitionInflater.from(this).inflateTransition(R.transition.dissolve_ease_out);
            getWindow().setExitTransition(dissolveEaseOutTransition);

            // Start the "login_email" activity
            Intent intent = new Intent(this, login_email.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "dissolve_ease_out");
            startActivity(intent, options.toBundle());

            // Wait for 2 seconds before closing the current activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        });

        txtLogin.setOnClickListener(v->{
            // Trigger the transition defined in the transition XML
            Transition dissolveEaseOutTransition = TransitionInflater.from(this).inflateTransition(R.transition.dissolve_ease_out);
            getWindow().setExitTransition(dissolveEaseOutTransition);

            // Start the "login_email" activity
            Intent intent = new Intent(this, login_email.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "dissolve_ease_out");
            startActivity(intent, options.toBundle());

            // Wait for 2 seconds before closing the current activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        });


}}