package com.example.sfspertanian;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.shuhart.stepview.StepView;

import java.util.Arrays;

public class Pencatatan extends Fragment {

    private ImageButton btnBack;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private StepView stepView;
    private TextView tesTextView;
    private PencatatanKetelusuranViewPagerAdapter viewPagerAdapter;

    public Pencatatan() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_pencatatan_ketelusuran, container, false);

        tesTextView = view.findViewById(R.id.detail);

        // Panggil fungsi untuk melakukan permintaan Volley


        Resources res = getResources();

        stepView = view.findViewById(R.id.step_view);
        String[] stepsArray = res.getStringArray(R.array.steps);
        stepView.setSteps(Arrays.asList(stepsArray));

        btnBack = view.findViewById(R.id.btnBack);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager);

        viewPagerAdapter = new PencatatanKetelusuranViewPagerAdapter(getActivity());
        viewPager2.setAdapter(viewPagerAdapter);

        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                stepView.go(stepView.getCurrentStep() + 1, true);
            }
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

        return view;
    }



    // Rest of the existing code...
}
