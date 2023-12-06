package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class literasiPraTanam extends Fragment {

    private RecyclerView recyclerView;
    private List<DataItem> dataItemList;
    private adapter_card_bibit adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_literasi_pra_tanam, container, false);

        // Initialize RecyclerView and adapter
        recyclerView = view.findViewById(R.id.recyclerView); // Replace with your actual RecyclerView ID
        dataItemList = new ArrayList<>();
        adapter = new adapter_card_bibit(dataItemList, new adapter_card_bibit.OnItemClickListener() {
            @Override
            public void onItemClick(String bibitName) {
                Intent intent = new Intent(requireContext(), KontenLiterasiActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        makeVolleyRequest();

        recyclerView.setAdapter(adapter);
        return view;
    }

    private void makeVolleyRequest() {
        String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/get_data_literasi_pra_tanam.php";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataItemList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String namaBibit = jsonObject.getString("judul");
                                String deskripsiSingkat = jsonObject.getString("tanggal");
                                String gambarPathMain = jsonObject.getString("gambar_path_main"); // Ambil path gambar

                                // Buat objek DataItem dengan atribut baru
                                DataItem dataItem = new DataItem(namaBibit, deskripsiSingkat, gambarPathMain);
                                dataItemList.add(dataItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(), "Error loading data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }
}
