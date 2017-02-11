package com.laotek.mobile.churchguru;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {


    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        LinearLayout currentLayout =
                (LinearLayout) findViewById(R.id.splashMain);
        currentLayout.setBackgroundColor(Color.WHITE);

        ImageView iv = (ImageView)findViewById(R.id.splashImage);
        iv.setBackgroundColor(Color.WHITE);

//        TextView tv = (TextView)findViewById(R.id.splashName);
//        SpannableString spanString = new SpannableString("Trinity Chapel");
//        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
//        spanString.setSpan(new RelativeSizeSpan(2f), 0, 14, 0);
//        tv.setText(spanString);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
