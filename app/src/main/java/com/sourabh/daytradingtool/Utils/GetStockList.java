package com.sourabh.daytradingtool.Utils;

import android.content.Context;
import android.util.Log;

import com.sourabh.daytradingtool.Data.SearchStockItemDetail;
import com.sourabh.daytradingtool.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class GetStockList {

    public static ArrayList<SearchStockItemDetail> readData(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.nse_stocks_list);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        ArrayList<SearchStockItemDetail> searchStockItemDetails = new ArrayList<>();
        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");

                SearchStockItemDetail searchStockItemDetail = new SearchStockItemDetail(tokens[0], tokens[1]);

                searchStockItemDetails.add(searchStockItemDetail);
            }

            return searchStockItemDetails;
        } catch (IOException e) {
            Log.e("GetStockList", "Error" + line, e);
            e.printStackTrace();
        }

        return null;
    }
}
