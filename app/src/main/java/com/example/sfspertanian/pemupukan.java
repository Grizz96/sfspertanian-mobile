package com.example.sfspertanian;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pemupukan extends AppCompatActivity {

    private static final String TAG = pemupukan.class.getSimpleName();
    private RequestQueue requestQueue;
    private TextView selectedIdPupukTextView;
    private Spinner jenisPemupukanSpinner, pilihPupukSpinner;
    private EditText deskripsiEditText, jumlahPemupukanEditText;
    SessionManager sessionManager;
    String idUser;
    String idSawah;

    private Button btnSimpan, btnBatal, tanggalpupuk;
    private List<String> idPupukList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemupukan);
        sessionManager = new SessionManager(this);
        idUser = sessionManager.getUserId();
        idSawah = sessionManager.getSawahId();
        requestQueue = Volley.newRequestQueue(this);

        jenisPemupukanSpinner = findViewById(R.id.JenisPemupukan);
        pilihPupukSpinner = findViewById(R.id.PilihPupuk);
        deskripsiEditText = findViewById(R.id.DeskripsiPemupukan);
        tanggalpupuk = findViewById(R.id.TanggalSemai);
        jumlahPemupukanEditText = findViewById(R.id.JumlahPemupukan);
        btnSimpan = findViewById(R.id.btnsimpan);
        btnBatal = findViewById(R.id.btnbatal);
        selectedIdPupukTextView = findViewById(R.id.id_pupuk);

        ArrayAdapter<CharSequence> jenisPemupukanAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.jenis_pemupukan_array,
                android.R.layout.simple_spinner_item
        );
        jenisPemupukanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisPemupukanSpinner.setAdapter(jenisPemupukanAdapter);

        ArrayAdapter<Integer> pupukIdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        pupukIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihPupukSpinner.setAdapter(pupukIdAdapter);

        pilihPupukSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPupukName = pilihPupukSpinner.getSelectedItem().toString();
                String selectedIdPupuk = getIdPupukByName(selectedPupukName);
                selectedIdPupukTextView.setText(selectedIdPupuk);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        fetchPupukData();

        tanggalpupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToServer();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle cancel action if needed
            }
        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(pemupukan.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        tanggalpupuk.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                        showTimePicker();
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(pemupukan.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tanggalpupuk.append(" " + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    private void fetchPupukData() {
        String url = Db_Contract.urlSpinnerPupuk;
        ArrayAdapter<String> pupukIdAdapter = new ArrayAdapter<>(pemupukan.this, android.R.layout.simple_spinner_item);
        pupukIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihPupukSpinner.setAdapter(pupukIdAdapter);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Response: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject pupukObject = response.getJSONObject(i);
                                String idPupuk = pupukObject.getString("id_pupuk");
                                String namaPupuk = pupukObject.getString("nama_pupuk");

                                idPupukList.add(idPupuk);
                                pupukIdAdapter.add(namaPupuk);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(pemupukan.this, "Error fetching pupuk data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void sendDataToServer() {
        String jenisPemupukan = jenisPemupukanSpinner.getSelectedItem().toString();
        String deskripsi = deskripsiEditText.getText().toString();
        String tanggal = tanggalpupuk.getText().toString();
        String jumlahPenggunaan = jumlahPemupukanEditText.getText().toString();
        String idpupuk = selectedIdPupukTextView.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlInsertPupuk,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        Toast.makeText(pemupukan.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(pemupukan.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("jenis_pemupukan", jenisPemupukan);
                params.put("deskripsi", deskripsi);
                params.put("tanggal", tanggal);
                params.put("jumlah_penggunaan", jumlahPenggunaan);
                params.put("id_user", idUser);
                params.put("id_pupuk", idpupuk);
                params.put("id_sawah", idSawah);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private String getIdPupukByName(String pupukName) {
        for (int i = 0; i < idPupukList.size(); i++) {
            if (pupukName.equals(pilihPupukSpinner.getItemAtPosition(i))) {
                return idPupukList.get(i);
            }
        }
        return "";
    }
}
