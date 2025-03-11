package com.sourabh.daytradingtool.UserInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sourabh.daytradingtool.R;

public class HelpRecyclerViewAdaper extends RecyclerView.Adapter<HelpRecyclerViewAdaper.HelpViewHolder>{

    private String[] titles = {
            "About",
            "Position Size",
            "Profit & Loss",
            "Taxes & Charges",
            "Save trades"
    };

    private String[] descriptions = {
            "TradeSizer is a risk management tool designed to help traders accurately calculate their position size in trades. This is particularly valuable for both new and experienced traders who need to manage risk effectively in the market.",
            "This app allows traders and investors to calculate the exact number of shares they should buy to avoid significant losses, ensuring their account remains stable even during a series of losing trades.",
            "TradeSizer provides insights into potential profits and losses for each trade, helping traders plan their strategies more effectively.",
            "A crucial aspect many traders overlook is 'taxes and fees.' Every trade comes with associated costs, such as brokerage fees. TradeSizer includes a unique feature that calculates these expenses, enabling traders to manage their capital efficiently and sustain long-term market participation.",
            "Successful trading often involves pre-planning trades during off-market hours and executing them during live trading sessions. TradeSizer allows traders to save their planned trades for quick execution when the right market opportunities arise."
    };


    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.help_recycler_view_item, parent, false);
        return new HelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {

        holder.title.setText(titles[position]);
        holder.description.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class HelpViewHolder extends RecyclerView.ViewHolder{

        private TextView title, description;

        public HelpViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.help_recycler_view_item_title);
            description = (TextView) itemView.findViewById(R.id.help_recycler_view_item_description);
        }
    }
}
