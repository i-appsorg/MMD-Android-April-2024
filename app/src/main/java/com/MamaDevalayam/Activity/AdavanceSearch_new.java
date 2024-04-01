package com.MamaDevalayam.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Model.Category_new;
import com.MamaDevalayam.Model.child_categorynew;
import com.MamaDevalayam.Model.subcategorynew;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdavanceSearch_new extends AppCompatActivity {
    private static final String TAG = AdavanceSearch_new.class.getSimpleName();
    ExpandableListView recyclerView_ad;
    ApiInterface apiService;
    static SessionManager session;
    static HashMap<String, String> userDetails;
    static ArrayList<Category_new> category_newArrayList = new ArrayList<>();
    static ArrayList<subcategorynew> sub_category_newArrayList = new ArrayList<>();
    static ArrayList<child_categorynew> child_category_newArrayList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> GrandItems = new ArrayList<>();
    private ArrayList<ArrayList<HashMap<String, String>>> parentItems1 = new ArrayList<ArrayList<HashMap<String, String>>>();
    private ArrayList<ArrayList<HashMap<String, String>>> parentItems = new ArrayList<>();
    private ArrayList<ArrayList<HashMap<String, String>>> childItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adavance_search_uidesign);
        init();
        AdvanceCatAPI();

    }

    private void init() {
        session = new SessionManager(getApplicationContext());
        recyclerView_ad = (ExpandableListView) findViewById(R.id.expandable_listview);


    }

    private void AdvanceCatAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id = "";
        String token = "";
//todo login
        if (session.isLoggedIn()) {

            user_id = userDetails.get(SessionManager.KEY_UID);
            token = userDetails.get(SessionManager.KEY_token);

        }
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("token", token);

        Log.e("jsonObject1", "" + jsonObject1);


        apiService = ApiClient.getClient().create(ApiInterface.class);

        try {

// TODO not used
            Call<JsonObject> call = apiService.Adavncecategories(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    child_category_newArrayList.clear();
                    sub_category_newArrayList.clear();
                    category_newArrayList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        Log.e("jsonObject", "" + jsonObject);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        String data = jsonObject.getString("data");
                        if (status.equalsIgnoreCase("1")) {
                            JSONArray jsonArray = new JSONArray(data);
                            Log.e("1232", "" + jsonArray.length());
                            if (!data.equalsIgnoreCase("")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Category_new category_new = new Category_new();
                                    category_new.setCategory_id(object.getString("category_id"));
                                    category_new.setCategory_code(object.getString("category_code"));
                                    category_new.setCategory_name(object.getString("category_name"));
                                    String subcategory = object.getString("subcategory");
                                    Log.e("subcategory", "" + subcategory);
                                    JSONArray subcatearray = new JSONArray(subcategory);
                                    for (int j = 0; j < subcatearray.length(); j++) {
                                        JSONObject subObject = subcatearray.getJSONObject(j);
                                        subcategorynew subcategorynew = new subcategorynew();
                                        subcategorynew.setSub_category_id(subObject.getString("sub_category_id"));
                                        subcategorynew.setSub_category_code(subObject.getString("sub_category_code"));
                                        subcategorynew.setSub_category_name(subObject.getString("sub_category_name"));
                                        String childcategory = subObject.getString("child_category");
                                        Log.e("childcategory", "" + childcategory);
                                        JSONArray childcatarrray = new JSONArray(childcategory);

                                        for (int k = 0; k < childcatarrray.length(); k++) {
                                            JSONObject childObject = childcatarrray.getJSONObject(k);
                                            child_categorynew child_categorynew = new child_categorynew();
                                            child_categorynew.setChild_category_id(childObject.getString("child_category_id"));
                                            child_categorynew.setChild_category_code(childObject.getString("child_category_code"));
                                            child_categorynew.setChild_category_name(childObject.getString("child_category_name"));
                                            Log.e("suss", " " + childObject.getString("child_category_name"));
                                            child_category_newArrayList.add(child_categorynew);
                                            Log.e("suss", " "+ child_category_newArrayList);
                                        }
                                        subcategorynew.setChild_category_news(child_category_newArrayList);
                                        sub_category_newArrayList.add(subcategorynew);

                                    }
                                    //  category_new.setSubcategory(sub_category_newArrayList);
                                    Log.e("test12", "test");
                                    category_newArrayList.add(category_new);
                                    Log.e("test1232wsw", "test"+category_newArrayList.get(2).getSubcategory());
                                    // Log.e("sub_newArrayList",""+sub_category_newArrayList.get(j).getSub_category_name());


                                }
                                // datade();
                                if (!category_newArrayList.isEmpty()){
                                    recyclerView_ad.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                        @Override
                                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                            return true;
                                        }
                                    });


                                    ExpandableListView.OnGroupClickListener grpLst = new ExpandableListView.OnGroupClickListener() {
                                        @Override
                                        public boolean onGroupClick(ExpandableListView eListView, View view, int groupPosition,
                                                                    long id) {

                                            return true/* or false depending on what you need */;
                                        }
                                    };


                                    ExpandableListView.OnChildClickListener childLst = new ExpandableListView.OnChildClickListener() {
                                        @Override
                                        public boolean onChildClick(ExpandableListView eListView, View view, int groupPosition,
                                                                    int childPosition, long id) {

                                            return true/* or false depending on what you need */;
                                        }
                                    };

                                    ExpandableListView.OnGroupExpandListener grpExpLst = new ExpandableListView.OnGroupExpandListener() {
                                        @Override
                                        public void onGroupExpand(int groupPosition) {

                                        }
                                    };

                                    Log.e("grpLst",""+grpLst);
                                    Log.e("childLst",""+childLst);
                                    Log.e("grpExpLst",""+grpExpLst);

                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("Exception", "" + e);
        }


    }

   /* private void AdvanceCatAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id = "";
        String token = "";
//todo login
        if (session.isLoggedIn()) {

            user_id = userDetails.get(SessionManager.KEY_UID);
            token = userDetails.get(SessionManager.KEY_token);

        }
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("token", token);

        Log.e("jsonObject1", "" + jsonObject1);


        apiService = ApiClient.getClient().create(ApiInterface.class);

        try {

            Call<JsonObject> call = apiService.Adavncecategories(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    child_category_newArrayList.clear();
                    sub_category_newArrayList.clear();
                    category_newArrayList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        Log.e("jsonObject", "" + jsonObject);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        String data = jsonObject.getString("data");
                        if (status.equalsIgnoreCase("1")) {
                            JSONArray jsonArray = new JSONArray(data);
                            Log.e("1232", "" + jsonArray.length());
                            if (!data.equalsIgnoreCase("")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Category_new category_new = new Category_new();
                                    category_new.setCategory_id(object.getString("category_id"));
                                    category_new.setCategory_code(object.getString("category_code"));
                                    category_new.setCategory_name(object.getString("category_name"));
                                    String subcategory = object.getString("subcategory");
                                    Log.e("subcategory", "" + subcategory);
                                    JSONArray subcatearray = new JSONArray(subcategory);
                                    for (int j = 0; j < subcatearray.length(); j++) {
                                        JSONObject subObject = subcatearray.getJSONObject(j);
                                        subcategorynew subcategorynew = new subcategorynew();
                                        subcategorynew.setSub_category_id(subObject.getString("sub_category_id"));
                                        subcategorynew.setSub_category_code(subObject.getString("sub_category_code"));
                                        subcategorynew.setSub_category_name(subObject.getString("sub_category_name"));
                                        String childcategory = subObject.getString("child_category");
                                        Log.e("childcategory", "" + childcategory);
                                        JSONArray childcatarrray = new JSONArray(childcategory);

                                        for (int k = 0; k < childcatarrray.length(); k++) {
                                            JSONObject childObject = childcatarrray.getJSONObject(k);
                                            child_categorynew child_categorynew = new child_categorynew();
                                            child_categorynew.setChild_category_id(childObject.getString("child_category_id"));
                                            child_categorynew.setChild_category_code(childObject.getString("child_category_code"));
                                            child_categorynew.setChild_category_name(childObject.getString("child_category_name"));
                                            Log.e("suss", " " + childObject.getString("child_category_name"));
                                            child_category_newArrayList.add(child_categorynew);
                                            Log.e("suss", " "+ child_category_newArrayList);
                                        }
                                        subcategorynew.setChild_category_news(child_category_newArrayList);
                                        sub_category_newArrayList.add(subcategorynew);

                                    }
                                  //  category_new.setSubcategory(sub_category_newArrayList);
                                    Log.e("test12", "test");
                                    category_newArrayList.add(category_new);
                                    Log.e("test1232wsw", "test"+category_newArrayList.get(2).getSubcategory());
                                    // Log.e("sub_newArrayList",""+sub_category_newArrayList.get(j).getSub_category_name());


                                }
                               // datade();
                                if (!category_newArrayList.isEmpty()){
                                    recyclerView_ad.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                        @Override
                                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                            return true;
                                        }
                                    });


                                    ExpandableListView.OnGroupClickListener grpLst = new ExpandableListView.OnGroupClickListener() {
                                        @Override
                                        public boolean onGroupClick(ExpandableListView eListView, View view, int groupPosition,
                                                                    long id) {

                                            return true*//* or false depending on what you need *//*;
                                        }
                                    };


                                    ExpandableListView.OnChildClickListener childLst = new ExpandableListView.OnChildClickListener() {
                                        @Override
                                        public boolean onChildClick(ExpandableListView eListView, View view, int groupPosition,
                                                                    int childPosition, long id) {

                                            return true*//* or false depending on what you need *//*;
                                        }
                                    };

                                    ExpandableListView.OnGroupExpandListener grpExpLst = new ExpandableListView.OnGroupExpandListener() {
                                        @Override
                                        public void onGroupExpand(int groupPosition) {

                                        }
                                    };

                                    Log.e("grpLst",""+grpLst);
                                    Log.e("childLst",""+childLst);
                                    Log.e("grpExpLst",""+grpExpLst);

                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("Exception", "" + e);
        }


    }*/

   /* private void datade() {

        Log.e("Exception", "12");
        childItems.clear();
        parentItems1.clear();
        GrandItems.clear();
        for (Category_new category_new : category_newArrayList) {
            Log.e("Exception", "12");
            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String, String>> parentArrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapGrand = new HashMap<String, String>();

            mapGrand.put(ConstantManager.Parameter.CATEGORY_ID, category_new.getCategory_id());
            mapGrand.put(ConstantManager.Parameter.CATEGORY_NAME, category_new.getCategory_name());
            Log.e("GrandmapGrand", "" + mapGrand);
            for (subcategorynew subcategorynew : category_new.getSubcategory()) {
                HashMap<String, String> mapParent = new HashMap<String, String>();

                mapParent.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subcategorynew.getSub_category_name());
                mapParent.put(ConstantManager.Parameter.CATEGORY_ID, subcategorynew.getSub_category_id());

                for (child_categorynew child_categorynew : subcategorynew.getChild_category_news()) {

                    HashMap<String, String> mapChild = new HashMap<String, String>();
                    mapChild.put(ConstantManager.Parameter.CHILD_CATEGORY_NAME, child_categorynew.getChild_category_name());
                    mapChild.put(ConstantManager.Parameter.CATEGORY_ID, child_categorynew.getChild_category_id());
                    childArrayList.add(mapChild);
                }
                parentArrayList.add(mapParent);

            }

            childItems.add(childArrayList);
            parentItems1.add(parentArrayList);
            GrandItems.add(mapGrand);

        }
        ConstantManager.grandItems = GrandItems;
      ConstantManager.parentItems = parentItems1;
        ConstantManager.childItems = childItems;
        Log.e("Grand2w2", "" + GrandItems.size());
        Log.e("Parent", "" + parentItems);
      Log.e("Child", "" + childItems);

       *//* advanceSearchnew = new SearchAdapter(AdavanceSearch_new.this, parentItems1, childItems, GrandItems);
        recyclerView_ad.setAdapter(advanceSearchnew);*//*

       *//* advanceSearchnew = new AdvancesearchAdapterList(AdavanceSearch_new.this, parentItems, childItems, GrandItems);
        recyclerView_ad.setAdapter(advanceSearchnew);*//*

    }*/

    @Override
    protected void onResume() {
        super.onResume();
        init();
        AdvanceCatAPI();
    }
}
