package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DataSawahFragment extends Fragment {
    ImageButton btnPesan,btnDetail;
    FloatingActionButton btnTambah;
    private RecyclerView recyclerView;
    private adapter_card_location locationAdapter;
    private List<LocationItem> locationList;
    private SessionManager sessionManager;
    private String userId;


    public DataSawahFragment() {
    }
    private Context context;
    private DataSawahFragment dataSawahFragment;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_data_sawah, container, false);
        sessionManager=new SessionManager(requireContext());
        userId = sessionManager.getUserId();


        // Perbaikan: Menginisialisasi btnPesan dengan ID dari layout
        btnPesan = view.findViewById(R.id.btnPesan);
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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        locationList = new ArrayList<>();
        locationAdapter = new adapter_card_location(requireContext(), locationList);
        recyclerView.setAdapter(locationAdapter);

        fetchDataFromApi(requireContext());

        return view;
    }

    public void fetchDataFromApi(Context context) {
        String apiUrl = Db_Contract.urlGetDataMap+userId;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Inisialisasi locationList jika null
                        if (locationList == null) {
                            locationList = new ArrayList<>();
                        }

                        if (!locationList.isEmpty()) {
                            locationList.clear();
                        }

                        locationList.addAll(parseJsonArray(response));

                        // Pastikan bahwa locationAdapter telah diinisialisasi sebelum digunakan
                        if (locationAdapter != null) {
                            // Update the adapter with the new data
                            locationAdapter.setData(locationList);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        // You can show an error message or log the issue
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private List<LocationItem> parseJsonArray(JSONArray jsonArray) {
        List<LocationItem> locationItems = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int idSawah = jsonObject.getInt("id_sawah");
                String namaSawah = jsonObject.getString("nama_sawah");
                String lokasiSawah = jsonObject.getString("lokasi_sawah");
                String luasSawah = jsonObject.getString("luas_sawah");
                String deskripsi = jsonObject.getString("deskripsi");
                int idUser = jsonObject.getInt("id_user");
                String startDate = jsonObject.getString("created_at");

                // Create LocationItem with all fields
                LocationItem locationItem = new LocationItem(idSawah, namaSawah, lokasiSawah,
                        luasSawah, deskripsi, idUser, startDate);
                locationItem.setIdSawah(idSawah);
                locationItems.add(locationItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locationItems;
    }

}
