package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class PencatatanPraTanamFragment extends Fragment {

    RelativeLayout cardPilihBibit, cardPersemaian;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pencatatan_pra_tanam, container, false);

        cardPilihBibit = view.findViewById(R.id.card_pilih_padi);
        cardPersemaian = view.findViewById(R.id.card_persemaian);

        cardPilihBibit.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihBibitActivity.class); // Perbaikan: Nama kelas diawali huruf kapitalmed
            startActivity(intent);
        });
        cardPersemaian.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SemaiMainActivity.class);
            // Perbaikan: Nama kelas diawali huruf kapitalmed
            
            startActivity(intent);
        });
        return view;

    }

}
