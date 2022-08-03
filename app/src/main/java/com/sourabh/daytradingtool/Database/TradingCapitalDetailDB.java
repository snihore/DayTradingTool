package com.sourabh.daytradingtool.Database;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.Utils.DatabaseUtils;

public class TradingCapitalDetailDB {

    private Context context;

    public TradingCapitalDetailDB(Context context) {
        this.context = context;
    }

    public boolean saveTradingCapitalDetail(TradingCapitalData tradingCapitalData){

        if(tradingCapitalData == null){
            return false;
        }

        try{
            SharedPreferences.Editor editor = context.getSharedPreferences(DatabaseUtils.TRADING_CAPITAL_DETAIL, MODE_PRIVATE).edit();

            editor.putString("tradingcapital", String.valueOf(tradingCapitalData.getTradingCapital()));
            editor.putString("riskpertrade", String.valueOf(tradingCapitalData.getRiskPerTrade()));
            editor.putString("margin", String.valueOf(tradingCapitalData.getMargin()));

            editor.apply();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public TradingCapitalData getTradingCapitalDetail(){

        try{

            SharedPreferences prefs = context.getSharedPreferences(DatabaseUtils.TRADING_CAPITAL_DETAIL, MODE_PRIVATE);

            String tradingCapitalStr = prefs.getString("tradingcapital", "");
            String riskPerTradeStr = prefs.getString("riskpertrade", "");
            String marginStr = prefs.getString("margin", "");

            if(tradingCapitalStr.matches("") || riskPerTradeStr.matches("") || marginStr.matches("")){
                return new TradingCapitalData();
            }

            TradingCapitalData tradingCapitalData = new TradingCapitalData(
                    Double.parseDouble(tradingCapitalStr),
                    Double.parseDouble(riskPerTradeStr),
                    Float.parseFloat(marginStr)
            );

            return tradingCapitalData;

        }catch (Exception e){
            e.printStackTrace();
            return new TradingCapitalData();
        }
    }

}
