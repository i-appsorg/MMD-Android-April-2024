package com.MamaDevalayam.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.Adapter.CategorylistAdapter;
import com.MamaDevalayam.Adapter.MyCategoriesExpandableListAdapterComplete;
import com.MamaDevalayam.Adapter.TypesCategorylistAdapter;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.Model.subcategorynew;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SubCategoryActivity extends AppCompatActivity {
    static IDonateSharedPreference iDonateSharedPreference;
    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;
    ExpandableListView expandable_listview;
    MyCategoriesExpandableListAdapterComplete myCategoriesExpandableListAdapter;
    TextView category_title_name;
    ImageView back_icon_advance_img;
    Button reset_button, apply_button;
    public static String isChecked, isChildChecked;
    static LinearLayout bottom_layout;
    static ArrayList<subcategorynew> sub_category_newArrayList = new ArrayList<>();
    public static ArrayList<String> listofItems = new ArrayList<>();
    public static ArrayList<String> listofsubcategory = new ArrayList<>();
    public static ArrayList<String> listofchildcategory = new ArrayList<>();
    public static ArrayList<String> listofchildcodecategory = new ArrayList<>();
    public static ArrayList<String> selectedSubcategory = new ArrayList<>();
    public static ArrayList<String> selectedChildcategory = new ArrayList<>();
    public static ArrayList<String> arraychecked_item = new ArrayList<>();
    public static ArrayList<String> arraychildchecked_item = new ArrayList<>();
    static ArrayList<String> arrayunchecked_item = new ArrayList<>();
    static Context context;
    AlertDialog.Builder builder;
    int index = 0;
    String page;
    static String TAG = SubCategoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();
        parentItems.clear();
        childItems.clear();
        context = getApplicationContext();
        builder = new AlertDialog.Builder(this);
        iDonateSharedPreference = new IDonateSharedPreference();
        parentItems = iDonateSharedPreference.getsubcategory(getApplicationContext());
        childItems = iDonateSharedPreference.getsubchild(getApplicationContext());
        sub_category_newArrayList = iDonateSharedPreference.getcategory(getApplicationContext());
        listofItems = iDonateSharedPreference.getSelectedItems(getApplicationContext());
        Log.e(TAG, "parent121 - " + iDonateSharedPreference.getsubcategory(getApplicationContext()));
        Log.e(TAG, "sub_category232 - " + sub_category_newArrayList);
        init();
        listener();
    }

    private void init() {
        listofsubcategory.clear();
        listofchildcategory.clear();
        listofchildcodecategory.clear();
        selectedSubcategory.clear();
        selectedChildcategory.clear();
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("category_name");
        page = bundle.getString("page");/**/
        Log.e(TAG, "name - " + name);
        expandable_listview = (ExpandableListView) findViewById(R.id.expandable_listview);
        category_title_name = (TextView) findViewById(R.id.category_title_name);
        back_icon_advance_img = (ImageView) findViewById(R.id.back_icon_advance_img);
        apply_button = (Button) findViewById(R.id.apply_button);
        reset_button = (Button) findViewById(R.id.reset_button);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        category_title_name.setText(name);
        myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapterComplete(this, parentItems, expandable_listview, childItems, false, index = 0);
        expandable_listview.setAdapter(myCategoriesExpandableListAdapter);
        expandable_listview.setGroupIndicator(null);
        expandable_listview.setChildIndicator(null);

    }

    private void listener() {
        back_icon_advance_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                dailogue_forgot();
            }
        });
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_layout.setVisibility(View.GONE);
                listofsubcategory.clear();
                listofchildcategory.clear();
                listofchildcodecategory.clear();
                selectedSubcategory.clear();
                selectedChildcategory.clear();
                iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubcategory);
                iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchildcategory);
                iDonateSharedPreference.setselecteditem(getApplicationContext(), arrayunchecked_item);
                myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapterComplete(SubCategoryActivity.this, parentItems, expandable_listview, childItems, false, index = 1);
                expandable_listview.setAdapter(myCategoriesExpandableListAdapter);
                expandable_listview.setGroupIndicator(null);
                expandable_listview.setChildIndicator(null);
                expandablemethod(sub_category_newArrayList);
                /*Intent intent = getIntent();
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(intent);*/
              /*  myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapterComplete(SubCategoryActivity.this,parentItems,expandable_listview,childItems,false);
                expandable_listview.setAdapter(myCategoriesExpandableListAdapter);
                expandable_listview.setGroupIndicator(null);
                expandable_listview.setChildIndicator(null);*/
            }
        });

        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDonateSharedPreference.setselected_iem_list(getApplicationContext(), arraychecked_item);
                iDonateSharedPreference.setSelectedItems(getApplicationContext(), listofItems);

                Set<String> setSubCategory = new HashSet<>(selectedSubcategory);
                selectedSubcategory.clear();
                selectedSubcategory.addAll(setSubCategory);
                Collections.sort(selectedSubcategory, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });
                iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubcategory);
                iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchildcategory);
                Log.e(TAG, "SubCategoryActivity - Selected SubCategory : " + selectedSubcategory);

                Set<String> setChildCategory = new HashSet<>(selectedChildcategory);
                selectedChildcategory.clear();
                selectedChildcategory.addAll(setChildCategory);
                Collections.sort(selectedChildcategory, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });
                if (page.equalsIgnoreCase("typepage")) {
                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), TypesCategorylistAdapter.categoty_item);
                } else {
                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), CategorylistAdapter.categoty_item);
                }
                TitleSubTitleActivity.listOfcategory.clear();
                iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                Log.e(TAG, "ChildCategoryActivity - Selected ChildCategory : " + selectedChildcategory);
                if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("unitedstate")) {
                    Intent intent = new Intent(SubCategoryActivity.this, UnitedStateActivity.class);
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    finishAffinity();
                } else if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("international")) {
                    Intent intent = new Intent(SubCategoryActivity.this, InternationalCharitiesActivity.class);
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Intent intent = new Intent(SubCategoryActivity.this, NameSearchActivity.class);
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });
    }

    private void dailogue_forgot() {
        builder.setMessage(R.string.alert_message);

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.alert_message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public static void checkeddata() {
        arraychecked_item.clear();
        arraychildchecked_item.clear();
        listofsubcategory.clear();
        listofchildcategory.clear();
        listofchildcodecategory.clear();

        bottom_layout.setVisibility(View.GONE);
        for (int i = 0; i < MyCategoriesExpandableListAdapterComplete.parentItems.size(); i++) {
            isChecked = MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.IS_CHECKED);
            arraychecked_item.add(isChecked);

            Log.e(TAG, "isChecked - " + isChecked);
            Log.e(TAG, "arraychecked_item123 - " + arraychecked_item);
            if (isChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                MyCategoriesExpandableListAdapterComplete.parentItems.get(i).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                Log.e(TAG, "Checked - true");
                Log.e(TAG, "Items - " + MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_NAME));
                //tvParent.setText(tvParent.getText() + MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.CATEGORY_NAME));
                //bottom_layout.setVisibility(View.VISIBLE);
                listofsubcategory.add(MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_CODE));
                selectedSubcategory.add(MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_CODE));
                Log.e(TAG, "SubCategoryActivity121 - List of subCategory = " + listofsubcategory);
                if (listofItems.contains(MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_CODE))) {
                    //Do Nothing
                } else {
                    listofItems.add(MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_CODE));
                }
            } else if (isChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_FALSE)) {
                Log.e(TAG, "Checked - false");
                MyCategoriesExpandableListAdapterComplete.parentItems.get(i).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                listofsubcategory.remove(MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_CODE));
                selectedSubcategory.remove(MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_CODE));
                listofItems.remove(MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.SUB_CATEGORY_CODE));
                Log.e(TAG, "SubCategoryActivity212 - List of subCategory = " + listofsubcategory);
            }

            for (int j = 0; j < MyCategoriesExpandableListAdapterComplete.childItems.get(i).size(); j++) {

                isChildChecked = MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);
//                arraychecked_item.add(isChildChecked);
                arraychildchecked_item.add(isChildChecked);
                Log.e(TAG, "isChildChecked12 - " + isChildChecked);
                Log.e(TAG, "arraychildchecked_item - " + arraychildchecked_item);
                if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                    MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                    Log.e(TAG, "Items - " + MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_NAME));
                    listofchildcategory.add(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE));
                    listofchildcodecategory.add(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_ID));
                    selectedChildcategory.add(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE));
                    Log.e(TAG, "childActivity - List of childCategory = " + listofchildcategory);
                    if (listofItems.contains(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE))) {
                        //Do Nothing
                    } else
                        listofItems.add(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE));
                    // tvChild.setText(tvChild.getText() +" , " + MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.CATEGORY_NAME) + " "+(j+1));
                    //  bottom_layout.setVisibility(View.VISIBLE);
                } else if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_FALSE)) {
                    // bottom_layout.setVisibility(View.GONE);
                    Log.e(TAG, "child123Activity - List of childCategory = " + listofchildcategory);
                    Log.e(TAG, "child1234Activity - List of childCategory = " + MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE));
                    MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                    for (int k = 0; k < listofchildcodecategory.size(); k++) {
                        if (listofchildcodecategory.equals(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_ID))) {
                            Log.e(TAG, "child123Activity - List of childCategory = " + listofchildcategory);
                            listofchildcategory.remove(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE));
                            selectedChildcategory.remove(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE));
                        }
                    }

                    listofItems.remove(MyCategoriesExpandableListAdapterComplete.childItems.get(i).get(j).get(ConstantManager.Parameter.CHILD_CATEGORY_CODE));
                    Log.e(TAG, "SubCategoryActivity - List of childCategory = " + listofchildcategory);
                    Log.e(TAG, "SubCategoryActivity - List of childCategory = " + listofchildcodecategory.size());

                }

            }
            // check();
            Log.e(TAG, "Activity212 - List of arraychildchecked_item = " + arraychildchecked_item);
        }
        check();
        Log.e(TAG, "Activity212 - List of subCategory = " + listofsubcategory);
        Log.e(TAG, "selected_Activity212 - List of subCategory = " + selectedSubcategory);
    }

    public static void check() {

        iDonateSharedPreference.setselected_iem_list(context, arraychecked_item);
        iDonateSharedPreference.setchildselected_iem_list(context, arraychildchecked_item);
        StringBuilder builder = new StringBuilder();/*isChildChecked*/
        for (String details : arraychildchecked_item) {
            Log.e(TAG, "details121 - " + details);
            if (details.equalsIgnoreCase("YES")) {
                bottom_layout.setVisibility(View.VISIBLE);
            }

        }
        for (String details : arraychecked_item) {
            Log.e(TAG, "details - " + details);
            if (details.equalsIgnoreCase("YES")) {
                bottom_layout.setVisibility(View.VISIBLE);
            }

        }

    }

    private void expandablemethod(ArrayList<subcategorynew> subcategory) {
        Log.e(TAG, "subcategory - " + subcategory);
        childItems.clear();
        parentItems.clear();
        // iDonateSharedPreference.setcategory(getApplicationContext(),subcategory);
        for (subcategorynew subcategorynew : subcategory) {

            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
            childArrayList.clear();
            HashMap<String, String> mapParent = new HashMap<String, String>();
            mapParent.put(ConstantManager.Parameter.SUB_ID, subcategorynew.getSub_category_id());
            mapParent.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subcategorynew.getSub_category_name());
            mapParent.put(ConstantManager.Parameter.SUB_CATEGORY_CODE, subcategorynew.getSub_category_code());
            mapParent.put(ConstantManager.Parameter.NO_CHILD, subcategorynew.getDatafind());
            Log.e(TAG, "getSub_child - " + subcategorynew.getChild_category_news());
            int countIsChecked = 0;

            for (com.MamaDevalayam.Model.child_categorynew child_categorynew : subcategorynew.getChild_category_news()) {
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

//                subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);

            } else {
//                subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                subcategorynew.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
            }
            mapParent.put(ConstantManager.Parameter.IS_CHECKED, subcategorynew.getIsChecked());
            childItems.add(childArrayList);
            parentItems.add(mapParent);

        }
        Log.e(TAG, "childItems - " + childItems);
        Log.e(TAG, "parentItems - " + parentItems);
        ConstantManager.parentItems1 = parentItems;
        ConstantManager.childItems = childItems;

    }

    @Override
    protected void onResume() {
        super.onResume();
        arraychecked_item = iDonateSharedPreference.getselected_iem_list(getApplicationContext());
        for (String details : arraychecked_item) {
            Log.e(TAG, "details - "  + details);
            if (details.equalsIgnoreCase("YES")) {
                bottom_layout.setVisibility(View.VISIBLE);
            }
        }
    }

    /* iDonateSharedPreference.setselected_iem_list(context,arraychecked_item);*/
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        dailogue_forgot();
    }
}
