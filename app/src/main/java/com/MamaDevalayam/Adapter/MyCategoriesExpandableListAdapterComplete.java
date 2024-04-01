package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MamaDevalayam.Activity.SubCategoryActivity;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zerones on 04-Oct-17.
 */

public class MyCategoriesExpandableListAdapterComplete extends BaseExpandableListAdapter {

    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
    public static ArrayList<HashMap<String, String>> parentItems;
    private LayoutInflater inflater;
    private Activity activity;
    private HashMap<String, String> child;
    private int count = 0;
    private boolean isFromMyCategoriesFragment;
    private ExpandableListView expandableListView;
   /* static ArrayList<String> arraychecked_item=new ArrayList<>();*/
    static ArrayList<String> arraychecked_item1=new ArrayList<>();
    IDonateSharedPreference iDonateSharedPreference;
    int index=0,indexj=0;
    public boolean selected;
    public MyCategoriesExpandableListAdapterComplete(Activity activity, ArrayList<HashMap<String, String>> parentItems,
                                                     ExpandableListView expandable_listview, ArrayList<ArrayList<HashMap<String, String>>> childItems, boolean isFromMyCategoriesFragment,int index) {

        this.parentItems = parentItems;
        this.childItems = childItems;
        this.activity = activity;
        this.expandableListView=expandable_listview;
        this.indexj=index;
        this.isFromMyCategoriesFragment = isFromMyCategoriesFragment;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iDonateSharedPreference = new IDonateSharedPreference();
        Log.e("parentItemssize", "" + parentItems.size());
        Log.e("childItems", "" + childItems.size());

    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        final ViewHolderParent viewHolderParent;
        if (convertView == null) {
          //  iDonateSharedPreference.setselecteditem(activity,arraychecked_item);
            if (isFromMyCategoriesFragment) {
                Log.e("true", "true");
                convertView = inflater.inflate(R.layout.group_list_layout_my_categories, null);
            } else {
                Log.e("false", "false");
                convertView = inflater.inflate(R.layout.group_list_layout_choose_categories, null);
            }

            expandableListView.setDividerHeight(0);
            convertView.setPadding(0, 0, 0, 0);
            viewHolderParent = new ViewHolderParent();
           // arraychecked_item1=iDonateSharedPreference.getselecteditem(activity);
            Log.e("arraychecked_item1",""+arraychecked_item1);

            viewHolderParent.tvMainCategoryName = convertView.findViewById(R.id.tvMainCategoryName);
            viewHolderParent.cbMainCategory = convertView.findViewById(R.id.cbMainCategory);
            viewHolderParent.no_data_item_linear = convertView.findViewById(R.id.no_data_item_linear);
            viewHolderParent.add_linear = convertView.findViewById(R.id.add_linear);
            viewHolderParent.remove_linear = convertView.findViewById(R.id.remove_linear);
            convertView.setTag(viewHolderParent);
        } else {
            viewHolderParent = (ViewHolderParent) convertView.getTag();
        }
        if (indexj==0){
            if (parentItems.get(groupPosition).get(ConstantManager.Parameter.IS_CHECKED).equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                viewHolderParent.cbMainCategory.setChecked(true);
                SubCategoryActivity.checkeddata();
                notifyDataSetChanged();

            } else {
                viewHolderParent.cbMainCategory.setChecked(false);
                SubCategoryActivity.checkeddata();
                notifyDataSetChanged();

            }
        }else {
            parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
            SubCategoryActivity.checkeddata();
            for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                childItems.get(groupPosition).get(i).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);

                SubCategoryActivity.checkeddata();
            }
        }


        if (isExpanded) {
           // convertView.setPadding(-10, -10, -10, -10);
            viewHolderParent.add_linear.setVisibility(View.GONE);
            viewHolderParent.remove_linear.setVisibility(View.VISIBLE);
            convertView.setPadding(-10, -10, -10, -10);
        } else {
           // convertView.setPadding(0, -10, -10, -10);
            viewHolderParent.remove_linear.setVisibility(View.GONE);
            viewHolderParent.add_linear.setVisibility(View.VISIBLE);
            convertView.setPadding(0, -10, -10, -10);
        }
        if (parentItems.get(groupPosition).get(ConstantManager.Parameter.NO_CHILD).equalsIgnoreCase("no")) {
            viewHolderParent.add_linear.setVisibility(View.GONE);
            viewHolderParent.tvMainCategoryName.setText(parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));
            viewHolderParent.add_linear.setVisibility(View.GONE);
            viewHolderParent.remove_linear.setVisibility(View.GONE);
            viewHolderParent.no_data_item_linear.setVisibility(View.GONE);
        } else {
            viewHolderParent.no_data_item_linear.setVisibility(View.VISIBLE);
          //  viewHolderParent.add_linear.setVisibility(View.VISIBLE);
            // viewHolderParent.add_linear.setVisibility(View.VISIBLE);
//            String sourceString = "<b>" + parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_CATEGORY_NAME) + "</b> ";
//            viewHolderParent.tvMainCategoryName.setText(Html.fromHtml(sourceString));
            viewHolderParent.tvMainCategoryName.setText(parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));

        }


        viewHolderParent.cbMainCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewHolderParent.cbMainCategory.isChecked()) {
                    indexj=0;
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                    SubCategoryActivity.checkeddata();

                    String isChecked = MyCategoriesExpandableListAdapterComplete.parentItems.get(groupPosition).get(ConstantManager.Parameter.IS_CHECKED);

                    Log.e("isChecked_status",""+parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));
                    Log.e("SUB_ID21",""+parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_ID));
                  //  arraychecked_item.add(parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_ID));
                   // iDonateSharedPreference.setselecteditem(activity,arraychecked_item);
                    for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                        childItems.get(groupPosition).get(i).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                        try {
                            String isChecked1 = MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.IS_CHECKED);
                            Log.e("isChecked_status",""+isChecked1);
                            if (isChecked1.equalsIgnoreCase("NO")){
                                // arraychecked_item.remove(parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_ID));
                                //  iDonateSharedPreference.setselecteditem(activity,arraychecked_item);
                            }
                            SubCategoryActivity.checkeddata();
                        }catch (Exception e){
                            e.printStackTrace();
                        }




                    }

                    notifyDataSetChanged();


                } else {
                    //arraychecked_item.remove(parentItems.get(groupPosition).get(ConstantManager.Parameter.SUB_ID));
                  //  iDonateSharedPreference.setselecteditem(activity,arraychecked_item);
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                    SubCategoryActivity.checkeddata();
                    for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                        childItems.get(groupPosition).get(i).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);

                        SubCategoryActivity.checkeddata();
                    }

                    notifyDataSetChanged();

                }
            }
        });

        ConstantManager.childItems = childItems;
        ConstantManager.parentItems1 = parentItems;

        convertView.setPadding(-10, -10, -10, -10);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean b, View convertView, ViewGroup viewGroup) {

        final ViewHolderChild viewHolderChild;
        final Pair<Long, Long> tag = new Pair<Long, Long>(
                getGroupId(groupPosition),
                getChildId(groupPosition, childPosition));
        child = childItems.get(groupPosition).get(childPosition);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_list_layout_choose_category, null);
            viewHolderChild = new ViewHolderChild();

            viewHolderChild.tvSubCategoryName = convertView.findViewById(R.id.tvSubCategoryName);
            viewHolderChild.cbSubCategory = convertView.findViewById(R.id.cbSubCategory);
            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }
        expandableListView.setDividerHeight(0);
        if (indexj==0){
            if (childItems.get(groupPosition).get(childPosition).get(ConstantManager.Parameter.IS_CHECKED).equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                viewHolderChild.cbSubCategory.setChecked(true);
                SubCategoryActivity.checkeddata();
                notifyDataSetChanged();
            } else {
                viewHolderChild.cbSubCategory.setChecked(false);
                SubCategoryActivity.checkeddata();
                notifyDataSetChanged();
            }
        }


        if (childPosition == childItems.get(groupPosition).get(childPosition).size() - 1) {
            convertView.setPadding(-10, -10, -10, -10);
        } else
            convertView.setPadding(-10, -10, -10, -10);
        viewHolderChild.tvSubCategoryName.setText(child.get(ConstantManager.Parameter.CHILD_CATEGORY_NAME));

        viewHolderChild.cbSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=1;
                if (viewHolderChild.cbSubCategory.isChecked()) {
                    count = 0;
                    indexj=0;
                    childItems.get(groupPosition).get(childPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                    SubCategoryActivity.checkeddata();
                    notifyDataSetChanged();

                } else {
                    count = 0;
                    childItems.get(groupPosition).get(childPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                    SubCategoryActivity.checkeddata();
                    notifyDataSetChanged();
                }

                for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                    if (childItems.get(groupPosition).get(i).get(ConstantManager.Parameter.IS_CHECKED).equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                        count++;
                    }
                }
                if (count == childItems.get(groupPosition).size()) {
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                    SubCategoryActivity.checkeddata();
                    notifyDataSetChanged();


                } else {
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                    SubCategoryActivity.checkeddata();
                    notifyDataSetChanged();
                }


                ConstantManager.childItems = childItems;
                ConstantManager.parentItems1 = parentItems;
            }
        });

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
        LinearLayout no_data_item_linear;
        ImageView add_linear, remove_linear;
    }

    private class ViewHolderChild {

        TextView tvSubCategoryName;
        CheckBox cbSubCategory;
        View viewDivider;
    }


}
