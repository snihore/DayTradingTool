package com.sourabh.daytradingtool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.UserInterface.BottomSheetPriceType;
import com.sourabh.daytradingtool.UserInterface.KeyboardHandler;
import com.sourabh.daytradingtool.UserInterface.PriceTypeHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity{

    //Views
    private Switch switchBtn;
    private EditText entryPrice, stoploss, exitPrice;
    private Button getPositionSizeBtn;
    private ImageView stoplossOptionsbtn, exitPriceOptionsBtn, tradingCapitalEditBtn, tradeListBtn;
    //Custom Classes

    //Variables
    private boolean isSwitchBtnChecked = false;
    private final String[] PRICE_TYPES = {"PRICE", "PERCENTAGE", "POINTS"};
    private final String[] TYPE = {"STOPLOSS_PRICETYPE", "EXITPRICE_PRICETYPE"};
    private HashMap<String, String> stoplossPriceType = new HashMap<>();
    private HashMap<String, String> exitPricePriceType = new HashMap<>();

    private TradingCapitalData tradingCapitalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        testTradingCapitalData();

    }

    private void testTradingCapitalData() {
        tradingCapitalData = new TradingCapitalData(
                100000,
                2000,
                20
        );
    }

    private void initViews() {

        switchBtn = (Switch) findViewById(R.id.enter_trade_details_layout_switch_btn);
        entryPrice = (EditText)findViewById(R.id.entry_price_et);
        stoploss = (EditText)findViewById(R.id.stoploss_et);
        exitPrice = (EditText)findViewById(R.id.exit_price_et);
        getPositionSizeBtn = (Button)findViewById(R.id.get_position_size_btn);
        stoplossOptionsbtn = (ImageView)findViewById(R.id.stoploss_options_btn);
        exitPriceOptionsBtn = (ImageView)findViewById(R.id.exit_price_options_btn);
        tradingCapitalEditBtn = (ImageView)findViewById(R.id.trading_capital_edit_btn);
        tradeListBtn = (ImageView)findViewById(R.id.trade_list_btn);


        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchBtn.isChecked()){
                    Log.i("SWITCH BUTTON", "Sell");
                    isSwitchBtnChecked = true;
                    entryPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_red_bg));
                    stoploss.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_red_bg));
                    exitPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_red_bg));

                    float density = getApplicationContext().getResources().getDisplayMetrics().density;
                    int paddingPixel10 = (int)(10 * density);
                    int paddingPixel15 = (int)(15 * density);
                    entryPrice.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    stoploss.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    exitPrice.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);

                }else{
                    Log.i("SWITCH BUTTON", "Buy");
                    isSwitchBtnChecked = false;
                    entryPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_green_bg));
                    stoploss.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_green_bg));
                    exitPrice.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_green_bg));

                    float density = getApplicationContext().getResources().getDisplayMetrics().density;
                    int paddingPixel10 = (int)(10 * density);
                    int paddingPixel15 = (int)(15 * density);
                    entryPrice.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    stoploss.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    exitPrice.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                }
            }
        });

        getPositionSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PositionSizeActivity.class);
                startActivity(intent);
            }
        });


        stoplossPriceType.put(TYPE[0], PRICE_TYPES[0]);
        stoplossOptionsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPriceType bottomSheetPriceType = new BottomSheetPriceType(stoplossPriceType);
                bottomSheetPriceType.show(getSupportFragmentManager(), "BOTTOM_SHEET_PRICE_TYPE");
            }
        });


        exitPricePriceType.put(TYPE[1], PRICE_TYPES[0]);
        exitPriceOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPriceType bottomSheetPriceType = new BottomSheetPriceType(exitPricePriceType);
                bottomSheetPriceType.show(getSupportFragmentManager(), "BOTTOM_SHEET_PRICE_TYPE");
            }
        });

        tradingCapitalEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.full_screen_alert);
                View view1 = getLayoutInflater().inflate(R.layout.trading_capital_dialog_layout, null);
                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        tradeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TradeListActivity.class);
                startActivity(intent);
            }
        });
    }


}