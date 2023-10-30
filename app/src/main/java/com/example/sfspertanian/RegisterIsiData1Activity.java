package com.example.sfspertanian;

import android.os.Bundle;
import android.transition.Fade;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterIsiData1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Menerapkan transisi masuk
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_isi_data_1);

    }
}
