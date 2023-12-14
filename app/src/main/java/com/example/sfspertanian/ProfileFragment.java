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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {
    RelativeLayout btnPesan;
    RelativeLayout btnSignOut;
    SessionManager sessionManager;
    String idUser;
    TextView etName;

    Button btnEditProfile;
    public ProfileFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi sessionManager
        sessionManager = new SessionManager(requireContext());

        // Perbaikan: Menginisialisasi btnPesan dengan ID dari layout
        btnPesan = view.findViewById(R.id.btnPesann);
        btnEditProfile = view.findViewById(R.id.btnEditProfil);
        btnSignOut = view.findViewById(R.id.btnKeluar);
        etName = view.findViewById(R.id.tvName);


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
        sessionManager = new SessionManager(requireContext());
        idUser = sessionManager.getUserId();
        readProfil();
        return view;
    }
    private void readProfil() {
        String url = Db_Contract.urlGetUserData+ "?id_user=" + idUser;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response
                            String namaDepan = response.getString("nama_depan");
                            String namaBelakang = response.getString("nama_belakang");
                            String alamat = response.getString("alamat");
                            String email = response.getString("email");
                            String noHP = response.getString("no_handphone");
                            String ttl = response.getString("tanggal_lahir");

                            idUser= response.getString("id_user");
                            // Set the data to the respective EditText fields
                            String nama = namaDepan+" "+namaBelakang;
                            etName.setText(nama);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(requireContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(requireContext(), "Error fetching data"+idUser, Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(jsonObjectRequest);
    }
}
