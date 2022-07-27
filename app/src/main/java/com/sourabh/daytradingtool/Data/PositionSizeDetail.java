package com.sourabh.daytradingtool.Data;

import java.io.Serializable;

public class PositionSizeDetail implements Serializable {
    private int quantity;
    private float riskToReward;
    private double profit;
    private float profitByPercentage;
    private double profitPerShare;
    private float profitPerShareByPercentage;
    private double loss;
    private float lossByPercentage;
    private double lossPerShare;
    private float lossPerShareByPercentage;
    private double marginRequired;
    private double actualCapitalRequired;

    public PositionSizeDetail() {
    }

    public PositionSizeDetail(int quantity, float riskToReward, double profit, float profitByPercentage, double profitPerShare, float profitPerShareByPercentage, double loss, float lossByPercentage, double lossPerShare, float lossPerShareByPercentage, double marginRequired, double actualCapitalRequired) {
        this.quantity = quantity;
        this.riskToReward = riskToReward;
        this.profit = profit;
        this.profitByPercentage = profitByPercentage;
        this.profitPerShare = profitPerShare;
        this.profitPerShareByPercentage = profitPerShareByPercentage;
        this.loss = loss;
        this.lossByPercentage = lossByPercentage;
        this.lossPerShare = lossPerShare;
        this.lossPerShareByPercentage = lossPerShareByPercentage;
        this.marginRequired = marginRequired;
        this.actualCapitalRequired = actualCapitalRequired;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getRiskToReward() {
        return riskToReward;
    }

    public void setRiskToReward(float riskToReward) {
        this.riskToReward = riskToReward;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public float getProfitByPercentage() {
        return profitByPercentage;
    }

    public void setProfitByPercentage(float profitByPercentage) {
        this.profitByPercentage = profitByPercentage;
    }

    public double getProfitPerShare() {
        return profitPerShare;
    }

    public void setProfitPerShare(double profitPerShare) {
        this.profitPerShare = profitPerShare;
    }

    public float getProfitPerShareByPercentage() {
        return profitPerShareByPercentage;
    }

    public void setProfitPerShareByPercentage(float profitPerShareByPercentage) {
        this.profitPerShareByPercentage = profitPerShareByPercentage;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public float getLossByPercentage() {
        return lossByPercentage;
    }

    public void setLossByPercentage(float lossByPercentage) {
        this.lossByPercentage = lossByPercentage;
    }

    public double getLossPerShare() {
        return lossPerShare;
    }

    public void setLossPerShare(double lossPerShare) {
        this.lossPerShare = lossPerShare;
    }

    public float getLossPerShareByPercentage() {
        return lossPerShareByPercentage;
    }

    public void setLossPerShareByPercentage(float lossPerShareByPercentage) {
        this.lossPerShareByPercentage = lossPerShareByPercentage;
    }

    public double getMarginRequired() {
        return marginRequired;
    }

    public void setMarginRequired(double marginRequired) {
        this.marginRequired = marginRequired;
    }

    public double getActualCapitalRequired() {
        return actualCapitalRequired;
    }

    public void setActualCapitalRequired(double actualCapitalRequired) {
        this.actualCapitalRequired = actualCapitalRequired;
    }

    @Override
    public String toString() {
        return "PositionSizeDetail{" +
                "quantity=" + quantity +
                ",\n riskToReward=" + riskToReward +
                ",\n profit=" + profit +
                ",\n profitByPercentage=" + profitByPercentage +
                ",\n profitPerShare=" + profitPerShare +
                ",\n profitPerShareByPercentage=" + profitPerShareByPercentage +
                ",\n loss=" + loss +
                ",\n lossByPercentage=" + lossByPercentage +
                ",\n lossPerShare=" + lossPerShare +
                ",\n lossPerShareByPercentage=" + lossPerShareByPercentage +
                ",\n marginRequired=" + marginRequired +
                ",\n actualCapitalRequired=" + actualCapitalRequired +
                '}';
    }
}
