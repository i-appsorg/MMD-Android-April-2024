package com.MamaDevalayam.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Activity.AdavanceSearch_new;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Gowrishankar on 14/05/19.
 */
public class AdvanceSearchnew extends RecyclerView.Adapter<AdvanceSearchnew.MyViewHolder> {

    private Context mContext;
    List<Map<String, String>> listOfdate;
    private int lastPosition = -1;
    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
    public static ArrayList<ArrayList<HashMap<String, String>>> parentItems;
    public static ArrayList<HashMap<String, String>> grandItems;
    public AdvanceSearchnew(List<Map<String, String>> listOfdate) {
        this.mContext = mContext;
        this.listOfdate=listOfdate;
        Log.e("listOfdate",""+listOfdate);
    }

    public AdvanceSearchnew(AdavanceSearch_new adavanceSearch_new, ArrayList<ArrayList<HashMap<String, String>>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {
        this.mContext =adavanceSearch_new;
        this.grandItems=grandItems;
        this.parentItems=parentItems;
        this.childItems=childItems;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advance_item_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.category_tv.setText(grandItems.get(position).get(ConstantManager.Parameter.CATEGORY_NAME));

     /*   MyCategoriesExpandableListAdapter threeLevelListAdapterAdapters = new MyCategoriesExpandableListAdapter(this,  parentItems, childItems);
        holder.expandable_listview.setAdapter(threeLevelListAdapterAdapters);*/


    }




    @Override
    public int getItemCount() {
        return grandItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category_tv;
        ExpandableListView expandable_listview;

        public MyViewHolder(View view) {
            super(view);
            category_tv = (TextView) view.findViewById(R.id.category_tv);
            expandable_listview=(ExpandableListView)view.findViewById(R.id.expandable_listview);
        }
    }
}