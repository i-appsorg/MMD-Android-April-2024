package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Adapter.CategorylistAdapter;
import com.MamaDevalayam.Adapter.TypesCategorylistAdapter;
import com.MamaDevalayam.Model.Category_new;
import com.MamaDevalayam.Model.ConstantManager;
import com.MamaDevalayam.Model.child_categorynew;
import com.MamaDevalayam.Model.subcategorynew;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewtypesActivity extends AppCompatActivity {
    private static final String TAG = AdvanceCompletedActivity.class.getSimpleName();
    RecyclerView recyclerview_types_sub_types;
    SessionManager session;
    static HashMap<String, String> userDetails;
    private RecyclerView.LayoutManager layoutManager;
    ApiInterface apiService;
    AlertDialog.Builder builder;
    public static ArrayList<Category_new> category_newArrayList = new ArrayList<>();
    public static ArrayList<subcategorynew> sub_category_newArrayList = new ArrayList<>();
    public static ArrayList<child_categorynew> child_category_newArrayList = new ArrayList<>();
    TypesCategorylistAdapter categorylistAdapter;
    String response_data;
    ImageView back_icon_login_img;
    static Button reset_button, apply_button;
    static LinearLayout bottom_layout, no_data_linear;
    static Context context;
    static ArrayList<String> listOfcategory = new ArrayList<>();
    static ArrayList<String> listOfItem = new ArrayList<>();
    static ArrayList<String> arraychecked_item = new ArrayList<>();
    static IDonateSharedPreference iDonateSharedPreference;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtypes);
        init();
        listener();
    }

    private void init() {
        iDonateSharedPreference = new IDonateSharedPreference();
        session = new SessionManager(getApplicationContext());
        context = getApplicationContext();
        builder = new AlertDialog.Builder(this);
        recyclerview_types_sub_types = (RecyclerView) findViewById(R.id.recyclerview_types_sub_types);
        back_icon_login_img = (ImageView) findViewById(R.id.back_icon_login_img);
        reset_button = (Button) findViewById(R.id.reset_button);
        apply_button = (Button) findViewById(R.id.apply_button);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        no_data_linear = (LinearLayout) findViewById(R.id.no_data_linear);
        layoutManager = new LinearLayoutManager(this);
        recyclerview_types_sub_types.setLayoutManager(layoutManager);
        recyclerview_types_sub_types.setItemAnimator(new DefaultItemAnimator());
        listOfcategory = iDonateSharedPreference.getselectedcategorydata(getApplicationContext());

     /*   if(i==0) {
            StoreSubChildCode();
        }else {*/
        AdvanceCatAPI();
        //  }

    }

    private void listener() {
        back_icon_login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailogue_forgot();
            }
        });

        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "apply_button: ");
                Log.e(TAG, "dhruvi " + iDonateSharedPreference.getAdvancepage(NewtypesActivity.this));
                arraychecked_item.clear();
                iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfcategory);
                iDonateSharedPreference.setselected_iem_list(getApplicationContext(), arraychecked_item);
                Log.e("arraychecked_item1", "" + arraychecked_item);
                //Intent intent = new Intent(AdvanceCompletedActivity.this, NameSearchActivity.class);
                if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("unitedstate")) {
                    Log.e(TAG, "dhruvi " + iDonateSharedPreference.getAdvancepage(NewtypesActivity.this));
                    Intent intent = new Intent(NewtypesActivity.this, UnitedStateActivity.class);
                    intent.putExtra("data", "1");
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                iDonateSharedPreference.setadvance(getApplicationContext(),"1");
                    startActivity(intent);
                    TitleSubTitleActivity.listOfcategory.clear();
                    iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                    Log.e("listOfcategory_item1", "" + TitleSubTitleActivity.listOfcategory);
                    finishAffinity();
                } else if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("international")) {
                    Log.e(TAG, "dhruvi " + iDonateSharedPreference.getAdvancepage(NewtypesActivity.this));
                    Intent intent = new Intent(NewtypesActivity.this, InternationalCharitiesActivity.class);
                    intent.putExtra("data", "1");
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                iDonateSharedPreference.setadvance(getApplicationContext(),"1");
                    startActivity(intent);
                    TitleSubTitleActivity.listOfcategory.clear();
                    iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                    Log.e("listOfcategory_item1", "" + TitleSubTitleActivity.listOfcategory);
                    finishAffinity();
                } else if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("namesearch")) {
                    Log.e(TAG, "c " + iDonateSharedPreference.getAdvancepage(NewtypesActivity.this));
                    Intent intent = new Intent(NewtypesActivity.this, NameSearchActivity.class);
                    Log.e(TAG, "NameSearchActivity:----- ");
                    intent.putExtra("data", "1");
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                iDonateSharedPreference.setadvance(getApplicationContext(),"1");
                    startActivity(intent);
                    TitleSubTitleActivity.listOfcategory.clear();
                    iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                    Log.e("listOfcategory_item1", "" + TitleSubTitleActivity.listOfcategory);
                    finishAffinity();
                } else {
                    Log.e(TAG, "TypeSerchActivity:----- ");
                    Intent intent = new Intent(NewtypesActivity.this, TypeSerchActivity.class);
                    intent.putExtra("data", "1");
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                iDonateSharedPreference.setadvance(getApplicationContext(),"1");
                    startActivity(intent);
                    TitleSubTitleActivity.listOfcategory.clear();
                    iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                    Log.e("listOfcategory_item1", "" + TitleSubTitleActivity.listOfcategory);
                    finishAffinity();
                }


             /*   Intent intent = new Intent(TitleSubTitleActivity.this, UnitedStateActivity.class);
                intent.putExtra("data","1");
                iDonateSharedPreference.setselectedcategorydata(getApplicationContext(),listOfcategory);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                iDonateSharedPreference.setadvance(getApplicationContext(),"1");
                startActivity(intent);
                finish();*/
            }
        });
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfcategory.clear();
                listOfItem.clear();
                CategorylistAdapter.categoty_item.clear();
                SubCategoryActivity.listofsubcategory.clear();
                SubCategoryActivity.listofchildcategory.clear();
                iDonateSharedPreference.setSelectedItems(getApplicationContext(), listOfItem);
                iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), listOfcategory);
                iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), SubCategoryActivity.listofsubcategory);
                iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), SubCategoryActivity.listofchildcategory);
                bottom_layout.setVisibility(View.GONE);
                seletedApi(response_data);
                StoreSubChildCode();
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

    public static void selecteddata(ArrayList<String> listOfdate1, String select) {

        listOfcategory = listOfdate1;
        if (listOfcategory.size() != 0) {
            bottom_layout.setVisibility(View.VISIBLE);
            Log.e("listOfdate1b", "" + listOfdate1);
            Log.e("listOfdatebg", "" + listOfcategory);
        } else {
            //
            if (select.equalsIgnoreCase("remove")) {
                reset_button.performClick();
            }
            bottom_layout.setVisibility(View.GONE);
            Log.e("listOfdatebgm", "" + listOfcategory);
        }

    }

    public static void checkeddata() {
        arraychecked_item.clear();
        bottom_layout.setVisibility(View.GONE);
        Log.e("isChecked", "YES : " + CategorylistAdapter.categorylis.size());
        for (int i = 0; i < CategorylistAdapter.categorylis.size(); i++) {
            String data = CategorylistAdapter.categorylis.get(i).getSlected();
            String isChecked = "NO";
            if (data.equalsIgnoreCase("true")) {
                isChecked = "YES";
            }
            arraychecked_item.add(isChecked);

            Log.e("isChecked", "" + isChecked);
            if (isChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                //tvParent.setText(tvParent.getText() + MyCategoriesExpandableListAdapterComplete.parentItems.get(i).get(ConstantManager.Parameter.CATEGORY_NAME));
                //bottom_layout.setVisibility(View.VISIBLE);

            } else if (isChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_FALSE)) {

            }

        }
        check();
    }

    private static void check() {

        iDonateSharedPreference.setselected_iem_list(context, arraychecked_item);
        StringBuilder builder = new StringBuilder();
        for (String details : arraychecked_item) {
            Log.e("details", "" + details);
            if (details.equalsIgnoreCase("YES")) {
                bottom_layout.setVisibility(View.VISIBLE);
            }

        }

    }

    public static String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
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
        jsonObject1.addProperty("device_id", getDeviceUniqueID(NewtypesActivity.this));
        Log.e("jsonObject1", "" + jsonObject1);


        apiService = ApiClient.getClient().create(ApiInterface.class);

        try {
// TODO not use
            Call<JsonObject> call = apiService.Adavncecategories(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    response_data = String.valueOf(response.body());
                    no_data_linear.setVisibility(View.GONE);
                    i = 2;
                    listOfcategory.clear();
                    category_newArrayList.clear();
                    child_category_newArrayList.clear();
                    sub_category_newArrayList.clear();
                    bottom_layout.setVisibility(View.GONE);
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
                                    sub_category_newArrayList.clear();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Category_new category_new = new Category_new();
                                    category_new.setCategory_id(object.getString("category_id"));
                                    category_new.setCategory_code(object.getString("category_code"));
                                    category_new.setCategory_name(object.getString("category_name"));
                                    category_new.setSubcategory(object.getJSONArray("subcategory"));
                                    category_new.setSelected(false);
//                                    category_new.setSlected("false");
                                    JSONArray subjsonarray = object.getJSONArray("subcategory");
                                    Log.e("subcategory", "" + subjsonarray);
                                    Log.e("subjsonarraylength", "" + subjsonarray.length());
                                    category_newArrayList.add(category_new);

                                    Log.e("category_newArrayList", "" + category_newArrayList);
                                    Log.e("catetsize", "" + category_newArrayList.size());
                                    categorylistAdapter = new TypesCategorylistAdapter(NewtypesActivity.this, category_newArrayList);
                                    recyclerview_types_sub_types.setAdapter(categorylistAdapter);

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
                    no_data_linear.setVisibility(View.VISIBLE);
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("Exception", "" + e);
        }


    }

    private void StoreSubChildCode() {

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
        jsonObject1.addProperty("device_id", getDeviceUniqueID(NewtypesActivity.this));
        Log.e("jsonObject1", "" + jsonObject1);


        apiService = ApiClient.getClient().create(ApiInterface.class);

        try {

            Call<JsonObject> call = apiService.Adavncecategories(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.e(TAG, "" + response.body());
                    response_data = String.valueOf(response.body());
                    i = 2;
                    /*category_newArrayList.clear();
                    child_category_newArrayList.clear();
                    sub_category_newArrayList.clear();*/
                    listOfItem.clear();

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
                                    JSONArray subjsonarray = object.getJSONArray("subcategory");
                                    Log.e("subcategory", "" + subjsonarray);
                                    Log.e("subjsonarraylength", "" + subjsonarray.length());
                                    for (int j = 0; j < subjsonarray.length(); j++) {
                                        listOfItem.add(subjsonarray.getJSONObject(j).getString("sub_category_code"));
                                        JSONArray childjsonarray = subjsonarray.getJSONObject(j).getJSONArray("child_category");
                                        for (int k = 0; k < childjsonarray.length(); k++) {
                                            listOfItem.add(childjsonarray.getJSONObject(k).getString("child_category_code"));
                                        }
                                    }

                                }
                            }
                            Log.e("ListOfItem", "" + listOfItem);
                            iDonateSharedPreference.setSelectedItems(context, listOfItem);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("Exception", "" + e);
        }


    }

    private void seletedApi(String response_data) {
        category_newArrayList.clear();
        child_category_newArrayList.clear();
        sub_category_newArrayList.clear();
        i = 2;

        try {
            JSONObject jsonObject = new JSONObject(response_data);
            Log.e("jsonObject", "" + jsonObject);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            String data = jsonObject.getString("data");
            if (status.equalsIgnoreCase("1")) {

                JSONArray jsonArray = new JSONArray(data);
                Log.e("1232", "" + jsonArray.length());
                if (!data.equalsIgnoreCase("")) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        sub_category_newArrayList.clear();
                        JSONObject object = jsonArray.getJSONObject(i);
                        Category_new category_new = new Category_new();
                        category_new.setCategory_id(object.getString("category_id"));
                        category_new.setCategory_code(object.getString("category_code"));
                        category_new.setCategory_name(object.getString("category_name"));
                        category_new.setSubcategory(object.getJSONArray("subcategory"));
//                        category_new.setSlected("false");
                        JSONArray subjsonarray = object.getJSONArray("subcategory");
                        Log.e("subcategory", "" + subjsonarray);
                        Log.e("subjsonarraylength", "" + subjsonarray.length());
                        category_newArrayList.add(category_new);
                        Log.e("category_newArrayList", "" + category_newArrayList);
                        Log.e("catetsize", "" + category_newArrayList.size());
                        categorylistAdapter = new TypesCategorylistAdapter(NewtypesActivity.this, category_newArrayList);
                        recyclerview_types_sub_types.setAdapter(categorylistAdapter);

                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        arraychecked_item = iDonateSharedPreference.getselectedcategorydata(getApplicationContext());
        Log.e("TitleSubTitleActivity", "" + arraychecked_item);
        if (arraychecked_item.size() > 0) {
            Log.e(TAG, "Non-Empty");
            // bottom_layout.setVisibility(View.VISIBLE);
        }
        categorylistAdapter = new TypesCategorylistAdapter(NewtypesActivity.this, category_newArrayList);
        recyclerview_types_sub_types.setAdapter(categorylistAdapter);
        /*for (String details : arraychecked_item) {
            Log.e("details",""+details);
            if (details.equalsIgnoreCase("YES")){
                bottom_layout.setVisibility(View.VISIBLE);
                i=1;
            }
        }*/
     /* if (i==2){
            seletedApi(response_data);
            Log.e("back","back");
        }*/
        /*  else {
            Log.e("apply","apply");
            categorylistAdapter = new CategorylistAdapter(TitleSubTitleActivity.this, category_newArrayList);
            recyclerview_types_sub_types.setAdapter(categorylistAdapter);
//        } */
    }

    @Override
    public void onBackPressed() {
        dailogue_forgot();
    }
}