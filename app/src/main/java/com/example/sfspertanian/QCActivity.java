package com.example.sfspertanian;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QCActivity extends AppCompatActivity {
    private WebView webView;
    SessionManager sessionManager;
    String idUser;
    Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qc);

        // Get id_sawah from the Intent
        Intent intent = getIntent();
        String id_sawah = intent.getStringExtra("id_sawah");

        // Set up WebView settings
        webView = findViewById(R.id.webView1);
        btnKembali = findViewById(R.id.btnBack);

        btnKembali.setText("Kembali"+id_sawah);

        btnKembali.setOnClickListener(v -> {
            finish();
        });
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

        // Construct the URL with the id_sawah
        String url = "https://jejakpadi.com/resources/views/scanQrCode/index.php?id=" + id_sawah;

        // Load the URL in the WebView
        webView.loadUrl(url);
    }
}
