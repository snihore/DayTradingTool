package com.sourabh.daytradingtool.UserInterface;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sourabh.daytradingtool.Data.CalculateCharges;
import com.sourabh.daytradingtool.Data.PositionSizeDetail;
import com.sourabh.daytradingtool.Data.TradeDetail;
import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.Database.TradingCapitalDetailDB;
import com.sourabh.daytradingtool.MainActivity;
import com.sourabh.daytradingtool.R;
import com.sourabh.daytradingtool.TradeListActivity;
import com.sourabh.daytradingtool.Utils.FormatUtils;

import java.text.DecimalFormat;

public class ViewPositionSizeLayoutDialog {

    private TradeListActivity activity;
    private TradeDetailPOJO tradeDetailPOJO;

    private TextView quantityTv, riskToRewardTv, profitTv, profitPerShareTv, lossTv, lossPerShareTv, marginRequiredTv, actualCapitalRequiredTv;
    private TextView stockTitleTv, entryPriceTv, stoplossTv, exitPriceTv, profitChargesTv, lossChargesTv;

    private ImageView backBtn;

    private PositionSizeDetail positionSizeDetail;
    private TradingCapitalData tradingCapitalData;
    private String stockTitle;

    private AlertDialog dialog;

    public ViewPositionSizeLayoutDialog(TradeListActivity activity, TradeDetailPOJO tradeDetailPOJO, TradingCapitalData tradingCapitalData, String stockTitle) {
        this.activity = activity;
        this.tradeDetailPOJO = tradeDetailPOJO;
        this.stockTitle = stockTitle;
        this.tradingCapitalData = tradingCapitalData;

    }

    public void view(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.full_screen_alert);
        View view1 = activity.getLayoutInflater().inflate(R.layout.view_position_size_layout_dialog, null);
        builder.setView(view1);
        try{
            initViews(view1);
            setViews();
            dialog = builder.create();
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
            if(dialog != null){
                dialog.dismiss();
            }
        }
    }

    private void initViews(View view) {
        quantityTv = (TextView)view.findViewById(R.id.quantity_tv);
        riskToRewardTv = (TextView)view.findViewById(R.id.risk_to_reward_tv);
        profitTv = (TextView)view.findViewById(R.id.profit_tv);
        profitPerShareTv = (TextView)view.findViewById(R.id.profit_per_share_tv);
        lossTv = (TextView)view.findViewById(R.id.loss_tv);
        lossPerShareTv = (TextView)view.findViewById(R.id.loss_per_share_tv);
        marginRequiredTv = (TextView)view.findViewById(R.id.margin_required_tv);
        actualCapitalRequiredTv = (TextView)view.findViewById(R.id.actual_capital_required_tv);
        stockTitleTv = (TextView) view.findViewById(R.id.stock_title_tv);
        entryPriceTv = (TextView) view.findViewById(R.id.entry_price_tv);
        stoplossTv = (TextView) view.findViewById(R.id.stoploss_tv);
        exitPriceTv = (TextView) view.findViewById(R.id.exit_price_tv);
        backBtn = (ImageView) view.findViewById(R.id.view_position_size_back_btn);
        profitChargesTv = (TextView)view.findViewById(R.id.profit_charges);
        lossChargesTv = (TextView)view.findViewById(R.id.loss_charges);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setViews() {

        try{
            if(tradeDetailPOJO == null || stockTitle == null){
                return;
            }

            stockTitleTv.setText(stockTitle);
            entryPriceTv.setText(String.valueOf(tradeDetailPOJO.getEntryPrice()));
            stoplossTv.setText(String.valueOf(tradeDetailPOJO.getStoploss()));
            exitPriceTv.setText(String.valueOf(tradeDetailPOJO.getExitPrice()));


            TradeDetail tradeDetail = new TradeDetail(
                    tradeDetailPOJO.getEntryPrice(),
                    tradeDetailPOJO.isBuy(),
                    tradeDetailPOJO.getStoploss(),
                    "PRICE",
                    tradeDetailPOJO.getExitPrice(),
                    "PRICE",
                    tradingCapitalData
            );

            if(tradeDetail != null && tradeDetail.getPositionSizeDetail() != null){

                positionSizeDetail = tradeDetail.getPositionSizeDetail();

                if(positionSizeDetail == null || tradingCapitalData == null){
                    return;
                }

                //Set Views
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
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void setCharges(double entry, double exit, double stoploss, int quantity) {

        //PROFIT
        double profitCharges = 0;
        if(entry < exit){
            //BUY
            profitCharges = CalculateCharges.getZerodhaChargesIntraday(entry, exit, quantity);
        }else{
            //SELL
            profitCharges = CalculateCharges.getZerodhaChargesIntraday(exit, entry, quantity);
        }
        if(tradingCapitalData.getMargin() == 100){
            if(entry < exit){
                //BUY
                profitCharges = CalculateCharges.getZerodhaChargesDelivery(entry, exit, quantity);
            }else{
                //SELL
                profitCharges = CalculateCharges.getZerodhaChargesDelivery(exit, entry, quantity);
            }
        }

        //LOSS
        double lossCharges = 0;
        if(entry > stoploss){
            //BUY
            lossCharges = CalculateCharges.getZerodhaChargesIntraday(entry, stoploss, quantity);
        }else{
            //SELL
            lossCharges = CalculateCharges.getZerodhaChargesIntraday(stoploss, entry, quantity);
        }
        if(tradingCapitalData.getMargin() == 100){
            if(entry > stoploss){
                //BUY
                lossCharges = CalculateCharges.getZerodhaChargesDelivery(entry, stoploss, quantity);
            }else{
                //SELL
                lossCharges = CalculateCharges.getZerodhaChargesDelivery(stoploss, entry, quantity);
            }
        }

        //Set Views
        profitChargesTv.setText("\u20B9 "+profitCharges);
        lossChargesTv.setText("\u20B9 "+lossCharges);
    }

}
