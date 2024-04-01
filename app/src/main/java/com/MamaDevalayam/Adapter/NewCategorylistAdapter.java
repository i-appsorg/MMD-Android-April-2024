package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Activity.AdvanceCompletedNewActivity;
import com.MamaDevalayam.Activity.SearchNewActivity;
import com.MamaDevalayam.Model.child_categorynew;
import com.MamaDevalayam.Model.subcategorynew;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Gowrishankar on 14/05/19.
 */
public class NewCategorylistAdapter extends RecyclerView.Adapter<NewCategorylistAdapter.MyViewHolder> {

    private Activity mContext;
    public static ArrayList<AdvanceCompletedNewActivity.GroupName> categorylis;
    static ArrayList<String> arraychecked_item = new ArrayList<>();
    public static ArrayList<String> categoty_item = new ArrayList<>();
    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;
    IDonateSharedPreference iDonateSharedPreference;
    public static ArrayList<subcategorynew> sub_category_newArrayList = new ArrayList<>();
    public static ArrayList<child_categorynew> child_category_newArrayList = new ArrayList<>();
    static ArrayList<String> listOfcategory = new ArrayList<>();
    static ArrayList<String> listofItems = new ArrayList<>();
    public static ArrayList<String> listOfAItem = new ArrayList<>();
    public static ArrayList<String> listofsubcategory = new ArrayList<>();
    public static ArrayList<String> listofchildcategory = new ArrayList<>();
    public boolean selected, checked;

    public NewCategorylistAdapter(AdvanceCompletedNewActivity advanceCompletedActivity, ArrayList<AdvanceCompletedNewActivity.GroupName> categorylis) {
        this.mContext = advanceCompletedActivity;
        this.categorylis = categorylis;
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();
        iDonateSharedPreference = new IDonateSharedPreference();
        Log.e("listOfdate", "" + categorylis.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adavanced_search_listitem_new, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.listview_tv_item1_title.setText(categorylis.get(position).getTempleDhamName());
        holder.listview_tv_item1_desc.setText(categorylis.get(position).getTempleDhamDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, SearchNewActivity.class).putExtra("SearchItem", categorylis.get(position).getTempleDhamName()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return categorylis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listview_tv_item1_title, listview_tv_item1_desc;

        public MyViewHolder(View view) {
            super(view);
            listview_tv_item1_title = (TextView) view.findViewById(R.id.listview_tv_item1_title);
            listview_tv_item1_desc = (TextView) view.findViewById(R.id.listview_tv_item1_desc);

        }
    }
}