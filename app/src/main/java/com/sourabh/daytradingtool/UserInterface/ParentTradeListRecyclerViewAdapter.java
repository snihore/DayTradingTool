package com.sourabh.daytradingtool.UserInterface;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.R;
import com.sourabh.daytradingtool.TradeListActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class ParentTradeListRecyclerViewAdapter extends RecyclerView.Adapter<ParentTradeListRecyclerViewAdapter.ParentTradeListViewHolder>{

    private ArrayList<String> dates;
    private HashMap<String, ArrayList<Long>> timestampHashMap;

    //For Child RecyclerView
    private Context context;
    private HashMap<Long, Integer> quantities;
    private HashMap<Long, String> stockTitles;
    private HashMap<Long, TradeDetailPOJO> tradingDetails;
    private HashMap<Long, TradingCapitalData> tradingCapitals;

    private TextView showingTv;

    public ParentTradeListRecyclerViewAdapter(Context context, ArrayList<String> dates, HashMap<String, ArrayList<Long>> timestampHashMap, HashMap<Long, Integer> quantities, HashMap<Long, String> stockTitles, HashMap<Long, TradeDetailPOJO> tradingDetails, HashMap<Long, TradingCapitalData> tradingCapitals, TextView showingTv) {
        this.context = context;
        this.dates = dates;
        this.timestampHashMap = timestampHashMap;
        this.quantities = quantities;
        this.stockTitles = stockTitles;
        this.tradingDetails = tradingDetails;
        this.tradingCapitals = tradingCapitals;
        this.showingTv = showingTv;
    }

    @NonNull
    @Override
    public ParentTradeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.parent_trade_list_item_layout, parent, false);
        return new ParentTradeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentTradeListViewHolder holder, int position) {

        try{

            holder.dateTv.setText(dates.get(position));

            handleRecyclerView(holder.recyclerView, position);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class ParentTradeListViewHolder extends RecyclerView.ViewHolder{

        private TextView dateTv;
        private RecyclerView recyclerView;

        public ParentTradeListViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = (TextView) itemView.findViewById(R.id.parent_trade_list_item_tv);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.parent_trade_list_item_recycler_view);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    private void handleRecyclerView(RecyclerView recyclerView, int position) {

        //Sort Timestamp ArrayList
        ArrayList<Long> timestamps = timestampHashMap.get(dates.get(position));

        Collections.sort(timestamps, Collections.reverseOrder());

        Log.i("ITEM_TimestampsHashMap", timestampHashMap.toString());
        Log.i("ITEM_TimestampsHashMap", dates.toString());
        Log.i("ITEM_Timestamps", timestamps.toString());

        TradeListItemClickListener tradeListItemClickListener = new TradeListItemClickListener() {
            @Override
            public void onClick(View view, int position2) {

                //When click on Trade Item (Child recycler view)

                Log.i("Child RV Position", String.valueOf(position2));


                try{

                    Log.i("StockTitle", stockTitles.get(timestamps.get(position2)));

                    TradeDetailPOJO tradeDetailPOJO = tradingDetails.get(timestamps.get(position2));

                    ViewPositionSizeLayoutDialog viewPositionSizeLayoutDialog = new ViewPositionSizeLayoutDialog((TradeListActivity) context, tradeDetailPOJO, tradingCapitals.get(timestamps.get(position2)), stockTitles.get(timestamps.get(position2)));

                    viewPositionSizeLayoutDialog.view();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        if(timestamps != null && timestamps.size()>0 && quantities != null && tradingDetails != null && tradingCapitals != null){

            TradeListRecyclerViewAdapter tradeListRecyclerViewAdapter = new TradeListRecyclerViewAdapter(
                    this,
                    context,
                    timestamps,
                    timestampHashMap,
                    quantities,
                    stockTitles,
                    tradingDetails,
                    tradingCapitals,
                    showingTv,
                    tradeListItemClickListener
            );

            recyclerView.setAdapter(tradeListRecyclerViewAdapter);
        }else{

        }
    }

}
