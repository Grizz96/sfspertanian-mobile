package com.example.sfspertanian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MapsFragment extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        webView = findViewById(R.id.webview);

        // Set up WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // Enable zoom controls if needed
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // Enable WebView debugging
        WebView.setWebContentsDebuggingEnabled(true);

        // Set up WebViewClient to handle page navigation
        webView.setWebViewClient(new WebViewClient());

        // Set up WebChromeClient to handle JavaScript alerts, etc.
        webView.setWebChromeClient(new WebChromeClient());

        // Add JavaScript interface for communication between WebView and Android
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        // Load the Leaflet map HTML
        webView.loadUrl("file:///android_asset/leaflet_map.html");
    }

    // Interface to handle JavaScript calls from WebView
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context context) {
            mContext = context;
        }

        // Method to be called from JavaScript to open the form activity
        @JavascriptInterface
        public void openForm(double latitude, double longitude) {
            // Create an Intent to open the form activity with coordinates
            Intent intent = new Intent(mContext, login_email.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        }
    }
}
