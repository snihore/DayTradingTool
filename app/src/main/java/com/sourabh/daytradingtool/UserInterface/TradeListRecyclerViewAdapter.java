package com.sourabh.daytradingtool.UserInterface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sourabh.daytradingtool.Data.TradeDetail;
import com.sourabh.daytradingtool.Data.TradeDetailPOJO;
import com.sourabh.daytradingtool.Data.TradingCapitalData;
import com.sourabh.daytradingtool.Database.PositionSizeDetailDB;
import com.sourabh.daytradingtool.Database.TradingCapitalDetailDB;
import com.sourabh.daytradingtool.R;
import com.sourabh.daytradingtool.TradeListActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class TradeListRecyclerViewAdapter extends RecyclerView.Adapter<TradeListRecyclerViewAdapter.TradeListRecyclerViewHolder> {

    private ParentTradeListRecyclerViewAdapter parentTradeListRecyclerViewAdapter;

    private Context context;
    private ArrayList<Long> timestamps;
    private HashMap<String, ArrayList<Long>> timestampHashMap;
    private HashMap<Long, Integer> quantities;
    private HashMap<Long, String> stockTitles;
    private HashMap<Long, TradeDetailPOJO> tradingDetails;
    private HashMap<Long, TradingCapitalData> tradingCapitals;
    private TextView showingTv;

    private TradeListItemClickListener tradeListItemClickListener;



    public TradeListRecyclerViewAdapter(ParentTradeListRecyclerViewAdapter parentTradeListRecyclerViewAdapter, Context context, ArrayList<Long> timestamps, HashMap<String, ArrayList<Long>> timestampHashMap, HashMap<Long, Integer> quantities, HashMap<Long, String> stockTitles, HashMap<Long, TradeDetailPOJO> tradingDetails, HashMap<Long, TradingCapitalData> tradingCapitals, TextView showingTv,  TradeListItemClickListener tradeListItemClickListener) {
        this.parentTradeListRecyclerViewAdapter = parentTradeListRecyclerViewAdapter;
        this.context = context;
        this.timestamps = timestamps;
        this.timestampHashMap = timestampHashMap;
        this.quantities = quantities;
        this.stockTitles = stockTitles;
        this.tradingDetails = tradingDetails;
        this.tradingCapitals = tradingCapitals;
        this.showingTv = showingTv;
        this.tradeListItemClickListener = tradeListItemClickListener;

        Log.i("Timestamps", timestamps.toString());

    }

    @NonNull
    @Override
    public TradeListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.trade_list_item_layout, parent, false);
        return new TradeListRecyclerViewHolder(view, tradeListItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeListRecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Log.i("POSITION", String.valueOf(position));
        TradeDetailPOJO tradeDetailPOJO = tradingDetails.get(timestamps.get(position));
        TradingCapitalData tradingCapitalData = tradingCapitals.get(timestamps.get(position));

        holder.stockTitle.setText(String.valueOf(stockTitles.get(timestamps.get(position))));
        holder.entryPrice.setText(String.valueOf(tradingDetails.get(timestamps.get(position)).getEntryPrice()));
        holder.quantity.setText(String.valueOf(quantities.get(timestamps.get(position))));

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

        Log.i("ChildRV tradingdetails1", tradeDetailPOJO.toString());

        setPadding(holder.quantity);

        setRiskToReward(holder, tradeDetailPOJO, position);

        holder.moreOptions.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                //More Options
                PopupMenu popupMenu = new PopupMenu(context, view);

                showPopupMenuIcons(popupMenu);

                popupMenu.getMenuInflater().inflate(R.menu.trade_list_item_popup_menu, popupMenu.getMenu());


                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.view:
                                try{
                                    if (tradeDetailPOJO != null && tradingCapitalData != null){
                                        Log.i("ChildRV tradingdetails2", tradeDetailPOJO.toString());
                                        ViewPositionSizeLayoutDialog dialog = new ViewPositionSizeLayoutDialog((TradeListActivity) context, tradeDetailPOJO, tradingCapitalData, stockTitles.get(timestamps.get(position)));
                                        dialog.view();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.delete:
                                try{
                                    PositionSizeDetailDB db = new PositionSizeDetailDB(context);

                                    boolean result = db.delete(timestamps.get(position));

                                    if(result){
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                        if(timestamps.remove(timestamps.get(position))){
                                            notifyDataSetChanged();
                                            parentTradeListRecyclerViewAdapter.notifyDataSetChanged();
                                            if(showingTv != null ){
                                                if(timestamps.size() == 0){
                                                    showingTv.setText(" No data found ");
                                                }else{
                                                    setShowingTv();
                                                }

                                            }
                                        }
                                    }else{
                                        Toast.makeText(context, "Not deleted, please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }

                        return true;
                    }
                });
            }
        });


    }

    private void setShowingTv() {

        showingTv.setText(" Showing "+timestampHashMap.values().size()+" entries ");
    }

    private void showPopupMenuIcons(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon",boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setRiskToReward(TradeListRecyclerViewHolder holder, TradeDetailPOJO tradeDetailPOJO, int position) {

        try{
            if(tradeDetailPOJO == null){
                return;
            }
            TradingCapitalDetailDB tradingCapitalDetailDB = new TradingCapitalDetailDB(context);

            TradeDetail tradeDetail = new TradeDetail(
                    tradeDetailPOJO.getEntryPrice(),
                    tradeDetailPOJO.isBuy(),
                    tradeDetailPOJO.getStoploss(),
                    "PRICE",
                    tradeDetailPOJO.getExitPrice(),
                    "PRICE",
                    tradingCapitalDetailDB.getTradingCapitalDetail()
            );

            if(tradeDetail != null && tradeDetail.getPositionSizeDetail() != null){
                holder.riskToRewardTv.setText("Risk to Reward 1:"+tradeDetail.getPositionSizeDetail().getRiskToReward());
                holder.stoploss.setText(
                        String.valueOf(tradingDetails.get(timestamps.get(position)).getStoploss())+"(" +
                                "" +tradeDetail.getPositionSizeDetail().getLossPerShareByPercentage()+
                                "%)"
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return timestamps.size();
    }

    public class TradeListRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView stockTitle, entryPrice, stoploss, quantity, riskToRewardTv;
        private ImageView moreOptions;

        private TradeListItemClickListener tradeListItemClickListener;

        public TradeListRecyclerViewHolder(@NonNull View itemView, TradeListItemClickListener tradeListItemClickListener) {
            super(itemView);

            stockTitle = (TextView) itemView.findViewById(R.id.trade_list_item_stock_title);
            entryPrice = (TextView) itemView.findViewById(R.id.trade_list_item_entry_price_tv);
            stoploss = (TextView) itemView.findViewById(R.id.trade_list_item_stoploss_tv);
            quantity = (TextView) itemView.findViewById(R.id.trade_list_item_quantity_tv);
            riskToRewardTv = (TextView) itemView.findViewById(R.id.trade_list_item_risk_to_reward_tv);
            moreOptions = (ImageView) itemView.findViewById(R.id.trade_list_item_more_option);

            this.tradeListItemClickListener = tradeListItemClickListener;

            //Click Events
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            tradeListItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public void setPadding(TextView textView){
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel1 = (int)(5 * density);
        int paddingPixel2 = (int)(20 * density);
        textView.setPadding(paddingPixel2, paddingPixel1, paddingPixel2, paddingPixel1);
    }

}
