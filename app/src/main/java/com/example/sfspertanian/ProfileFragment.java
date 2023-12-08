package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    RelativeLayout btnPesan;
    RelativeLayout btnSignOut;
    SessionManager sessionManager;

    Button btnEditProfile;
    public ProfileFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Perbaikan: Menginisialisasi btnPesan dengan ID dari layout
        btnPesan = view.findViewById(R.id.btnPesann);
        btnEditProfile = view.findViewById(R.id.btnEditProfil);
        btnSignOut = view.findViewById(R.id.btnKeluar);

        btnPesan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pesanActivity.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(intent, options.toBundle());

        });
        btnEditProfile.setOnClickListener(v->{
            Intent intent = new Intent(requireActivity(),EditProfilActivity.class);
            startActivity(intent);
        });
        btnSignOut.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), GetStartedMainActivity.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(intent, options.toBundle());
            sessionManager.clearSession();
        });



        return view;
    }
}
