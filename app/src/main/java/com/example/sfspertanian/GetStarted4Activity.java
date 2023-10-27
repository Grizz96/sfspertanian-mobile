package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GetStarted4Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_4);

    }
    public void nextTransition(View view) {
        // Trigger the transition defined in the transition XML
        Transition dissolveEaseOutTransition = TransitionInflater.from(this).inflateTransition(R.transition.dissolve_ease_out);
        getWindow().setExitTransition(dissolveEaseOutTransition);

        // Start the "login_email"
        Intent intent = new Intent(this, login_email.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, "dissolve_ease_out");
        startActivity(intent, options.toBundle());
        // Tunggu selama 2 detik sebelum menutup
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}
