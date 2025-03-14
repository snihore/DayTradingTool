package com.sourabh.daytradingtool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipboardManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sourabh.daytradingtool.Data.CalculateCharges;
import com.sourabh.daytradingtool.Data.PositionSizeDetail;
import com.sourabh.daytradingtool.Data.SearchStockItemDetail;
import com.sourabh.daytradingtool.Data.TradeDetail;
import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.Database.PositionSizeDetailDB;
import com.sourabh.daytradingtool.Database.TradingCapitalDetailDB;
import com.sourabh.daytradingtool.UserInterface.ChargesInfoDialog;
import com.sourabh.daytradingtool.UserInterface.MarginInfoDialog;
import com.sourabh.daytradingtool.UserInterface.SearchStockRecyclerViewAdapter;
import com.sourabh.daytradingtool.UserInterface.SearchStockRecyclerViewClickListener;
import com.sourabh.daytradingtool.Utils.ChargesUtils;
import com.sourabh.daytradingtool.Utils.FormatUtils;
import com.sourabh.daytradingtool.Utils.GetStockList;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class PositionSizeActivity extends AppCompatActivity {

    private ImageView tradeListBtn, tradeSaveBtn, backBtn, copyBtn, chargesInfoBtn, marginInfoBtn;
    private TextView quantityTv, riskToRewardTv, profitTv, profitPerShareTv, lossTv, lossPerShareTv, marginRequiredTv, actualCapitalRequiredTv, profitChargesTv, lossChargesTv;

    private TradeDetailPOJO tradeDetailPOJO;
    private PositionSizeDetail positionSizeDetail;
    private TradingCapitalData tradingCapitalData;

    private CalculateCharges.GetIndividualCharges profitChargesObj, lossChargesObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_size);

        initViews();

        try {
            positionSizeDetail = (PositionSizeDetail) getIntent().getSerializableExtra("GET_POSITION_SIZE_DETAIL");
            tradeDetailPOJO = (TradeDetailPOJO) getIntent().getSerializableExtra("TRADE_DETAIL_POJO");

            tradingCapitalData = new TradingCapitalDetailDB(this).getTradingCapitalDetail();

            if(tradingCapitalData == null){
                tradingCapitalData = new TradingCapitalData();
            }

            setView(positionSizeDetail);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setView(PositionSizeDetail positionSizeDetail) {

//        Log.i("POSITION SIZE DETAIL", positionSizeDetail.toString());

        quantityTv.setText(String.valueOf(positionSizeDetail.getQuantity()));
        riskToRewardTv.setText("Risk to Reward 1:"+positionSizeDetail.getRiskToReward());

        profitTv.setText("+"+ FormatUtils.addCommasInNumber(positionSizeDetail.getProfit())+"("+positionSizeDetail.getProfitByPercentage()+"%)");

        profitPerShareTv.setText("+"+positionSizeDetail.getProfitPerShare()+"("+positionSizeDetail.getProfitPerShareByPercentage()+"%)");
        lossTv.setText("-"+FormatUtils.addCommasInNumber(positionSizeDetail.getLoss())+"("+positionSizeDetail.getLossByPercentage()+"%)");
        lossPerShareTv.setText("-"+positionSizeDetail.getLossPerShare()+"("+positionSizeDetail.getLossPerShareByPercentage()+"%)");
        marginRequiredTv.setText("\u20B9 "+FormatUtils.addCommasInNumber(positionSizeDetail.getMarginRequired())+"("+tradingCapitalData.getMargin()+"%)");
        actualCapitalRequiredTv.setText("\u20B9 "+FormatUtils.addCommasInNumber(positionSizeDetail.getActualCapitalRequired()));

        setCharges(tradeDetailPOJO.getEntryPrice(), tradeDetailPOJO.getExitPrice(), tradeDetailPOJO.getStoploss(), positionSizeDetail.getQuantity());
    }

    private void setCharges(double entry, double exit, double stoploss, int quantity) {

        //PROFIT
        double profitCharges = 0;
        CalculateCharges.GetIndividualCharges getIndividualCharges = null;

        CalculateCharges calculateCharges = new CalculateCharges();

        if(entry < exit){
            //BUY
            getIndividualCharges = calculateCharges.getZerodhaChargesIntraday(entry, exit, quantity);
            if(getIndividualCharges != null){
                profitCharges = getIndividualCharges.getTotal();
            }
        }else{
            //SELL
            getIndividualCharges = calculateCharges.getZerodhaChargesIntraday(exit, entry, quantity);
            if(getIndividualCharges != null){
                profitCharges = getIndividualCharges.getTotal();
            }
        }
        if(tradingCapitalData.getMargin() == 100){
            if(entry < exit){
                //BUY
                getIndividualCharges = calculateCharges.getZerodhaChargesDelivery(entry, exit, quantity);
                if(getIndividualCharges != null){
                    profitCharges = getIndividualCharges.getTotal();
                }
            }else{
                //SELL
                getIndividualCharges = calculateCharges.getZerodhaChargesDelivery(exit, entry, quantity);
                if(getIndividualCharges != null){
                    profitCharges = getIndividualCharges.getTotal();
                }
            }
        }
        if(getIndividualCharges != null){
            profitChargesObj = getIndividualCharges;
        }

        //LOSS
        double lossCharges = 0;
        CalculateCharges.GetIndividualCharges getIndividualChargesL = null;

        if(entry > stoploss){
            //BUY
            getIndividualChargesL = calculateCharges.getZerodhaChargesIntraday(entry, stoploss, quantity);
            if(getIndividualChargesL != null){
                lossCharges = getIndividualChargesL.getTotal();
            }
        }else{
            //SELL
            getIndividualChargesL = calculateCharges.getZerodhaChargesIntraday(stoploss, entry, quantity);
            if(getIndividualChargesL != null){
                lossCharges = getIndividualChargesL.getTotal();
            }
        }
        if(tradingCapitalData.getMargin() == 100){
            if(entry > stoploss){
                //BUY
                getIndividualChargesL = calculateCharges.getZerodhaChargesDelivery(entry, stoploss, quantity);
                if(getIndividualChargesL != null){
                    lossCharges = getIndividualChargesL.getTotal();
                }
            }else{
                //SELL
                getIndividualChargesL = calculateCharges.getZerodhaChargesDelivery(stoploss, entry, quantity);
                if(getIndividualChargesL != null){
                    lossCharges = getIndividualChargesL.getTotal();
                }
            }
        }

        if(getIndividualChargesL != null){
            lossChargesObj = getIndividualChargesL;
        }

        //Set Views
        profitChargesTv.setText("\u20B9 "+profitCharges);
        lossChargesTv.setText("\u20B9 "+lossCharges);
    }

    private void initViews() {
        tradeListBtn = (ImageView) findViewById(R.id.trade_list_btn);
        quantityTv = (TextView)findViewById(R.id.quantity_tv);
        riskToRewardTv = (TextView)findViewById(R.id.risk_to_reward_tv);
        profitTv = (TextView)findViewById(R.id.profit_tv);
        profitPerShareTv = (TextView)findViewById(R.id.profit_per_share_tv);
        lossTv = (TextView)findViewById(R.id.loss_tv);
        lossPerShareTv = (TextView)findViewById(R.id.loss_per_share_tv);
        marginRequiredTv = (TextView)findViewById(R.id.margin_required_tv);
        actualCapitalRequiredTv = (TextView)findViewById(R.id.actual_capital_required_tv);
        tradeSaveBtn = (ImageView)findViewById(R.id.trade_save_btn);
        backBtn = (ImageView)findViewById(R.id.position_size_back_btn);
        profitChargesTv = (TextView)findViewById(R.id.profit_charges);
        lossChargesTv = (TextView)findViewById(R.id.loss_charges);
        copyBtn = (ImageView)findViewById(R.id.copy_img_btn);
        chargesInfoBtn = (ImageView)findViewById(R.id.charges_info_btn);
        marginInfoBtn = (ImageView)findViewById(R.id.margin_info_btn);


        tradeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TradeListActivity.class);
                startActivity(intent);
            }
        });

        tradeSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectStockDialog();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    if(quantityTv != null && !quantityTv.getText().toString().trim().matches("")){

                        String copyText = quantityTv.getText().toString().trim();

                        int sdk = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            ClipboardManager clipboard = (ClipboardManager)
                                    getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                            clipboard.setText(copyText);
                        } else {
                            ClipboardManager clipboard = (ClipboardManager)
                                    getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                            android.content.ClipData clip = android.content.ClipData
                                    .newPlainText("Your Quantity", copyText);
                            clipboard.setPrimaryClip(clip);
                        }

                        Toast.makeText(PositionSizeActivity.this, copyText+" Quantity copied", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(PositionSizeActivity.this, "Can't copied", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(PositionSizeActivity.this, "Can't copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chargesInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ChargesInfoDialog chargesInfoDialog = new ChargesInfoDialog();

                    chargesInfoDialog.view(PositionSizeActivity.this, profitChargesObj, lossChargesObj);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        marginInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MarginInfoDialog marginInfoDialog = new MarginInfoDialog();

                    marginInfoDialog.view(PositionSizeActivity.this, tradingCapitalData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void openSelectStockDialog() {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(PositionSizeActivity.this, R.style.full_screen_alert);
            View view = getLayoutInflater().inflate(R.layout.search_stock_list_dialog_layout, null);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            openSelectStockDialogView(view, dialog);
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openSelectStockDialogView(View view, AlertDialog dialog) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search_stock_list_recycler_view);
        EditText searchStockEt = (EditText) view.findViewById(R.id.search_stock_list_et);
        ImageView backBtn = (ImageView)view.findViewById(R.id.search_stock_list_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ArrayList<SearchStockItemDetail> searchStockItemDetails = GetStockList.readData(this);
        if(searchStockItemDetails != null && searchStockItemDetails.size()>0){

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            SearchStockRecyclerViewClickListener clickListener = new SearchStockRecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {

                    //Handle Search Stock Item Click Listener
                    handleSearchStockRecyclerViewClickListener(view, position, searchStockItemDetails, dialog);

                }
            };

            SearchStockRecyclerViewAdapter searchStockRecyclerViewAdapter = new SearchStockRecyclerViewAdapter(searchStockItemDetails, clickListener);
            recyclerView.setAdapter(searchStockRecyclerViewAdapter);

            //SEARCH
            searchStockEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    //Search
                    if(searchStockRecyclerViewAdapter != null && charSequence != null){
                        searchStockRecyclerViewAdapter.getFilter().filter(charSequence);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }else {

        }
    }

    private void handleSearchStockRecyclerViewClickListener(View view, int position, ArrayList<SearchStockItemDetail> searchStockItemDetails, AlertDialog dialog){

        PositionSizeDetailDB positionSizeDetailDB = new PositionSizeDetailDB(this);

        if(tradeDetailPOJO == null || positionSizeDetail == null){
            Toast.makeText(this, "Unable to save your trade, please try again", Toast.LENGTH_SHORT).show();
            return;
        }

        if(tradingCapitalData.getTradingCapital() == 0 ||
        tradingCapitalData.getRiskPerTrade() == 0 ||
        tradingCapitalData.getMargin() == 0){
            Toast.makeText(this, "Unable to save your trade, please enter your trading capital details", Toast.LENGTH_SHORT).show();
            return;
        }

        int isBuyInt = -1;

        if(tradeDetailPOJO.isBuy()){
            isBuyInt = 1;
        }else{
            isBuyInt = 0;
        }


        boolean result = positionSizeDetailDB.insertData(
                new Date().getTime(),
                searchStockItemDetails.get(position).getTitle(),
                tradeDetailPOJO.getEntryPrice(),
                isBuyInt,
                tradeDetailPOJO.getStoploss(),
                tradeDetailPOJO.getExitPrice(),
                positionSizeDetail.getQuantity(),
                tradingCapitalData.getTradingCapital(),
                tradingCapitalData.getRiskPerTrade(),
                tradingCapitalData.getMargin()

        );

        if(result){
            dialog.dismiss();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TradeListActivity.class);
            startActivity(intent);
            onBackPressed();
        }else{
            Toast.makeText(this, "Unable to save your trade, please try again", Toast.LENGTH_SHORT).show();
        }



    }

}