package com.MamaDevalayam.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Adapter.LoadMoreUnitesStateLocationAdapterList2;
import com.MamaDevalayam.Adapter.LoadmoreInternationlocationAdapterList2;
import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.Model.TempleListDataModel;
import com.MamaDevalayam.Model.TempleListModel;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Adapter.CategorylistAdapter;
import com.MamaDevalayam.Adapter.LoadMoreUnitesStateLocationAdapterList;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.Model.Charitylist;
import com.MamaDevalayam.Model.CurrencyBean;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;
import com.MamaDevalayam.utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameSearchActivity extends CommonBackActivity {

    private static String TAG = "NameSearchActivity";
    static ApiInterface apiService;
    Toolbar toolbar;
    ImageView close_img, filter_show_img, back_icon_img, back_icon_img1, search_icon, type_img1;
    static RecyclerView united_state_name_recyclerview;
    static LoadmoreInternationlocationAdapterList2 internationlocationAdapterList2;
    static EditText search_name_et;
    LinearLayout namesearchLayout, type_linear_layout, location_search_layout, type_linear_layout1, name_loc_search_layout, name_search_layout1_default, name_search_layout, name_location_search_layout1, type_linear_layout_white, type_linear_layout1_white, name_search_layout_default, name_search_layout1;
    TextView title_tv1, advance_search_text, advance_search_text1, text_type1, advance_search_text_white, advance_search_text1_white;
    static LinearLayout no_data_linear, linear_search1, linear_tool_test;
    static TextView no_data_tv;
    static ArrayList<Charitylist> charitylist1 = new ArrayList<>();
    private static LinearLayoutManager layoutManager;
    static LoadMoreUnitesStateLocationAdapterList unitesStateLocationAdapterList;
    static LoadMoreUnitesStateLocationAdapterList2 unitesStateLocationAdapterList2;
    static ShimmerFrameLayout shimmer_view_container;
    private AppBarLayout appbar_layout;
    Animation slideUp;
    static SessionManager session;
    static HashMap<String, String> userDetails;
    RelativeLayout relative_before_toolbar, relative_toolbar, search_relativelayout;
    int index = 0;
    int index1 = 0;
    static int flag = 0, backflag = 0;
    static String data;
    static Context context;
    static IDonateSharedPreference iDonateSharedPreference;
    static ArrayList<String> listOfdate = new ArrayList<>();
    static ArrayList<String> listofsubCategory = new ArrayList<>();
    static ArrayList<String> listofchilCategory = new ArrayList<>();
    String show_type, show_advance;
    static Context instance;
    static String latlanvalue = "";
    static JSONArray jsonArray;
    Boolean loading = false;
    NestedScrollView nestedscrollview;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    static JSONArray jsonArray1;
    static JSONArray jsonArray2;
    static int arrayListsize = 0;
    static int pageno = 1;
    TextView name_title_tv;
    int filter = 1;
//    static List<DataItem> templeDataList;
    static List<TempleListDataModel> filteredDataList;
    public static ArrayList<CurrencyBean> userDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_name_search_new, TAG);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setTitle("United State");


        userDataArrayList = new ArrayList<>();
        try {
            JSONArray m_jArry = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(i);
                CurrencyBean userData = new CurrencyBean();
                userData.setCurrency_code(jsonObject.getString("sortname"));
                userData.setCurrency_name(jsonObject.getString("name"));
                userDataArrayList.add(userData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.commonMenuActivityToolbar);
        toolbar.setVisibility(View.GONE);
        instance = getApplicationContext();
        init();
        listener();
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("country_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @SuppressLint({"Range", "ResourceAsColor"})
    private void init() {
        iDonateSharedPreference = new IDonateSharedPreference();
        name_title_tv = (TextView) findViewById(R.id.name_title_tv);
        if (iDonateSharedPreference.getSelectedtype(getApplicationContext()).equalsIgnoreCase("typesearch")) {
            name_title_tv.setText("Search By Type");
        } else if (iDonateSharedPreference.getSelectedtype(getApplicationContext()).equalsIgnoreCase("advancesearch")) {
            name_title_tv.setText("Advanced Search");
        } else {
            name_title_tv.setText("Search By Name");
        }
        iDonateSharedPreference.setdailoguepage(getApplicationContext(), "0");
        session = new SessionManager(getApplicationContext());
        slideUp = AnimationUtils.loadAnimation(this, R.anim.visiblity_animation);
        back_icon_img = (ImageView) findViewById(R.id.back_icon_login_img);
        back_icon_img1 = (ImageView) findViewById(R.id.back_icon_img1);
        appbar_layout = (AppBarLayout) findViewById(R.id.appbar_layout);
        relative_before_toolbar = (RelativeLayout) findViewById(R.id.relative_before_toolbar);
        relative_toolbar = (RelativeLayout) findViewById(R.id.relative_toolbar);
        filter_show_img = (ImageView) findViewById(R.id.filter_show_img);
        linear_tool_test = (LinearLayout) findViewById(R.id.linear_tool_test);
        advance_search_text1_white = (TextView) findViewById(R.id.advance_search_text1_white);
        text_type1 = (TextView) findViewById(R.id.text_type1);
        name_loc_search_layout = (LinearLayout) findViewById(R.id.name_loc_search_layout);
        name_search_layout = (LinearLayout) findViewById(R.id.name_search_layout);
        location_search_layout = (LinearLayout) findViewById(R.id.location_search_layout);
        name_location_search_layout1 = (LinearLayout) findViewById(R.id.name_location_search_layout1);
        title_tv1 = (TextView) findViewById(R.id.title_tv1);
        name_search_layout_default = (LinearLayout) findViewById(R.id.name_search_layout_default);
        name_search_layout1 = (LinearLayout) findViewById(R.id.name_search_layout1);
        search_relativelayout = (RelativeLayout) findViewById(R.id.search_relativelayout);
        linear_search1 = (LinearLayout) findViewById(R.id.linear_search1);
        type_img1 = (ImageView) findViewById(R.id.type_img1);
        name_search_layout1_default = (LinearLayout) findViewById(R.id.name_search_layout1_default);
        united_state_name_recyclerview = (RecyclerView) findViewById(R.id.united_state_name_recyclerview);
        search_name_et = (EditText) findViewById(R.id.search_name_et1);
        type_linear_layout_white = (LinearLayout) findViewById(R.id.type_linear_layout_white);
        type_linear_layout1_white = (LinearLayout) findViewById(R.id.type_linear_layout1_white);
        search_name_et.setFocusable(false);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        advance_search_text = (TextView) findViewById(R.id.advance_search_text);
        advance_search_text_white = (TextView) findViewById(R.id.advance_search_text_white);
        advance_search_text1 = (TextView) findViewById(R.id.advance_search_text1);
        no_data_linear = (LinearLayout) findViewById(R.id.no_data_linear);
        no_data_tv = findViewById(R.id.no_data_tv);
        type_linear_layout = (LinearLayout) findViewById(R.id.type_linear_layout);
        type_linear_layout1 = (LinearLayout) findViewById(R.id.type_linear_layout1);
        close_img = (ImageView) findViewById(R.id.close_img);
        nestedscrollview = (NestedScrollView) findViewById(R.id.nestedscrollview);
        shimmer_view_container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        namesearchLayout = findViewById(R.id.namesearchLayout);
        shimmer_view_container.setVisibility(View.VISIBLE);
        shimmer_view_container.startShimmer();
        context = NameSearchActivity.this;
        data = getIntent().getStringExtra("data");
        listOfdate = iDonateSharedPreference.getselectedtypedata(getApplicationContext());

        listofsubCategory = iDonateSharedPreference.getselectedsubcategorydata(getApplicationContext());
        listofchilCategory = iDonateSharedPreference.getselectedchildcategorydata(getApplicationContext());
        layoutManager = new LinearLayoutManager(context);
        united_state_name_recyclerview.setLayoutManager(layoutManager);
       /* unitesStateLocationAdapterList = new LoadMoreUnitesStateLocationAdapterList((NameSearchActivity) context, charitylist1);
        united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList);*/

        unitesStateLocationAdapterList2 = new LoadMoreUnitesStateLocationAdapterList2((NameSearchActivity) context, filteredDataList);
        united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList2);

        StringBuilder builder = new StringBuilder();
        for (String details : listOfdate) {
            for (int i = 0; i < listOfdate.size(); i++) {
                builder.append(details + " ," + " ");
            }
        }
    }

    private void listener() {

        nestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int i, int i1, int i2, int i3) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((i1 >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            i1 > i3) {

                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if (!loading) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                loading = true;
                                loadMore();
                            }
                        }
                    }
                }
            }
        });

        location_search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = 1;
                search_name_et.setText("");
                search_name_et.setHint("Enter nonprofit/charity name");
                location_search_layout.setVisibility(View.GONE);
                name_search_layout.setVisibility(View.VISIBLE);
            }
        });

        name_search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 1;
                String getText = "";
                if (search_name_et.getText().length() > 0) {
                    getText = search_name_et.getText().toString().trim();
                }
                iDonateSharedPreference.setSearchName(getApplicationContext(), getText);

                if (iDonateSharedPreference.getcountrycode(getApplicationContext()).equalsIgnoreCase("INTsearch")) {
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NamePlaceSerachActivity.class, "2");
                } else if (iDonateSharedPreference.getcountrycode(getApplicationContext()).equalsIgnoreCase("USsearch")) {
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NamePlaceSerachActivity.class, "1");
                } else {
                    Intent intent = new Intent(NameSearchActivity.this, NamePlaceSerachActivity.class);
                    intent.putExtra("mylist", charitylist1);
                    if (search_name_et.getText().toString().length() > 0)
                        intent.putExtra("charityname", search_name_et.getText().toString());
                    else {
                        intent.putExtra("charityname", "");
                    }
                    intent.putExtra("data", "3");
                    startActivity(intent);
                }

                advance_search_text.setVisibility(View.VISIBLE);
                advance_search_text_white.setVisibility(View.GONE);
                advance_search_text1.setVisibility(View.VISIBLE);
                advance_search_text1_white.setVisibility(View.GONE);
                name_search_layout_default.setVisibility(View.GONE);
                name_search_layout.setVisibility(View.VISIBLE);
                type_linear_layout_white.setVisibility(View.GONE);
                type_linear_layout.setVisibility(View.VISIBLE);

            }
        });
        name_search_layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 1;

                advance_search_text.setVisibility(View.VISIBLE);
                advance_search_text_white.setVisibility(View.GONE);
                advance_search_text1.setVisibility(View.VISIBLE);
                advance_search_text1_white.setVisibility(View.GONE);
                name_search_layout1_default.setVisibility(View.GONE);
                name_search_layout1.setVisibility(View.VISIBLE);
                type_linear_layout1_white.setVisibility(View.GONE);
                type_linear_layout1.setVisibility(View.VISIBLE);
                listOfdate.clear();
                listOfdate = iDonateSharedPreference.getselectedtypedata(getApplicationContext());
                listofsubCategory = iDonateSharedPreference.getselectedsubcategorydata(getApplicationContext());
                listofchilCategory = iDonateSharedPreference.getselectedchildcategorydata(getApplicationContext());

                StringBuilder builder = new StringBuilder();
                for (String details : listOfdate) {
                    for (int i = 0; i < listOfdate.size(); i++) {
                        builder.append(details + " ," + " ");
                    }
                }
            }
        });
        back_icon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        back_icon_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search_name_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                search_name_et.setFocusableInTouchMode(true);
                v.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {


                                Rect r = new Rect();
                                v.getWindowVisibleDisplayFrame(r);
                                int screenHeight = v.getRootView().getHeight();

                                // r.bottom is the position above soft keypad or device button.
                                // if keypad is shown, the r.bottom is smaller than that before.
                                int keypadHeight = screenHeight - r.bottom;

                                Log.d(TAG, "keypadHeight = " + keypadHeight);

                                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                                    // keyboard is opened

                                    CommonBackActivity.hide();
                                } else {
                                    // keyboard is closed
                                    CommonBackActivity.show();
                                }
                            }
                        });
            }
        });
        search_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = search_name_et.getText().toString();
                if (text.length() > 0) {
                    search_icon.setVisibility(View.GONE);
                    close_img.setVisibility(View.VISIBLE);
                    backflag = 1;
                } else {
                    search_icon.setVisibility(View.VISIBLE);
                    close_img.setVisibility(View.GONE);
                    backflag = 0;
                }

                pageno = 1;
//                CharityAPI(pageno);
                TempleListAPI(pageno);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_name_et.setText("");

                pageno = 1;
                backflag = 0;
//                CharityAPI(pageno);
                TempleListAPI(pageno);
            }
        });

        type_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if(session.isLoggedIn()){
                advance_search_text.setVisibility(View.VISIBLE);
                advance_search_text_white.setVisibility(View.GONE);
                advance_search_text1.setVisibility(View.VISIBLE);
                advance_search_text1_white.setVisibility(View.GONE);
                iDonateSharedPreference.setadvance(getApplicationContext(), "0");
                if (data.equalsIgnoreCase("1")) {
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    iDonateSharedPreference.setcountrycode(getApplicationContext(), "normalsearch");
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NewSeachtypesActivity.class, "1");
                } else {
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    iDonateSharedPreference.setcountrycode(getApplicationContext(), "normalsearch");
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NewSeachtypesActivity.class, "0");
                }
            }
        });
        type_linear_layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advance_search_text.setVisibility(View.VISIBLE);
                advance_search_text_white.setVisibility(View.GONE);
                advance_search_text1.setVisibility(View.VISIBLE);
                advance_search_text1_white.setVisibility(View.GONE);
                iDonateSharedPreference.setadvance(getApplicationContext(), "0");
                if (data.equalsIgnoreCase("1")) {
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    iDonateSharedPreference.setcountrycode(getApplicationContext(), "normalsearch");
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NewtypesActivity.class, "1");
                } else {
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    iDonateSharedPreference.setcountrycode(getApplicationContext(), "normalsearch");
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NewtypesActivity.class, "0");
                }

            }
        });
        type_linear_layout_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advance_search_text.setVisibility(View.VISIBLE);
                advance_search_text_white.setVisibility(View.GONE);
                advance_search_text1.setVisibility(View.VISIBLE);
                advance_search_text1_white.setVisibility(View.GONE);
                iDonateSharedPreference.setadvance(getApplicationContext(), "0");
                if (data.equalsIgnoreCase("1")) {
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NewtypesActivity.class, "1");
                } else {
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    ChangeActivity.changeActivityData(NameSearchActivity.this, NewtypesActivity.class, "0");
                }
            }
        });

        advance_search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//todo login
                if (session.isLoggedIn()) {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();
                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    ChangeActivity.changeActivity(NameSearchActivity.this, AdvanceCompletedNewActivity.class);
                    finish();
                } else {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();

                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    LoginDialog();
                }
            }
        });
        advance_search_text_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo login
                if (session.isLoggedIn()) {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();

                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    ChangeActivity.changeActivity(NameSearchActivity.this, AdvanceCompletedNewActivity.class);

                } else {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();

                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    LoginDialog();

                }
            }
        });
        advance_search_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//todo login
                if (session.isLoggedIn()) {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();

                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    ChangeActivity.changeActivity(NameSearchActivity.this, AdvanceCompletedNewActivity.class);

                } else {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();

                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    LoginDialog();
                }
            }
        });
        advance_search_text1_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo login
                if (session.isLoggedIn()) {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();

                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    iDonateSharedPreference.setAdvancepage(getApplicationContext(), "namesearch");
                    ChangeActivity.changeActivity(NameSearchActivity.this, AdvanceCompletedNewActivity.class);

                } else {
                    listOfdate.clear();
                    listofsubCategory.clear();
                    listofchilCategory.clear();

                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listofsubCategory);
                    iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listofchilCategory);
                    iDonateSharedPreference.settype(getApplicationContext(), "0");
                    LoginDialog();

                }
            }
        });
        appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                boolean isShow = true;

                String text = String.valueOf(verticalOffset);
                int newNumber = Integer.parseInt(text.replace("-", ""));
                Log.e("demo112", "" + verticalOffset);

                if (newNumber >= 270) {

                    if (index == 0) {
                        index = 1;
                        Log.e("demo112", "" + verticalOffset);
                        relative_before_toolbar.setVisibility(View.GONE);
                        linear_tool_test.setVisibility(View.GONE);

                        if (!search_name_et.getText().toString().trim().isEmpty()) {

                            index1 = 1;
                            search_name_et.setFocusable(false);
                        }
                    }

                } else if (isShow) {
                    index = 0;
                    search_relativelayout.setVisibility(View.VISIBLE);
                    relative_before_toolbar.setVisibility(View.VISIBLE);
                    linear_search1.setVisibility(View.GONE);
                    relative_toolbar.setVisibility(View.GONE);
                    linear_tool_test.setVisibility(View.GONE);
                }

            }
        });
    }

    private void LoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NameSearchActivity.this);
        builder.setTitle("");
        builder.setMessage("For Advance Features Please Log-in/Register");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ChangeActivity.changeActivity(NameSearchActivity.this, LoginActivity.class);
                    }
                });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public static void like() {
        pageno = 1;
//        CharityAPI(pageno);
        TempleListAPI(pageno);
    }

    public static String getDeviceUniqueID(Context activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private static void TempleListAPI(final int page) {

        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id = "";
        if (session.isLoggedIn()) {
            user_id = userDetails.get(SessionManager.KEY_UID);
        }
        JsonArray category_Array = new JsonArray();
        JsonArray subCategory_Array = new JsonArray();
        JsonArray childCategory_Array = new JsonArray();

        for (int i = 0; i < listOfdate.size(); i++) {
            category_Array.add(listOfdate.get(i));
        }

        for (int j = 0; j < listofsubCategory.size(); j++) {
            subCategory_Array.add(listofsubCategory.get(j));
        }

        for (int k = 0; k < listofchilCategory.size(); k++) {
            childCategory_Array.add(listofchilCategory.get(k));
        }

        String searchDeductible = iDonateSharedPreference.getDeductible(context);
        String searchRevenue = iDonateSharedPreference.getRevenue(context);
        String from_income = "";
        String to_income = "";

        if (searchRevenue.equalsIgnoreCase("")) {
            from_income = "";
            to_income = "";
        } else if (searchRevenue.equalsIgnoreCase("90")) {
            from_income = "0";
            to_income = "90000";

        } else if (searchRevenue.equalsIgnoreCase("200")) {
            from_income = "90001";
            to_income = "200000";
        } else if (searchRevenue.equalsIgnoreCase("500")) {
            from_income = "200001";
            to_income = "500000";
        } else if (searchRevenue.equalsIgnoreCase("1000")) {
            from_income = "500001";
            to_income = "1000000";
        } else if (searchRevenue.equalsIgnoreCase("2000")) {
            from_income = "1000001";
            to_income = "";
        }

        Log.e(TAG, "CharityAPI:data " + data);
        String getText = "";
        if (search_name_et.getText().toString().length() > 0) {
            getText = search_name_et.getText().toString().trim();
        }
        Log.e("Text------", getText);
        String lat = "", lng = "";
        String location = iDonateSharedPreference.getLocation(context);
        latlanvalue = location;
        if (location.equalsIgnoreCase(null) || location.equalsIgnoreCase("")) {
            location = "";
        }
        Log.e("Location : ", location);
        if (!location.equalsIgnoreCase("")) {
            if (data.equalsIgnoreCase("1")) {
                LatLng loc = Constants.getFromLocation(context, location);
                lat = String.valueOf(loc.latitude);
                lng = String.valueOf(loc.longitude);
            }
        }

        String device_id = getDeviceUniqueID(context);
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("name", getText);
        jsonObject1.addProperty("latitude", lat);
        jsonObject1.addProperty("longitude", lng);
//        jsonObject1.addProperty("page", page + "");
        jsonObject1.addProperty("address", location);
        jsonObject1.addProperty("device_id", device_id);
        jsonObject1.addProperty("deductible", searchDeductible);
        jsonObject1.addProperty("income_from", from_income);
        jsonObject1.addProperty("income_to", to_income);
        jsonObject1.addProperty("country_code", "US");
        jsonObject1.add("category_code", category_Array);
        jsonObject1.add("sub_category_code", subCategory_Array);
        jsonObject1.add("child_category_code", childCategory_Array);
        jsonObject1.addProperty("user_id", user_id);
        Log.e("jsonObject1****", "" + jsonObject1);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

//        Call<JsonObject> call = apiService.Charitylist(jsonObject1);
        Call<TempleListModel> call = apiService.getTemples();

        call.enqueue(new Callback<TempleListModel>() {
            @Override
            public void onResponse(Call<TempleListModel> call, Response<TempleListModel> response) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.GONE);
               /* if (String.valueOf(page).equalsIgnoreCase("1")) {
                    charitylist1.clear();
                    arrayListsize = 0;
                    jsonArray1 = new JSONArray();
                    jsonArray2 = new JSONArray();
                }*/

                TempleListModel templeResponse = response.body();
                List<TempleListDataModel> templeDataList = templeResponse.getData();
                Log.e(TAG, "onResponse:templeResponse--->> "+templeResponse );
                Log.e(TAG, "onResponse:templeDataList--->> "+templeDataList );
                Log.e(TAG, "onResponse:templeDataList.size()--->> "+templeDataList.size());

                /*List<DataItem>*/ filteredDataList = new ArrayList<>();
                for (TempleListDataModel templeData : templeDataList) {
//                    if (templeData.getCountry().equals(countryCode)) {
                        filteredDataList.add(templeData);

                        Log.e(TAG, "onResponse:templeData = "+templeData );
//                    }
                }
                Log.e(TAG, "onResponse:filteredDataList = "+filteredDataList );

                if (response.isSuccessful()) {
                    Log.e(TAG, "nameserch: " + response);
                    try {
                        Log.e(TAG, "onResponse:response.body()--->> "+response.body().toString());
                      /*  JSONObject jsonObject = new JSONObject(response.body().toString());
                        String message = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            String data = jsonObject.getString("data");

                            jsonArray = new JSONArray(data);
                            Log.e(TAG, "onResponseinternational: " + jsonArray);
                            int maxvalue = 10;
                            if (jsonArray.length() >= 10) {
                                maxvalue = 10;
                            } else {
                                maxvalue = jsonArray.length();
                            }
                            Log.e("jsonArraylength", "" + jsonArray.length());
                            arrayListsize = arrayListsize + jsonArray.length();
                            if (String.valueOf(page).equalsIgnoreCase("1")) {
                                jsonArray1 = new JSONArray();
                            }

                            jsonArray2 = concatArray(jsonArray2, jsonArray);
                            Log.e("jsonArray2length", "" + jsonArray2.length());
                            for (int i = 0; i < maxvalue; i++) {

                                HashMap<String, String> map = new HashMap<>();
                                Charitylist charitylistm = new Charitylist();
                                JSONObject object = jsonArray.getJSONObject(i);
                                map.put("id", object.getString("id"));
                                charitylistm.setId(object.getString("id"));
                                charitylistm.setName(object.getString("name"));
                                charitylistm.setStreet(object.getString("street"));
                                charitylistm.setCity(object.getString("city"));
                                charitylistm.setState(object.getString("state"));
                                charitylistm.setZip_code(object.getString("zip_code"));
                                charitylistm.setLogo(object.getString("logo"));

                                charitylistm.setLiked(object.getString("liked"));
                                charitylistm.setFollowed(object.getString("followed"));
                                charitylistm.setLike_count(object.getString("like_count"));


                                for (int j = 0; j < userDataArrayList.size(); j++) {
                                    if (userDataArrayList.get(j).getCurrency_code().equals(object.getString("country"))) {
                                        charitylistm.setCountry(userDataArrayList.get(j).getCurrency_name());
                                    }
                                }

                                map.put("name", object.getString("name"));
                                map.put("street", object.getString("street"));
                                map.put("city", object.getString("city"));
                                map.put("state", object.getString("state"));
                                map.put("zip_code", object.getString("zip_code"));
                                map.put("logo", object.getString("logo"));

                                map.put("liked", object.getString("liked"));
                                map.put("followed", object.getString("followed"));
                                map.put("like_count", object.getString("like_count"));
                                map.put("country", object.getString("country"));
                                charitylist1.add(charitylistm);
                            }*/

                            if (filteredDataList.size() != 0) {
                                Log.e(TAG, "onResponse:--->> " );
                                united_state_name_recyclerview.setVisibility(View.VISIBLE);
                                layoutManager = new LinearLayoutManager(context);
                                united_state_name_recyclerview.setLayoutManager(layoutManager);
                                united_state_name_recyclerview.setHasFixedSize(true);
                                united_state_name_recyclerview.setNestedScrollingEnabled(true);
//                                unitesStateLocationAdapterList.notifyDataSetChanged();
                                unitesStateLocationAdapterList2.notifyDataSetChanged();
                                united_state_name_recyclerview.setNestedScrollingEnabled(true);
                                united_state_name_recyclerview.setItemAnimator(null);
                                /*unitesStateLocationAdapterList = new LoadMoreUnitesStateLocationAdapterList((NameSearchActivity) context, charitylist1);
                                united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList);*/

//                                TempleListModel templeResponse = response.body();
//                                List<DataItem> templeDataList = templeResponse.getData();

                                unitesStateLocationAdapterList2 = new LoadMoreUnitesStateLocationAdapterList2((NameSearchActivity) context, filteredDataList);
                                united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList2);

                            } else {
                                united_state_name_recyclerview.setVisibility(View.GONE);
                                no_data_linear.setVisibility(View.VISIBLE);
//                                no_data_tv.setText(message);
                                no_data_tv.setText("NO Data Found");
                            }

                       /* } else {
                            if (String.valueOf(page).equalsIgnoreCase("1")) {
                                united_state_name_recyclerview.setVisibility(View.GONE);
                                no_data_linear.setVisibility(View.VISIBLE);
                                no_data_tv.setText(message);
                            }
                        }*/
                    } /*catch (JSONException e) {
                        e.printStackTrace();
                    }*/ catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    shimmer_view_container.stopShimmer();
                    shimmer_view_container.setVisibility(View.GONE);
                    no_data_linear.setVisibility(View.VISIBLE);
                    united_state_name_recyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TempleListModel> call, Throwable t) {
                Log.e(TAG, t.toString());
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.VISIBLE);
                united_state_name_recyclerview.setVisibility(View.GONE);
            }
        });
    }

  /*  private static void CharityAPI(final int page) {

        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id = "";
        if (session.isLoggedIn()) {
            user_id = userDetails.get(SessionManager.KEY_UID);
        }
        JsonArray category_Array = new JsonArray();
        JsonArray subCategory_Array = new JsonArray();
        JsonArray childCategory_Array = new JsonArray();

        for (int i = 0; i < listOfdate.size(); i++) {
            category_Array.add(listOfdate.get(i));
        }

        for (int j = 0; j < listofsubCategory.size(); j++) {
            subCategory_Array.add(listofsubCategory.get(j));
        }

        for (int k = 0; k < listofchilCategory.size(); k++) {
            childCategory_Array.add(listofchilCategory.get(k));
        }

        String searchDeductible = iDonateSharedPreference.getDeductible(context);
        String searchRevenue = iDonateSharedPreference.getRevenue(context);
        String from_income = "";
        String to_income = "";

        if (searchRevenue.equalsIgnoreCase("")) {
            from_income = "";
            to_income = "";
        } else if (searchRevenue.equalsIgnoreCase("90")) {
            from_income = "0";
            to_income = "90000";

        } else if (searchRevenue.equalsIgnoreCase("200")) {
            from_income = "90001";
            to_income = "200000";
        } else if (searchRevenue.equalsIgnoreCase("500")) {
            from_income = "200001";
            to_income = "500000";
        } else if (searchRevenue.equalsIgnoreCase("1000")) {
            from_income = "500001";
            to_income = "1000000";
        } else if (searchRevenue.equalsIgnoreCase("2000")) {
            from_income = "1000001";
            to_income = "";
        }

        Log.e(TAG, "CharityAPI:data " + data);
        String getText = "";
        if (search_name_et.getText().toString().length() > 0) {
            getText = search_name_et.getText().toString().trim();
        }
        Log.e("Text------", getText);
        String lat = "", lng = "";
        String location = iDonateSharedPreference.getLocation(context);
        latlanvalue = location;
        if (location.equalsIgnoreCase(null) || location.equalsIgnoreCase("")) {
            location = "";
        }
        Log.e("Location : ", location);
        if (!location.equalsIgnoreCase("")) {
            if (data.equalsIgnoreCase("1")) {
                LatLng loc = Constants.getFromLocation(context, location);
                lat = String.valueOf(loc.latitude);
                lng = String.valueOf(loc.longitude);
            }
        }

        String device_id = getDeviceUniqueID(context);
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("name", getText);
        jsonObject1.addProperty("latitude", lat);
        jsonObject1.addProperty("longitude", lng);
//        jsonObject1.addProperty("page", page + "");
        jsonObject1.addProperty("address", location);
        jsonObject1.addProperty("device_id", device_id);
        jsonObject1.addProperty("deductible", searchDeductible);
        jsonObject1.addProperty("income_from", from_income);
        jsonObject1.addProperty("income_to", to_income);
        jsonObject1.addProperty("country_code", "US");
        jsonObject1.add("category_code", category_Array);
        jsonObject1.add("sub_category_code", subCategory_Array);
        jsonObject1.add("child_category_code", childCategory_Array);
        jsonObject1.addProperty("user_id", user_id);
        Log.e("jsonObject1****", "" + jsonObject1);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiService.Charitylist(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.GONE);
                if (String.valueOf(page).equalsIgnoreCase("1")) {
                    charitylist1.clear();
                    arrayListsize = 0;
                    jsonArray1 = new JSONArray();
                    jsonArray2 = new JSONArray();
                }
                if (response.isSuccessful()) {
                    Log.e(TAG, "nameserch: " + response);
                    try {
                        Log.e(TAG, response.body().toString());
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String message = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            String data = jsonObject.getString("data");

                            jsonArray = new JSONArray(data);
                            Log.e(TAG, "onResponseinternational: " + jsonArray);
                            int maxvalue = 10;
                            if (jsonArray.length() >= 10) {
                                maxvalue = 10;
                            } else {
                                maxvalue = jsonArray.length();
                            }
                            Log.e("jsonArraylength", "" + jsonArray.length());
                            arrayListsize = arrayListsize + jsonArray.length();
                            if (String.valueOf(page).equalsIgnoreCase("1")) {
                                jsonArray1 = new JSONArray();
                            }

                            jsonArray2 = concatArray(jsonArray2, jsonArray);
                            Log.e("jsonArray2length", "" + jsonArray2.length());
                            for (int i = 0; i < maxvalue; i++) {

                                HashMap<String, String> map = new HashMap<>();
                                Charitylist charitylistm = new Charitylist();
                                JSONObject object = jsonArray.getJSONObject(i);
                                map.put("id", object.getString("id"));
                                charitylistm.setId(object.getString("id"));
                                charitylistm.setName(object.getString("name"));
                                charitylistm.setStreet(object.getString("street"));
                                charitylistm.setCity(object.getString("city"));
                                charitylistm.setState(object.getString("state"));
                                charitylistm.setZip_code(object.getString("zip_code"));
                                charitylistm.setLogo(object.getString("logo"));

                                charitylistm.setLiked(object.getString("liked"));
                                charitylistm.setFollowed(object.getString("followed"));
                                charitylistm.setLike_count(object.getString("like_count"));


                                for (int j = 0; j < userDataArrayList.size(); j++) {
                                    if (userDataArrayList.get(j).getCurrency_code().equals(object.getString("country"))) {
                                        charitylistm.setCountry(userDataArrayList.get(j).getCurrency_name());
                                    }
                                }

                                map.put("name", object.getString("name"));
                                map.put("street", object.getString("street"));
                                map.put("city", object.getString("city"));
                                map.put("state", object.getString("state"));
                                map.put("zip_code", object.getString("zip_code"));
                                map.put("logo", object.getString("logo"));

                                map.put("liked", object.getString("liked"));
                                map.put("followed", object.getString("followed"));
                                map.put("like_count", object.getString("like_count"));
                                map.put("country", object.getString("country"));
                                charitylist1.add(charitylistm);
                            }

                            if (charitylist1.size() != 0) {
                                united_state_name_recyclerview.setVisibility(View.VISIBLE);
                                layoutManager = new LinearLayoutManager(context);
                                united_state_name_recyclerview.setLayoutManager(layoutManager);
                                united_state_name_recyclerview.setHasFixedSize(true);
                                united_state_name_recyclerview.setNestedScrollingEnabled(true);
                                unitesStateLocationAdapterList.notifyDataSetChanged();
                                united_state_name_recyclerview.setNestedScrollingEnabled(true);
                                united_state_name_recyclerview.setItemAnimator(null);
                                unitesStateLocationAdapterList = new LoadMoreUnitesStateLocationAdapterList((NameSearchActivity) context, charitylist1);
                                united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList);
                            } else {
                                united_state_name_recyclerview.setVisibility(View.GONE);
                                no_data_linear.setVisibility(View.VISIBLE);
                                no_data_tv.setText(message);
                            }

                        } else {
                            if (String.valueOf(page).equalsIgnoreCase("1")) {
                                united_state_name_recyclerview.setVisibility(View.GONE);
                                no_data_linear.setVisibility(View.VISIBLE);
                                no_data_tv.setText(message);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    shimmer_view_container.stopShimmer();
                    shimmer_view_container.setVisibility(View.GONE);
                    no_data_linear.setVisibility(View.VISIBLE);
                    united_state_name_recyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, t.toString());
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.VISIBLE);
                united_state_name_recyclerview.setVisibility(View.GONE);
            }
        });
    }*/

    private static JSONArray concatArray(JSONArray arr1, JSONArray arr2)
            throws JSONException {
        JSONArray result = new JSONArray();
        for (int i = 0; i < arr1.length(); i++) {
            result.put(arr1.get(i));
        }
        for (int i = 0; i < arr2.length(); i++) {
            result.put(arr2.get(i));
        }
        return result;
    }

    private void loadMore() {
        charitylist1.add(null);

//        unitesStateLocationAdapterList.notifyItemInserted(charitylist1.size() - 1);
        unitesStateLocationAdapterList2.notifyItemInserted(charitylist1.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    charitylist1.remove(charitylist1.size() - 1);
                    int scrollPosition = charitylist1.size();

                    united_state_name_recyclerview.setNestedScrollingEnabled(true);
//                    unitesStateLocationAdapterList.notifyItemRemoved(scrollPosition);
                    unitesStateLocationAdapterList2.notifyItemRemoved(scrollPosition);
                    int currentSize = scrollPosition;

                    int nextLimit = currentSize + 20;


                    if (nextLimit >= arrayListsize) {
                        pageno++;
//                        CharityAPI(pageno);
                        TempleListAPI(pageno);
                        loading = false;
                    }

                    for (int i = currentSize - 1; i < nextLimit; i++) {
                        HashMap<String, String> map = new HashMap<>();
                        Charitylist charitylistm = new Charitylist();
                        JSONObject object = jsonArray2.getJSONObject(i);
                        map.put("id", object.getString("id"));
                        charitylistm.setId(object.getString("id"));
                        charitylistm.setName(object.getString("name"));
                        charitylistm.setStreet(object.getString("street"));
                        charitylistm.setCity(object.getString("city"));
                        charitylistm.setState(object.getString("state"));
                        charitylistm.setZip_code(object.getString("zip_code"));
                        charitylistm.setLogo(object.getString("logo"));

                        charitylistm.setLiked(object.getString("liked"));
                        charitylistm.setFollowed(object.getString("followed"));
                        charitylistm.setLike_count(object.getString("like_count"));
                        charitylistm.setDescription("");
                        charitylistm.setCountry(object.getString("country"));
                        map.put("name", object.getString("name"));
                        map.put("street", object.getString("street"));
                        map.put("city", object.getString("city"));
                        map.put("state", object.getString("state"));
                        map.put("zip_code", object.getString("zip_code"));
                        map.put("logo", object.getString("logo"));

                        map.put("liked", object.getString("liked"));
                        map.put("followed", object.getString("followed"));
                        map.put("like_count", object.getString("like_count"));

                        map.put("country", object.getString("country"));

                        charitylist1.add(charitylistm);
                        loading = false;
                    }

                    layoutManager = new LinearLayoutManager(context);
                    united_state_name_recyclerview.setLayoutManager(layoutManager);
                    united_state_name_recyclerview.setHasFixedSize(true);
                    united_state_name_recyclerview.setNestedScrollingEnabled(true);
//                    unitesStateLocationAdapterList.notifyDataSetChanged();
                    unitesStateLocationAdapterList2.notifyDataSetChanged();
                    united_state_name_recyclerview.setItemAnimator(null);
                    notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }

    public static void nodata(int i) {
        if (i == 0) {
            no_data_linear.setVisibility(View.VISIBLE);
            united_state_name_recyclerview.setVisibility(View.GONE);

        } else if (i == 1) {
            no_data_linear.setVisibility(View.GONE);
            united_state_name_recyclerview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        listOfdate = iDonateSharedPreference.getselectedtypedata(getApplicationContext());
        listofsubCategory = iDonateSharedPreference.getselectedsubcategorydata(getApplicationContext());
        listofchilCategory = iDonateSharedPreference.getselectedchildcategorydata(getApplicationContext());
        pageno = 1;
        if (listOfdate.size() > 0) {
            backflag = 1;
//            CharityAPI(pageno);
            TempleListAPI(pageno);
        }

        StringBuilder builder = new StringBuilder();
        for (String details : listOfdate) {
            if (!details.isEmpty()) {
                builder.append(details + " ," + " ");
            } else {
                builder.append(details);
            }

        }
        if (data.equalsIgnoreCase("1")) {
            backflag = 1;
            flag = 1;

        } else if (data.equalsIgnoreCase("0")) {
            backflag = 0;
            flag = 0;
        }

        if (flag == 1) {
            search_name_et.setText(iDonateSharedPreference.getLocation(context));

//            CharityAPI(pageno);
            TempleListAPI(pageno);
        } else {
            if (flag == 0) {
//                CharityAPI(pageno);
                TempleListAPI(pageno);

            } else {
                /*unitesStateLocationAdapterList = new LoadMoreUnitesStateLocationAdapterList((NameSearchActivity) context, charitylist1);
                united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList);*/

                unitesStateLocationAdapterList2 = new LoadMoreUnitesStateLocationAdapterList2((NameSearchActivity) context, filteredDataList);
                united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList2);
            }

        }
        show_type = iDonateSharedPreference.gettype(getApplicationContext());

        show_advance = iDonateSharedPreference.getadvance(getApplicationContext());

        /*unitesStateLocationAdapterList = new LoadMoreUnitesStateLocationAdapterList((NameSearchActivity) context, charitylist1);
        united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList);*/

        unitesStateLocationAdapterList2 = new LoadMoreUnitesStateLocationAdapterList2((NameSearchActivity) context, filteredDataList);
        united_state_name_recyclerview.setAdapter(unitesStateLocationAdapterList2);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (backflag == 1) {
            pageno = 1;
            listOfdate.clear();
            search_name_et.setText("");
            iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
            iDonateSharedPreference.setselectedcategorydata(getApplicationContext(), listOfdate);
            iDonateSharedPreference.setselectedsubcategorydata(getApplicationContext(), listOfdate);
            iDonateSharedPreference.setselectedchildcategorydata(getApplicationContext(), listOfdate);
            iDonateSharedPreference.setSearchName(getApplicationContext(), "");
            iDonateSharedPreference.setLocation(getApplicationContext(), "");
            iDonateSharedPreference.setRevenue(getApplicationContext(), "");
            iDonateSharedPreference.setDeductible(getApplicationContext(), "");
            CategorylistAdapter.categoty_item.clear();
            listofsubCategory = iDonateSharedPreference.getselectedsubcategorydata(getApplicationContext());
            listofchilCategory = iDonateSharedPreference.getselectedchildcategorydata(getApplicationContext());
//            CharityAPI(pageno);
            TempleListAPI(pageno);
            backflag = 0;
        } else {
            ChangeActivity.changeActivity(NameSearchActivity.this, BrowseActivity.class);
            finishAffinity();
        }
    }
}
