package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tindakan yang ingin dilakukan setelah tampilan splash
                Intent intent = new Intent(SplashActivity.this, GetStartedMainActivity.class);

//              // Transisi
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                startActivity(intent, options.toBundle());
                // Tunggu selama 2 detik sebelum menutup SplashActivity
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000); // Menutup SplashActivity setelah 2 detik
            }
        }, 2000);// Tampilkan splash screen selama 2 detik (dalam milidetik)
    }
}

