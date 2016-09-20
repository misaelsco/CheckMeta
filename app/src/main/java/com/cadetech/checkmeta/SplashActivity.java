package com.cadetech.checkmeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    static final int TIME_SPLASH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(splash);
                SplashActivity.this.finish();
            }
        }, TIME_SPLASH);
    }
}
