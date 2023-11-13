package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DetailPupukActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnPilih;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pupuk);

        btnBack = findViewById(R.id.backToHalamanBefore);

        btnBack.setOnClickListener(v->{
            finish();
        });

        btnPilih = findViewById(R.id.btnPilihBibit);
        btnPilih.setOnClickListener(v->{
            // Trigger the transition defined in the transition XML
            Transition dissolveEaseOutTransition = TransitionInflater.from(this).inflateTransition(R.transition.dissolve_ease_out);
            getWindow().setExitTransition(dissolveEaseOutTransition);

            // Start the "login_email"
            Intent intent = new Intent(this, PencatatanKetelusuranActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, btnPilih, "dissolve_ease_out");
            startActivity(intent, options.toBundle());
            // Tunggu selama 2 detik sebelum menutup
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        });


    }
}
