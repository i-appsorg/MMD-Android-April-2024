package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zerones on 04-Oct-17.
 */

public class MyCategoriesExpandableListAdapter extends BaseExpandableListAdapter {

    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
    public static ArrayList<ArrayList<HashMap<String, String>>> parent1Items;
    public static ArrayList<HashMap<String, String>> parent1Items3 ;
    public static ArrayList<HashMap<String, String>> parentItems;
   // private final ArrayList<HashMap<String, String>> childItems;
    private LayoutInflater inflater;
    private Activity activity;
    private HashMap<String, String> child;
    private HashMap<String, String> child1;
    private int count = 0;
    Activity advanceSearchnew;
    private boolean isFromMyCategoriesFragment;



    public MyCategoriesExpandableListAdapter(AdvanceSearchnew advanceSearchnew, ArrayList<ArrayList<HashMap<String, String>>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems) {
      //  this.advanceSearchnew=advanceSearchnew;
        this.parent1Items=parentItems;
        this.childItems=childItems;
        Log.e("childItems",""+parent1Items);
        Log.e("childItems",""+childItems);
       // inflater = (LayoutInflater) advanceSearchnew.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public MyCategoriesExpandableListAdapter(Activity activity, ArrayList<ArrayList<HashMap<String, String>>> parentItems, ArrayList<ArrayList<HashMap<String, String>>> childItems) {
        this.advanceSearchnew=activity;
        this.parent1Items=parentItems;
        this.childItems=childItems;
        Log.e("childItems",""+parent1Items);
     //   Log.e("childItems",""+childItems);
    }


    @Override
    public int getGroupCount() {
        return parent1Items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (childItems.get(groupPosition)).size();
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
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.group_list_layout_my_categories, viewGroup, false);

            parent1Items3=parent1Items.get(groupPosition);
            viewHolderParent = new ViewHolderParent();

            viewHolderParent.tvMainCategoryName = convertView.findViewById(R.id.tvMainCategoryName);
            viewHolderParent.cbMainCategory = convertView.findViewById(R.id.cbMainCategory);
           // viewHolderParent.ivCategory = convertView.findViewById(R.id.ivCategory);
            convertView.setTag(viewHolderParent);
        } else {
            viewHolderParent = (ViewHolderParent) convertView.getTag();
        }
        Log.e("SUB_CATEGORY_NAME",""+parent1Items3.get(groupPosition).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));
        viewHolderParent.tvMainCategoryName.setText(parent1Items3.get(groupPosition).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));




        //viewHolderParent.tvMainCategoryName.setText((CharSequence) parent1Items.get(groupPosition).get(Integer.parseInt(ConstantManager.Parameter.CATEGORY_NAME)));
        convertView.setPadding(20, 10, 0, 10);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean b, View convertView, ViewGroup viewGroup) {

        final ViewHolderChild viewHolderChild;
        child = childItems.get(groupPosition).get(childPosition);
        Log.e("NAME+item",""+child);
        Log.e("SUB_CATEGORY_NAME+item",""+child.get(ConstantManager.Parameter.CHILD_CATEGORY_NAME));

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.child_list_layout_choose_category, viewGroup, false);
            viewHolderChild = new ViewHolderChild();

            viewHolderChild.tvSubCategoryName = convertView.findViewById(R.id.tvSubCategoryName);
            viewHolderChild.cbSubCategory = convertView.findViewById(R.id.cbSubCategory);
            viewHolderChild.viewDivider = convertView.findViewById(R.id.viewDivider);
            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }


        viewHolderChild.tvSubCategoryName.setText(child.get(ConstantManager.Parameter.CHILD_CATEGORY_NAME));


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
