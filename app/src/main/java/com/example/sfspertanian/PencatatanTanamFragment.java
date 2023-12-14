package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public class PencatatanTanamFragment extends Fragment {
    RelativeLayout cardPenanaman, cardPilihPupuk, cardInfoPemupukan, cardPersemaian, cardPenanggulanganHama;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pencatatan_tanam, container, false);

        cardPenanaman = view.findViewById(R.id.card_tanam);
        cardPilihPupuk = view.findViewById(R.id.card_pilih_pupuk);
        cardPersemaian = view.findViewById(R.id.card_persemaian);
        cardInfoPemupukan = view.findViewById(R.id.card_penyemprotan);
        cardPenanggulanganHama = view.findViewById(R.id.card_penanggulanahan_hama);

        cardPenanaman.setOnClickListener(v->{
            showBottomSheet();
        });

        cardPilihPupuk.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihPupukActivity.class);
            startActivity(intent);
        });
        cardPersemaian.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pemupukan.class); // Make sure to use the correct activity name
            startActivity(intent);
        });
        cardInfoPemupukan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PilihSemprotActivity.class);
            startActivity(intent);
        });
        cardPenanggulanganHama.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), penyemprotan.class); // Make sure to use the correct activity name
            startActivity(intent);
        });

        return view;
    }
    private void showBottomSheet() {
        PenanamanBottomSheetLayout penanamanBottomSheetLayout = new PenanamanBottomSheetLayout();
        penanamanBottomSheetLayout.show(requireActivity().getSupportFragmentManager(), penanamanBottomSheetLayout.getTag());
    }
}
