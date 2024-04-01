package com.MamaDevalayam.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Adapter.AdvancesearchAdapterList;
import com.MamaDevalayam.Model.Category;
import com.MamaDevalayam.Model.ChildCategory;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.Model.SubCategory;
import com.MamaDevalayam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvanceSearch1Activity extends AppCompatActivity {

    ImageView back_icon_login_img;
    ExpandableListView expandible_listview;
    private static final String TAG = AdvanceSearch1Activity.class.getSimpleName();
    ApiInterface apiService;
    List<String> firstLevelArray = new ArrayList<String>();
    List<LinkedHashMap<String, String[]>> dataFinal = new ArrayList<>();
    List<String[]> secondLevelNew = new ArrayList<>();
    Context context;
    private ArrayList<Category> categories;
    private ArrayList<SubCategory> subCategories;
    private ArrayList<ChildCategory> childcategories;
    private ArrayList<HashMap<String, String>> grantItems;
    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search1);
        context = this;
        categories = new ArrayList<>();
        subCategories = new ArrayList<>();
        childcategories = new ArrayList<>();
        grantItems = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();
        init();
        listener();
//        AdvanceCatAPI();

    }

  /*  private void AdvanceCatAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", "29");
        jsonObject1.addProperty("token", "bbc992d8a42968de21b97d1051adb4e8");

        Log.e(TAG, " jsonObject1 - " + jsonObject1);


        apiService = ApiClient.getClient().create(ApiInterface.class);

        try {

            Call<JsonObject> call = apiService.Adavncecategories(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    try {
                        String[] firstLevelValueNew;
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        Log.e(TAG, "123" + jsonObject);
                        String message = jsonObject.getString("message");
                        Log.e(TAG, "" + message);
                        if (jsonObject.getString("status").equals("1")) {
                            String data = jsonObject.getString("data");
                            Log.e(TAG, "data232 - " + data);
                            JSONArray jsonArray = new JSONArray(data);
                            Log.e(TAG, "jsonarray - " + jsonArray.length());

                            for (int i = 0; i <= jsonArray.length(); i++) {
                                if (jsonArray.length() == i) {
                                    break;
                                }
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                Category category = new Category();
                                category.setCategoryId(jsonObject2.getString("category_id"));
                                category.setCategoryName(jsonObject2.getString("category_name"));

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("category_name", jsonObject2.getString("category_name"));
                                map.put("category_id", jsonObject2.getString("category_id"));
                                // firstLevelArray.add(String.valueOf(map));
                                firstLevelArray.add(jsonObject2.getString("category_name"));
                                String levelTwo = jsonObject2.getString("subcategory");
                                JSONArray levelTwojsonArray = new JSONArray(levelTwo);
                                String[] firstLevelValue = new String[firstLevelArray.size()];

                                firstLevelArray.toArray(firstLevelValue);
                                String[] secondLevelValue = new String[0];
                                List<String> secondLevelArray = new ArrayList<String>();
                                LinkedHashMap<String, String[]> thirdLevelNewFinal = new LinkedHashMap<>();
                                LinkedHashMap<String, String[]> thirdLevelNew = new LinkedHashMap<>();

                                for (int j = 0; j <= levelTwojsonArray.length(); j++) {
                                    if (levelTwojsonArray.length() == j) {
                                        break;
                                    }
                                    SubCategory subCategory = new SubCategory();

                                    JSONObject jsonObjectCategory2 = levelTwojsonArray.getJSONObject(j);
                                    subCategory.setCategoryId(jsonObjectCategory2.getString("sub_category_id"));
                                    subCategory.setCategoryId(jsonObjectCategory2.getString("sub_category_name"));
                                    subCategory.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                                    subCategories.add(subCategory);
                                    secondLevelArray.add(jsonObjectCategory2.getString("sub_category_name"));
                                    secondLevelValue = new String[secondLevelArray.size()];
                                    secondLevelArray.toArray(secondLevelValue);


                                    String levelTthree = jsonObjectCategory2.getString("child_category");
                                    JSONArray levelThreejsonArray = new JSONArray(levelTthree);
                                    String[] thirdLevelVale = new String[0];
                                    List<String> thirdLevelArray = new ArrayList<String>();

                                    for (int k = 0; k <= levelThreejsonArray.length(); k++) {

                                        if (levelThreejsonArray.length() == k) {
                                            break;
                                        }
                                        ChildCategory childCategory = new ChildCategory();
                                        JSONObject jsonObjectCategory3 = levelThreejsonArray.getJSONObject(k);
                                        childCategory.setCategoryId(jsonObjectCategory3.getString("child_category_id"));
                                        childCategory.setCategoryId(jsonObjectCategory3.getString("child_category_name"));
                                        childcategories.add(childCategory);
                                        thirdLevelArray.add(jsonObjectCategory3.getString("child_category_name"));
                                    }
                                    thirdLevelVale = new String[thirdLevelArray.size()];
                                    thirdLevelArray.toArray(thirdLevelVale);

                                    thirdLevelNew.put(secondLevelValue[j], thirdLevelVale);
                                    subCategory.setSubCategory(childcategories);
                                    subCategories.add(subCategory);
                                }

                                category.setSubCategory(subCategories);
                                categories.add(category);

                                secondLevelNew.add(secondLevelValue);
                                dataFinal.add(thirdLevelNew);

                            }

                            firstLevelValueNew = new String[firstLevelArray.size()];

                            firstLevelArray.toArray(firstLevelValueNew);
                            /// checkbox();
                           *//* AdvanceSearchAdapterGrand threeLevelListAdapterAdapters = new AdvanceSearchAdapterGrand(context, firstLevelValueNew, secondLevelNew, dataFinal);
                            expandible_listview.setAdapter(threeLevelListAdapterAdapters);*//*
                            String[] firstLevelValue = new String[firstLevelArray.size()];
                            firstLevelArray.toArray(firstLevelValue);

                        } else if (jsonObject.getString("status").equals("0")) {

                            Toast.makeText(AdvanceSearch1Activity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, "onFailure - " + t.toString());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Exception - " + e);
            e.printStackTrace();
        }
    }*/

    private void checkbox() {

        for (Category category : categories) {
            parentItems.clear();
            childItems.clear();
            grantItems.clear();
            HashMap<String, String> mapGrand = new HashMap<String, String>();

            mapGrand.put(ConstantManager.Parameter.CATEGORY_ID, category.getCategoryId());
            mapGrand.put(ConstantManager.Parameter.CATEGORY_NAME, category.getCategoryName());
            for (SubCategory subCategory : category.getSubCategory()) {
                ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> mapparent = new HashMap<String, String>();
                mapparent.put(ConstantManager.Parameter.CATEGORY_ID, subCategory.getCategoryId());
                mapparent.put(ConstantManager.Parameter.CATEGORY_NAME, subCategory.getCategoryName());
                int countIsChecked = 0;
                for (ChildCategory childCategory : subCategory.getSubCategory()) {
                    HashMap<String, String> mapChild = new HashMap<String, String>();
                    mapChild.put(ConstantManager.Parameter.SUB_ID, childCategory.getSubId());
                    mapChild.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, childCategory.getSubCategoryName());
                    mapChild.put(ConstantManager.Parameter.CATEGORY_ID, childCategory.getCategoryId());
                    mapChild.put(ConstantManager.Parameter.IS_CHECKED, childCategory.getIsChecked());

                    if (childCategory.getIsChecked().equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                        countIsChecked++;
                    }
                    childArrayList.add(mapChild);
                }
                if (countIsChecked == subCategory.getSubCategory().size()) {

                    subCategory.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                } else {
                    subCategory.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                }

                mapparent.put(ConstantManager.Parameter.IS_CHECKED, subCategory.getIsChecked());
                childItems.add(childArrayList);
                parentItems.add(mapparent);
                grantItems.add(mapGrand);
            }
        }
       /* ConstantManager.parentItems = parentItems;
        ConstantManager.childItems = childItems;
        ConstantManager.grandItems = grantItems;*/
        AdvancesearchAdapterList threeLevelListAdapterAdapters = new AdvancesearchAdapterList(this, grantItems, parentItems, childItems, false);
        expandible_listview.setAdapter(threeLevelListAdapterAdapters);
    }


    private void init() {

        back_icon_login_img = (ImageView) findViewById(R.id.back_icon_login_img);
        expandible_listview = (ExpandableListView) findViewById(R.id.expandible_listview);


    }

    private void listener() {

        back_icon_login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // ChangeActivity.changeActivity(AdvanceSearch1Activity.this, UnitedStateActivity.class);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
}
