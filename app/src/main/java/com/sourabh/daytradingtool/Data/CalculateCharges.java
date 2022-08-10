package com.sourabh.daytradingtool.Data;

import android.util.Log;

import com.sourabh.daytradingtool.Utils.ChargesUtils;

public class CalculateCharges {

    public static double getZerodhaChargesDelivery(double buy, double sell, int quantity){

        if (buy == 0.0 || sell == 0.0 || quantity < 1){
            return 0.0;
        }

        double brokerage = 0;

        double sttBuy = quantity*((buy* ChargesUtils.ZerodhaCharges.STT_CTT_DELIVERY_PER_B_S)/100);
        sttBuy = round(sttBuy, 2);
        double sttSell = quantity*((sell* ChargesUtils.ZerodhaCharges.STT_CTT_DELIVERY_PER_B_S)/100);
        sttSell = round(sttSell, 2);

        double stampCharges1 = quantity*((buy*ChargesUtils.ZerodhaCharges.STEMP_CHARGES_DELIVERY_PER_B_MIN)/100);
        double stampCharges2 = ChargesUtils.ZerodhaCharges.STEMP_CHARGES_DELIVERY_B_MAX/10000000; // per crore
        stampCharges1 = round(stampCharges1, 2);
        stampCharges2 = round(stampCharges2, 2);

        double stampCharges = stampCharges1;

//        if(stampCharges1>stampCharges2){
//            stampCharges = stampCharges2;
//        }

        double turnover = quantity*(buy+sell);
        turnover = round(turnover, 2);

        double transactionChargesNSE = (turnover* ChargesUtils.ZerodhaCharges.TRANSACTION_CHARGES_DELIVERY_NSE_PER)/100;
        transactionChargesNSE = round(transactionChargesNSE, 2);

        double gst = ((brokerage+transactionChargesNSE)* ChargesUtils.ZerodhaCharges.GST_PER)/100;
        gst = round(gst, 2);

        double sebiCharges = ChargesUtils.ZerodhaCharges.SEBI_CHARGES /10000000;// per crore
        sebiCharges = round(sebiCharges, 2);

        double dpCharges = ChargesUtils.ZerodhaCharges.DP_CHARGES_DELIVERY +
                ((ChargesUtils.ZerodhaCharges.DP_CHARGES_DELIVERY*18)/100);
        dpCharges = round(dpCharges, 2);

        double totalCharges = brokerage + sttBuy + sttSell + stampCharges + transactionChargesNSE + gst + sebiCharges + dpCharges;
        totalCharges = round(totalCharges, 2);



        Log.i("ZERODHA_CHARGES", "Brokerage: "+brokerage+"\n" +
                "STT BUY: "+sttBuy+"\n" +
                "STT SELL: "+sttSell+"\n" +
                "STT Total: "+(sttSell+sttBuy)+"\n" +
                "Transaction Charges: "+transactionChargesNSE+"\n" +
                "GST: "+gst+"\n" +
                "Turnover: "+turnover+"\n" +
                "DP Charges: "+dpCharges+"\n" +
                "SEBI Charges: "+sebiCharges+"\n" +
                "Stamp Charegs: "+stampCharges+"\n" +
                "Total Charges: "+totalCharges);

        return totalCharges;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
