package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.MamaDevalayam.Activity.AdavanceSearchUIDesign;
import com.MamaDevalayam.Activity.AdavanceSearch_new;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.Model.Expandableclass.SecondLevelExpandableListView;
import com.MamaDevalayam.Model.SubCategory;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;



public class AdvancesearchAdapterList extends BaseExpandableListAdapter {

    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
    public static ArrayList<ArrayList<HashMap<String, String>>> parentItems;
    public static ArrayList<HashMap<String, String>> grantitems;
    private SubCategory childItem;
    private LayoutInflater inflater;
    private Activity activity;
    private String child;
    private int count = 0;
    private boolean isFromMyCategoriesFragment;

    public AdvancesearchAdapterList(Activity activity, ArrayList<HashMap<String, String>> parentItems,
                                    ArrayList<HashMap<String, String>> grantItems, ArrayList<ArrayList<HashMap<String, String>>> childItems, boolean isFromMyCategoriesFragment) {

     /*   this.parentItems = parentItems;
        this.childItems = childItems;
        this.grantitems=grantItems;
        this.activity = activity;
        Log.e("childItems",""+childItems.size());
        Log.e("parentItems",""+parentItems.size());
        Log.e("grandsitems",""+grantItems.size());*/
        this.isFromMyCategoriesFragment = isFromMyCategoriesFragment;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public AdvancesearchAdapterList(AdavanceSearch_new adavanceSearch_new, ArrayList<ArrayList<HashMap<String, String>>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {
        this.childItems=childItems;
        this.parentItems=parentItems;
        this.grantitems=grandItems;
        this.activity = adavanceSearch_new;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdvancesearchAdapterList(AdavanceSearchUIDesign adavanceSearchUIDesign, ArrayList<ArrayList<HashMap<String, String>>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems, ArrayList<HashMap<String, String>> grandItems) {
        this.childItems=childItems;
        this.parentItems=parentItems;
        this.grantitems=grandItems;
        this.activity = adavanceSearchUIDesign;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return grantitems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (parentItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean b, View convertView, ViewGroup viewGroup) {
        final ViewHolderParent viewHolderParent;
        if (convertView == null) {
/*
            if(isFromMyCategoriesFragment) {
                convertView = inflater.inflate(R.layout.group_list_layout_my_categories, null);
            }else {
                convertView = inflater.inflate(R.layout.group_list_layout_choose_categories, null);
            }*/
            convertView = inflater.inflate(R.layout.group_list_layout_my_categories, null);
            viewHolderParent = new ViewHolderParent();

            viewHolderParent.tvMainCategoryName = convertView.findViewById(R.id.tvMainCategoryName);
            viewHolderParent.cbMainCategory = convertView.findViewById(R.id.cbMainCategory);
           // viewHolderParent.ivCategory = convertView.findViewById(R.id.ivCategory);
            convertView.setTag(viewHolderParent);
        } else {
            viewHolderParent = (ViewHolderParent) convertView.getTag();
        }



        viewHolderParent.tvMainCategoryName.setText(grantitems.get(groupPosition).get(ConstantManager.Parameter.CATEGORY_NAME));


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean b, View convertView, ViewGroup viewGroup) {
        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(activity);
        final ViewHolderChild viewHolderChild;
        child = grantitems.get(groupPosition).get(childPosition);

        secondLevelELV.setAdapter(new MyCategoriesExpandableListAdapter(activity, parentItems,childItems));

        secondLevelELV.setGroupIndicator(null);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    private class ViewHolderParent {

        TextView tvMainCategoryName;
        CheckBox cbMainCategory;
        ImageView ivCategory;
    }

    private class ViewHolderChild {

        TextView tvSubCategoryName;
        CheckBox cbSubCategory;
        View viewDivider;
    }


}
