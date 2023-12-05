package com.example.sfspertanian;

import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sfspertanian.R;

public class RatingActivity extends AppCompatActivity {
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v->{
            finish();
        });

    }
}
