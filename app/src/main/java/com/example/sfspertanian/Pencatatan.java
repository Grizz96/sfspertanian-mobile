package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
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
    private StepView stepView;
    private TextView tesTextView;
    private PencatatanKetelusuranViewPagerAdapter viewPagerAdapter;
    SessionManager sessionManager;
    String idSawah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pencatatan_ketelusuran, container, false);

        spinnerselect = view.findViewById(R.id.spinnerselect);
        tesTextView = view.findViewById(R.id.detail);

        Resources res = getResources();

        stepView = view.findViewById(R.id.step_view);
        String[] stepsArray = res.getStringArray(R.array.steps);
        stepView.setSteps(Arrays.asList(stepsArray));

        btnBack = view.findViewById(R.id.btnBack);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager);

        viewPagerAdapter = new PencatatanKetelusuranViewPagerAdapter(requireActivity());
        viewPager2.setAdapter(viewPagerAdapter);

        stepView.setOnStepClickListener(step -> stepView.go(stepView.getCurrentStep() + 1, true));

        btnBack.setOnClickListener(v -> {
            requireActivity().getWindow().setExitTransition(new Fade());
            // Kembali ke PencatatanFragment
            Intent backIntent = new Intent(requireActivity(), MainActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(backIntent, options.toBundle());

            // Tunggu selama 2 detik sebelum menutup
            new Handler().postDelayed(() -> requireActivity().finish(), 2000);
        });
        sessionManager = new SessionManager(requireContext()); // Make sure your SessionManager accepts Context
        idSawah = sessionManager.getSawahId();
        spinnerselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                Sawah selectedSawah = (Sawah) parentView.getItemAtPosition(position);
                String selectedSawahId = selectedSawah.getIdSawah();
                String selectedSawahName = selectedSawah.getNamaSawah();

                // You can do something with the selected item, for example, display it in a TextView
                sessionManager.setSawahId(selectedSawahId);
                String storedSawahId = sessionManager.getSawahId();

// Display the stored Sawah ID in the TextView


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here if no item is selected
            }
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

        // Panggil fungsi untuk melakukan permintaan Volley
        fetchDataFromApi();

        return view;
    }

    private void fetchDataFromApi() {
        String url = "https://jejakpadi.com/app/Http/mobileController/get_data_map.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
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
