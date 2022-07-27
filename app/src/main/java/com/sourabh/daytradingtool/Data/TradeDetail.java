package com.sourabh.daytradingtool.Data;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.Serializable;

public class TradeDetail {
    private double entryPrice;
    private boolean isBuy;
    private double stoploss;
    private String stoplossType;
    private double exitPrice;
    private String exitPriceType;
    private TradingCapitalData tradingCapitalData;
    private PositionSizeDetail positionSizeDetail = new PositionSizeDetail();

    private final String[] PRICE_TYPES = {"PRICE", "PERCENTAGE", "POINTS"};

    public TradeDetail(double entryPrice, boolean isBuy, double stoploss, String stoplossType, double exitPrice, String exitPriceType, TradingCapitalData tradingCapitalData) throws Exception{
        this.entryPrice = entryPrice;
        this.isBuy = isBuy;
        this.stoploss = stoploss;
        this.stoplossType = stoplossType;
        this.exitPrice = exitPrice;
        this.exitPriceType = exitPriceType;
        this.tradingCapitalData = tradingCapitalData;

        riskManagement();
    }

    private void riskManagement() throws Exception{

        if(isBuy){
            //Buy
            if(stoplossType.equals(PRICE_TYPES[0])){
                //Price
            }else if(stoplossType.equals(PRICE_TYPES[1])){
                //Percentage
                stoploss = entryPrice - ((entryPrice*stoploss)/100);
            }else if(stoplossType.equals(PRICE_TYPES[2])){
                //Points
                stoploss =entryPrice - stoploss;
            }

            if(exitPriceType.equals(PRICE_TYPES[0])){
                //Price
            }else if(exitPriceType.equals(PRICE_TYPES[1])){
                //Percentage
                exitPrice = entryPrice + ((entryPrice*exitPrice)/100);
            }else if(exitPriceType.equals(PRICE_TYPES[2])){
                //Points
                exitPrice = entryPrice + exitPrice;
            }

            if(entryPrice>stoploss && entryPrice<exitPrice){

                get(isBuy);

            }else {
                throw new Exception("Please enter valid inputs");
            }
        }else{
            //Sell
            if(stoplossType.equals(PRICE_TYPES[0])){
                //Price
            }else if(stoplossType.equals(PRICE_TYPES[1])){
                //Percentage
                stoploss = entryPrice + ((entryPrice*stoploss)/100);
            }else if(stoplossType.equals(PRICE_TYPES[2])){
                //Points
                stoploss =entryPrice + stoploss;
            }

            if(exitPriceType.equals(PRICE_TYPES[0])){
                //Price
            }else if(exitPriceType.equals(PRICE_TYPES[1])){
                //Percentage
                exitPrice = entryPrice - ((entryPrice*exitPrice)/100);
            }else if(exitPriceType.equals(PRICE_TYPES[2])){
                //Points
                exitPrice = entryPrice - exitPrice;
            }
            if(entryPrice<stoploss && entryPrice>exitPrice){
                get(isBuy);
            }else {
                throw new Exception("Please enter valid inputs");
            }
        }
    }

    private void get(boolean isBuy) {

        double profitPerShare;
        double lossPerShare;

        if(isBuy){
            //Buy
            entryPrice = round(entryPrice, 2);
            exitPrice = round(exitPrice, 2);
            stoploss = round(stoploss, 2);

            profitPerShare = round(exitPrice - entryPrice, 2);
            lossPerShare = round(entryPrice - stoploss, 2);


        }else{

            //Sell
            entryPrice = round(entryPrice, 2);
            exitPrice = round(exitPrice, 2);
            stoploss = round(stoploss, 2);

            profitPerShare = round(entryPrice - exitPrice, 2);
            lossPerShare = round(stoploss - entryPrice, 2);

        }

        double riskPerTrade = round(tradingCapitalData.getRiskPerTrade(), 2);
        double tradingCapital = round(tradingCapitalData.getTradingCapital(), 2);
        double margin = round(tradingCapitalData.getMargin(), 2);

        int quantity = (int) (riskPerTrade/lossPerShare);

        double entryPrice2 = round((entryPrice*margin)/100, 2);

        double marginRequired = round(quantity*entryPrice2, 2);

        double actualCapitalRequired = round(quantity*entryPrice, 2);

        if(marginRequired > tradingCapital){

            quantity = (int) (tradingCapital/entryPrice2);

            marginRequired = round(quantity*entryPrice2, 2);

            actualCapitalRequired = round(quantity*entryPrice, 2);

        }

        double profit = round(quantity*profitPerShare, 2);
        double loss = round(quantity*lossPerShare, 2);

        float profitPerShareByPercentage = (float)round((profitPerShare/entryPrice)*100, 2);
        float lossPerShareByPercentage = (float) round((lossPerShare/entryPrice)*100, 2);
        float profitByPercentage = (float) round((profit/marginRequired)*100, 2);
        float lossByPercentage = (float) round((loss/marginRequired)*100, 2);
        float riskToReward = (float) round(profitPerShare/lossPerShare, 2);

        positionSizeDetail.setProfitPerShare(profitPerShare);
        positionSizeDetail.setLossPerShare(lossPerShare);
        positionSizeDetail.setQuantity(quantity);
        positionSizeDetail.setMarginRequired(marginRequired);
        positionSizeDetail.setActualCapitalRequired(actualCapitalRequired);
        positionSizeDetail.setProfit(profit);
        positionSizeDetail.setLoss(loss);
        positionSizeDetail.setProfitPerShareByPercentage(profitPerShareByPercentage);
        positionSizeDetail.setLossPerShareByPercentage(lossPerShareByPercentage);
        positionSizeDetail.setProfitByPercentage(profitByPercentage);
        positionSizeDetail.setLossByPercentage(lossByPercentage);
        positionSizeDetail.setRiskToReward(riskToReward);


    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public double getStoploss() {
        return stoploss;
    }

    public void setStoploss(double stoploss) {
        this.stoploss = stoploss;
    }

    public String getStoplossType() {
        return stoplossType;
    }

    public void setStoplossType(String stoplossType) {
        this.stoplossType = stoplossType;
    }

    public double getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(double exitPrice) {
        this.exitPrice = exitPrice;
    }

    public String getExitPriceType() {
        return exitPriceType;
    }

    public void setExitPriceType(String exitPriceType) {
        this.exitPriceType = exitPriceType;
    }

    public TradingCapitalData getTradingCapitalData() {
        return tradingCapitalData;
    }

    public void setTradingCapitalData(TradingCapitalData tradingCapitalData) {
        this.tradingCapitalData = tradingCapitalData;
    }

    public PositionSizeDetail getPositionSizeDetail() {
        return positionSizeDetail;
    }

    public void setPositionSizeDetail(PositionSizeDetail positionSizeDetail) {
        this.positionSizeDetail = positionSizeDetail;
    }

    @Override
    public String toString() {
        return "TradeDetail{" +
                "entryPrice=" + entryPrice +
                ",\n isBuy=" + isBuy +
                ",\n stoploss=" + stoploss +
                ",\n exitPrice=" + exitPrice +
                '}';
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
