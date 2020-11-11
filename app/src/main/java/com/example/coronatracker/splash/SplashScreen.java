package com.example.coronatracker.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.coronatracker.MainActivity;
import com.example.coronatracker.R;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentSplash = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(intentSplash);
                SplashScreen.this.finish();
            }
        }, 2000); // time
    }
}
