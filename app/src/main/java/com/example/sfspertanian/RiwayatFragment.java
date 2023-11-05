package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
public class RiwayatFragment extends Fragment {

    ImageButton btnPesan;
    public RiwayatFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        // Perbaikan: Menginisialisasi btnPesan dengan ID dari layout
        btnPesan = view.findViewById(R.id.btnPesan);

        btnPesan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pesanActivity.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(intent, options.toBundle());

        });

        return view;
    }
}