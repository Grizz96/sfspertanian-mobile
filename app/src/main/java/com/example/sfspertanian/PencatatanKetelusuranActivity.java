package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.shuhart.stepview.StepView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class PencatatanKetelusuranActivity extends AppCompatActivity {

    ImageButton btnBack;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private StepView stepView;
    TextView tesTextView;
    PencatatanKetelusuranViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencatatan_ketelusuran);

        tesTextView = findViewById(R.id.detail);



        // Panggil fungsi untuk melakukan permintaan Volley


        Resources res = getResources();

        stepView = findViewById(R.id.step_view);
        String[] stepsArray = res.getStringArray(R.array.steps);
        stepView.setSteps(Arrays.asList(stepsArray));

        btnBack = findViewById(R.id.btnBack);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        viewPagerAdapter = new PencatatanKetelusuranViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                stepView.go(stepView.getCurrentStep()+1,true);
            }
        });

        btnBack.setOnClickListener(v->{
            getWindow().setExitTransition(new Fade());
            // Kembali ke PencatatanFragment
            Intent backIntent = new Intent(this, MainActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "smart_animate");
            startActivity(backIntent, options.toBundle());

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


    private int getCurrentStepBasedOnDate(String tanggalTanam, String[] stepsArray) {
        if (tanggalTanam != null && !tanggalTanam.isEmpty()) {
            // Placeholder logic for date comparison
            // You need to replace this with your actual date comparison logic

            // Placeholder: Assume planting is before April 1, cultivation is before July 1
            int plantingStepIndex = 0;
            int cultivationStepIndex = 1;
            int harvestStepIndex = 2;

            if (isBeforeDate(tanggalTanam, "2023-04-01")) {
                return plantingStepIndex;
            } else if (isBeforeDate(tanggalTanam, "2023-07-01")) {
                return cultivationStepIndex;
            } else {
                return harvestStepIndex;
            }
        }

        // Default to the first step if the date is not available
        return 0;
    }

    private boolean isBeforeDate(String currentDate, String targetDate) {
        // Placeholder: Compare dates as strings (yyyy-MM-dd)
        // Replace this with your actual date comparison logic

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date current = sdf.parse(currentDate);
            Date target = sdf.parse(targetDate);

            return current.before(target);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ... existing code ...
}