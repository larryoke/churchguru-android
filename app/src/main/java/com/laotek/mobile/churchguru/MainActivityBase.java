package com.laotek.mobile.churchguru;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import static android.content.pm.ActivityInfo.*;

public abstract class MainActivityBase extends Activity {

    private static WebView   webView  = null;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "starting onCreate.");

        if (!DetectConnection.checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "No Internet. Please try later", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, 3000);

        } else {
            setContentView(R.layout.activity_main);

            webView = (WebView) findViewById(R.id.webview);

            initWebView();

            //initRegBroadcastReceiver();
        }
        Log.i(TAG, "ending onCreate.");
    }

    private void initWebView() {
        this.setRequestedOrientation (SCREEN_ORIENTATION_PORTRAIT);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.clearCache(true);
        webView.clearHistory();

        webView.addJavascriptInterface(new AppJavaScriptProxy(this), "androidAppProxy");
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.loadUrl(getURL());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    abstract String getURL();

    private class MyWebViewClient extends WebViewClient {
        private Activity activity = null;

        public MyWebViewClient(Activity activity){
            this.activity = activity;
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if ( url.indexOf("https://trinitychapel.churchg.com/mob") > -1 ||
                    url.indexOf("10.0.2.2/mob") > -1  ) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if (view.canGoBack()) {
                view.goBack();
            }
            String desc = error.getDescription().toString();
            Toast toast = Toast.makeText(getBaseContext(), desc,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
            toast.show();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (Consts.SERVER.equals("http://10.0.2.2")) {
                handler.proceed(); // Ignore SSL certificate errors
            }else{
                super.onReceivedSslError(view, handler, error);
            }
        }
    }


    public void clearHistory() {
        webView.clearHistory();
    }



    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void   registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
            Log.i(TAG, "Registered.");
        }
    }

}

