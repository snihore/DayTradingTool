package com.sourabh.daytradingtool.Data;

public class TradeDetail {
    private double entryPrice;
    private boolean isBuy;
    private double stoploss;
    private String stoplossType;
    private double exitPrice;
    private String exitPriceType;
    private TradingCapitalData tradingCapitalData;

    public TradeDetail(double entryPrice, boolean isBuy, double stoploss, String stoplossType, double exitPrice, String exitPriceType, TradingCapitalData tradingCapitalData) {
        this.entryPrice = entryPrice;
        this.isBuy = isBuy;
        this.stoploss = stoploss;
        this.stoplossType = stoplossType;
        this.exitPrice = exitPrice;
        this.exitPriceType = exitPriceType;
        this.tradingCapitalData = tradingCapitalData;
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
}
