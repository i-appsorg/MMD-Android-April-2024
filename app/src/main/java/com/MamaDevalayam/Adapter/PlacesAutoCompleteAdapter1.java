package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.utility.PlacesAPI;
import com.MamaDevalayam.R;

import java.util.ArrayList;

public class PlacesAutoCompleteAdapter1 extends RecyclerView.Adapter<PlacesAutoCompleteAdapter1.ViewHolder>/* implements Filterable*/{

    ArrayList<String> placeList = new ArrayList<>();
    PlacesAPI mPlaceAPI;
    Activity activity;

    public PlacesAutoCompleteAdapter1(Activity activity, ArrayList<String> placeList) {
        this.activity = activity;
        this.placeList = placeList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_search_list, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.placesTextView.setText(placeList.get(position));
    }

    @Override
    public int getItemCount() {
        if(placeList!=null) {
            Log.e("Count", String.valueOf(placeList.size()));
            return placeList.size();
        }
        else return 0;

    }
   /* @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    placeList = mPlaceAPI.autocomplete(constraint.toString());

                    filterResults.values = placeList;
                    filterResults.count = placeList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {


                }
            }
        };

        return filter;
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView placesTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            placesTextView = (TextView) itemView.findViewById(R.id.address);
        }
    }
}
