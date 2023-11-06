package com.example.sfspertanian;

import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class PilihBibitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_bibit);


    }
}
