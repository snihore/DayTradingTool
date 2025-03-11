package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourabh.daytradingtool.UserInterface.ViewPageAdapter;
import com.sourabh.daytradingtool.Utils.DatabaseUtils;

public class OnboardingActivity extends AppCompatActivity {

    private ImageView skipBtn;
    private Button nextBtn;
    private ViewPager mslideViewPager;
    private LinearLayout mDotLayout;

    private ViewPageAdapter viewPageAdapter;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        intiViews();

        setUpIndicator(0);
        mslideViewPager.addOnPageChangeListener(viewListerner);
    }

    private void intiViews() {
        skipBtn = (ImageView) findViewById(R.id.skip_onboarding);
        nextBtn = (Button) findViewById(R.id.next_onboarding_btn);
        mslideViewPager  =(ViewPager) findViewById(R.id.slide_view_pager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //SAVE Onboarding details
                try{

                    SharedPreferences.Editor editor = getSharedPreferences(DatabaseUtils.ONBOARDING_DETAIL, MODE_PRIVATE).edit();

                    editor.putString(DatabaseUtils.onboardingKey, DatabaseUtils.onboardingvalue);

                    editor.apply();

                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItem(0) < 4){
                    mslideViewPager.setCurrentItem(getItem(1), true);
                }else{

                    //SAVE Onboarding details
                    try{

                        SharedPreferences.Editor editor = getSharedPreferences(DatabaseUtils.ONBOARDING_DETAIL, MODE_PRIVATE).edit();

                        editor.putString(DatabaseUtils.onboardingKey, DatabaseUtils.onboardingvalue);

                        editor.apply();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewPageAdapter = new ViewPageAdapter(this);

        mslideViewPager.setAdapter(viewPageAdapter);
    }

    public void setUpIndicator(int position){
        dots = new TextView[5];
        mDotLayout.removeAllViews();

        for(int i=0; i<dots.length; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray_list));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.green));
    }

    ViewPager.OnPageChangeListener viewListerner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if(position == 4){
                nextBtn.setText("Set Trading Capital");
            }else{
                nextBtn.setText("Next");
            }
            setUpIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem(int i){
        return mslideViewPager.getCurrentItem() + i;
    }
}