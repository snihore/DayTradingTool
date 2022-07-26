package com.sourabh.daytradingtool.UserInterface;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.sourabh.daytradingtool.MainActivity;
import com.sourabh.daytradingtool.R;


public class PriceTypeHandler {

    private MainActivity mainActivity;
    private int sharePrice, percentage, slPoints;

    private boolean[] isClicked = {false, false, false};

    public boolean[] getIsClicked() {
        return isClicked;
    }

    public PriceTypeHandler(MainActivity view, int sharePrice, int percentage, int slPoints) {
        this.mainActivity = view;
        this.sharePrice = sharePrice;
        this.percentage = percentage;
        this.slPoints = slPoints;

        init(sharePrice, percentage, slPoints);

    }

    private void init(int sharePrice, int percentage, int slPoints) {

        TextView sharePriceTv = (TextView) mainActivity.findViewById(sharePrice);
        TextView percentageTv = (TextView) mainActivity.findViewById(percentage);
        TextView slPointsTv = (TextView) mainActivity.findViewById(slPoints);

        initClick(sharePriceTv, percentageTv, slPointsTv);

        sharePriceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isClicked[0] = true;
                isClicked[1] = false;
                isClicked[2] = false;

                //Clicked
                sharePriceTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_selected_bg));
                percentageTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));
                slPointsTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));

                //Set Background
                setPadding(sharePriceTv);
                setPadding(percentageTv);
                setPadding(slPointsTv);

                //Set text color
                sharePriceTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.other));
                percentageTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));
                slPointsTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));



            }
        });

        percentageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isClicked[0] = false;
                isClicked[1] = true;
                isClicked[2] = false;

                //Clicked
                sharePriceTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));
                percentageTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_selected_bg));
                slPointsTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));

                //Set Background
                setPadding(sharePriceTv);
                setPadding(percentageTv);
                setPadding(slPointsTv);

                //Set text color
                sharePriceTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));
                percentageTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.other));
                slPointsTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));

            }
        });

        slPointsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isClicked[0] = false;
                isClicked[1] = false;
                isClicked[2] = true;

                //Clicked
                sharePriceTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));
                percentageTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));
                slPointsTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_selected_bg));

                //Set Background
                setPadding(sharePriceTv);
                setPadding(percentageTv);
                setPadding(slPointsTv);

                //Set text color
                sharePriceTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));
                percentageTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));
                slPointsTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.other));

            }
        });


    }

    private void setPadding(TextView textView){
        int paddingDp = 10;
        float density = mainActivity.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        textView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
    }

    private void initClick(TextView sharePriceTv, TextView percentageTv, TextView slPointsTv){

        isClicked[0] = true;
        isClicked[1] = false;
        isClicked[2] = false;

        //Clicked
        sharePriceTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_selected_bg));
        percentageTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));
        slPointsTv.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.text_view_gray_bg));

        //Set Background
        setPadding(sharePriceTv);
        setPadding(percentageTv);
        setPadding(slPointsTv);

        //Set text color
        sharePriceTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.other));
        percentageTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));
        slPointsTv.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray));

    }

}
