package com.sourabh.daytradingtool.Utils;

import java.util.ArrayList;

public class ChargesUtils {

    public static class ZerodhaCharges {

        public static final float BROKERAGE_MAX_B_S = 20;
        public static final float BROKERAGE_DELIVERY = 0;
        public static final float BROKERAGE_INTRADAY_PER_B_S = 0.03f;
        public static final float BROKERAGE_FUTURES_PER_B_S = 0.03f;
        public static final float BROKERAGE_OPTIONS_B_S = 20;

        public static final float STT_CTT_DELIVERY_PER_B_S = 0.1f;
        public static final float STT_CTT_INTRADAY_PER_S = 0.025f;
        public static final float STT_CTT_FUTURES_PER_S = 0.0125f;
        public static final float STT_CTT_OPTIONS_PER_PREMIUM_S = 0.125f;

        public static final float TRANSACTION_CHARGES_DELIVERY_NSE_PER = 0.00322f;
        public static final float TRANSACTION_CHARGES_DELIVERY_BSE_PER = 0.00375f;
        public static final float TRANSACTION_CHARGES_INTRADAY_NSE_PER = 0.00322f;
        public static final float TRANSACTION_CHARGES_INTRADAY_BSE_PER = 0.00375f;
        public static final float TRANSACTION_CHARGES_FUTURES_NSE_PER = 0.00188f;
        public static final float TRANSACTION_CHARGES_OPTIONS_PREMIUM_NSE_PER =  0.0495f;

        public static final float GST_PER = 18; // 18% on (brokerage + SEBI + transaction charges)

        public static final float SEBI_CHARGES = 10; // 10 rs / Crore + GST

        public static final float STEMP_CHARGES_DELIVERY_PER_B_MIN = 0.015f; // 0.015% or ₹1500 / crore on buy side
        public static final float STEMP_CHARGES_DELIVERY_B_MAX = 1500;
        public static final float STEMP_CHARGES_INTRADAY_PER_B_MIN = 0.003f; // 0.003% or ₹300 / crore on buy side
        public static final float STEMP_CHARGES_INTRADAY_B_MAX = 300;
        public static final float STEMP_CHARGES_FUTURES_PER_B_MIN = 0.002f; // 0.002% or ₹200 / crore on buy side
        public static final float STEMP_CHARGES_FUTURES_B_MAX = 200;
        public static final float STEMP_CHARGES_OPTIONS_PER_B_MIN = 0.003f; // 0.003% or ₹300 / crore on buy side
        public static final float STEMP_CHARGES_OPTIONS_B_MAX = 300;

        public static final float DP_CHARGES_DELIVERY = 13.5f; // 13.5 + gst
    }
}
