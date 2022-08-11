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

import com.sourabh.daytradingtool.Data.CalculateCharges;
import com.sourabh.daytradingtool.Data.TradeDetail;
import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.Database.TradingCapitalDetailDB;
import com.sourabh.daytradingtool.UserInterface.BottomSheetPriceType;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    //Views
    private Switch switchBtn;
    private EditText entryPriceEt, stoplossEt, exitPriceEt;
    private Button getPositionSizeBtn;
    private ImageView stoplossOptionsbtn, exitPriceOptionsBtn, tradingCapitalEditBtn, tradeListBtn;
    private TextView stoplossOptionsTv, exitPriceOptionsTv, stoplossPriceShowTv, exitPricePriceShowTv, tradingCapitalTv, riskPerTradeTv, marginTv;
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


    }

    private void setTradingCapitalDetail() {

        try{

            TradingCapitalDetailDB tradingCapitalDetailDB = new TradingCapitalDetailDB(this);

            if(tradingCapitalDetailDB == null){
                return;
            }

            tradingCapitalData = tradingCapitalDetailDB.getTradingCapitalDetail();

            if(tradingCapitalData == null){
                return;
            }

            tradingCapitalTv.setText("\u20B9 "+addCommasInNumber(tradingCapitalData.getTradingCapital())+" ");
            riskPerTradeTv.setText("\u20B9 "+addCommasInNumber(tradingCapitalData.getRiskPerTrade())+" ");
            marginTv.setText(tradingCapitalData.getMargin()+"% ");

        }catch (Exception e){
            e.printStackTrace();
        }


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
        tradingCapitalTv = (TextView)findViewById(R.id.trading_capital_tv);
        riskPerTradeTv = (TextView)findViewById(R.id.risk_per_trade_tv);
        marginTv = (TextView)findViewById(R.id.margin_tv);


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
                    handleTradingCapitalEdit(dialog, view1);
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

    private void handleTradingCapitalEdit(AlertDialog dialog, View view) {

        if(dialog == null || view == null){
            return;
        }

        EditText tradingCapitalEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_trading_capital_et);
        EditText riskPerTradeEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_risk_per_trade_et);
        EditText marginlEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_margin_et);
        Button saveBtn = (Button) view.findViewById(R.id.trading_capital_dialog_layout_save_btn);
        TextView typeIntraday = (TextView)view.findViewById(R.id.trading_capital_type_intraday);
        TextView typeDelivery = (TextView)view.findViewById(R.id.trading_capital_type_delivery);
        ImageView backBtn = (ImageView)view.findViewById(R.id.trading_capital_dialog_layout_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        typeIntraday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeIntraday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_black_bg));
                typeIntraday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                typeDelivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_gray_bg));
                typeDelivery.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                marginlEt.setText("");
                marginlEt.setEnabled(true);
            }
        });

        typeDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeIntraday.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_gray_bg));
                typeIntraday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                typeDelivery.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_black_bg));
                typeDelivery.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                //Set Margin to 100%
                marginlEt.setText("100");
                marginlEt.setEnabled(false);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                try{
                    if(!validateTradingCapitalInputs(view)){
                        Toast.makeText(getApplicationContext(), "Please provide the valid inputs", Toast.LENGTH_SHORT).show();
                    }else{

                        TradingCapitalDetailDB tradingCapitalDetailDB = new TradingCapitalDetailDB(getApplicationContext());

                        if(tradingCapitalDetailDB == null){
                            Toast.makeText(MainActivity.this, "Not saved, please try again", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }

                        boolean result = tradingCapitalDetailDB.saveTradingCapitalDetail(new TradingCapitalData(
                                Double.parseDouble(tradingCapitalEt.getText().toString().trim()),
                                Double.parseDouble(riskPerTradeEt.getText().toString().trim()),
                                Float.parseFloat(marginlEt.getText().toString().trim())
                        ));

                        if(result){
                            Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            setTradingCapitalDetail();
                        }else{
                            Toast.makeText(MainActivity.this, "Not saved, please try again", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Not saved, please try again", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

    }

    private boolean validateTradingCapitalInputs(View view) {
        try {
            EditText tradingCapitalEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_trading_capital_et);
            EditText riskPerTradeEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_risk_per_trade_et);
            EditText marginlEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_margin_et);

            if(tradingCapitalEt.getText().toString().trim().matches("") ||
                    riskPerTradeEt.getText().toString().trim().matches("") ||
                    marginlEt.getText().toString().trim().matches("")){
                return false;
            }

            if(tradingCapitalEt.getText().toString().trim().equals("0") ||
                    riskPerTradeEt.getText().toString().trim().equals("0") ||
                    marginlEt.getText().toString().trim().equals("0")){
                return false;
            }

            if(Float.valueOf(marginlEt.getText().toString().trim()) > 100){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
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

        if(tradingCapitalData.getTradingCapital() == 0 || tradingCapitalData.getRiskPerTrade() == 0 || tradingCapitalData.getMargin() == 0){
            Toast.makeText(this, "Please add your trading capital", Toast.LENGTH_SHORT).show();
            return;
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
        intent.putExtra("GET_POSITION_SIZE_DETAIL", tradeDetail.getPositionSizeDetail());
        intent.putExtra("TRADE_DETAIL_POJO", new TradeDetailPOJO(
                tradeDetail.getEntryPrice(),
                tradeDetail.isBuy(),
                tradeDetail.getStoploss(),
                tradeDetail.getExitPrice()
        ));
        startActivity(intent);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTradingCapitalDetail();
    }

    private static String addCommasInNumber(double num){
        try{
            DecimalFormat df = new DecimalFormat("#,###.00");

            return df.format(num);
        }catch (Exception e){

        }

        return String.valueOf(num);
    }
}