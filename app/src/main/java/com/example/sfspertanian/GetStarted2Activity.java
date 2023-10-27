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

public class GetStarted2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_2);

    }
    public void nextTransition(View view) {
        // Trigger the transition defined in the transition XML
        Transition smartAnimateTransition = TransitionInflater.from(this).inflateTransition(R.transition.smart_animate);
        getWindow().setExitTransition(smartAnimateTransition);

        // Start the "activity_get_started_3"
        Intent intent = new Intent(this, GetStarted3Activity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        // Tunggu selama 2 detik sebelum menutup
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}
