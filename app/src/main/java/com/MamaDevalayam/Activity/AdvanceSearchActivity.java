package com.MamaDevalayam.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Adapter.ThreeLevelViewAdapter;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvanceSearchActivity extends AppCompatActivity {

    private static final String TAG = AdvanceSearchActivity.class.getSimpleName();
    ExpandableListView recyclerView_ad;
    ApiInterface apiService;
    ImageView backButton;
    RecyclerView.LayoutManager layoutManager;
    static SessionManager session;
    static HashMap<String, String> userDetails;
    TextView exempt_tv_deselect, exempt_tv_select, non_exempt_deselect_tv, non_exempt_select_tv;
    private String[] grandItems = new String[]{"Arts, culture & humanities", "Education", "Environment", "Animal-related", "Health care", "Mental health & crisis intervention", "Diseases, disorders & medical disciplines"};

    private String[] parentItem1 = new String[]{"Alliances & advocacy", "Management & technical assistance", "Professional societies & associations", "Research institutes & public policy analysis", "Single organization support", "Arts & culture"};
    private String[] parentItem2 = new String[]{"Alliances & advocacy", "Management & technical assistance", "Professional societies & associations", "Research institutes & public policy analysis", "Single organization support", "Arts & culture"};
    private String[] parentItem3 = new String[]{"Alliances & advocacy", "Management & technical assistance", "Professional societies & associations", "Research institutes & public policy analysis", "Single organization support", "Arts & culture"};
    private String[] parentItem4 = new String[]{"Alliances & advocacy", "Management & technical assistance", "Professional societies & associations", "Research institutes & public policy analysis", "Single organization support", "Arts & culture"};
    private String[] parentItem5 = new String[]{"Alliances & advocacy", "Management & technical assistance", "Professional societies & associations", "Research institutes & public policy analysis", "Single organization support", "Arts & culture"};
    private String[] parentItem6 = new String[]{"Alliances & advocacy", "Management & technical assistance", "Professional societies & associations", "Research institutes & public policy analysis", "Single organization support", "Arts & culture"};
    private String[] parentItem7 = new String[]{"Alliances & advocacy", "Management & technical assistance", "Professional societies & associations", "Research institutes & public policy analysis", "Single organization support", "Arts & culture"};

    private String[] childItems = new String[]{"Cultural & ethnic awareness", "Folk arts", "Arts education", "Arts & humanities councils & agencies", "Community celebrations"};
    private String[] emptyItems = new String[]{"No Subtypes Available"};

    LinkedHashMap<String, String[]> thirdLevelparentItem1 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelparentItem2 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelparentItem3 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelparentItem4 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelparentItem5 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelparentItem6 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelparentItem7 = new LinkedHashMap<>();

    List<String[]> secondLevel = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();
    static LinearLayout bottom_layout;
    static Button reset_button, apply_button;
    SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);
        init();
        listener();
    }

    private void listener() {

        exempt_tv_deselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                non_exempt_select_tv.setVisibility(View.GONE);
                non_exempt_deselect_tv.setVisibility(View.VISIBLE);
                exempt_tv_deselect.setVisibility(View.GONE);
                exempt_tv_select.setVisibility(View.VISIBLE);
            }
        });
        exempt_tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exempt_tv_select.setVisibility(View.GONE);
                exempt_tv_deselect.setVisibility(View.VISIBLE);
            }
        });
        non_exempt_deselect_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exempt_tv_select.setVisibility(View.GONE);
                exempt_tv_deselect.setVisibility(View.VISIBLE);
                non_exempt_deselect_tv.setVisibility(View.GONE);
                non_exempt_select_tv.setVisibility(View.VISIBLE);
            }
        });
        non_exempt_select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                non_exempt_select_tv.setVisibility(View.GONE);
                non_exempt_deselect_tv.setVisibility(View.VISIBLE);
            }
        });
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreeLevelViewAdapter threeLevelListAdapterAdapter = new ThreeLevelViewAdapter(AdvanceSearchActivity.this, grandItems, secondLevel, data);
                recyclerView_ad.setAdapter(threeLevelListAdapterAdapter);
                bottom_layout.setVisibility(View.GONE);
            }
        });
        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity.changeActivity(AdvanceSearchActivity.this, UnitedStateActivity.class);
                finish();
            }
        });


    }

    private void init() {
        session = new SessionManager(getApplicationContext());
        secondLevel.add(parentItem1);
        secondLevel.add(parentItem2);
        secondLevel.add(parentItem3);
        secondLevel.add(parentItem4);
        secondLevel.add(parentItem5);
        secondLevel.add(parentItem6);
        secondLevel.add(parentItem7);


        thirdLevelparentItem1.put(parentItem1[0], emptyItems);
        thirdLevelparentItem1.put(parentItem1[1], emptyItems);
        thirdLevelparentItem1.put(parentItem1[2], emptyItems);
        thirdLevelparentItem1.put(parentItem1[3], emptyItems);
        thirdLevelparentItem1.put(parentItem1[4], emptyItems);
        thirdLevelparentItem1.put(parentItem1[5], childItems);

        thirdLevelparentItem2.put(parentItem1[0], emptyItems);
        thirdLevelparentItem2.put(parentItem1[1], emptyItems);
        thirdLevelparentItem2.put(parentItem1[2], emptyItems);
        thirdLevelparentItem2.put(parentItem1[3], emptyItems);
        thirdLevelparentItem2.put(parentItem1[4], emptyItems);
        thirdLevelparentItem2.put(parentItem2[5], childItems);

        thirdLevelparentItem3.put(parentItem1[0], emptyItems);
        thirdLevelparentItem3.put(parentItem1[1], emptyItems);
        thirdLevelparentItem3.put(parentItem1[2], emptyItems);
        thirdLevelparentItem3.put(parentItem1[3], emptyItems);
        thirdLevelparentItem3.put(parentItem1[4], emptyItems);
        thirdLevelparentItem3.put(parentItem3[5], childItems);

        thirdLevelparentItem4.put(parentItem1[0], emptyItems);
        thirdLevelparentItem4.put(parentItem1[1], emptyItems);
        thirdLevelparentItem4.put(parentItem1[2], emptyItems);
        thirdLevelparentItem4.put(parentItem1[3], emptyItems);
        thirdLevelparentItem4.put(parentItem1[4], emptyItems);
        thirdLevelparentItem4.put(parentItem4[5], childItems);

        thirdLevelparentItem5.put(parentItem1[0], emptyItems);
        thirdLevelparentItem5.put(parentItem1[1], emptyItems);
        thirdLevelparentItem5.put(parentItem1[2], emptyItems);
        thirdLevelparentItem5.put(parentItem1[3], emptyItems);
        thirdLevelparentItem5.put(parentItem1[4], emptyItems);
        thirdLevelparentItem5.put(parentItem5[5], childItems);

        thirdLevelparentItem6.put(parentItem1[0], emptyItems);
        thirdLevelparentItem6.put(parentItem1[1], emptyItems);
        thirdLevelparentItem6.put(parentItem1[2], emptyItems);
        thirdLevelparentItem6.put(parentItem1[3], emptyItems);
        thirdLevelparentItem6.put(parentItem1[4], emptyItems);
        thirdLevelparentItem6.put(parentItem6[5], childItems);

        thirdLevelparentItem7.put(parentItem1[0], emptyItems);
        thirdLevelparentItem7.put(parentItem1[1], emptyItems);
        thirdLevelparentItem7.put(parentItem1[2], emptyItems);
        thirdLevelparentItem7.put(parentItem1[3], emptyItems);
        thirdLevelparentItem7.put(parentItem1[4], emptyItems);
        thirdLevelparentItem7.put(parentItem7[5], childItems);

        data.add(thirdLevelparentItem1);
        data.add(thirdLevelparentItem2);
        data.add(thirdLevelparentItem3);
        data.add(thirdLevelparentItem4);
        data.add(thirdLevelparentItem5);
        data.add(thirdLevelparentItem6);
        data.add(thirdLevelparentItem7);

        ThreeLevelViewAdapter threeLevelListAdapterAdapter = new ThreeLevelViewAdapter(this, grandItems, secondLevel, data);

        exempt_tv_deselect = (TextView) findViewById(R.id.exempt_tv_deselect);
        exempt_tv_select = (TextView) findViewById(R.id.exempt_tv_select);
        non_exempt_deselect_tv = (TextView) findViewById(R.id.non_exempt_deselect_tv);
        non_exempt_select_tv = (TextView) findViewById(R.id.non_exempt_select_tv);
        recyclerView_ad = (ExpandableListView) findViewById(R.id.expandable_listview);
        reset_button = (Button) findViewById(R.id.reset_button);
        apply_button = (Button) findViewById(R.id.apply_button);
        recyclerView_ad.setAdapter(threeLevelListAdapterAdapter);
        backButton = (ImageView) findViewById(R.id.back_icon_login_img);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setMax(5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView_ad.setGroupIndicator(null);
        recyclerView_ad.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    recyclerView_ad.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

    }

    public static void showbottombottm() {
        bottom_layout.setVisibility(View.VISIBLE);
    }

    private void AdvanceCatAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        userDetails = session.getUserDetails();
        Log.e(TAG, "userDetails - " + userDetails);
        Log.e(TAG, "KEY_UID - " + userDetails.get(SessionManager.KEY_UID));
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

        Log.e(TAG, "jsonObject1 - " + jsonObject1);


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
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        Log.e(TAG, "jsonObject - " + jsonObject);
                        String status = jsonObject.getString("status");
                        String tokenStatus = jsonObject.getString("token_status");
                        String message = jsonObject.getString("message");
                        String data = jsonObject.getString("data");
                        if (status.equalsIgnoreCase("1")) {
                            if (tokenStatus.equalsIgnoreCase("1")) {
                                JSONArray jsonArray = new JSONArray(data);
                                Log.e(TAG, "1232 - " + jsonArray.length());
                                if (!data.equalsIgnoreCase("")) {

                                } else ConstantFunctions.showSnakBar("Empty Data", backButton);
                            } else ConstantFunctions.showSnakBar("Invalid Token", backButton);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, " onFailure - " + t.toString());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Exception - " + e);
            e.printStackTrace();

        }


    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
//        AdvanceCatAPI();
    }
}