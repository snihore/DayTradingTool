package com.sourabh.daytradingtool.UserInterface;

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

    public SearchStockRecyclerViewAdapter(ArrayList<SearchStockItemDetail> searchStockItemDetails) {
        this.searchStockItemDetails = searchStockItemDetails;
        this.searchStockItemDetailsBackup = new ArrayList<>(searchStockItemDetails);
    }

    @NonNull
    @Override
    public SearchStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_stock_list_layout_item, parent, false);
        return new SearchStockViewHolder(view);
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

    public class SearchStockViewHolder extends RecyclerView.ViewHolder{

        private TextView stockTitle, stockFullName;

        public SearchStockViewHolder(@NonNull View itemView) {
            super(itemView);

            stockTitle = (TextView) itemView.findViewById(R.id.search_stock_layout_item_title);
            stockFullName = (TextView) itemView.findViewById(R.id.search_stock_layout_item_full_name);
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
            notifyDataSetChanged();
        }
    };


}
