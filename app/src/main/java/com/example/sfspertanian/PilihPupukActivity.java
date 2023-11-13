package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PilihPupukActivity extends AppCompatActivity {
    ImageButton btnBack;

    RelativeLayout btnDetailBibit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_pupuk);

        btnDetailBibit = findViewById(R.id.card1);
        btnBack = findViewById(R.id.btnBackBefore);

        btnDetailBibit.setOnClickListener(v->{
            Intent intent = new Intent(this, DetailPupukActivity.class);
            startActivity(intent);
        });
        btnBack.setOnClickListener(v->{
            finish();
        });


    }

}
