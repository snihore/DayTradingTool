package com.sourabh.daytradingtool.UserInterface;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TradeListRecyclerViewAdapter extends RecyclerView.Adapter<TradeListRecyclerViewAdapter.TradeListRecyclerViewHolder> {

    private Context context;
    private ArrayList<Long> timestamps;
    private HashMap<Long, Integer> quantities;
    private HashMap<Long, String> stockTitles;
    private HashMap<Long, TradeDetailPOJO> tradingDetails;

    public TradeListRecyclerViewAdapter(Context context, ArrayList<Long> timestamps, HashMap<Long, Integer> quantities, HashMap<Long, String> stockTitles, HashMap<Long, TradeDetailPOJO> tradingDetails) {
        this.context = context;
        this.timestamps = timestamps;
        this.quantities = quantities;
        this.stockTitles = stockTitles;
        this.tradingDetails = tradingDetails;
    }

    @NonNull
    @Override
    public TradeListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.trade_list_item_layout, parent, false);
        return new TradeListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeListRecyclerViewHolder holder, int position) {

        holder.stockTitle.setText(String.valueOf(stockTitles.get(timestamps.get(position))));
        holder.entryPrice.setText(String.valueOf(tradingDetails.get(timestamps.get(position)).getEntryPrice()));
        holder.stoploss.setText(String.valueOf(tradingDetails.get(timestamps.get(position)).getStoploss()));
        holder.quantity.setText(String.valueOf(quantities.get(timestamps.get(position))));
        holder.time.setText(convertTime(timestamps.get(position)));

        boolean isBuy = tradingDetails.get(timestamps.get(position)).isBuy();

        if(isBuy){
            //BUY
            holder.quantity.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text_green_bg));
            holder.quantity.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else{
            //SELL
            holder.quantity.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text_red_bg));
            holder.quantity.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        setPadding(holder.quantity);


    }

    @Override
    public int getItemCount() {
        return timestamps.size();
    }

    public class TradeListRecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView stockTitle, entryPrice, stoploss, quantity, time;

        public TradeListRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            stockTitle = (TextView) itemView.findViewById(R.id.trade_list_item_stock_title);
            entryPrice = (TextView) itemView.findViewById(R.id.trade_list_item_entry_price_tv);
            stoploss = (TextView) itemView.findViewById(R.id.trade_list_item_stoploss_tv);
            quantity = (TextView) itemView.findViewById(R.id.trade_list_item_quantity_tv);
            time = (TextView) itemView.findViewById(R.id.trade_list_item_time_tv);
        }
    }

    private void setPadding(TextView textView){
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel1 = (int)(5 * density);
        int paddingPixel2 = (int)(20 * density);
        textView.setPadding(paddingPixel2, paddingPixel1, paddingPixel2, paddingPixel1);
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return format.format(date);

    }
}
