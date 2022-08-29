package com.sourabh.daytradingtool.Utils;

import android.content.Context;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {

    public static String addCommasInNumber(double num){
        try{
            DecimalFormat df = new DecimalFormat("#,###.00");

            return df.format(num);
        }catch (Exception e){

        }

        return String.valueOf(num);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);

    }

    public static String floatToString(Float d) {
        if (d == null)
            return null;
        if (d.isNaN() || d.isInfinite())
            return d.toString();

        return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
    }
    public static String doubleToString(Double d) {
        if (d == null)
            return null;
        if (d.isNaN() || d.isInfinite())
            return d.toString();

        return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
    }
}
