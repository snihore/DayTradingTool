package com.sourabh.daytradingtool.UserInterface;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.PositionSizeActivity;
import com.sourabh.daytradingtool.R;
import com.sourabh.daytradingtool.TradeListActivity;
import com.sourabh.daytradingtool.Utils.FormatUtils;


public class MarginInfoDialog {

    private AlertDialog dialog;

    private TradingCapitalData tradingCapitalData;

    public void view(PositionSizeActivity activity, TradingCapitalData tradingCapitalData){
        this.tradingCapitalData = tradingCapitalData;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.full_screen_alert);
        View view1 = activity.getLayoutInflater().inflate(R.layout.margin_info_layout, null);
        builder.setView(view1);
        try{
            setViews(view1);
            dialog = builder.create();
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
            if(dialog != null){
                dialog.dismiss();
            }
        }
    }

    public void view(TradeListActivity activity, TradingCapitalData tradingCapitalData){
        this.tradingCapitalData = tradingCapitalData;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.full_screen_alert);
        View view1 = activity.getLayoutInflater().inflate(R.layout.margin_info_layout, null);
        builder.setView(view1);
        try{
            setViews(view1);
            dialog = builder.create();
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
            if(dialog != null){
                dialog.dismiss();
            }
        }
    }

    private void setViews(View view) {
        TextView tradingCapitalTv = (TextView)view.findViewById(R.id.margin_info_trading_capital);
        TextView riskPerTradeTv = (TextView)view.findViewById(R.id.margin_info_risk_per_trade);
        TextView marginTv = (TextView)view.findViewById(R.id.margin_info_margin);

        ImageView backBtn = (ImageView) view.findViewById(R.id.margin_info_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });

        try{

            if(tradingCapitalData != null){

                tradingCapitalTv.setText(
                        "\u20B9 " +FormatUtils.addCommasInNumber(tradingCapitalData.getTradingCapital())
                );
                riskPerTradeTv.setText(
                        "\u20B9 " +FormatUtils.addCommasInNumber(tradingCapitalData.getRiskPerTrade())
                );
                marginTv.setText(
                        tradingCapitalData.getMargin()+"%"
                );

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

