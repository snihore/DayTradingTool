package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Database.PositionSizeDetailDB;
import com.sourabh.daytradingtool.UserInterface.TradeListItemClickListener;
import com.sourabh.daytradingtool.UserInterface.TradeListRecyclerViewAdapter;
import com.sourabh.daytradingtool.UserInterface.ViewPositionSizeLayoutDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TradeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Cursor cursor;

    private ArrayList<Long> timestamps = new ArrayList<>();
    private HashMap<Long, Integer> quantities = new HashMap<>();
    private HashMap<Long, String> stockTitles = new HashMap<>();
    private HashMap<Long, TradeDetailPOJO> tradingDetails = new HashMap<>();


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
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                }else{

                    while(cursor.moveToNext()){

                        Log.i("CURSOR: ", "Timestamp: "+cursor.getString(0)+",\n" +
                                "StockTitle: "+cursor.getString(1)+",\n" +
                                "EntryPrice: "+cursor.getString(2)+",\n" +
                                "IsBuy: "+cursor.getString(3)+",\n" +
                                "Stoploss: "+cursor.getString(4)+",\n" +
                                "ExitPrice: "+cursor.getString(5)+",\n" +
                                "Quantity: "+cursor.getString(6));

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

                        handleRecyclerView();

                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleRecyclerView() {

        //Sort Timestamp ArrayList
        Collections.sort(timestamps, Collections.reverseOrder());

        TradeListItemClickListener tradeListItemClickListener = new TradeListItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                //View Position Size Layout
                ViewPositionSizeLayoutDialog viewPositionSizeLayoutDialog = new ViewPositionSizeLayoutDialog(TradeListActivity.this);

                viewPositionSizeLayoutDialog.view();
            }
        };

        if(timestamps != null && timestamps.size()>0 && quantities != null && tradingDetails != null){
            TradeListRecyclerViewAdapter tradeListRecyclerViewAdapter = new TradeListRecyclerViewAdapter(
                    this,
                    timestamps,
                    quantities,
                    stockTitles,
                    tradingDetails,
                    tradeListItemClickListener
            );

            recyclerView.setAdapter(tradeListRecyclerViewAdapter);
        }else{
            Toast.makeText(this, "No trades found, kindly save your trades", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.trade_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}