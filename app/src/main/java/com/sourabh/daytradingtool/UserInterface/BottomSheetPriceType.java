package com.sourabh.daytradingtool.UserInterface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sourabh.daytradingtool.R;

import java.util.ArrayList;
import java.util.HashMap;

public class BottomSheetPriceType extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView priceTv, percentageTv, pointsTv, optionsTv, priceShowTv;
    private EditText editText;
    private boolean isBuy;

    private String stoplossValue, exitPriceValue;
    private HashMap<String, String> priceType;
    private final String[] PRICE_TYPES = {"PRICE", "PERCENTAGE", "POINTS"};
    private final String[] TYPE = {"STOPLOSS_PRICETYPE", "EXITPRICE_PRICETYPE"};

    public BottomSheetPriceType(HashMap<String, String> priceType, TextView optionsTv, EditText editText, TextView priceShowTv){
        this.priceType = priceType;
        this.optionsTv = optionsTv;
        this.editText = editText;
        this.priceShowTv = priceShowTv;
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
                    if(editText != null && priceShowTv != null && !stoplossValue.matches(PRICE_TYPES[0])){
                        editText.setText("");
                        priceShowTv.setVisibility(View.GONE);
                    }
                }
                if(exitPriceValue != null && !exitPriceValue.matches("")){
                    priceType.put(TYPE[1], PRICE_TYPES[0]);
                    if(editText != null && priceShowTv != null && !exitPriceValue.matches(PRICE_TYPES[0])){
                        editText.setText("");
                        priceShowTv.setVisibility(View.GONE);
                    }
                }
                optionsTv.setText("price ");
                dismiss();
                break;
            case R.id.bottom_sheet_price_type_percentage_tv:
                if(stoplossValue != null && !stoplossValue.matches("")){
                    priceType.put(TYPE[0], PRICE_TYPES[1]);
                    if(editText != null && priceShowTv != null && !stoplossValue.matches(PRICE_TYPES[1])){
                        editText.setText("");
                        priceShowTv.setVisibility(View.GONE);
                    }
                }
                if(exitPriceValue != null && !exitPriceValue.matches("")){
                    priceType.put(TYPE[1], PRICE_TYPES[1]);
                    if(editText != null && priceShowTv != null && !exitPriceValue.matches(PRICE_TYPES[1])){
                        editText.setText("");
                        priceShowTv.setVisibility(View.GONE);
                    }
                }
                optionsTv.setText("percentage ");
                getPriceByPercentage();
                dismiss();
                break;
            case R.id.bottom_sheet_price_type_points_tv:
                if(stoplossValue != null && !stoplossValue.matches("")){
                    priceType.put(TYPE[0], PRICE_TYPES[2]);
                    if(editText != null && priceShowTv != null && !stoplossValue.matches(PRICE_TYPES[2])){
                        editText.setText("");
                        priceShowTv.setVisibility(View.GONE);
                    }
                }
                if(exitPriceValue != null && !exitPriceValue.matches("")){
                    priceType.put(TYPE[1], PRICE_TYPES[2]);
                    if(editText != null && priceShowTv != null && !exitPriceValue.matches(PRICE_TYPES[2])){
                        editText.setText("");
                        priceShowTv.setVisibility(View.GONE);
                    }
                }
                optionsTv.setText("points ");
                getPriceByPoints();
                dismiss();
                break;
        }
    }

    private void getPriceByPoints() {
    }

    private void getPriceByPercentage() {
    }
}
