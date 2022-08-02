package com.sourabh.daytradingtool.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sourabh.daytradingtool.Utils.DatabaseUtils;

public class PositionSizeDetailDB extends SQLiteOpenHelper {


    public PositionSizeDetailDB(@Nullable Context context) {
        super(context, DatabaseUtils.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE IF NOT EXISTS "+DatabaseUtils.TABLE_NAME_POSITION_SIZE_DETAIL+" (" +
                "timestamp number PRIMARY KEY, " +
                "stocktitle text, " +
                "entryprice real, " +
                "isbuy number, " +
                "stoploss real, " +
                "exitprice real," +
                "quantity number)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean insertData(long timestamp, String stockTitle, double entryPrice, int isBuy, double stoploss, double exitPrice, int quantity){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(
                stockTitle == null ||
                        entryPrice == 0 ||
                        isBuy == -1 ||
                        stoploss == 0 ||
                        exitPrice == 0 ||
                        quantity == 0
        ){
            return false;
        }

        contentValues.put("timestamp", timestamp);
        contentValues.put("stocktitle", stockTitle);
        contentValues.put("entryprice", entryPrice);
        contentValues.put("isbuy", isBuy);
        contentValues.put("stoploss", stoploss);
        contentValues.put("exitprice", exitPrice);
        contentValues.put("quantity", quantity);

        long result = sqLiteDatabase.insert(DatabaseUtils.TABLE_NAME_POSITION_SIZE_DETAIL, null, contentValues);

        if(result == -1){
            return false;
        }
        return true;

    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+DatabaseUtils.TABLE_NAME_POSITION_SIZE_DETAIL, null);

        return cursor;
    }
}
