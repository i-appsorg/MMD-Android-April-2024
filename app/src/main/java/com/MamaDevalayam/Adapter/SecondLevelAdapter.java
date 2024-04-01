package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;


    List<String[]> data;

    String[] headers;
    Activity activity;
    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
    public static ArrayList<ArrayList<HashMap<String, String>>> parentItems;
    public static ArrayList<HashMap<String, String>> parentItems1;
    private HashMap<String, String> chi;

    public SecondLevelAdapter(Context context, String[] headers, List<String[]> data) {
        this.context = context;
        this.data = data;
        this.headers = headers;
    }

   /* public SecondLevelAdapter(Activity activity, ArrayList<ArrayList<HashMap<String, String>>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems) {
        this.activity=activity;
        this.parentItems=parentItems;
        this.childItems=childItems;
        Log.e("parentItems",""+parentItems);
        Log.e("childItems",""+childItems);
    }
*/
    public SecondLevelAdapter(Activity activity, ArrayList<HashMap<String, String>> parentItems1, ArrayList<ArrayList<HashMap<String, String>>> childItems) {
        this.activity=activity;
        this.parentItems1=parentItems1;
        this.childItems=childItems;
        Log.e("parentItems",""+parentItems1);
        Log.e("childItems",""+childItems);
    }

    @Override
    public Object getGroup(int groupPosition) {

        return parentItems1.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        return parentItems1.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


          /*  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adavanced_search_non_choose_listitem2, null);*/
         convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adavanced_search_non_choose_listitem2, null);
            TextView text = (TextView) convertView.findViewById(R.id.parent_tv);

          //Log.e("sub_cat",""+parentItems.get(groupPosition).get(Integer.parseInt(ConstantManager.Parameter.CATEGORY_NAME)));
          //  text.setText((CharSequence) parentItems.get(groupPosition).get(Integer.parseInt(ConstantManager.Parameter.CATEGORY_NAME)));
        text.setText(parentItems1.get(groupPosition).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        String[] childData;

        childData = data.get(groupPosition);


        return childData[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

           /* LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adavanced_search_non_choose_listitem2, null);*/
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adavanced_search_non_choose_listitem3, null);
            TextView textView = (TextView) convertView.findViewById(R.id.child_tv);
        chi=childItems.get(groupPosition).get(childPosition);
        Log.e("childdata",""+chi.get(ConstantManager.Parameter.CHILD_CATEGORY_NAME));
           /* String[] childArray=data.get(groupPosition);

            String text = childArray[childPosition];*/

            textView.setText(chi.get(ConstantManager.Parameter.CHILD_CATEGORY_NAME));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //String[] children = data.get(groupPosition);


        return childItems.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}