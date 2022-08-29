package com.sourabh.daytradingtool.Data;

import android.util.Log;

import com.sourabh.daytradingtool.Utils.ChargesUtils;

public class CalculateCharges {

    public class GetIndividualCharges{
        private double brokerage;
        private double stt;
        private double transaction;
        private double gst;
        private double sebi;
        private double stamp;
        private double total;

        public GetIndividualCharges(double brokerage, double stt, double transaction, double gst, double sebi, double stamp, double total) {
            this.brokerage = brokerage;
            this.stt = stt;
            this.transaction = transaction;
            this.gst = gst;
            this.sebi = sebi;
            this.stamp = stamp;
            this.total = total;
        }

        public double getBrokerage() {
            return brokerage;
        }

        public double getStt() {
            return stt;
        }

        public double getTransaction() {
            return transaction;
        }

        public double getGst() {
            return gst;
        }

        public double getSebi() {
            return sebi;
        }

        public double getStamp() {
            return stamp;
        }

        public double getTotal() {
            return total;
        }
    }

    public GetIndividualCharges getZerodhaChargesDelivery(double buy, double sell, int quantity){

        if (buy == 0.0 || sell == 0.0 || quantity < 1){
            return null;
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

        return new GetIndividualCharges(
                brokerage,
                (sttSell+sttBuy),
                transactionChargesNSE,
                gst,
                sebiCharges,
                stampCharges,
                totalCharges
        );

    }

    public GetIndividualCharges getZerodhaChargesIntraday(double buy, double sell, int quantity){

        if (buy == 0.0 || sell == 0.0 || quantity < 1){
            return null;
        }

        double brokerage1 = quantity*((buy* ChargesUtils.ZerodhaCharges.BROKERAGE_INTRADAY_PER_B_S)/100);
        brokerage1 = round(brokerage1, 2);
        if(brokerage1 > ChargesUtils.ZerodhaCharges.BROKERAGE_MAX_B_S){
            brokerage1 = ChargesUtils.ZerodhaCharges.BROKERAGE_MAX_B_S;
        }
        double brokerage2 = quantity*((sell* ChargesUtils.ZerodhaCharges.BROKERAGE_INTRADAY_PER_B_S)/100);
        brokerage2 = round(brokerage2, 2);
        if(brokerage2 > ChargesUtils.ZerodhaCharges.BROKERAGE_MAX_B_S){
            brokerage2 = ChargesUtils.ZerodhaCharges.BROKERAGE_MAX_B_S;
        }

        double brokerage =brokerage1 + brokerage2;
        brokerage = round(brokerage, 2);

        double stt = quantity*((sell* ChargesUtils.ZerodhaCharges.STT_CTT_INTRADAY_PER_S)/100);
        stt = round(stt, 2);

        double stampCharges1 = quantity*((buy*ChargesUtils.ZerodhaCharges.STEMP_CHARGES_INTRADAY_PER_B_MIN)/100);
        double stampCharges2 = ChargesUtils.ZerodhaCharges.STEMP_CHARGES_INTRADAY_B_MAX/10000000; // per crore
        stampCharges1 = round(stampCharges1, 2);
        stampCharges2 = round(stampCharges2, 2);

        double stampCharges = stampCharges1;

//        if(stampCharges1>stampCharges2){
//            stampCharges = stampCharges2;
//        }

        double turnover = quantity*(buy+sell);
        turnover = round(turnover, 2);

        double transactionChargesNSE = (turnover* ChargesUtils.ZerodhaCharges.TRANSACTION_CHARGES_INTRADAY_NSE_PER)/100;
        transactionChargesNSE = round(transactionChargesNSE, 2);

        double gst = ((brokerage+transactionChargesNSE)* ChargesUtils.ZerodhaCharges.GST_PER)/100;
        gst = round(gst, 2);

        double sebiCharges = ChargesUtils.ZerodhaCharges.SEBI_CHARGES /10000000;// per crore
        sebiCharges = round(sebiCharges, 2);


        double totalCharges = brokerage + stt + stampCharges + transactionChargesNSE + gst + sebiCharges;
        totalCharges = round(totalCharges, 2);



        Log.i("ZERODHA_CHARGES", "Brokerage: "+brokerage+"\n" +
                "STT Sell: "+stt+"\n" +
                "Transaction Charges: "+transactionChargesNSE+"\n" +
                "GST: "+gst+"\n" +
                "Turnover: "+turnover+"\n" +
                "SEBI Charges: "+sebiCharges+"\n" +
                "Stamp Charegs: "+stampCharges+"\n" +
                "Total Charges: "+totalCharges);

        return new GetIndividualCharges(
                brokerage,
                stt,
                transactionChargesNSE,
                gst,
                sebiCharges,
                stampCharges,
                totalCharges
        );
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
