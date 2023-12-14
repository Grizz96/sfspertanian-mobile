package com.example.sfspertanian;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsFragment extends AppCompatActivity implements OnMapReadyCallback {
    private LocationManager locationManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 300;
    private RequestQueue requestQueue;
    private GoogleMap mMap;
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    private Button pilihSawah;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    public static String lokasiSawah;
    private boolean oke = false;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        requestQueue = Volley.newRequestQueue(this);

        floatingActionButton = findViewById(R.id.btnGetLocation);
        pilihSawah = findViewById(R.id.btnPilihSawah);

        floatingActionButton.setOnClickListener(v -> {
            oke = true;
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                // Initialize location manager
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    mMap.clear();
                    LatLng lokasiSekarang = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(lokasiSekarang).title("Lokasi Sekarang"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiSekarang,20));
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                }, LOCATION_PERMISSION_REQUEST_CODE);
            }
        });

        pilihSawah.setOnClickListener(v -> {
            showBottomSheet();
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 300);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        oke = true;

        // Add a marker at a specific location (latitude and longitude)
        LatLng jenggawah = new LatLng(-8.168577, -246.296838);
        mMap.addMarker(new MarkerOptions().position(jenggawah).title("Marker in jenggawah"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jenggawah, 13));
        // Set latitude and longitude
        latitude = String.valueOf(-8.168577);
        longitude = String.valueOf(-246.296838);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Clear the map
                mMap.clear();

                // Add a marker at the clicked location
                mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi Anda Pilih"));

                // Move the camera to the clicked location
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // Set latitude and longitude
                latitude = String.valueOf(latLng.latitude);
                longitude = String.valueOf(latLng.longitude);
            }
        });
    }
    private void showBottomSheet() {
        CreateSawahBottomSheetLayout createSawahBottomSheet = new CreateSawahBottomSheetLayout();
        createSawahBottomSheet.show(getSupportFragmentManager(), createSawahBottomSheet.getTag());
    }
    public void finishLayout() {
        finish();
    }


}