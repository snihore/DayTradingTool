package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.Database.PositionSizeDetailDB;
import com.sourabh.daytradingtool.UserInterface.ParentTradeListRecyclerViewAdapter;
import com.sourabh.daytradingtool.UserInterface.TradeListItemClickListener;
import com.sourabh.daytradingtool.UserInterface.TradeListRecyclerViewAdapter;
import com.sourabh.daytradingtool.UserInterface.ViewPositionSizeLayoutDialog;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class TradeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TextView showingTv;

    private Cursor cursor;

    private ImageView backBtn;

    private ArrayList<Long> timestamps = new ArrayList<>();
    private HashMap<Long, Integer> quantities = new HashMap<>();
    private HashMap<Long, String> stockTitles = new HashMap<>();
    private HashMap<Long, TradeDetailPOJO> tradingDetails = new HashMap<>();
    private HashMap<Long, TradingCapitalData> tradingCapitals = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        initViews();

        try {

            PositionSizeDetailDB positionSizeDetailDB = new PositionSizeDetailDB(this);

            cursor = positionSizeDetailDB.getData();

            if(cursor != null){

                if(cursor.getCount() == 0){
//                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                    showingTv.setText(" No data found ");
                }else{

                    while(cursor.moveToNext()){

                        Log.i("CURSOR: ", "Timestamp: "+cursor.getString(0)+",\n" +
                                "StockTitle: "+cursor.getString(1)+",\n" +
                                "EntryPrice: "+cursor.getString(2)+",\n" +
                                "IsBuy: "+cursor.getString(3)+",\n" +
                                "Stoploss: "+cursor.getString(4)+",\n" +
                                "ExitPrice: "+cursor.getString(5)+",\n" +
                                "Quantity: "+cursor.getString(6)+",\n"+
                                "TradingCapital: "+cursor.getString(7)+",\n"+
                                "RiskPerTrade: "+cursor.getString(8)+",\n"+
                                "Margin: "+cursor.getString(9));

                        timestamps.add(cursor.getLong(0));

                        quantities.put(cursor.getLong(0), cursor.getInt(6));

                        boolean isBuy = false;

                        if(cursor.getInt(3) == 1){
                            isBuy = true;
                        }

                        tradingDetails.put(cursor.getLong(0), new TradeDetailPOJO(
                                cursor.getDouble(2),
                                isBuy,
                                cursor.getDouble(4),
                                cursor.getDouble(5)
                        ));

                        stockTitles.put(cursor.getLong(0), cursor.getString(1));

                        tradingCapitals.put(cursor.getLong(0), new TradingCapitalData(
                                cursor.getDouble(7),
                                cursor.getDouble(8),
                                cursor.getFloat(9)
                        ));

                        handleParentRecyclerView();

                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            showingTv.setText(" No data found ");
        }
    }

    private void handleParentRecyclerView() {
        //Sort Timestamp ArrayList

        if(timestamps != null && timestamps.size()>0 && quantities != null && tradingDetails != null && stockTitles != null && tradingCapitals != null){

            HashMap<String, ArrayList<Long>> timestampHashMap = separate();

            if(timestampHashMap.size()>0){

                ArrayList<String> dates = new ArrayList<>(timestampHashMap.keySet());

                Collections.sort(dates, Collections.reverseOrder());

                Log.i("TimestampsHashMap", timestampHashMap.toString());
                Log.i("TimestampsHashMap", dates.toString());

                ParentTradeListRecyclerViewAdapter parentAdapter = new ParentTradeListRecyclerViewAdapter(this, dates, timestampHashMap, quantities, stockTitles, tradingDetails, tradingCapitals, showingTv);

                recyclerView.setAdapter(parentAdapter);

                showingTv.setText(" Showing "+timestamps.size()+" entries ");

            }else {
                Toast.makeText(this, "No trades found, kindly save your trades", Toast.LENGTH_SHORT).show();
                showingTv.setText(" No data found ");
            }

        }else{
            Toast.makeText(this, "No trades found, kindly save your trades", Toast.LENGTH_SHORT).show();
            showingTv.setText(" No data found ");
        }
    }

    private HashMap<String, ArrayList<Long>> separate() {

        HashMap<String, ArrayList<Long>> hashMap = new HashMap<>();

        HashSet<String> dateSet = new HashSet<>();

        for(int i=0; i<timestamps.size(); i++){
            String date = convertTime(timestamps.get(i));

            dateSet.add(date);
        }

        for(String date: dateSet){

            ArrayList<Long> timestampsDateWise = new ArrayList<>();

            for(int j=0; j<timestamps.size(); j++){
                if(date.equals(convertTime(timestamps.get(j)))){
                    timestampsDateWise.add(timestamps.get(j));
                }
            }

            hashMap.put(date, timestampsDateWise);
        }

        return hashMap;
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.trade_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showingTv = (TextView) findViewById(R.id.trade_list_showing_tv);

        backBtn = (ImageView)findViewById(R.id.trade_list_layout_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);

    }
}