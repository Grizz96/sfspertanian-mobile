package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class KontenLiterasiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konten_literasi);

    }
}
