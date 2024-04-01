package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Activity.AdavanceSearch_new;
import com.MamaDevalayam.Activity.TypesActivity;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.Model.Typenamelist;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Gowrishankar on 14/05/19.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context mContext;
    List<Map<String, String>> listOfdate;
    private int lastPosition = -1;
    ArrayList<Typenamelist> typenamelists;
    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
    public static ArrayList<ArrayList<HashMap<String, String>>> parentItems;
    public static ArrayList<HashMap<String, String>> grantitems;
    int index = 1;
    Activity activity;

    public SearchAdapter(ArrayList<Typenamelist> typenamelists) {
        this.mContext = mContext;
        this.typenamelists = typenamelists;
        Log.e("listOfdate", "" + typenamelists);
    }

    /*public TypeSearchAdapter(TypesActivity context, ArrayList<Category_new> category_newArrayList) {
        this.mContext=context;
        this.category_newArrayList=category_newArrayList;
        Log.e("newArrayList",""+category_newArrayList.size());

    }*/

    public SearchAdapter(TypesActivity context, ArrayList<Typenamelist> typenamelists) {
        this.mContext=context;
        this.typenamelists=typenamelists;
        Log.e("newArrayList",""+typenamelists.size());
    }

    public SearchAdapter(AdavanceSearch_new adavanceSearch_new, ArrayList<ArrayList<HashMap<String, String>>> parentItems1, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {
        this.activity=adavanceSearch_new;
        this.childItems=childItems;
        this.parentItems=parentItems1;
        this.grantitems=grandItems;
        Log.e("grantitems23",""+grantitems.size());


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_first, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.type_select_tv1.setText(grantitems.get(position).get(ConstantManager.Parameter.CATEGORY_NAME));
        MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter=new MyCategoriesExpandableListAdapter(activity, parentItems,childItems);
        holder.expandable_listview.setAdapter(myCategoriesExpandableListAdapter);
        holder.expandable_listview.setGroupIndicator(null);

      /*  final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(activity);

        secondLevelELV.setAdapter(new MyCategoriesExpandableListAdapter(activity, parentItems,childItems));

        secondLevelELV.setGroupIndicator(null);*/




    }


    @Override
    public int getItemCount() {
        return grantitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  type_select_tv1;
        ExpandableListView expandable_listview;
        public MyViewHolder(View view) {
            super(view);
            type_select_tv1 = (TextView) view.findViewById(R.id.rowParentText);
            expandable_listview=(ExpandableListView) view.findViewById(R.id.expandable_listview);
        }
    }
}
