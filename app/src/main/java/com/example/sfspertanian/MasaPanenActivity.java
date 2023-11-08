package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MasaPanenActivity extends AppCompatActivity {
    protected ImageButton btnBack;
    protected Button btnPanen;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa_panen);

        btnPanen = findViewById(R.id.btnPanen);
        btnBack = findViewById(R.id.btnBack);

        btnPanen.setOnClickListener(v->{
            getWindow().setExitTransition(new Fade());
            Intent intent = new Intent(this, PencatatanKetelusuranActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "smart_animate");
            startActivity(intent, options.toBundle());
            // Tunggu selama 2 detik sebelum menutup
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        });

        btnBack.setOnClickListener(v->{
            getWindow().setExitTransition(new Fade());
            Intent intent = new Intent(this, PencatatanKetelusuranActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "smart_animate");
            startActivity(intent, options.toBundle());

            //Tunggu selama 2 detik sebelum menutup
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },2000);
        });

    }
}
