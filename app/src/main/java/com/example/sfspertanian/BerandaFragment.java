package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class BerandaFragment extends Fragment {
    private final String CITY = "jenggawah,jember";
    private final String API = "77df1d2c457c45f7ec127f27a527fbbb"; // Use API key
    private View rootView;  // Change 'view' to 'rootView'

    public BerandaFragment() {
        // Required empty public constructor
    }

    // Perbaikan: Deklarasikan button btnPesan
    private ImageButton btnPesan;

    private TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_beranda, container, false);  // Change 'View view' to 'rootView'

        // Perbaikan: Menginisialisasi btnPesan dengan ID dari layout
        btnPesan = rootView.findViewById(R.id.btnPesan);

        // Perbaikan: Menggunakan requireActivity() untuk mendapatkan konteks activity
        btnPesan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), pesanActivity.class); // Perbaikan: Nama kelas diawali huruf kapital
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), v, "smart_animate");
            startActivity(intent, options.toBundle());
        });

        new WeatherTask().execute();

        return rootView;  // Change 'return view' to 'return rootView'
    }

    private class WeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /* Showing the ProgressBar, Making the main design GONE */
            rootView.findViewById(R.id.loader).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.mainContainer).setVisibility(View.GONE);
            rootView.findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            String response;
            try {
                // Open the URL connection and get the input stream
                InputStream inputStream = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API).openStream();

                // Read the input stream and convert it to a string
                response = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();

                // Close the input stream
                inputStream.close();
            } catch (IOException e) {
                response = null;
            }
            return response;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                /* Extracting JSON returns from the API */
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                long sunrise = sys.getLong("sunrise");
                long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                /* Populating extracted data into our views */
                ((TextView) rootView.findViewById(R.id.address)).setText(address);
                ((TextView) rootView.findViewById(R.id.updated_at)).setText(updatedAtText);
                ((TextView) rootView.findViewById(R.id.status)).setText(weatherDescription.toUpperCase());
                ((TextView) rootView.findViewById(R.id.temp)).setText(temp);
                ((TextView) rootView.findViewById(R.id.temp_min)).setText(tempMin);
                ((TextView) rootView.findViewById(R.id.temp_max)).setText(tempMax);
                ((TextView) rootView.findViewById(R.id.sunrise)).setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                ((TextView) rootView.findViewById(R.id.sunset)).setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                ((TextView) rootView.findViewById(R.id.wind)).setText(windSpeed);
                ((TextView) rootView.findViewById(R.id.pressure)).setText(pressure);
                ((TextView) rootView.findViewById(R.id.humidity)).setText(humidity);

                /* Views populated, Hiding the loader, Showing the main design */
                rootView.findViewById(R.id.loader).setVisibility(View.GONE);
                rootView.findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);

            } catch (Exception e) {
                Log.e("WeatherTask", "Error during execution", e);
                rootView.findViewById(R.id.loader).setVisibility(View.GONE);
                rootView.findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }
    }
}
