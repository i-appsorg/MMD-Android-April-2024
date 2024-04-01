package com.MamaDevalayam.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.MamaDevalayam.Activity.AdvanceSearchActivity;
import com.MamaDevalayam.R;

import java.util.List;

public class SecondLevelViewAdapter extends BaseExpandableListAdapter {

    private Context context;


    List<String[]> data;

    String[] headers;


    public SecondLevelViewAdapter(Context context, String[] headers, List<String[]> data) {
        this.context = context;
        this.data = data;
        this.headers = headers;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers[groupPosition];
    }

    @Override
    public int getGroupCount() {

        return headers.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_second, null);
        TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);
        final CheckBox parent_checkbox=(CheckBox)convertView.findViewById(R.id.parent_checkbox);
        parent_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent_checkbox.isChecked()){
                    AdvanceSearchActivity.showbottombottm();
                }
            }
        });
        String groupText = getGroup(groupPosition).toString();
        text.setText(groupText);
        if(groupPosition==5) {
            text.setTypeface(text.getTypeface(), Typeface.BOLD);
        }

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_third, null);
        Log.e("SecondLevel", String.valueOf(childPosition));
        TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);
        final CheckBox child_checkbox=(CheckBox)convertView.findViewById(R.id.child_checkbox);
        Log.e("GroupPosition", String.valueOf(groupPosition));
        child_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child_checkbox.isChecked()){

                    AdvanceSearchActivity.showbottombottm();
                }
            }
        });
        String[] childArray = data.get(groupPosition);
        String text = childArray[childPosition];
        textView.setText(text);
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] children = data.get(groupPosition);
        return children.length;
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