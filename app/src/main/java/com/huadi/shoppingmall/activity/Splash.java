package com.huadi.shoppingmall.activity;

import com.facebook.stetho.Stetho;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Splash extends Activity {
    boolean isFirstIn = false;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final long SPLASH_DELAY_MILLIS = 3000;
    public static final String SHAREDPREFERENCES_NAME = "first_pref";
    public static final String IS_FIRST_IN = "isFirstIn";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.splash_mimile);
        init();


    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        isFirstIn = preferences.getBoolean(IS_FIRST_IN, true);
        if (isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);

        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        }
    }

    private void goHome() {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        Splash.this.startActivity(intent);
        Splash.this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(Splash.this, Welcome.class);
        Splash.this.startActivity(intent);
        Splash.this.finish();
    }


}
