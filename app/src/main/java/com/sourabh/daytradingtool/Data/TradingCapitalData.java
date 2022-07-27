package com.sourabh.daytradingtool.Data;

public class TradingCapitalData {
    private double tradingCapital;
    private double riskPerTrade;
    private float margin;

    public TradingCapitalData(double tradingCapital, double riskPerTrade, float margin) {
        this.tradingCapital = tradingCapital;
        this.riskPerTrade = riskPerTrade;
        this.margin = margin;
    }

    public double getTradingCapital() {
        return tradingCapital;
    }

    public void setTradingCapital(double tradingCapital) {
        this.tradingCapital = tradingCapital;
    }

    public double getRiskPerTrade() {
        return riskPerTrade;
    }

    public void setRiskPerTrade(double riskPerTrade) {
        this.riskPerTrade = riskPerTrade;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }
}
