package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class pesanActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btnwaadmin, btnwagrup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        btnBack = findViewById(R.id.backToHalamanBefore);
        btnwaadmin = findViewById(R.id.btnwaadmin);
        btnwagrup = findViewById(R.id.btnwagrup);
        btnBack.setOnClickListener(v-> finish());
        // Set an OnClickListener for the Chat Admin button
        btnwaadmin.setOnClickListener(v -> {
            // Replace "6287840199095" with the actual phone number
            String phoneNumber = "6287840199095";

            // Create a Uri with the WhatsApp number
            Uri uri = Uri.parse("https://wa.me/" + phoneNumber);

            // Create an Intent to open the WhatsApp app
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            // Start the Intent
            startActivity(intent);
        });
        btnwagrup.setOnClickListener(v -> {
            // Replace "6287840199095" with the actual phone number

            // Create a Uri with the WhatsApp number
            Uri uri = Uri.parse("https://chat.whatsapp.com/I1yBjK1WVYjHxbaZ6dsZ7P/");

            // Create an Intent to open the WhatsApp app
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            // Start the Intent
            startActivity(intent);
        });
    }
}


