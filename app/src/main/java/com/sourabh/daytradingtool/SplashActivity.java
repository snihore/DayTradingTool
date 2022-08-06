package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //This method will be executed once the timer is over
                    // Start your app main activity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            }, 1000);

        }catch (Exception e){
            e.printStackTrace();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}