package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class PencatatanTanamFragment extends Fragment {
    RelativeLayout cardPilihPupuk, cardInfoPemupukan, cardPersemaian, cardPenanggulanganHama;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pencatatan_tanam, container, false);

        cardPilihPupuk = view.findViewById(R.id.card_pilih_pupuk);
        cardPersemaian = view.findViewById(R.id.card_persemaian);
        cardInfoPemupukan = view.findViewById(R.id.card_penyemprotan);
        cardPenanggulanganHama = view.findViewById(R.id.card_penanggulanahan_hama);

        cardPilihPupuk.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihPupukActivity.class);
            startActivity(intent);
        });
        cardPersemaian.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pemupukan.class); // Make sure to use the correct activity name
            startActivity(intent);
        });
        cardInfoPemupukan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihPupukActivity.class);
            startActivity(intent);
        });
        cardPenanggulanganHama.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), penyemprotan.class); // Make sure to use the correct activity name
            startActivity(intent);
        });

        return view;
    }
}
