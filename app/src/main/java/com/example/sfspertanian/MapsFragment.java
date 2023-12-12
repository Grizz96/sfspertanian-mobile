package com.example.sfspertanian;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class MapsFragment extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private WebView webView;
    private Button pilihSawah;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    public static String lokasiSawah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        requestQueue = Volley.newRequestQueue(this);
        webView = findViewById(R.id.webview);

        pilihSawah = findViewById(R.id.btnPilihSawah);

        // Set up WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setGeolocationEnabled(true);

        // Enable zoom controls if needed
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // Enable WebView debugging
        WebView.setWebContentsDebuggingEnabled(true);

        // Set up WebViewClient to handle page navigation
        webView.setWebViewClient(new WebViewClient());

        // Set up WebChromeClient to handle JavaScript alerts, etc.
        webView.setWebChromeClient(new WebChromeClient());

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Add JavascriptInterface to communicate between WebView and Android
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        // Load the Leaflet map HTML
        webView.loadUrl("file:///android_asset/leaflet_map.html");

        // Set up action when the FAB is clicked
        FloatingActionButton fabGetLocation = findViewById(R.id.btnGetLocation);
        fabGetLocation.setOnClickListener(v -> {
            webView.loadUrl("javascript:getCurrentLocation()");
        });

        pilihSawah.setOnClickListener(v->{
            showBottomSheet();
        });



    }
    private void showBottomSheet() {
        CreateSawahBottomSheetLayout createSawahBottomSheet = new CreateSawahBottomSheetLayout();
        createSawahBottomSheet.show(getSupportFragmentManager(), createSawahBottomSheet.getTag());
    }
    // Create a JavascriptInterface class
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void openForm(String latitude, String longitude) {
            lokasiSawah = "Latitude: " + latitude + ", Longitude: " + longitude;
            Toast.makeText(mContext, lokasiSawah, Toast.LENGTH_SHORT).show();
        }
    }
}