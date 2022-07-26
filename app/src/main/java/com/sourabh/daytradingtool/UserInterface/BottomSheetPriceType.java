package com.sourabh.daytradingtool.UserInterface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sourabh.daytradingtool.R;

import java.util.ArrayList;
import java.util.HashMap;

public class BottomSheetPriceType extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView priceTv, percentageTv, pointsTv;

    private String stoplossValue, exitPriceValue;
    private HashMap<String, String> priceType;
    private final String[] PRICE_TYPES = {"PRICE", "PERCENTAGE", "POINTS"};
    private final String[] TYPE = {"STOPLOSS_PRICETYPE", "EXITPRICE_PRICETYPE"};

    public BottomSheetPriceType(HashMap<String, String> priceType){
        this.priceType = priceType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bottom_sheet_price_type_layout,
                container, false);

        initViews(view);

        if(priceType != null){

            stoplossValue = priceType.get(TYPE[0]);
            exitPriceValue = priceType.get(TYPE[1]);

            if(stoplossValue != null && !stoplossValue.matches("")){
                if(stoplossValue.equals(PRICE_TYPES[0])){
                    priceTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                    priceTv.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }else if(stoplossValue.equals(PRICE_TYPES[1])){
                    percentageTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                    percentageTv.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }else if(stoplossValue.equals(PRICE_TYPES[2])){
                    pointsTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                    pointsTv.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }
            }

            if(exitPriceValue != null && !exitPriceValue.matches("")){
                if(exitPriceValue.equals(PRICE_TYPES[0])){
                    priceTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                    priceTv.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }else if(exitPriceValue.equals(PRICE_TYPES[1])){
                    percentageTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                    percentageTv.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }else if(exitPriceValue.equals(PRICE_TYPES[2])){
                    pointsTv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                    pointsTv.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }
            }
        }

        priceTv.setOnClickListener(this);
        percentageTv.setOnClickListener(this);
        pointsTv.setOnClickListener(this);

        return view;

    }

    private void initViews(View view) {
        priceTv = (TextView) view.findViewById(R.id.bottom_sheet_price_type_price_tv);
        percentageTv = (TextView) view.findViewById(R.id.bottom_sheet_price_type_percentage_tv);
        pointsTv = (TextView) view.findViewById(R.id.bottom_sheet_price_type_points_tv);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bottom_sheet_price_type_price_tv:
                if(stoplossValue != null && !stoplossValue.matches("")){
                    priceType.put(TYPE[0], PRICE_TYPES[0]);
                }
                if(exitPriceValue != null && !exitPriceValue.matches("")){
                    priceType.put(TYPE[1], PRICE_TYPES[0]);
                }
                dismiss();
                break;
            case R.id.bottom_sheet_price_type_percentage_tv:
                if(stoplossValue != null && !stoplossValue.matches("")){
                    priceType.put(TYPE[0], PRICE_TYPES[1]);
                }
                if(exitPriceValue != null && !exitPriceValue.matches("")){
                    priceType.put(TYPE[1], PRICE_TYPES[1]);
                }
                dismiss();
                break;
            case R.id.bottom_sheet_price_type_points_tv:
                if(stoplossValue != null && !stoplossValue.matches("")){
                    priceType.put(TYPE[0], PRICE_TYPES[2]);
                }
                if(exitPriceValue != null && !exitPriceValue.matches("")){
                    priceType.put(TYPE[1], PRICE_TYPES[2]);
                }
                dismiss();
                break;
        }
    }
}
