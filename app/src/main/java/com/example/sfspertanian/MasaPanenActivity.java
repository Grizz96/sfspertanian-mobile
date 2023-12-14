package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MasaPanenActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText jumlah;
    private Button btnPanen;
    private Button timePickerButton;
    private Calendar calendar = Calendar.getInstance();
    private RequestQueue requestQueue;
    SessionManager sessionManager;
    String idUser;
    String idSawah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply enter and exit transitions
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa_panen);

        // Initialize views and variables
        jumlah = findViewById(R.id.jumlahpanen);
        sessionManager = new SessionManager(MasaPanenActivity.this);
        idUser = sessionManager.getUserId();
        idSawah = sessionManager.getSawahId();

        btnPanen = findViewById(R.id.btnPanen);
        btnBack = findViewById(R.id.btnBack);
        timePickerButton = findViewById(R.id.timePickerButton);
        requestQueue = Volley.newRequestQueue(this);

        // Set click listeners
        btnPanen.setOnClickListener(v -> {
            sendPanenDataToServer();
        });

        btnBack.setOnClickListener(v -> {
            startNextActivity(v);
        });

        timePickerButton.setOnClickListener(v -> {
            showDatePicker();
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startNextActivity(View v) {
        getWindow().setExitTransition(new Fade());
        Intent intent = new Intent(this, Pencatatan.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "smart_animate");
        startActivity(intent, options.toBundle());

        // Wait for 2 seconds before finishing
        new Handler().postDelayed(() -> finish(), 2000);
    }

    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateButtonText();
        };

        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateButtonText() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String buttonText = dateTimeFormat.format(calendar.getTime());
        timePickerButton.setText(buttonText);
    }

    private void sendPanenDataToServer() {
        // Replace the URL with the actual URL of your PHP script
        String url = Db_Contract.urlInsertPanen;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle the server response here
                    showToast("Data sent successfully");
                },
                error -> {
                    // Handle the error here
                    showToast("Error sending data to server: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("jumlah_panen", jumlah.getText().toString());
                params.put("tanggal_panen", getFormattedDateTime());
                params.put("id_sawah", idSawah);
                params.put("id_user", idUser);
                return params;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }

    private String getFormattedDateTime() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateTimeFormat.format(calendar.getTime());
    }
}
