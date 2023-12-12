package com.example.sfspertanian;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }

    BerandaFragment berandaFragment = new BerandaFragment();
        Pencatatan riwayatFragment = new Pencatatan();
    PencatatanFragment pencatatanFragment = new PencatatanFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, berandaFragment)
                        .commit();
                return true;

            case R.id.history:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment,riwayatFragment )
                        .commit();
                return true;

            case R.id.assignment:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, pencatatanFragment)
                        .commit();
                return true;

            case R.id.account:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, profileFragment)
                        .commit();
                return true;
        }
        return false;
    }

}