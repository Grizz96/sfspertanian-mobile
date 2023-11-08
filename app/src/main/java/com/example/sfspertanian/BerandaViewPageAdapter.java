package com.example.sfspertanian;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BerandaViewPageAdapter extends FragmentStateAdapter {
    public BerandaViewPageAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch(position){
            case 0:
                return new literasiPraTanam();
            case 1:
                return new literasiTanam();
            case 2:
                return new literasiPascaTanam();
            default:
                return new literasiPraTanam();
        }
    }
    @Override
    public int getItemCount(){
        return 3;
    }
}
