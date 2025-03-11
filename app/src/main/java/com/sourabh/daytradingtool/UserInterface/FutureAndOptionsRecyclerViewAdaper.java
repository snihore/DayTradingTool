package com.sourabh.daytradingtool.UserInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sourabh.daytradingtool.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FutureAndOptionsRecyclerViewAdaper extends RecyclerView.Adapter<FutureAndOptionsRecyclerViewAdaper.FutureAndOptionsViewHolder> {

    private ArrayList<HashMap> arrayList;

    public FutureAndOptionsRecyclerViewAdaper(ArrayList<HashMap> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FutureAndOptionsRecyclerViewAdaper.FutureAndOptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fno_recyclerview_item, parent, false);
        return new FutureAndOptionsRecyclerViewAdaper.FutureAndOptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureAndOptionsRecyclerViewAdaper.FutureAndOptionsViewHolder holder, int position) {

        try {
            holder.stoplossPointsTv.setText(String.valueOf("Stoploss points: "+arrayList.get(position).get("stoploss_points")));
            holder.lotsTv.setText(String.valueOf(arrayList.get(position).get("lots")));
            holder.totalLossTv.setText(String.valueOf(arrayList.get(position).get("total_loss")));
            holder.quantityTv.setText(String.valueOf(arrayList.get(position).get("quantity")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FutureAndOptionsViewHolder extends RecyclerView.ViewHolder{

        private TextView stoplossPointsTv, lotsTv, quantityTv, totalLossTv;

        public FutureAndOptionsViewHolder(@NonNull View itemView) {
            super(itemView);

            stoplossPointsTv = (TextView) itemView.findViewById(R.id.fno_item_stoploss_points);
            lotsTv = (TextView) itemView.findViewById(R.id.fno_item_lots_tv);
            quantityTv = (TextView) itemView.findViewById(R.id.fno_item_quantity_tv);
            totalLossTv = (TextView) itemView.findViewById(R.id.fno_item_total_loss_tv);
        }
    }
}
