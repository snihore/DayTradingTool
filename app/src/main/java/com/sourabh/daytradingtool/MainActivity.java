package com.sourabh.daytradingtool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.daytradingtool.Data.TradeDetail;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.UserInterface.BottomSheetPriceType;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    //Views
    private Switch switchBtn;
    private EditText entryPriceEt, stoplossEt, exitPriceEt;
    private Button getPositionSizeBtn;
    private ImageView stoplossOptionsbtn, exitPriceOptionsBtn, tradingCapitalEditBtn, tradeListBtn;
    private TextView stoplossOptionsTv, exitPriceOptionsTv, stoplossPriceShowTv, exitPricePriceShowTv;
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
        entryPriceEt = (EditText)findViewById(R.id.entry_price_et);
        stoplossEt = (EditText)findViewById(R.id.stoploss_et);
        exitPriceEt = (EditText)findViewById(R.id.exit_price_et);
        getPositionSizeBtn = (Button)findViewById(R.id.get_position_size_btn);
        stoplossOptionsbtn = (ImageView)findViewById(R.id.stoploss_options_btn);
        exitPriceOptionsBtn = (ImageView)findViewById(R.id.exit_price_options_btn);
        tradingCapitalEditBtn = (ImageView)findViewById(R.id.trading_capital_edit_btn);
        tradeListBtn = (ImageView)findViewById(R.id.trade_list_btn);
        stoplossOptionsTv = (TextView)findViewById(R.id.stoploss_options_tv);
        exitPriceOptionsTv = (TextView)findViewById(R.id.exit_price_options_tv);
        stoplossPriceShowTv = (TextView)findViewById(R.id.stoploss_price_show_tv);
        exitPricePriceShowTv = (TextView)findViewById(R.id.exit_price_price_show_tv);


        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchBtn.isChecked()){
                    Log.i("SWITCH BUTTON", "Sell");
                    isSwitchBtnChecked = true;
                    entryPriceEt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_red_bg));
                    stoplossEt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_red_bg));
                    exitPriceEt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_red_bg));

                    float density = getApplicationContext().getResources().getDisplayMetrics().density;
                    int paddingPixel10 = (int)(10 * density);
                    int paddingPixel15 = (int)(15 * density);
                    entryPriceEt.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    stoplossEt.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    exitPriceEt.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);

                }else{
                    Log.i("SWITCH BUTTON", "Buy");
                    isSwitchBtnChecked = false;
                    entryPriceEt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_green_bg));
                    stoplossEt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_green_bg));
                    exitPriceEt.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_green_bg));

                    float density = getApplicationContext().getResources().getDisplayMetrics().density;
                    int paddingPixel10 = (int)(10 * density);
                    int paddingPixel15 = (int)(15 * density);
                    entryPriceEt.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    stoplossEt.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                    exitPriceEt.setPadding(paddingPixel10, paddingPixel15, paddingPixel10, paddingPixel15);
                }
            }
        });

        getPositionSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    positionSizeHandle();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        stoplossPriceType.put(TYPE[0], PRICE_TYPES[0]);
        stoplossOptionsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPriceType bottomSheetPriceType = new BottomSheetPriceType(stoplossPriceType, stoplossOptionsTv);
                bottomSheetPriceType.show(getSupportFragmentManager(), "BOTTOM_SHEET_PRICE_TYPE");
            }
        });


        exitPricePriceType.put(TYPE[1], PRICE_TYPES[0]);
        exitPriceOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPriceType bottomSheetPriceType = new BottomSheetPriceType(exitPricePriceType, exitPriceOptionsTv);
                bottomSheetPriceType.show(getSupportFragmentManager(), "BOTTOM_SHEET_PRICE_TYPE");
            }
        });

        tradingCapitalEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.full_screen_alert);
                    View view1 = getLayoutInflater().inflate(R.layout.trading_capital_dialog_layout, null);
                    builder.setView(view1);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        tradeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TradeListActivity.class);
                startActivity(intent);
            }
        });

        ////EDIT TEXT
        try{

            stoplossEt.addTextChangedListener(new TextWatcher()
            {
                public void afterTextChanged(Editable s){}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() > 0) {
//                        Log.i("STOPLOSS EDITTEXT", s.toString());

                        String stoplossType = stoplossPriceType.get(TYPE[0]);

                        String entryPriceStr = entryPriceEt.getText().toString();
                        if(entryPriceStr != null && !entryPriceStr.matches("")){

                        }else{
                            stoplossPriceShowTv.setVisibility(View.GONE);
                            return;
                        }
                        Double entryPrice = null;
                        Double stoploss = null;
                        try{
                            entryPrice = Double.parseDouble(entryPriceStr);
                            stoploss = Double.parseDouble(s.toString());
                        }catch (Exception e){
                            stoplossPriceShowTv.setVisibility(View.GONE);
                        }

                        if(entryPrice == null || stoploss == null){
                            stoplossPriceShowTv.setVisibility(View.GONE);
                            return;
                        }

                        if(!isSwitchBtnChecked){

                            //BUY

                            if(stoplossType.equals(PRICE_TYPES[0])){
                                //Price
                                stoplossPriceShowTv.setVisibility(View.GONE);
                            }else if(stoplossType.equals(PRICE_TYPES[1])){
                                //Percentage
                                stoplossPriceShowTv.setVisibility(View.VISIBLE);
                                stoplossPriceShowTv.setText(String.valueOf(round(entryPrice - ((entryPrice*stoploss)/100), 2)));
                            }else if(stoplossType.equals(PRICE_TYPES[2])){
                                //Points
                                stoplossPriceShowTv.setVisibility(View.VISIBLE);
                                stoplossPriceShowTv.setText(String.valueOf(round(entryPrice - stoploss, 2)));
                            }

                        }else{
                            //SELL

                            if(stoplossType.equals(PRICE_TYPES[0])){
                                //Price
                                stoplossPriceShowTv.setVisibility(View.GONE);
                            }else if(stoplossType.equals(PRICE_TYPES[1])){
                                //Percentage
                                stoplossPriceShowTv.setVisibility(View.VISIBLE);
                                stoplossPriceShowTv.setText(String.valueOf(round(entryPrice + ((entryPrice*stoploss)/100), 2)));
                            }else if(stoplossType.equals(PRICE_TYPES[2])){
                                //Points
                                stoplossPriceShowTv.setVisibility(View.VISIBLE);
                                stoplossPriceShowTv.setText(String.valueOf(round(entryPrice + stoploss, 2)));
                            }
                        }
                    }
                }
            });

            exitPriceEt.addTextChangedListener(new TextWatcher()
            {
                public void afterTextChanged(Editable s){}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() > 0) {

                        String exitPriceType = exitPricePriceType.get(TYPE[1]);

                        String entryPriceStr = entryPriceEt.getText().toString();
                        if(entryPriceStr != null && !entryPriceStr.matches("")){

                        }else{
                            exitPricePriceShowTv.setVisibility(View.GONE);
                            return;
                        }
                        Double entryPrice = null;
                        Double exitPrice = null;
                        try{
                            entryPrice = Double.parseDouble(entryPriceStr);
                            exitPrice = Double.parseDouble(s.toString());
                        }catch (Exception e){
                            exitPricePriceShowTv.setVisibility(View.GONE);
                        }

                        if(entryPrice == null || exitPrice == null){
                            exitPricePriceShowTv.setVisibility(View.GONE);
                            return;
                        }

                        if(!isSwitchBtnChecked){
                            //BUY

                            if(exitPriceType.equals(PRICE_TYPES[0])){
                                //Price
                                exitPricePriceShowTv.setVisibility(View.GONE);
                            }else if(exitPriceType.equals(PRICE_TYPES[1])){
                                //Percentage
                                exitPricePriceShowTv.setVisibility(View.VISIBLE);
                                exitPricePriceShowTv.setText(String.valueOf(round(entryPrice + ((entryPrice*exitPrice)/100), 2)));
                            }else if(exitPriceType.equals(PRICE_TYPES[2])){
                                //Points
                                exitPricePriceShowTv.setVisibility(View.VISIBLE);
                                exitPricePriceShowTv.setText(String.valueOf(round(entryPrice + exitPrice, 2)));
                            }

                        }else{

                            //SELL

                            if(exitPriceType.equals(PRICE_TYPES[0])){
                                //Price
                                exitPricePriceShowTv.setVisibility(View.GONE);
                            }else if(exitPriceType.equals(PRICE_TYPES[1])){
                                //Percentage
                                exitPricePriceShowTv.setVisibility(View.VISIBLE);
                                exitPricePriceShowTv.setText(String.valueOf(round(entryPrice - ((entryPrice*exitPrice)/100), 2)));
                            }else if(exitPriceType.equals(PRICE_TYPES[2])){
                                //Points
                                exitPricePriceShowTv.setVisibility(View.VISIBLE);
                                exitPricePriceShowTv.setText(String.valueOf(round(entryPrice - exitPrice, 2)));
                            }
                        }
                    }
                }
            });

        }catch (Exception e){

        }
    }

    private void positionSizeHandle() throws Exception{

        String tempStr = entryPriceEt.getText().toString();
        double entryPrice, stoploss, exitPrice;

        if(tempStr != null && !tempStr.matches("")){
            entryPrice = Double.parseDouble(tempStr);
        }else {
            throw new Exception("Please enter valid inputs");
        }

        tempStr = stoplossEt.getText().toString();

        if(tempStr != null && !tempStr.matches("")){
            stoploss = Double.parseDouble(tempStr);
        }else {
            throw new Exception("Please enter valid inputs");
        }

        tempStr = exitPriceEt.getText().toString();

        if(tempStr != null && !tempStr.matches("")){
            exitPrice = Double.parseDouble(tempStr);
        }else {
            throw new Exception("Please enter valid inputs");
        }

        TradeDetail tradeDetail = new TradeDetail(
                entryPrice,
                !isSwitchBtnChecked,
                stoploss,
                stoplossPriceType.get(TYPE[0]),
                exitPrice,
                exitPricePriceType.get(TYPE[1]),
                tradingCapitalData
        );

        Intent intent = new Intent(getApplicationContext(), PositionSizeActivity.class);
        intent.putExtra("TRADE_DETAIL", tradeDetail.getPositionSizeDetail());
        startActivity(intent);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}