package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class PencatatanTanamFragment extends Fragment {
    RelativeLayout cardPilihPupuk, cardInfoPemupukan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pencatatan_tanam, container, false);

        cardPilihPupuk = view.findViewById(R.id.card_pilih_pupuk);
        cardPilihPupuk.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihPupukActivity.class);
            startActivity(intent);
        });


        return view;
    }

}
