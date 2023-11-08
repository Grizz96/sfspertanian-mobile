package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DetailBibitActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnPilih;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bibit);

        btnBack = findViewById(R.id.backToHalamanBefore);

        btnBack.setOnClickListener(v->{
            finish();
        });

        btnPilih = findViewById(R.id.btnPilihBibit);
        btnPilih.setOnClickListener(v->{
            finish();
        });


    }
}
