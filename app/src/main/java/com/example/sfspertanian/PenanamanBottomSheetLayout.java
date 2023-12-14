package com.example.sfspertanian;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PenanamanBottomSheetLayout extends BottomSheetDialogFragment {
    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String url = "https://jejakpadi-develop.000webhostapp.com/mobileController/update_tanggal_tanam.php";
    private Button timePickerButton;
    SessionManager sessionManager;

    String idSawah;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.penanaman_bottom_sheet_layout, container, false);
        initializeViews(view);

        // Move the following lines inside onCreateView
        sessionManager = new SessionManager(requireContext()); // Assuming SessionManager requires a context
        idSawah = sessionManager.getSawahId();

        return view;
    }

    public interface OnDataAddedListener {
        void onDataAdded();
    }

    private OnDataAddedListener onDataAddedListener;

    public void setOnDataAddedListener(OnDataAddedListener listener) {
        this.onDataAddedListener = listener;
    }

    private void initializeViews(View view) {
        timePickerButton = view.findViewById(R.id.timePickerButton);

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        view.findViewById(R.id.btnSimpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateString = dateFormat.format(calendar.getTime());

                RequestQueue queue = Volley.newRequestQueue(requireContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("Data berhasil diupdate")) {
                                    showToast("Data berhasil diupdate");

                                    if (onDataAddedListener != null) {
                                        onDataAddedListener.onDataAdded();
                                    }

                                    // Dismiss the bottom sheet
                                    dismiss();
                                } else {
                                    showToast("Data update failed. Response: " + response);
                                    Log.e("DataUpdate", "Error response from server: " + response);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                showToast("Error: " + error.getMessage());
                                Log.e("Volley Error", "Error: " + error.getMessage(), error);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("tanggal_tanam", dateString); // Update to match the key from your PHP script
                        params.put("id_sawah", idSawah); // Update to match the key from your PHP script

                        return params;
                    }
                };

                queue.add(stringRequest);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateButtonText();
            }
        };

        new DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateButtonText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String buttonText = dateFormat.format(calendar.getTime());
        timePickerButton.setText(buttonText);
    }
}
