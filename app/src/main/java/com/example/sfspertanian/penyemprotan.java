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
import com.android.volley.toolbox.JsonObjectRequest;
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

public class penyemprotan extends AppCompatActivity {

    private static final String TAG = penyemprotan.class.getSimpleName();
    private RequestQueue requestQueue;
    private TextView selectedIdPupukTextView;
    private Spinner jenisPemupukanSpinner, pilihPupukSpinner;
    private EditText deskripsiEditText, jumlahPemupukanEditText;
    SessionManager sessionManager;
    String idUser;
    String idSawah;
    private Button btnSimpan, btnBatal, tanggalpupuk;
    private List<SemprotanItem> semprotanList = new ArrayList<>();
    private ArrayAdapter<SemprotanItem> semprotanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyemprotan);

        requestQueue = Volley.newRequestQueue(this);

        sessionManager = new SessionManager(penyemprotan.this); // Assuming SessionManager requires a context
        idUser = sessionManager.getUserId();
        idSawah = sessionManager.getSawahId();
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
                R.array.jenis_penyemprotan_array,
                android.R.layout.simple_spinner_item
        );
        jenisPemupukanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisPemupukanSpinner.setAdapter(jenisPemupukanAdapter);

        semprotanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        semprotanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihPupukSpinner.setAdapter(semprotanAdapter);

        pilihPupukSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SemprotanItem selectedSemprotan = semprotanAdapter.getItem(position);
                String selectedSemprotanId = selectedSemprotan.getIdSemprotan();
                selectedIdPupukTextView.setText(selectedSemprotanId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        fetchSemprotanData();

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

        DatePickerDialog datePickerDialog = new DatePickerDialog(penyemprotan.this,
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(penyemprotan.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tanggalpupuk.append(" " + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    private void fetchSemprotanData() {
        String url = Db_Contract.urlSpinnerSemprot;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response: " + response.toString());
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject semprotanObject = dataArray.getJSONObject(i);
                                String idSemprotan = semprotanObject.getString("id_semprotan");
                                String namaSemprotan = semprotanObject.getString("nama_semprotan");

                                SemprotanItem semprotanItem = new SemprotanItem(idSemprotan, namaSemprotan);
                                semprotanList.add(semprotanItem);
                                semprotanAdapter.add(semprotanItem);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(penyemprotan.this, "Error parsing semprotan data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(penyemprotan.this, "Error fetching semprotan data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void sendDataToServer() {
        String jenisPemupukan = jenisPemupukanSpinner.getSelectedItem().toString();
        String deskripsi = deskripsiEditText.getText().toString();
        String tanggal = tanggalpupuk.getText().toString();
        String jumlahPenggunaan = jumlahPemupukanEditText.getText().toString();
        String idpupuk = selectedIdPupukTextView.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlInsertSemprot,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response);
                        Toast.makeText(penyemprotan.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(penyemprotan.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("jenis_penyemprotan", jenisPemupukan);
                params.put("deskripsi", deskripsi);
                params.put("tanggal", tanggal);
                params.put("jumlah_penggunaan", jumlahPenggunaan);
                params.put("id_user", idUser);
                params.put("id_semprotan", idpupuk);
                params.put("id_sawah", idSawah);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private String getIdPupukByName(String pupukName) {
        for (int i = 0; i < semprotanList.size(); i++) {
            if (pupukName.equals(semprotanList.get(i).getNamaSemprotan())) {
                return semprotanList.get(i).getIdSemprotan();
            }
        }
        return "";
    }
}
