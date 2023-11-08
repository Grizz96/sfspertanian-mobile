package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PilihBibitActivity extends AppCompatActivity {
    ImageButton btnBack;

    RelativeLayout btnDetailBibit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_bibit);

        btnDetailBibit = findViewById(R.id.card1);
        btnBack = findViewById(R.id.btnBackBefore);

        btnDetailBibit.setOnClickListener(v->{
            Intent intent = new Intent(this, DetailBibitActivity.class);
            startActivity(intent);
        });
        btnBack.setOnClickListener(v->{
            finish();
        });


    }

}
