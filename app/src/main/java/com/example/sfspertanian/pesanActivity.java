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
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class pesanActivity extends AppCompatActivity {

    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        btnBack = findViewById(R.id.backToHalamanBefore);
        btnBack.setOnClickListener(v->{
            finish();
        });

    }
}
