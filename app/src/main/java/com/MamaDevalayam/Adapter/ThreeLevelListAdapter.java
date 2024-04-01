package com.MamaDevalayam.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.Model.Expandableclass.SecondLevelExpandableListView;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ThreeLevelListAdapter extends BaseExpandableListAdapter {

    String[] parentHeaders;
    List<String[]> secondLevel;
    private Context context;
    List<LinkedHashMap<String, String[]>> data;
    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
//    public static ArrayList<ArrayList<HashMap<String, String>>> parentItems;
    public static ArrayList<HashMap<String, String>> grantitems;
    public static ArrayList<HashMap<String, String>> parentItems;
    Activity activity;

    public ThreeLevelListAdapter(Context context, String[] parentHeader, List<String[]> secondLevel, List<LinkedHashMap<String, String[]>> data) {
        this.context = context;

        this.parentHeaders = parentHeader;

        this.secondLevel = secondLevel;

        this.data = data;
    }

  /*  public ThreeLevelListAdapter(AdavanceSearchUIDesign context, ArrayList<ArrayList<HashMap<String, String>>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {
        this.activity=context;
        this.grantitems=grandItems;
        this.parentItems=parentItems;
        this.childItems=childItems;

    }*/

    /*public ThreeLevelListAdapter(AdavanceSearchUIDesign adavanceSearchUIDesign, ArrayList<HashMap<String, String>> parentItems1, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {

        this.activity = adavanceSearchUIDesign;
        this.grantitems = grandItems;
        this.parentItems1 = parentItems1;
        this.childItems = childItems;
    }*/

    /*public ThreeLevelListAdapter(AdavanceSearch_new adavanceSearch_new, ArrayList<HashMap<String, String>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {
        this.activity = adavanceSearch_new;
        this.grantitems = grandItems;
        this.parentItems = parentItems;
        this.childItems = childItems;

    }*/
    public ThreeLevelListAdapter(Activity activity, ArrayList<HashMap<String, String>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {
        this.activity = activity;
        this.grantitems = grandItems;
        this.parentItems = parentItems;
        this.childItems = childItems;

    }

    @Override
    public int getGroupCount() {
        return grantitems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {


        // no idea why this code is working

        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {

        return groupPosition;
    }

    @Override
    public Object getChild(int group, int child) {


        return child;


    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adavanced_search_listitem1, null);
        TextView text = (TextView) convertView.findViewById(R.id.listview_tv_item1);
        ImageView listview_image_add_item1 = (ImageView) convertView.findViewById(R.id.listview_image_add_item1);
        ImageView listview_image_remove_item1=(ImageView)convertView.findViewById(R.id.listview_image_remove_item1);
        text.setText(grantitems.get(groupPosition).get(ConstantManager.Parameter.CATEGORY_NAME));
        if (isExpanded){
          //  text.setTextColor(R.color.colorPrimary);
            listview_image_add_item1.setVisibility(View.GONE);
            listview_image_remove_item1.setVisibility(View.VISIBLE);

        }else {
           // text.setTextColor(R.color.black);
            listview_image_remove_item1.setVisibility(View.GONE);
            listview_image_add_item1.setVisibility(View.VISIBLE);

        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(activity);
/*
        String[] headers = secondLevel.get(groupPosition);


        List<String[]> childData = new ArrayList<>();
        HashMap<String, String[]> secondLevelData=data.get(groupPosition);

        for(String key : secondLevelData.keySet())
        {


            childData.add(secondLevelData.get(key));

        }*/


        secondLevelELV.setAdapter(new SecondLevelAdapter(activity, parentItems, childItems));

        secondLevelELV.setGroupIndicator(null);


        secondLevelELV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    secondLevelELV.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });


        return secondLevelELV;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
