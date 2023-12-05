package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class PencatatanTanamFragment extends Fragment {
    RelativeLayout cardPilihPupuk, cardInfoPemupukan, cardpersemaian, card_penanggulanahan_hama;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pencatatan_tanam, container, false);

        cardPilihPupuk = view.findViewById(R.id.card_pilih_pupuk);
        cardPilihPupuk = view.findViewById(R.id.card_persemaian);
        cardPilihPupuk = view.findViewById(R.id.card_penyemprotan);
        cardPilihPupuk = view.findViewById(R.id.card_penanggulanahan_hama);

        cardPilihPupuk.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihPupukActivity.class);
            startActivity(intent);
        });
        cardpersemaian.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pemupukan.class);
            startActivity(intent);
        });
        cardPilihPupuk.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihPupukActivity.class);
            startActivity(intent);
        });
        card_penanggulanahan_hama.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), penyemprotan.class);
            startActivity(intent);
        });

        return view;
    }

}
