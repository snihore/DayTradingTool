package com.sourabh.daytradingtool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.sourabh.daytradingtool.Data.PositionSizeDetail;
import com.sourabh.daytradingtool.Data.SearchStockItemDetail;
import com.sourabh.daytradingtool.Data.TradeDetail;
import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Database.PositionSizeDetailDB;
import com.sourabh.daytradingtool.UserInterface.SearchStockRecyclerViewAdapter;
import com.sourabh.daytradingtool.UserInterface.SearchStockRecyclerViewClickListener;
import com.sourabh.daytradingtool.Utils.GetStockList;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class PositionSizeActivity extends AppCompatActivity {

    private ImageView tradeListBtn, tradeSaveBtn;
    private TextView quantityTv, riskToRewardTv, profitTv, profitPerShareTv, lossTv, lossPerShareTv, marginRequiredTv, actualCapitalRequiredTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_size);

        initViews();

        try {
            PositionSizeDetail positionSizeDetail = (PositionSizeDetail) getIntent().getSerializableExtra("GET_POSITION_SIZE_DETAIL");
            TradeDetailPOJO trade_detail_pojo = (TradeDetailPOJO) getIntent().getSerializableExtra("TRADE_DETAIL_POJO");

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

        profitTv.setText("+"+addCommasInNumber(positionSizeDetail.getProfit())+"("+positionSizeDetail.getProfitByPercentage()+"%)");

        profitPerShareTv.setText("+"+positionSizeDetail.getProfitPerShare()+"("+positionSizeDetail.getProfitPerShareByPercentage()+"%)");
        lossTv.setText("-"+addCommasInNumber(positionSizeDetail.getLoss())+"("+positionSizeDetail.getLossByPercentage()+"%)");
        lossPerShareTv.setText("-"+positionSizeDetail.getLossPerShare()+"("+positionSizeDetail.getLossPerShareByPercentage()+"%)");
        marginRequiredTv.setText("\u20B9 "+addCommasInNumber(positionSizeDetail.getMarginRequired()));
        actualCapitalRequiredTv.setText("\u20B9 "+addCommasInNumber(positionSizeDetail.getActualCapitalRequired()));
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

        boolean result = positionSizeDetailDB.insertData(new Date().getTime(), searchStockItemDetails.get(position).getTitle());

        if(result){
            dialog.dismiss();
            Intent intent = new Intent(this, TradeListActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Unable to save your trade, please try again", Toast.LENGTH_SHORT).show();
        }



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