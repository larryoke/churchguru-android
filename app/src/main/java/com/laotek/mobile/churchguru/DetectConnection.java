package com.laotek.mobile.churchguru;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by larryoke on 08/12/2015.
 */
public class DetectConnection {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
//            try {
//                URL url = new URL(MainActivity.URL);
//                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
//                urlc.setRequestProperty("User-Agent", "test");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(1000); // mTimeout is in seconds
//                urlc.connect();
//                if (urlc.getResponseCode() != 200) {
//                    return false;
//                }
//            }catch (Exception e){
//                return false;
//            }
            return true;
        } else {
            return false;
        }
    }
}
