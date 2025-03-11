package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.sourabh.daytradingtool.Utils.DatabaseUtils;

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

                    try{

                        //Check first onboarding
                        SharedPreferences prefs = getSharedPreferences(DatabaseUtils.ONBOARDING_DETAIL, MODE_PRIVATE);

                        if(prefs.getString(DatabaseUtils.onboardingKey, DatabaseUtils.onboardingDefaultStr).equals(DatabaseUtils.onboardingDefaultStr)){

                            Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                            startActivity(intent);
                            // close this activity
                            finish();

                        }else if(prefs.getString(DatabaseUtils.onboardingKey, DatabaseUtils.onboardingDefaultStr).equals(DatabaseUtils.onboardingvalue)){
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            // close this activity
                            finish();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
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