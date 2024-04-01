package com.MamaDevalayam.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Activity.AdvanceCompletedActivity;
import com.MamaDevalayam.Activity.SubCategoryActivity;
import com.MamaDevalayam.Activity.TitleSubTitleActivity;
import com.MamaDevalayam.Activity.TitleSubTitleNewActivity;
import com.MamaDevalayam.Model.Category_new;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.Model.child_categorynew;
import com.MamaDevalayam.Model.subcategorynew;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Activity.AdvanceCompletedNewActivity;
import com.MamaDevalayam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Gowrishankar on 14/05/19.
 */
public class CategorylistNewAdapter extends RecyclerView.Adapter<CategorylistNewAdapter.MyViewHolder> {

    private Activity mContext;
    public static ArrayList<Category_new> categorylis;
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

    public CategorylistNewAdapter(AdvanceCompletedActivity advanceCompletedActivity, ArrayList<Category_new> categorylis) {
        this.mContext = advanceCompletedActivity;
        this.categorylis = categorylis;
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();
        iDonateSharedPreference = new IDonateSharedPreference();
        Log.e("listOfdate", "" + categorylis.size());
    }

    public CategorylistNewAdapter(AdvanceCompletedNewActivity advanceCompletedActivity, ArrayList<Category_new> categorylis) {
        this.mContext = advanceCompletedActivity;
        this.categorylis = categorylis;
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();
        iDonateSharedPreference = new IDonateSharedPreference();
        Log.e("listOfdate", "" + categorylis.size());
    }

    public CategorylistNewAdapter(TitleSubTitleActivity TitleSubTitleNewActivity, ArrayList<Category_new> category_newArrayList) {

        this.mContext = TitleSubTitleNewActivity;
        this.categorylis = category_newArrayList;
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();
        iDonateSharedPreference = new IDonateSharedPreference();
        Log.e("listOfdate", "" + categorylis.size());
    }

    public CategorylistNewAdapter(TitleSubTitleNewActivity TitleSubTitleNewActivity, ArrayList<Category_new> category_newArrayList) {

        this.mContext = TitleSubTitleNewActivity;
        this.categorylis = category_newArrayList;
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();
        iDonateSharedPreference = new IDonateSharedPreference();
        Log.e("listOfdate", "" + categorylis.size());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_adavanced_search_listitem1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.listview_tv_item1.setText(categorylis.get(position).getCategory_name());
        holder.listview_tv_item2.setText(categorylis.get(position).getCategory_name());
        holder.listview_tv_subitem.setText("Refined "+categorylis.get(position).getCategory_name()+" search");
//        Log.e("selected_da",""+categorylis.get(position).getSlected());

        if (categorylis.get(position).getSubcategory().length() > 0) {
            holder.listview_image_add_item1.setImageResource(R.drawable.add_icon);
        } else {
            holder.listview_image_add_item1.setImageResource(R.drawable.ic_right_arrow);
        }
        holder.box1.setVisibility(View.GONE);

        holder.listview_image_add_item1.setVisibility(View.VISIBLE);
        if (iDonateSharedPreference.getselectedcategorydata(mContext) != null) {
            Log.e("Testing", "Selected list " + iDonateSharedPreference.getselectedcategorydata(mContext));
            Log.e("Testing", "Values " + categorylis.get(position).getCategory_id());
            selected = iDonateSharedPreference.getselectedcategorydata(mContext).contains(categorylis.get(position).getCategory_id());
            if (selected) {
                Log.e("Testing", "true");
                holder.linear1.setVisibility(View.GONE);
                holder.linear2.setVisibility(View.VISIBLE);
                holder.box2.setChecked(true);
                TitleSubTitleNewActivity.selecteddata(categoty_item);
                holder.sublinear.setVisibility(View.VISIBLE);
            } else {
                holder.linear1.setVisibility(View.VISIBLE);
                holder.linear2.setVisibility(View.GONE);
                Log.e("Testing", "false");
            }
        } else {
            holder.linear1.setVisibility(View.VISIBLE);
            holder.linear2.setVisibility(View.GONE);
            Log.e("Testing", "false");
        }
            /*if (categorylis.get(position).getSlected().equalsIgnoreCase("true")) {
                Log.e("Testing", "true");
                holder.linear1.setVisibility(View.GONE);
                holder.linear2.setVisibility(View.VISIBLE);
                holder.box2.setChecked(true);
                holder.sublinear.setVisibility(View.VISIBLE);
            *//*for (String str : SubCategoryActivity.arraychecked_item) {
                if (str.toLowerCase().contains("no")) {
                    categorylis.get(position).setSlected("false");
                } else categorylis.get(position).setSlected("true");
            }*//*
         *//*holder.linear1.setVisibility(View.GONE);
            holder.linear2.setVisibility(View.VISIBLE);
            holder.box2.setChecked(true);
            holder.sublinear.setVisibility(View.VISIBLE);*//*

            } else {
                holder.linear1.setVisibility(View.VISIBLE);
                holder.linear2.setVisibility(View.GONE);
                Log.e("Testing", "false");
            }*/
        /*Log.e("ArrayList", SubCategoryActivity.arraychecked_item.toString());
        for (String str : SubCategoryActivity.arraychecked_item) {
            if (str.toLowerCase().contains("no")) {
                categorylis.get(position).setSlected("false");
            } else categorylis.get(position).setSlected("true");
        }*/
        holder.box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.box1.isChecked()) {
                    categorylis.get(position).setSlected("true");
                    arraychecked_item.add(categorylis.get(position).getCategory_id());
                    categoty_item.add(categorylis.get(position).getCategory_code());
                    // categoty_item.remove(categorylis.get(position).getCategory_code());
                    TitleSubTitleNewActivity.selecteddata(categoty_item);
//                    TitleSubTitleNewActivity.checkeddata();
                    holder.box2.setChecked(true);
                    holder.linear1.setVisibility(View.GONE);
                    holder.linear2.setVisibility(View.VISIBLE);
                    holder.sublinear.setVisibility(View.VISIBLE);

                } else {
                    categorylis.get(position).setSlected("false");
                    arraychecked_item.remove(categorylis.get(position).getCategory_id());
                    categoty_item.remove(categorylis.get(position).getCategory_code());
//                    TitleSubTitleNewActivity.checkeddata();
                    TitleSubTitleNewActivity.selecteddata(categoty_item);
                    holder.box2.setChecked(false);
                    holder.sublinear.setVisibility(View.GONE);

                    holder.box2.setVisibility(View.GONE);
                    holder.listview_image_add_item2.setVisibility(View.VISIBLE);

                }
            }
        });
        holder.listview_image_add_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.box1.getVisibility() == View.VISIBLE) {
//                            categorylis.get(position).setSlected("false");
                            arraychecked_item.remove(categorylis.get(position).getCategory_id());
                            categoty_item.remove(categorylis.get(position).getCategory_code());
//                          TitleSubTitleNewActivity.checkeddata();
                            TitleSubTitleNewActivity.selecteddata(categoty_item);
//                            holder.box2.setChecked(false);
                            holder.sublinear.setVisibility(View.GONE);

                    holder.box1.setVisibility(View.GONE);
                    holder.listview_image_add_item1.setVisibility(View.VISIBLE);
                } else {
//                            categorylis.get(position).setSlected("true");
                            arraychecked_item.add(categorylis.get(position).getCategory_id());
                            categoty_item.add(categorylis.get(position).getCategory_code());
                            // categoty_item.remove(categorylis.get(position).getCategory_code());
                            TitleSubTitleNewActivity.selecteddata(categoty_item);
//                          TitleSubTitleNewActivity.checkeddata();
//                            holder.box2.setChecked(true);
                            holder.linear1.setVisibility(View.GONE);
                            holder.linear2.setVisibility(View.VISIBLE);
                            holder.sublinear.setVisibility(View.VISIBLE);

                    holder.box2.setVisibility(View.VISIBLE);
                    holder.listview_image_add_item2.setVisibility(View.GONE);
                }
            }
        });
        holder.listview_image_add_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.box1.setVisibility(View.VISIBLE);
                holder.listview_image_add_item1.setVisibility(View.GONE);
                categorylis.get(position).setSlected("true");
                arraychecked_item.add(categorylis.get(position).getCategory_id());
                categoty_item.add(categorylis.get(position).getCategory_code());
                // categoty_item.remove(categorylis.get(position).getCategory_code());
                TitleSubTitleNewActivity.selecteddata(categoty_item);
//                    TitleSubTitleNewActivity.checkeddata();
                holder.box2.setChecked(true);
                holder.linear1.setVisibility(View.GONE);
                holder.linear2.setVisibility(View.VISIBLE);
                holder.sublinear.setVisibility(View.VISIBLE);
            }
        });

        holder.box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.box2.isChecked()) {
                    categorylis.get(position).setSlected("true");
                    /*arraychecked_item.add(categorylis.get(position).getCategory_id());
//                    TitleSubTitleNewActivity.checkeddata();
                    TitleSubTitleNewActivity.selecteddata(arraychecked_item);*/
                    holder.box1.setChecked(true);
                    holder.sublinear.setVisibility(View.VISIBLE);
                } else {
                    categorylis.get(position).setSlected("false");
                    /*arraychecked_item.remove(categorylis.get(position).getCategory_id());
//                    TitleSubTitleNewActivity.checkeddata();
                    TitleSubTitleNewActivity.selecteddata(arraychecked_item);*/
                    arraychecked_item.remove(categorylis.get(position).getCategory_id());
                    categoty_item.remove(categorylis.get(position).getCategory_code());
                    TitleSubTitleNewActivity.selecteddata(categoty_item);
                    holder.box1.setChecked(false);
                    holder.linear1.setVisibility(View.VISIBLE);
                    holder.linear2.setVisibility(View.GONE);
                    holder.sublinear.setVisibility(View.GONE);
                }

            }
        });

        holder.sublinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listofchildcategory = iDonateSharedPreference.getselectedchildcategorydata(mContext);
                listofItems = iDonateSharedPreference.getSelectedItems(mContext);
                Log.e("ListOfItems", "Items are " + listofItems);
                categorylis.get(position).setSlected("true");
                child_category_newArrayList.clear();
                sub_category_newArrayList.clear();
                listOfAItem.clear();

                /*for(int i=0; i<listofItems.size(); i++){
                    if(listofItems.get(i).startsWith(categorylis.get(position).getCategory_code()))
                    {
                        listOfAItem.add(listofItems.get(i));
                    }
                }*/
                Log.e("ListOfAItem", "" + listOfAItem);

                Log.e("position12", "" + categorylis.get(position).getSubcategory().length());

                try {
                    for (int i = 0; i < categorylis.get(position).getSubcategory().length(); i++) {
                        JSONObject subObject = categorylis.get(position).getSubcategory().getJSONObject(i);
                        subcategorynew subcategorynew = new subcategorynew();
                        subcategorynew.setSub_category_id(subObject.getString("sub_category_id"));
                        subcategorynew.setSub_category_code(subObject.getString("sub_category_code"));
                        subcategorynew.setSub_category_name(subObject.getString("sub_category_name"));
                        subcategorynew.setDatafind("yes");
                        child_category_newArrayList = new ArrayList<>();
                        if (subObject.has("child_category")) {
                            JSONArray childcatarrray = subObject.getJSONArray("child_category");
                            if (childcatarrray.length() == 0) {
                                subcategorynew.setDatafind("no");
                            }
                            Log.e("childcatarrray", "" + childcatarrray.length());
                            for (int j = 0; j < childcatarrray.length(); j++) {
                                JSONObject childObject = childcatarrray.getJSONObject(j);
                                child_categorynew child_categorynew = new child_categorynew();
                                child_categorynew.setChild_category_id(childObject.getString("child_category_id"));
                                child_categorynew.setChild_category_code(childObject.getString("child_category_code"));
                                child_categorynew.setChild_category_name(childObject.getString("child_category_name"));
                                if (categorylis.get(position).getSlected().equalsIgnoreCase("true")) {
//                                     child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                                    /*if(listofchildcategory.size() > 0){
                                    if (listOfAItem.contains(listofchildcategory)) {
                                        child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                                    } else child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                                    } else child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);*/

                                    if (listofItems.contains(child_categorynew.getChild_category_code())) {
                                        child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                                    } else
                                        child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                                } else
                                    child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
//                                child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
//                                child_categorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                                child_category_newArrayList.add(child_categorynew);
                                Log.e("child_category", "" + child_category_newArrayList);
                                Log.e("child_categorysize", "" + child_category_newArrayList.size());

                            }
                            subcategorynew.setChild_category_news(child_category_newArrayList);

                        }
                        sub_category_newArrayList.add(subcategorynew);

                        Log.e("sub_category", "" + sub_category_newArrayList);
                        Log.e("sub_categorysize", "" + sub_category_newArrayList.size());


                    }
                    iDonateSharedPreference.setcategory(mContext, sub_category_newArrayList);
                    expandablemethod(sub_category_newArrayList, categorylis.get(position).getCategory_name());
                    // iDonateSharedPreference.setcategory(mContext,sub_category_newArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("", "" + categorylis.get(position));


            }
        });

    }

    private void expandablemethod(ArrayList<subcategorynew> subcategory, String category_name) {
        Log.e("subcategory", "" + subcategory);
        childItems.clear();
        parentItems.clear();
        listofsubcategory = iDonateSharedPreference.getselectedsubcategorydata(mContext);

        for (subcategorynew subcategorynew : subcategory) {

            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
            childArrayList.clear();
            HashMap<String, String> mapParent = new HashMap<String, String>();
            mapParent.put(ConstantManager.Parameter.SUB_ID, subcategorynew.getSub_category_id());
            mapParent.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subcategorynew.getSub_category_name());
            mapParent.put(ConstantManager.Parameter.SUB_CATEGORY_CODE, subcategorynew.getSub_category_code());
            mapParent.put(ConstantManager.Parameter.NO_CHILD, subcategorynew.getDatafind());
            Log.e("getSub_child", "" + subcategorynew.getChild_category_news());
            int countIsChecked = 0;

            for (child_categorynew child_categorynew : subcategorynew.getChild_category_news()) {
                HashMap<String, String> mapChild = new HashMap<String, String>();
                mapChild.put(ConstantManager.Parameter.CHILD_CATEGORY_NAME, child_categorynew.getChild_category_name());
                mapChild.put(ConstantManager.Parameter.CHILD_CATEGORY_CODE, child_categorynew.getChild_category_code());
                mapChild.put(ConstantManager.Parameter.CHILD_ID, child_categorynew.getChild_category_id());
                mapChild.put(ConstantManager.Parameter.IS_CHECKED, child_categorynew.getIsChecked());

                if (child_categorynew.getIsChecked().equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                    countIsChecked++;
                }
                childArrayList.add(mapChild);

            }

            if (countIsChecked == subcategorynew.getChild_category_news().size()) {

//                subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
               /* if(listofsubcategory.size() > 0){
                   if(listOfAItem.contains(listofsubcategory)){
                       subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                   } else subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                } subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);*/

                if (listofItems.contains(subcategorynew.getSub_category_code())) {
                    subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                } else subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);


            } else {

//                  subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
               /* if(listofsubcategory.size() > 0){
                    if(listOfAItem.contains(listofsubcategory)){
                        subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                    } else subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                } subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);*/

                if (listofItems.contains(subcategorynew.getSub_category_code())) {
                    subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                } else subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            }
            mapParent.put(ConstantManager.Parameter.IS_CHECKED, subcategorynew.getIsChecked());
            childItems.add(childArrayList);
            parentItems.add(mapParent);

        }
        Log.e("childItems", "" + childItems);
        Log.e("parentItems", "" + parentItems);
        ConstantManager.parentItems1 = parentItems;
        ConstantManager.childItems = childItems;

        Intent intent = new Intent(mContext, SubCategoryActivity.class);
        iDonateSharedPreference.setsubcategory(mContext, parentItems);
        iDonateSharedPreference.setsubchild(mContext, childItems);
        Bundle bundle = new Bundle();
        bundle.putString("category_name", category_name);
        bundle.putString("page", "advance");
        intent.putExtras(bundle);
        mContext.startActivity(intent);
        mContext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public int getItemCount() {

        return categorylis.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView listview_image_remove_item1, listview_image_remove_item2, listview_image_add_item1, listview_image_add_item2;
        private final CheckBox cbMainCategory1, cbMainCategory2;
        public TextView listview_tv_item1, listview_tv_item2,listview_tv_subitem;
        LinearLayout linear2, linear1;
        RelativeLayout sublinear;
        CheckBox box1, box2;

        public MyViewHolder(View view) {
            super(view);
            listview_tv_item1 = (TextView) view.findViewById(R.id.listview_tv_item1);
            listview_tv_item2 = (TextView) view.findViewById(R.id.listview_tv_item2);
            listview_tv_subitem = (TextView) view.findViewById(R.id.listview_tv_subitem);
            linear2 = (LinearLayout) view.findViewById(R.id.linear2);
            linear1 = (LinearLayout) view.findViewById(R.id.linear1);
            listview_image_remove_item1 = (android.widget.ImageView) view.findViewById(R.id.listview_image_remove_item1);
            listview_image_remove_item2 = (android.widget.ImageView) view.findViewById(R.id.listview_image_remove_item2);
            cbMainCategory1 = (android.widget.CheckBox) view.findViewById(R.id.cbMainCategory1);
            cbMainCategory2 = (android.widget.CheckBox) view.findViewById(R.id.cbMainCategory2);
            listview_image_add_item1 = (android.widget.ImageView) view.findViewById(R.id.listview_image_add_item1);
            listview_image_add_item2 = (android.widget.ImageView) view.findViewById(R.id.listview_image_add_item2);
            sublinear = (RelativeLayout) view.findViewById(R.id.sublinearLayout);
            box1 = (CheckBox) view.findViewById(R.id.cbMainCategory1);
            box2 = (CheckBox) view.findViewById(R.id.cbMainCategory2);

        }
    }
}