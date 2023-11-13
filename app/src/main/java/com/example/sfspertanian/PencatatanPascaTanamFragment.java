package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class PencatatanPascaTanamFragment extends Fragment {
    private RelativeLayout cardCatatanKualitasPanen, cardMasaPanen;

    public PencatatanPascaTanamFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pencatatan_pasca_tanam, container, false);

        cardMasaPanen = view.findViewById(R.id.card_masa_panen);
        cardCatatanKualitasPanen = view.findViewById(R.id.card_catatan_kualitas_panen);

        cardMasaPanen.setOnClickListener(v->{
            Intent intent = new Intent(requireActivity(), MasaPanenActivity.class);
            startActivity(intent);
        });
        cardCatatanKualitasPanen.setOnClickListener(v->{
            Intent intent = new Intent(requireActivity(), RatingActivity.class);
            startActivity(intent);
        });
        return view;

    }



}
