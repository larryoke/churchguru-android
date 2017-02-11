package com.laotek.mobile.churchguru;

import android.os.Build;

public class NoticesAndEventsEntryPointActivity extends MainActivityBase {

    @Override
    String getURL() {
        if(Build.PRODUCT.matches(".*_?sdk_?.*")){
            return  "http://10.0.2.2/mobile#MobileHomePlace:NoticesAndEvents";
        }else{
            return  "https://trinitychapel.churchg.com/mobile#MobileHomePlace:NoticesAndEvents";
        }
    }


}

