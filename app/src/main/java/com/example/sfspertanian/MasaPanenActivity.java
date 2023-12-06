package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MasaPanenActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnPanen;
    private Button timePickerButton;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply enter and exit transitions
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa_panen);

        btnPanen = findViewById(R.id.btnPanen);
        btnBack = findViewById(R.id.btnBack);
        timePickerButton = findViewById(R.id.timePickerButton);

        btnPanen.setOnClickListener(v -> {
            startNextActivity(v);
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
        Intent intent = new Intent(this, PencatatanKetelusuranActivity.class);
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
            showTimePicker();
        };

        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            updateButtonText();
        };

        new TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this)).show();
    }

    private void updateButtonText() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String buttonText = dateTimeFormat.format(calendar.getTime());
        timePickerButton.setText(buttonText);
    }
}
