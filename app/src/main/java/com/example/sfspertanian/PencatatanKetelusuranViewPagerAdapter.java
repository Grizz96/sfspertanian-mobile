package com.example.sfspertanian;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PencatatanKetelusuranViewPagerAdapter extends FragmentStateAdapter {

    public PencatatanKetelusuranViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PencatatanPraTanamFragment();
            case 1:
                return new PencatatanTanamFragment();
            case 2:
                return new PencatatanPascaTanamFragment();
            default:
                return new PencatatanPraTanamFragment(); // Fragment default
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Jumlah tab
    }
}
