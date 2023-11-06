package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;

public class PencatatanKetelusuranActivity extends AppCompatActivity {

    ImageButton btnBack;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PencatatanKetelusuranViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencatatan_ketelusuran);

        btnBack = findViewById(R.id.btnBack);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        viewPagerAdapter = new PencatatanKetelusuranViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        btnBack.setOnClickListener(v->{
            getWindow().setExitTransition(new Fade());
            Intent intent = new Intent(this, MainActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "smart_animate");
            startActivity(intent, options.toBundle());

            //Tunggu selama 2 detik sebelum menutup
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },2000);
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}
