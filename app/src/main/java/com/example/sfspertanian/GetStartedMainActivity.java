package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;


public class GetStartedMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_main);

    }
    public void startGetStartedTransition(View view) {
        // Trigger the transition defined in the transition XML
        Transition smartAnimateTransition = TransitionInflater.from(this).inflateTransition(R.transition.smart_animate);
        getWindow().setExitTransition(smartAnimateTransition);

        // Start the "activity_get_started_2"
        Intent intent = new Intent(this, GetStarted2Activity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, "smart_animate");
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
