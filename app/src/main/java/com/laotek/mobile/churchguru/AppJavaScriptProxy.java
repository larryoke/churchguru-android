package com.laotek.mobile.churchguru;

import android.app.Activity;
import android.os.Handler;
import android.webkit.JavascriptInterface;

/**
 * Created by larryoke on 08/12/2015.
 */
public class AppJavaScriptProxy {

    private Activity activity = null;

    public AppJavaScriptProxy(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void clearBrowserHistory() {
        ((MainActivity)activity).clearHistory();
    }

    @JavascriptInterface
    public void closeApp(){
        final boolean b = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 500);
    }

}
