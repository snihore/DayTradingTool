package com.sourabh.daytradingtool.UserInterface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sourabh.daytradingtool.Data.SearchStockItemDetail;
import com.sourabh.daytradingtool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchStockRecyclerViewAdapter extends RecyclerView.Adapter<SearchStockRecyclerViewAdapter.SearchStockViewHolder> implements Filterable{

    private ArrayList<SearchStockItemDetail> searchStockItemDetails;
    private ArrayList<SearchStockItemDetail> searchStockItemDetailsBackup;

    private SearchStockRecyclerViewClickListener clickListener;

    public SearchStockRecyclerViewAdapter(ArrayList<SearchStockItemDetail> searchStockItemDetails, SearchStockRecyclerViewClickListener clickListener) {
        this.searchStockItemDetails = searchStockItemDetails;
        this.searchStockItemDetailsBackup = new ArrayList<>(searchStockItemDetails);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SearchStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_stock_list_layout_item, parent, false);
        return new SearchStockViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchStockViewHolder holder, int position) {

        holder.stockTitle.setText(searchStockItemDetails.get(position).getTitle());
        holder.stockFullName.setText(searchStockItemDetails.get(position).getFullName());


    }

    @Override
    public int getItemCount() {
        return searchStockItemDetails.size();
    }

    public class SearchStockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView stockTitle, stockFullName;

        private SearchStockRecyclerViewClickListener clickListener;

        public SearchStockViewHolder(@NonNull View itemView, SearchStockRecyclerViewClickListener clickListener) {
            super(itemView);

            stockTitle = (TextView) itemView.findViewById(R.id.search_stock_layout_item_title);
            stockFullName = (TextView) itemView.findViewById(R.id.search_stock_layout_item_full_name);

            this.clickListener = clickListener;

            //Click Event
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter() {
        return inboxFilter;
    }

    Filter inboxFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<SearchStockItemDetail> filterInboxes = new ArrayList<>();

            if(charSequence == null && charSequence.length() == 0 && charSequence.toString().matches("")){
                filterInboxes.addAll(searchStockItemDetailsBackup);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(SearchStockItemDetail item: searchStockItemDetailsBackup){

                    if(item.getTitle().toLowerCase().trim().contains(filterPattern)){
                        filterInboxes.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterInboxes;


            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            searchStockItemDetails.clear();
            searchStockItemDetails.addAll((ArrayList<SearchStockItemDetail>)filterResults.values);

            if(searchStockItemDetails.size() == 0){

                ArrayList<SearchStockItemDetail> customNameList = new ArrayList<>();
                customNameList.add(new SearchStockItemDetail(
                        charSequence.toString(),
                        charSequence.toString()
                ));

                searchStockItemDetails.addAll(customNameList);
            }

            notifyDataSetChanged();
        }
    };


}
