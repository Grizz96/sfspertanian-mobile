package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PencatatanFragment extends Fragment {

    ImageButton btnPesan,btnDetail;
    FloatingActionButton btnTambah;
    public PencatatanFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pencatatan, container, false);

        // Perbaikan: Menginisialisasi btnPesan dengan ID dari layout
        btnPesan = view.findViewById(R.id.btnPesan);
        btnDetail = view.findViewById(R.id.detailLokasi1);
        btnTambah = view.findViewById(R.id.btntambahmap);


        btnTambah.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MapsFragment.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(intent, options.toBundle());


        });

        btnPesan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pesanActivity.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(intent, options.toBundle());


        });

        btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), PencatatanKetelusuranActivity.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "dissolve_ease_out");
            startActivity(intent, options.toBundle());


        });

        return view;
    }
}
