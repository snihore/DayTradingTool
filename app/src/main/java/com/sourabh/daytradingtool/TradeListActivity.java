package com.sourabh.daytradingtool;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sourabh.daytradingtool.Database.PositionSizeDetailDB;

public class TradeListActivity extends AppCompatActivity {

    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        try {

            PositionSizeDetailDB positionSizeDetailDB = new PositionSizeDetailDB(this);

            cursor = positionSizeDetailDB.getData();

            if(cursor != null){

                if(cursor.getCount() == 0){
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                }else{

                    while(cursor.moveToNext()){

                        Log.i("Timestamp: "+cursor.getString(0), "StockTitle: "+cursor.getString(1));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}