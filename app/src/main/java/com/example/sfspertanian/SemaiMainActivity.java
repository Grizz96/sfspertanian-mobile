package com.example.sfspertanian;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SemaiMainActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView tabItem1, tabItem2;
    private int selectedTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semai_main);

        tabItem1 = findViewById(R.id.tabItem1);
        tabItem2 = findViewById(R.id.tabItem2);

        // Default first fragment
        FragmentOverviewSemai fragmentOverviewSemai = new FragmentOverviewSemai();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, fragmentOverviewSemai)
                .commit();

        tabItem1.setOnClickListener(v -> selectTab(1));
        tabItem2.setOnClickListener(v -> selectTab(2));
        btnBack = findViewById(R.id.backToHalamanBefore);
        btnBack.setOnClickListener(v->{
            finish();
        });
    }

    private void selectTab(int tabNumber) {
        if (tabNumber == selectedTabNumber) {
            return; // No need to do anything if the selected tab is already active.
        }

        Fragment newFragment;
        TextView selectedTextView, nonSelectedTextView;

        if (tabNumber == 1) {
            selectedTextView = tabItem1;
            nonSelectedTextView = tabItem2;
            newFragment = new FragmentOverviewSemai();
        } else {
            selectedTextView = tabItem2;
            nonSelectedTextView = tabItem1;
            newFragment = new FragmentCatatanSemai();
        }

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, newFragment, null)
                .commit();

        float slideTo = (tabNumber - selectedTabNumber) * selectedTextView.getWidth();
        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0, 0);
        translateAnimation.setDuration(100);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Change the background of the selected Tab
                selectedTextView.setBackgroundResource(R.drawable.bg_white_radius);
                selectedTextView.setTextColor(Color.BLACK);

                // Change the background of the previously selected tab
                nonSelectedTextView.setBackgroundResource(R.drawable.bg_green_radius);
                nonSelectedTextView.setTextColor(Color.BLACK);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change the background of the selected Tab
                selectedTextView.setBackgroundResource(R.drawable.bg_green_radius);
                selectedTextView.setTextColor(Color.BLACK);

                // Change the background of the previously selected tab
                nonSelectedTextView.setBackgroundResource(R.drawable.bg_white_radius);
                nonSelectedTextView.setTextColor(Color.BLACK);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        if (selectedTabNumber == 1) {
            tabItem1.startAnimation(translateAnimation);
        } else if (selectedTabNumber == 2) {
            tabItem2.startAnimation(translateAnimation);
        }

        selectedTabNumber = tabNumber;
    }


}
