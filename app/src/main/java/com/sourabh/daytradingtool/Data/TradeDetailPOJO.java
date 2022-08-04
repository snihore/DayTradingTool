package com.sourabh.daytradingtool.Data;

import java.io.Serializable;

public class TradeDetailPOJO implements Serializable {

    private double entryPrice;
    private boolean isBuy;
    private double stoploss;
    private double exitPrice;

    public TradeDetailPOJO(double entryPrice, boolean isBuy, double stoploss, double exitPrice){
        this.entryPrice = entryPrice;
        this.isBuy = isBuy;
        this.stoploss = stoploss;
        this.exitPrice = exitPrice;
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

    public double getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(double exitPrice) {
        this.exitPrice = exitPrice;
    }

    @Override
    public String toString() {
        return "TradeDetailPOJO{" +
                "entryPrice=" + entryPrice +
                ", isBuy=" + isBuy +
                ", stoploss=" + stoploss +
                ", exitPrice=" + exitPrice +
                '}';
    }
}
