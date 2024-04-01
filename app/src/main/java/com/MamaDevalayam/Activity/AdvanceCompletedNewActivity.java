package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Adapter.CategorylistAdapter;
import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.Model.Category_new;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.Model.child_categorynew;
import com.MamaDevalayam.Model.subcategorynew;
import com.MamaDevalayam.R;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;
import com.MamaDevalayam.Adapter.NewCategorylistAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class AdvanceCompletedNewActivity extends AppCompatActivity {
    private static final String TAG = AdvanceCompletedNewActivity.class.getSimpleName();
    RecyclerView recyclerview_advancesearch;
    static SessionManager session;
    static HashMap<String, String> userDetails;
    ApiInterface apiService;
    String response_data;
    int i = 0;
    public static ArrayList<Category_new> category_newArrayList = new ArrayList<>();
    public static ArrayList<subcategorynew> sub_category_newArrayList = new ArrayList<>();
    public static ArrayList<child_categorynew> child_category_newArrayList = new ArrayList<>();
    AlertDialog.Builder builder;
    private RecyclerView.LayoutManager layoutManager;
    TextView exempt_tv_deselect, exempt_tv_select, non_exempt_deselect_tv, non_exempt_select_tv;
    LinearLayout bottom_layout;
    String select = "";
    String select1 = "";
    static ArrayList<String> listOfdate = new ArrayList<>();
    RelativeLayout search_types_sub_types, annualRevenue;
    int flag = 1;
    int IsType = 0, IsRevenue = 0;

    Button reset_button, apply_button;
    IDonateSharedPreference iDonateSharedPreference;
    RecyclerView RvSearch, RvRevenue;
    NewCategorylistAdapter categorylistAdapter;
    ArrayList<String> arraychecked_item = new ArrayList<>();
    ArrayList<String> categorychecked_item = new ArrayList<>();
    private ArrayList<GroupName> Array = new ArrayList<>();
    private TextView RefindDiety;
    private Toolbar toolbar;
    private View back_icon_login_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_completed_new);
        CommonBackActivity.show();
        init();
        listener();

    }

    private void init() {
        iDonateSharedPreference = new IDonateSharedPreference();
        iDonateSharedPreference.setselected_iem_list(getApplicationContext(), arraychecked_item);
        session = new SessionManager(getApplicationContext());
        builder = new AlertDialog.Builder(this);
        recyclerview_advancesearch = (RecyclerView) findViewById(R.id.recyclerview_advancesearch);
        exempt_tv_deselect = (TextView) findViewById(R.id.exempt_tv_deselect);
        exempt_tv_select = (TextView) findViewById(R.id.exempt_tv_select);
        annualRevenue = (RelativeLayout) findViewById(R.id.revenueRelativeLayout);
        non_exempt_deselect_tv = (TextView) findViewById(R.id.non_exempt_deselect_tv);
        non_exempt_select_tv = (TextView) findViewById(R.id.non_exempt_select_tv);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        reset_button = (Button) findViewById(R.id.reset_button);
        apply_button = (Button) findViewById(R.id.apply_button);
        search_types_sub_types = (RelativeLayout) findViewById(R.id.search_types_sub_types);
        RvSearch = (RecyclerView) findViewById(R.id.RvSearch);
        RvRevenue = (RecyclerView) findViewById(R.id.RvRevenue);
        RefindDiety = (android.widget.TextView) findViewById(R.id.RefindDiety);
        layoutManager = new LinearLayoutManager(this);
        recyclerview_advancesearch.setLayoutManager(layoutManager);
        recyclerview_advancesearch.setItemAnimator(new DefaultItemAnimator());
        arraychecked_item = iDonateSharedPreference.getselected_iem_list(getApplicationContext());
        for (String details : arraychecked_item) {
            if (details.equalsIgnoreCase("YES")) {
                bottom_layout.setVisibility(View.VISIBLE);
            }

        }
        IsType = 0;
        IsRevenue = 0;
        RvSearch.setVisibility(View.GONE);
        RvRevenue.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(this);
        RvRevenue.setLayoutManager(layoutManager);
        RvRevenue.setItemAnimator(new DefaultItemAnimator());
        AdvanceCatAPI();
        toolbar = findViewById(R.id.commonMenuActivityToolbar);
//        toolbar.setVisibility(View.GONE);

        back_icon_login_img = findViewById(R.id.back_icon_login_img);
        back_icon_login_img.setOnClickListener(v -> onBackPressed());
        session = new SessionManager(this);
        findViewById(R.id.linear_browse).setOnClickListener(v -> {

            ChangeActivity.changeActivity(this, BrowseActivity.class);
            ((TextView)findViewById(R.id.browse_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
            // browse_img.setImageTintMode();
            ((ImageView)findViewById(R.id.browse_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
            finish();
        });
        findViewById(R.id.linear_myspace).setOnClickListener(v -> {

            if (session.isLoggedIn()) {
                ChangeActivity.changeActivity(this, MyspaceActivity.class);
                ((TextView)findViewById(R.id.my_space_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
                ((ImageView)findViewById(R.id.my_space_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                finish();
            } else {
                ConstantFunctions.LoginDialog(this);
                // ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                //finish();
            }
        });
    }

    private void listener() {
        RefindDiety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeActivity.changeActivity(AdvanceCompletedNewActivity.this, TitleSubTitleNewActivity.class);
            }
        });
        exempt_tv_deselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = "a";
                iDonateSharedPreference.setDeductible(getApplicationContext(), "1");
                non_exempt_select_tv.setVisibility(View.GONE);
                non_exempt_deselect_tv.setVisibility(View.VISIBLE);
                exempt_tv_deselect.setVisibility(View.GONE);
                exempt_tv_select.setVisibility(View.VISIBLE);

                if (select.equalsIgnoreCase("a")) {
                    bottom_layout.setVisibility(View.VISIBLE);
                } else {
                    bottom_layout.setVisibility(View.GONE);
                    for (String details : arraychecked_item) {
                        if (details.equalsIgnoreCase("YES")) {
                            bottom_layout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        exempt_tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = "b";
                iDonateSharedPreference.setDeductible(getApplicationContext(), "");
                exempt_tv_select.setVisibility(View.GONE);
                exempt_tv_deselect.setVisibility(View.VISIBLE);
                if (select.equalsIgnoreCase("a")) {
                    bottom_layout.setVisibility(View.VISIBLE);
                } else {
                    bottom_layout.setVisibility(View.GONE);
                    for (String details : arraychecked_item) {
                        if (details.equalsIgnoreCase("YES")) {
                            bottom_layout.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }
        });
        non_exempt_deselect_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select1 = "a";
                iDonateSharedPreference.setDeductible(getApplicationContext(), "2");
                exempt_tv_select.setVisibility(View.GONE);
                exempt_tv_deselect.setVisibility(View.VISIBLE);
                non_exempt_deselect_tv.setVisibility(View.GONE);
                non_exempt_select_tv.setVisibility(View.VISIBLE);
                if (select1.equalsIgnoreCase("a")) {
                    bottom_layout.setVisibility(View.VISIBLE);
                } else {
                    bottom_layout.setVisibility(View.GONE);
                    for (String details : arraychecked_item) {
                        Log.e("details", "" + details);
                        if (details.equalsIgnoreCase("YES")) {
                            bottom_layout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        non_exempt_select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select1 = "b";
                iDonateSharedPreference.setDeductible(getApplicationContext(), "");
                non_exempt_select_tv.setVisibility(View.GONE);
                non_exempt_deselect_tv.setVisibility(View.VISIBLE);
                if (select1.equalsIgnoreCase("a")) {
                    bottom_layout.setVisibility(View.VISIBLE);
                } else {
                    bottom_layout.setVisibility(View.GONE);
                    for (String details : arraychecked_item) {
                        Log.e("details", "" + details);
                        if (details.equalsIgnoreCase("YES")) {
                            bottom_layout.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }
        });

        annualRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                if (IsRevenue == 0) {
                    IsRevenue = 1;
                    ((ImageView) findViewById(R.id.IvPlus2)).setImageResource(R.drawable.remove_icon);
                    RvRevenue.setVisibility(View.VISIBLE);
                } else {
                    ((ImageView) findViewById(R.id.IvPlus2)).setImageResource(R.drawable.add_icon);
                    IsRevenue = 0;
                    RvRevenue.setVisibility(View.GONE);
                }
//                ChangeActivity.changeActivity(AdvanceCompletedActivity.this, AnnualRevenueActivity.class);
            }
        });

        search_types_sub_types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsType == 0) {
                    IsType = 1;
                    ((ImageView) findViewById(R.id.IvPlus)).setImageResource(R.drawable.remove_icon);
                    RvSearch.setVisibility(View.VISIBLE);
                } else {
                    ((ImageView) findViewById(R.id.IvPlus)).setImageResource(R.drawable.add_icon);
                    IsType = 0;
                    RvSearch.setVisibility(View.GONE);
                }
//                ChangeActivity.changeActivity(AdvanceCompletedActivity.this, TitleSubTitleActivity.class);
            }
        });
        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraychecked_item.clear();

                iDonateSharedPreference.setselected_iem_list(getApplicationContext(), arraychecked_item);
                Log.e("search_type", "" + iDonateSharedPreference.getAdvancepage(getApplicationContext()));
                if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("unitedstate")) {
                    Intent intent = new Intent(AdvanceCompletedNewActivity.this, UnitedStateActivity.class);
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    TitleSubTitleActivity.listOfcategory.clear();
                    iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                    Log.e("listOfcategory_item1", "" + arraychecked_item);
                    finishAffinity();
                } else if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("international")) {
                    Intent intent = new Intent(AdvanceCompletedNewActivity.this, InternationalCharitiesActivity.class);
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    TitleSubTitleActivity.listOfcategory.clear();
                    iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                    Log.e("listOfcategory_item1", "" + arraychecked_item);
                    finishAffinity();
                } else {
                    Intent intent = new Intent(AdvanceCompletedNewActivity.this, NameSearchActivity.class);
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    TitleSubTitleActivity.listOfcategory.clear();
                    iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                    Log.e("listOfcategory_item1", "" + arraychecked_item);
                    finishAffinity();
                }

            }
        });
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraychecked_item.clear();
                CategorylistAdapter.categoty_item.clear();
                SubCategoryActivity.listofsubcategory.clear();
                SubCategoryActivity.listofchildcategory.clear();
                SubCategoryActivity.listofchildcodecategory.clear();
                iDonateSharedPreference.setselected_iem_list(getApplicationContext(), arraychecked_item);
                iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), SubCategoryActivity.listofsubcategory);
                iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), SubCategoryActivity.listofchildcategory);
                bottom_layout.setVisibility(View.GONE);
                exempt_tv_select.setVisibility(View.GONE);
                non_exempt_select_tv.setVisibility(View.GONE);
                exempt_tv_deselect.setVisibility(View.VISIBLE);
                non_exempt_deselect_tv.setVisibility(View.VISIBLE);
                TitleSubTitleActivity.listOfcategory.clear();
                iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), TitleSubTitleActivity.listOfcategory);
                iDonateSharedPreference.setRevenue(getApplicationContext(), "");
                iDonateSharedPreference.setDeductible(getApplicationContext(), "");
            }
        });
    }

    private void AdvanceCatAPI() {
        Array = new ArrayList<>();
        GroupName groupName = new GroupName("0", "Char / Chatur Dham", "All the 'dhamas' are related to four Yuga's (epochs)");
        Array.add(groupName);
        groupName = new GroupName("1", "Arrupadai Vedu", "Arupadaiveedu refers to the six abodes of Lord Murugan showing the six different forms of Murugan pertaining to the six major events that took place where he was the saviour");
        Array.add(groupName);
        groupName = new GroupName("2", "Adi Shakthi Peethas", "These original Shakthi Sthlams and are places of worship consecrated to Goddess Shakthi");
        Array.add(groupName);
        categorylistAdapter = new NewCategorylistAdapter(AdvanceCompletedNewActivity.this, Array);
        RvRevenue.setAdapter(categorylistAdapter);
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        userDetails = session.getUserDetails();
//        Log.e("userDetails", "" + userDetails);
//        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
//        String user_id = "";
//        String token = "";
////todo login
//        if (session.isLoggedIn()) {
//            user_id = userDetails.get(SessionManager.KEY_UID);
//            token = userDetails.get(SessionManager.KEY_token);
//        }
//        JsonObject jsonObject1 = new JsonObject();
//        jsonObject1.addProperty("user_id", user_id);
//        jsonObject1.addProperty("token", token);
//        jsonObject1.addProperty("device_id", getDeviceUniqueID(AdvanceCompletedNewActivity.this));
//        Log.e("jsonObject1", "" + jsonObject1);
//
//        apiService = ApiClient.getClient().create(ApiInterface.class);
//
//        try {
//            Call<JsonObject> call = apiService.Adavncecategories(jsonObject1);
//            call.enqueue(new Callback<JsonObject>() {
//                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    progressDialog.dismiss();
//                    Log.e(TAG, "" + response.body());
//                    response_data = String.valueOf(response.body());
//                    i = 2;
//                    category_newArrayList.clear();
//                    child_category_newArrayList.clear();
//                    sub_category_newArrayList.clear();
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
//                        Log.e("jsonObject", "" + jsonObject);
//                        String status = jsonObject.getString("status");
//                        String message = jsonObject.getString("message");
//                        String data = jsonObject.getString("data");
//                        if (status.equalsIgnoreCase("1")) {
//
//                            JSONArray jsonArray = new JSONArray(data);
//                            Log.e("1232", "" + jsonArray.length());
//                            if (!data.equalsIgnoreCase("")) {
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    sub_category_newArrayList.clear();
//                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    Category_new category_new = new Category_new();
//                                    category_new.setCategory_id(object.getString("category_id"));
//                                    category_new.setCategory_code(object.getString("category_code"));
//                                    category_new.setCategory_name(object.getString("category_name"));
//                                    category_new.setSubcategory(object.getJSONArray("subcategory"));
////                                    category_new.setSlected("false");
//                                    JSONArray subjsonarray = object.getJSONArray("subcategory");
//                                    Log.e("subcategory", "" + subjsonarray);
//                                    Log.e("subjsonarraylength", "" + subjsonarray.length());
//                                    category_newArrayList.add(category_new);
//
//                                    Log.e("category_newArrayList", "" + category_newArrayList);
//                                    Log.e("catetsize", "" + category_newArrayList.size());
//                                    categorylistAdapter = new CategorylistAdapter(AdvanceCompletedNewActivity.this, category_newArrayList);
//                                    RvSearch.setAdapter(categorylistAdapter);
//
//                                }
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    progressDialog.dismiss();
//                    Log.e(TAG, t.toString());
//                }
//            });
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Log.e("Exception", "" + e);
//        }
    }

    public static String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String deductible = iDonateSharedPreference.getDeductible(getApplicationContext());
        if (deductible.equalsIgnoreCase("1")) {
            exempt_tv_select.setVisibility(View.VISIBLE);
            exempt_tv_deselect.setVisibility(View.GONE);
            non_exempt_deselect_tv.setVisibility(View.VISIBLE);
            non_exempt_select_tv.setVisibility(View.GONE);
            bottom_layout.setVisibility(View.VISIBLE);
        } else if (deductible.equalsIgnoreCase("2")) {
            exempt_tv_select.setVisibility(View.GONE);
            exempt_tv_deselect.setVisibility(View.VISIBLE);
            non_exempt_deselect_tv.setVisibility(View.GONE);
            non_exempt_select_tv.setVisibility(View.VISIBLE);
            bottom_layout.setVisibility(View.VISIBLE);
        }

        listOfdate = iDonateSharedPreference.getselectedcategorydata(getApplicationContext());
        if (listOfdate.size() > 0) {
            listOfdate.clear();
            iDonateSharedPreference.setselectedtypedata(getApplicationContext(), CategorylistAdapter.categoty_item);
        } else {
            listOfdate.clear();
            iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
        }
        iDonateSharedPreference.setSearchName(getApplicationContext(), "");
        arraychecked_item = iDonateSharedPreference.getselected_iem_list(getApplicationContext());
        categorychecked_item = iDonateSharedPreference.getselectedcategorydata(getApplicationContext());
        if (categorychecked_item.size() > 0) {
            bottom_layout.setVisibility(View.VISIBLE);
        }
        for (String details : arraychecked_item) {
            if (details.equalsIgnoreCase("YES")) {
                bottom_layout.setVisibility(View.VISIBLE);
            }

        }
        if (iDonateSharedPreference.getRevenue(getApplicationContext()).equalsIgnoreCase("")) {
        } else bottom_layout.setVisibility(View.VISIBLE);
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

        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
        dailogue_forgot();
    }

    public class GroupName {
        String TempleId, TempleDhamName, TempleDhamDesc;

        public GroupName(String templeId, String templeDhamName, String templeDhamDesc) {
            TempleId = templeId;
            TempleDhamName = templeDhamName;
            TempleDhamDesc = templeDhamDesc;
        }

        public String getTempleId() {
            return TempleId;
        }

        public void setTempleId(String templeId) {
            TempleId = templeId;
        }

        public String getTempleDhamName() {
            return TempleDhamName;
        }

        public void setTempleDhamName(String templeDhamName) {
            TempleDhamName = templeDhamName;
        }

        public String getTempleDhamDesc() {
            return TempleDhamDesc;
        }

        public void setTempleDhamDesc(String templeDhamDesc) {
            TempleDhamDesc = templeDhamDesc;
        }
    }
}
