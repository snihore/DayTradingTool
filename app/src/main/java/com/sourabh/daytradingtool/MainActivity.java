package com.sourabh.daytradingtool;

import static com.sourabh.daytradingtool.Utils.FormatUtils.doubleToString;
import static com.sourabh.daytradingtool.Utils.FormatUtils.floatToString;
import static com.sourabh.daytradingtool.Utils.FormatUtils.round;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import com.sourabh.daytradingtool.Database.FirebaseHandle;
import com.sourabh.daytradingtool.Database.TradingCapitalDetailDB;
import com.sourabh.daytradingtool.UserInterface.BottomSheetDashboardOptions;
import com.sourabh.daytradingtool.UserInterface.BottomSheetPriceType;
import com.sourabh.daytradingtool.Utils.FirebaseUtils;
import com.sourabh.daytradingtool.Utils.FormatUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    //Views
    private Switch switchBtn;
    private EditText entryPriceEt, stoplossEt, exitPriceEt;
    private Button getPositionSizeBtn;
    private ImageView stoplossOptionsbtn, exitPriceOptionsBtn, tradingCapitalEditBtn, tradeListBtn, dashboardOptions;
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

    private void updateAppDialog() {
        try{
            FirebaseHandle firebaseHandle = new FirebaseHandle(MainActivity.this);
            firebaseHandle.updateApp();
        }catch (Exception e){
            e.printStackTrace();
        }
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

            tradingCapitalTv.setText("\u20B9 "+ FormatUtils.addCommasInNumber(tradingCapitalData.getTradingCapital())+" ");
            riskPerTradeTv.setText("\u20B9 "+FormatUtils.addCommasInNumber(tradingCapitalData.getRiskPerTrade())+" ");
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
        dashboardOptions = (ImageView)findViewById(R.id.dashboard_options);


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

                try{
                    stoplossTextChangeListener(stoplossEt.getText().toString());
                    exitPriceTextChangeListener(exitPriceEt.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
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

        dashboardOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDashboardOptions dashboardOptions = new BottomSheetDashboardOptions();
                dashboardOptions.show(getSupportFragmentManager(), "BOTTOM_SHEET_DASHBOARD_OPTIONS");
            }
        });

        stoplossPriceType.put(TYPE[0], PRICE_TYPES[0]);
        stoplossOptionsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPriceType bottomSheetPriceType = new BottomSheetPriceType(stoplossPriceType, stoplossOptionsTv, stoplossEt, stoplossPriceShowTv);
                bottomSheetPriceType.show(getSupportFragmentManager(), "BOTTOM_SHEET_PRICE_TYPE");
            }
        });


        exitPricePriceType.put(TYPE[1], PRICE_TYPES[0]);
        exitPriceOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPriceType bottomSheetPriceType = new BottomSheetPriceType(exitPricePriceType, exitPriceOptionsTv, exitPriceEt, exitPricePriceShowTv);
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

            entryPriceEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    stoplossTextChangeListener(stoplossEt.getText().toString());
                    exitPriceTextChangeListener(exitPriceEt.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            stoplossEt.addTextChangedListener(new TextWatcher()
            {
                public void afterTextChanged(Editable s){}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    stoplossTextChangeListener(s);
                }
            });

            exitPriceEt.addTextChangedListener(new TextWatcher()
            {
                public void afterTextChanged(Editable s){}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    exitPriceTextChangeListener(s);
                }
            });

        }catch (Exception e){

        }
    }

    private void exitPriceTextChangeListener(CharSequence s) {
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
                    exitPricePriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice + ((entryPrice*exitPrice)/100), 2)));
                }else if(exitPriceType.equals(PRICE_TYPES[2])){
                    //Points
                    exitPricePriceShowTv.setVisibility(View.VISIBLE);
                    exitPricePriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice + exitPrice, 2)));
                }

            }else{

                //SELL

                if(exitPriceType.equals(PRICE_TYPES[0])){
                    //Price
                    exitPricePriceShowTv.setVisibility(View.GONE);
                }else if(exitPriceType.equals(PRICE_TYPES[1])){
                    //Percentage
                    exitPricePriceShowTv.setVisibility(View.VISIBLE);
                    exitPricePriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice - ((entryPrice*exitPrice)/100), 2)));
                }else if(exitPriceType.equals(PRICE_TYPES[2])){
                    //Points
                    exitPricePriceShowTv.setVisibility(View.VISIBLE);
                    exitPricePriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice - exitPrice, 2)));
                }
            }
        }
    }

    private void stoplossTextChangeListener(CharSequence s) {

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
                    stoplossPriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice - ((entryPrice*stoploss)/100), 2)));
                }else if(stoplossType.equals(PRICE_TYPES[2])){
                    //Points
                    stoplossPriceShowTv.setVisibility(View.VISIBLE);
                    stoplossPriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice - stoploss, 2)));
                }

            }else{
                //SELL

                if(stoplossType.equals(PRICE_TYPES[0])){
                    //Price
                    stoplossPriceShowTv.setVisibility(View.GONE);
                }else if(stoplossType.equals(PRICE_TYPES[1])){
                    //Percentage
                    stoplossPriceShowTv.setVisibility(View.VISIBLE);
                    stoplossPriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice + ((entryPrice*stoploss)/100), 2)));
                }else if(stoplossType.equals(PRICE_TYPES[2])){
                    //Points
                    stoplossPriceShowTv.setVisibility(View.VISIBLE);
                    stoplossPriceShowTv.setText(String.valueOf(FormatUtils.round(entryPrice + stoploss, 2)));
                }
            }
        }
    }

    private void handleTradingCapitalEdit(AlertDialog dialog, View view) {

        if(dialog == null || view == null){
            return;
        }

        EditText tradingCapitalEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_trading_capital_et);
        EditText riskPerTradeEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_risk_per_trade_et);
        EditText marginEt = (EditText) view.findViewById(R.id.trading_capital_dialog_layout_margin_et);
        Button saveBtn = (Button) view.findViewById(R.id.trading_capital_dialog_layout_save_btn);
        TextView typeIntraday = (TextView)view.findViewById(R.id.trading_capital_type_intraday);
        TextView typeDelivery = (TextView)view.findViewById(R.id.trading_capital_type_delivery);
        TextView rupeeSelect = (TextView)view.findViewById(R.id.trading_capital_rupee_select);
        TextView percentageSelect = (TextView)view.findViewById(R.id.trading_capital_percentage_select);
        ImageView backBtn = (ImageView)view.findViewById(R.id.trading_capital_dialog_layout_back_btn);

        final boolean[] isRupeeSelect = {true};

        if(tradingCapitalData != null){
            try {
                tradingCapitalEt.setText(doubleToString(tradingCapitalData.getTradingCapital()));

            }catch (Exception e){
                tradingCapitalEt.setText(String.valueOf(tradingCapitalData.getTradingCapital()));
            }

            try {
                riskPerTradeEt.setText(doubleToString(tradingCapitalData.getRiskPerTrade()));

            }catch (Exception e){
                riskPerTradeEt.setText(String.valueOf(tradingCapitalData.getRiskPerTrade()));
            }

            try {
                marginEt.setText(floatToString(tradingCapitalData.getMargin()));

            }catch (Exception e){
                marginEt.setText(String.valueOf(tradingCapitalData.getMargin()));
            }

        }

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

                marginEt.setText("");
                marginEt.setEnabled(true);
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
                marginEt.setText("100");
                marginEt.setEnabled(false);
            }
        });

        rupeeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rupeeSelect.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                rupeeSelect.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                percentageSelect.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.background));
                percentageSelect.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                riskPerTradeEt.setText("");
                isRupeeSelect[0] = true;

            }
        });

        percentageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percentageSelect.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                percentageSelect.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                rupeeSelect.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.background));
                rupeeSelect.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                riskPerTradeEt.setText("");
                isRupeeSelect[0] = false;
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                try{
                    if(!validateTradingCapitalInputs(view, isRupeeSelect[0])){
                        Toast.makeText(getApplicationContext(), "Please provide the valid inputs", Toast.LENGTH_SHORT).show();
                    }else{

                        TradingCapitalDetailDB tradingCapitalDetailDB = new TradingCapitalDetailDB(getApplicationContext());

                        if(tradingCapitalDetailDB == null){
                            Toast.makeText(MainActivity.this, "Not saved, please try again", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }

                        double riskPerTrade = Double.parseDouble(riskPerTradeEt.getText().toString().trim());

                        if(!isRupeeSelect[0]){
                            //PERCENTAGE
                            double tempPercentage = Double.parseDouble(riskPerTradeEt.getText().toString().trim());

                            double tempCapital = Double.parseDouble(tradingCapitalEt.getText().toString().trim());

                            double tempRupee = round((tempCapital*tempPercentage)/100, 2);

                            riskPerTrade = tempRupee;
                        }

                        boolean result = tradingCapitalDetailDB.saveTradingCapitalDetail(new TradingCapitalData(
                                Double.parseDouble(tradingCapitalEt.getText().toString().trim()),
                                riskPerTrade,
                                Float.parseFloat(marginEt.getText().toString().trim())
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

    private boolean validateTradingCapitalInputs(View view, boolean isRupeeSelect) {
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

            if(!isRupeeSelect){
                //PERCENTAGE
                if(Float.valueOf(riskPerTradeEt.getText().toString().trim()) > 100){
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void positionSizeHandle() throws Exception{

        if(!validateInputs()){
            Toast.makeText(this, "Please enter valid inputs", Toast.LENGTH_SHORT).show();
            return;
        }

        double entryPrice = Double.parseDouble(entryPriceEt.getText().toString().trim());
        double exitPrice = Double.parseDouble(exitPriceEt.getText().toString().trim());
        double stoploss = Double.parseDouble(stoplossEt.getText().toString().trim());

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

    private boolean validateInputs() {

        if(entryPriceEt == null || exitPriceEt == null || stoplossEt == null){
            return false;
        }

        if(entryPriceEt.getText().toString().matches("") ||
        exitPriceEt.getText().toString().matches("") ||
        stoplossEt.getText().toString().matches("")){
            return false;
        }

        String stoplossType = stoplossPriceType.get(TYPE[0]); // Select Stoploss
        String exitPriceType = exitPricePriceType.get(TYPE[1]); // Select Exit Price

        double entry = Double.parseDouble(entryPriceEt.getText().toString().trim());
        double exit = Double.parseDouble(exitPriceEt.getText().toString().trim());
        double stoploss = Double.parseDouble(stoplossEt.getText().toString().trim());

        if(entry <= 0){
            return false;
        }

        if(!isSwitchBtnChecked){

            //BUY
            if(stoplossType.matches(PRICE_TYPES[0])){ // Price
                if(entry<=stoploss){ // Entry should not be less than stoploss in BUY
                    return false;
                }
                if(stoploss<=0){ // Stoploss should not be zero (100% stoploss)
                    return false;
                }
                if(tradingCapitalData != null && entry-stoploss > tradingCapitalData.getRiskPerTrade()){
                    return false;
                }
            }
            if(exitPriceType.matches(PRICE_TYPES[0])){ // Price
                if(entry>=exit){ // Entry should not be more than exit in BUY
                    return false;
                }
                if(exit<=0){ // Exit price should not be zero (stock price must not be zero at all)
                    return false;
                }
            }

            if(stoplossType.matches(PRICE_TYPES[1])){ // Percentage
                if(stoploss <= 0 || stoploss > 100){ // stoploss must be in between Zero and 100 (Exclusive)
                    return false;
                }
                if(tradingCapitalData != null && ((stoploss*entry)/100) > tradingCapitalData.getRiskPerTrade()){
                    return false;
                }
            }
            if(exitPriceType.matches(PRICE_TYPES[1])){ // Percentage
                if(exit <= 0 || exit > 100){ // Exit price must be in between Zero and 100 (Exclusive)
                    return false;
                }
            }

            if(stoplossType.matches(PRICE_TYPES[2])){ // Points
                if(stoploss<=0 || stoploss >= entry){// Stoploss must be more than Zero points
                    return false;
                }
                if(tradingCapitalData != null && stoploss>tradingCapitalData.getRiskPerTrade()){
                    return false;
                }
            }
            if(exitPriceType.matches(PRICE_TYPES[2])){ // Points
                if(exit<=0){
                    return false;
                }
            }

        }else{
            //SELL
            if(stoplossType.matches(PRICE_TYPES[0])){//Price
                if(entry>=stoploss){
                    return false;
                }
                if(stoploss<=0){
                    return false;
                }
                if(tradingCapitalData != null && stoploss-entry > tradingCapitalData.getRiskPerTrade()){
                    return false;
                }
            }
            if(exitPriceType.matches(PRICE_TYPES[0])){//Price
                if(entry<=exit){
                    return false;
                }
                if(exit<=0){
                    return false;
                }
            }
            if(stoplossType.matches(PRICE_TYPES[1])){//Percentage
                if(stoploss <= 0 || stoploss > 100){
                    return false;
                }
                if(tradingCapitalData != null && ((stoploss*entry)/100) > tradingCapitalData.getRiskPerTrade()){
                    return false;
                }
            }
            if(exitPriceType.matches(PRICE_TYPES[1])){//Percentage
                if(exit <= 0 || exit > 100){
                    return false;
                }
            }
            if(stoplossType.matches(PRICE_TYPES[2])){//Points
                if(stoploss<=0 || stoploss >= entry){
                    return false;
                }
                if(tradingCapitalData != null && stoploss>tradingCapitalData.getRiskPerTrade()){
                    return false;
                }
            }
            if(exitPriceType.matches(PRICE_TYPES[2])){//Points
                if(exit<=0 || exit>entry){
                    return false;
                }
            }

        }

        return true;

    }



    @Override
    protected void onResume() {
        super.onResume();

        setTradingCapitalDetail();

        //CHECK CONNECTION
        if(FirebaseUtils.isNetworkAvailable(getApplicationContext())){
            Log.i("NETWORK", "AVAILABLE");
            updateAppDialog();
        }
    }

}