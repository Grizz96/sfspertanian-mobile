package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;

public class BerandaFragment extends Fragment {

    public BerandaFragment() {
        // Diperlukan konstruktor publik kosong
    }

    // Perbaikan: Deklarasikan button btnPesan
    Button btnPesan;

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    BerandaViewPageAdapter berandaViewPageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager);
        berandaViewPageAdapter = new BerandaViewPageAdapter(requireActivity());
        viewPager2.setAdapter(berandaViewPageAdapter);

        // Perbaikan: Menginisialisasi btnPesan dengan ID dari layout
        btnPesan = view.findViewById(R.id.btnPesan);

        // Perbaikan: Menggunakan requireActivity() untuk mendapatkan konteks activity
        btnPesan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pesanActivity.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(intent, options.toBundle());

            // Tunggu selama 2 detik sebelum menutup
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Perbaikan: Gunakan requireActivity() untuk mengakhiri activity
                    requireActivity().finish();
                }
            }, 2000);
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
}
