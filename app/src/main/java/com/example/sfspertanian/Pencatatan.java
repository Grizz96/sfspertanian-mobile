package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.shuhart.stepview.StepView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Pencatatan extends Fragment {

    private ImageButton btnBack;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Spinner spinnerselect;
    private String[] steps;
    private StepView stepView;
    private TextView tesTextView;
    private PencatatanKetelusuranViewPagerAdapter viewPagerAdapter;
    SessionManager sessionManager;
    String idSawah,idUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pencatatan_ketelusuran, container, false);
        sessionManager = new SessionManager(requireContext()); // Make sure your SessionManager accepts Context
        idSawah = sessionManager.getSawahId();
        idUser = sessionManager.getUserId();

        spinnerselect = view.findViewById(R.id.spinnerselect);
        tesTextView = view.findViewById(R.id.detail);

        Resources res = getResources();

        spinnerselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                Sawah selectedSawah = (Sawah) parentView.getItemAtPosition(position);
                String selectedSawahId = selectedSawah.getIdSawah();

                // You can do something with the selected item, for example, display it in a TextView
                sessionManager.setSawahId(selectedSawahId);
                fetchDurationDataFromApi(sessionManager.getSawahId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here if no item is selected
            }
        });
        // Panggil fungsi untuk melakukan permintaan Volley
        fetchDataFromApi();


        stepView = view.findViewById(R.id.step_view);
        // Initialize the steps array
        steps = new String[5];
        fetchDurationDataFromApi(idSawah);



        btnBack = view.findViewById(R.id.btnBack);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager);

        viewPagerAdapter = new PencatatanKetelusuranViewPagerAdapter(requireActivity());
        viewPager2.setAdapter(viewPagerAdapter);

        btnBack.setOnClickListener(v -> {
            requireActivity().getWindow().setExitTransition(new Fade());
            // Kembali ke PencatatanFragment
            Intent backIntent = new Intent(requireActivity(), MainActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(backIntent, options.toBundle());

            // Tunggu selama 2 detik sebelum menutup
            new Handler().postDelayed(() -> requireActivity().finish(), 2000);
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        return view;
    }

    private void fetchDataFromApi() {
        String apiUrl = Db_Contract.urlGetDataMap+idUser;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    List<Sawah> sawahList = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Sawah sawah = new Sawah();
                            sawah.setIdSawah(jsonObject.getString("id_sawah"));
                            sawah.setNamaSawah(jsonObject.getString("nama_sawah"));

                            // Set other properties if needed

                            sawahList.add(sawah);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Set data to Spinner
                    ArrayAdapter<Sawah> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, sawahList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Replace 'yourSpinner' with your actual Spinner reference
                    spinnerselect.setAdapter(adapter);
                },
                error -> {
                    // Handle error
                    error.printStackTrace();
                });

        Volley.newRequestQueue(requireActivity()).add(jsonArrayRequest);
    }
    private void fetchDurationDataFromApi(String id) {
        String durationUrl = "https://jejakpadi-develop.000webhostapp.com/mobileController/get_data_kalender.php?id=" + id;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, durationUrl, null,
                response -> {
                    try {
                        // Retrieve the duration data from the API response
                        String durasiPenanaman = response.getString("durasiPenanaman");
                        String durasiAnakan = response.getString("durasiAnakan");
                        String durasiBunting = response.getString("durasiBunting");
                        String durasiPemasakan = response.getString("durasiPemasakan");
                        String durasiPanen = response.getString("durasiPanen");


                        // Add duration data to the list
                        String fixDurasiPenanaman = "Penanaman\n" + durasiPenanaman;
                        String fixDurasiAnakan = "Anakan\n" + durasiAnakan;
                        String fixDurasiBunting = "Bunting\n" + durasiBunting;
                        String fixDurasiPemasakan = "Pemasakan\n" + durasiPemasakan;
                        String fixDurasiPanen = "Panen\n" + durasiPanen;

                        steps = new String[5];

                        steps[0] = fixDurasiPenanaman;
                        steps[1] = fixDurasiAnakan;
                        steps[2] = fixDurasiBunting;
                        steps[3] = fixDurasiPemasakan;
                        steps[4] = fixDurasiPanen;

                        // Set the steps in StepView
                        stepView.setSteps(Arrays.asList(steps));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    error.printStackTrace();
                });

        Volley.newRequestQueue(requireActivity()).add(jsonObjectRequest);
    }


    private int getCurrentStepBasedOnDate(String tanggalTanam, String[] stepsArray) {
        if (tanggalTanam != null && !tanggalTanam.isEmpty()) {
            int plantingStepIndex = 0;
            int cultivationStepIndex = 1;
            int harvestStepIndex = 2;

            if (isBeforeDate(tanggalTanam, "2023-04-01")) {
                return plantingStepIndex;
            } else if (isBeforeDate(tanggalTanam, "2023-07-01")) {
                return cultivationStepIndex;
            } else {
                return harvestStepIndex;
            }
        }

        return 0;
    }

    private boolean isBeforeDate(String currentDate, String targetDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date current = sdf.parse(currentDate);
            Date target = sdf.parse(targetDate);

            return current.before(target);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
