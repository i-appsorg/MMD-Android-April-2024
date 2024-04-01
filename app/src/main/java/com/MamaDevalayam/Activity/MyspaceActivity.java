package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.CommonActivity.CommonMenuActivity;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Adapter.MyspaceAdapterList;
import com.MamaDevalayam.Model.Charitylist;
import com.MamaDevalayam.Model.DonatedCharityList;
import com.MamaDevalayam.Model.Selected;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyspaceActivity extends CommonMenuActivity {
    private String TAG = "MyspaceActivity";
    Toolbar toolbar;
    RecyclerView myspace_recyclerview_list;
    List<Map<String, String>> listOfdate = new ArrayList<Map<String, String>>();
    private RecyclerView.LayoutManager layoutManager;
    MyspaceAdapterList myspaceAdapterList;
    LinearLayout donation_layout, my_likes_linear_layout, follower_linear_layout;
    SessionManager session;
    static ApiInterface apiService;
    public static List<HashMap<String, String>> like_charity_Arraylist = new ArrayList<HashMap<String, String>>();
    public static List<HashMap<String, String>> follow_charity_Arraylist = new ArrayList<HashMap<String, String>>();
    public static List<HashMap<String, String>> payment_charity_Arraylist = new ArrayList<HashMap<String, String>>();
    static ArrayList<Charitylist> like_charitylist1 = new ArrayList<>();
    static ArrayList<Charitylist> follow_charitylist1 = new ArrayList<>();
    static ArrayList<Charitylist> donate_charitylist1 = new ArrayList<>();
    static ArrayList<DonatedCharityList> DonatedCharityList1 = new ArrayList<>();
    static ArrayList<DonatedCharityList> HistoryCharityList1 = new ArrayList<>();
    static HashMap<String, String> userDetails;
    TextView donation_tv_count, follower_tv_count, like_tv_count, myspace_title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_myspace, TAG);
        setTitle("My space");
        toolbar = findViewById(R.id.commonMenuActivityToolbar);
        setSelected(Selected.Myspace);
        init();
        listener();
    }

    private void init() {
        session = new SessionManager(getApplicationContext());
        myspace_recyclerview_list = (RecyclerView) findViewById(R.id.myspace_recyclerview_list);
        donation_layout = (LinearLayout) findViewById(R.id.donation_layout);
        my_likes_linear_layout = (LinearLayout) findViewById(R.id.my_likes_linear_layout);
        follower_linear_layout = (LinearLayout) findViewById(R.id.follower_linear_layout);
        donation_tv_count = (TextView) findViewById(R.id.donation_tv_count);
        follower_tv_count = (TextView) findViewById(R.id.follower_tv_count);
        like_tv_count = (TextView) findViewById(R.id.like_tv_count);
        myspace_title_tv = (TextView) findViewById(R.id.myspace_title_tv);
        layoutManager = new LinearLayoutManager(this);
        myspace_recyclerview_list.setLayoutManager(layoutManager);
        myspace_recyclerview_list.setItemAnimator(new DefaultItemAnimator());
        myspaceAdapterList = new MyspaceAdapterList(listOfdate);
        myspace_recyclerview_list.setAdapter(myspaceAdapterList);
        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        Log.e("KEY_username", "" + userDetails.get(SessionManager.KEY_NAME));
//        String upperString = userDetails.get(SessionManager.KEY_NAME).substring(0, 1).toUpperCase() + userDetails.get(SessionManager.KEY_NAME).substring(1);
//        myspace_title_tv.setText(upperString + "'s Space");


    }

    public static String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void FollowLikedonationAPI() {
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
        jsonObject1.addProperty("device_id", getDeviceUniqueID(MyspaceActivity.this));
        Log.e("jsonObject1", "" + jsonObject1);
        transaction_listAPI(user_id);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiService.likes_and_followings(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                like_charity_Arraylist.clear();
                follow_charity_Arraylist.clear();
                payment_charity_Arraylist.clear();
                like_charitylist1.clear();
                follow_charitylist1.clear();
                donate_charitylist1.clear();
                HistoryCharityList1.clear();

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Log.e(TAG, "my space: " + jsonObject.toString());
                        String message = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            String data = jsonObject.getString("data");
                            Log.e(TAG, data);
                            JSONObject jsonObject2 = new JSONObject(data);
                            String like_count = jsonObject2.getString("like_count");
                            like_tv_count.setText(like_count);
                            String like_charity_list = jsonObject2.getString("like_charity_list");
                            Log.e("like_charity_list", like_charity_list);
                            String following_count = jsonObject2.getString("following_count");
                            String payment_count = jsonObject2.getString("payment_count");
                            donation_tv_count.setText(payment_count);
                            follower_tv_count.setText(following_count);
                            String following_charity_list = jsonObject2.getString("following_charity_list");
                            String payment_history_list = jsonObject2.getString("payment_history_list");
                            Log.e("following_charity_list", following_charity_list);
                            JSONArray jsonArray = new JSONArray(like_charity_list);
                            Log.e("jsonArray", "" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HashMap<String, String> map = new HashMap<>();
                                Charitylist charitylistm = new Charitylist();
                                JSONObject object = jsonArray.getJSONObject(i);

                                charitylistm.setId(object.getString("id"));
                                charitylistm.setName(object.getString("name"));
                                charitylistm.setStreet(object.getString("street"));
                                charitylistm.setCity(object.getString("city"));
                                charitylistm.setState(object.getString("state"));
                                charitylistm.setZip_code(object.getString("zip_code"));
                                charitylistm.setLogo(object.getString("logo"));
                                //charitylistm.setBanner(object.getString("banner"));
                                // charitylistm.setLatitude(object.getString("latitude"));
                                // charitylistm.setLongitude(object.getString("longitude"));
                                charitylistm.setLiked(object.getString("liked"));
                                charitylistm.setFollowed(object.getString("followed"));
                                charitylistm.setLike_count(object.getString("like_count"));
                                // charitylistm.setDescription(object.getString("description"));
                                charitylistm.setCountry(object.getString("country"));
                                map.put("id", object.getString("id"));
                                map.put("name", object.getString("name"));
                                map.put("street", object.getString("street"));
                                map.put("city", object.getString("city"));
                                map.put("state", object.getString("state"));
                                map.put("zip_code", object.getString("zip_code"));
                                map.put("logo", object.getString("logo"));
                                // map.put("banner", object.getString("banner"));
                                // map.put("latitude", object.getString("latitude"));
                                // map.put("longitude", object.getString("longitude"));
                                map.put("liked", object.getString("liked"));
                                map.put("followed", object.getString("followed"));
                                map.put("like_count", object.getString("like_count"));
                                // map.put("description", object.getString("description"));
                                map.put("country", object.getString("country"));
                                like_charity_Arraylist.add(map);
                                like_charitylist1.add(charitylistm);
                                Log.e("like_charity_Arraylist", "" + like_charity_Arraylist);

                            }

                            JSONArray jsonArrayfollow = new JSONArray(following_charity_list);
                            Log.e("jsonArray", "" + jsonArrayfollow);
                            for (int i = 0; i < jsonArrayfollow.length(); i++) {
                                HashMap<String, String> map = new HashMap<>();
                                Charitylist charitylistf = new Charitylist();
                                JSONObject object = jsonArrayfollow.getJSONObject(i);

                                charitylistf.setId(object.getString("id"));
                                charitylistf.setName(object.getString("name"));
                                charitylistf.setStreet(object.getString("street"));
                                charitylistf.setCity(object.getString("city"));
                                charitylistf.setState(object.getString("state"));
                                charitylistf.setZip_code(object.getString("zip_code"));
                                charitylistf.setLogo(object.getString("logo"));
                                // charitylistf.setBanner(object.getString("banner"));
                                // charitylistf.setLatitude(object.getString("latitude"));
                                //  charitylistf.setLongitude(object.getString("longitude"));
                                charitylistf.setLiked(object.getString("liked"));
                                charitylistf.setFollowed(object.getString("followed"));
                                charitylistf.setLike_count(object.getString("like_count"));
                                //   charitylistf.setDescription(object.getString("description"));
                                charitylistf.setCountry(object.getString("country"));
                                map.put("id", object.getString("id"));
                                map.put("name", object.getString("name"));
                                map.put("street", object.getString("street"));
                                map.put("city", object.getString("city"));
                                map.put("state", object.getString("state"));
                                map.put("zip_code", object.getString("zip_code"));
                                map.put("logo", object.getString("logo"));
                                // map.put("banner", object.getString("banner"));
                                // map.put("latitude", object.getString("latitude"));
                                // map.put("longitude", object.getString("longitude"));
                                map.put("liked", object.getString("liked"));
                                map.put("followed", object.getString("followed"));
                                map.put("like_count", object.getString("like_count"));
                                //  map.put("description", object.getString("description"));
                                map.put("country", object.getString("country"));
                                follow_charity_Arraylist.add(map);
                                follow_charitylist1.add(charitylistf);
                                Log.e("follow_Arraylist", "" + follow_charity_Arraylist);

                            }

                            JSONArray jsonArraypayment = new JSONArray(payment_history_list);
                            Log.e("jsonArray", "" + jsonArraypayment.toString());
                            for (int i = 0; i < jsonArraypayment.length(); i++) {
                                HashMap<String, String> map = new HashMap<>();
                                Charitylist charitylistf = new Charitylist();
                                JSONObject object = jsonArraypayment.getJSONObject(i);
                                Log.e(TAG, "history: " + object.getString("history"));
                                charitylistf.setId(object.getString("id"));
                                charitylistf.setName(object.getString("name"));
                                charitylistf.setStreet(object.getString("street"));
                                charitylistf.setCity(object.getString("city"));
                                charitylistf.setState(object.getString("state"));
                                charitylistf.setZip_code(object.getString("zip_code"));
                                charitylistf.setLogo(object.getString("logo"));
                                //  charitylistf.setBanner(object.getString("banner"));
                                //charitylistf.setLatitude(object.getString("latitude"));
                                //charitylistf.setLongitude(object.getString("longitude"));
                                charitylistf.setLiked(object.getString("liked"));
                                charitylistf.setFollowed(object.getString("followed"));
                                charitylistf.setLike_count(object.getString("like_count"));
                                // charitylistf.setDescription(object.getString("description"));
                                charitylistf.setCountry(object.getString("country"));
                                map.put("id", object.getString("id"));
                                map.put("name", object.getString("name"));
                                map.put("street", object.getString("street"));
                                map.put("city", object.getString("city"));
                                map.put("state", object.getString("state"));
                                map.put("zip_code", object.getString("zip_code"));
                                map.put("logo", object.getString("logo"));
                                //map.put("banner", object.getString("banner"));
                                // map.put("latitude", object.getString("latitude"));
                                // map.put("longitude", object.getString("longitude"));
                                map.put("liked", object.getString("liked"));
                                map.put("followed", object.getString("followed"));
                                map.put("like_count", object.getString("like_count"));
                                //map.put("description", object.getString("description"));
                                map.put("country", object.getString("country"));
                                payment_charity_Arraylist.add(map);


                                JSONArray paymentHistory = object.getJSONArray("history");
                                for (int j = 0; j < paymentHistory.length(); j++) {
                                    JSONObject paymentObjcet = paymentHistory.getJSONObject(j);
                                    DonatedCharityList donatedCharityList = new DonatedCharityList();
                                    donatedCharityList.setCharity_name(object.getString("name"));
                                    donatedCharityList.setAmount(paymentObjcet.getString("amount"));
                                    donatedCharityList.setDate(paymentObjcet.getString("donate_date"));
                                    donatedCharityList.setPayment_type("");
                                    HistoryCharityList1.add(donatedCharityList);
                                    Log.e(TAG, "onResponse: " + paymentObjcet.getString("amount"));
                                    Log.e(TAG, "onResponse: " + paymentHistory.length());
                                }

                                donate_charitylist1.add(charitylistf);
                                Log.e("follow_Arraylist", "" + payment_charity_Arraylist);
                                Log.e(TAG, "donate_charitylist1: " + donate_charitylist1.size());

                            }
                        }
                        if (new IDonateSharedPreference().getnotificationpage(getApplicationContext()).equalsIgnoreCase("MyspaceActivity")) {
                            new IDonateSharedPreference().setnotificationpage(getApplicationContext(), "page");
                            Intent intent = new Intent(MyspaceActivity.this, FollowingActivity.class);
                            Log.e("payment_charitylist11", "" + donate_charitylist1);
                            intent.putExtra("mylist", HistoryCharityList1);
                            intent.putExtra("type", "donate");
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        Log.e("ER", "error1");
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, t.toString());
                Log.e("ER", "error2");
            }
        });
    }


    private void listener() {

        myspace_recyclerview_list.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        myspace_recyclerview_list.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < myspace_recyclerview_list.getChildCount(); i++) {
                            View v = myspace_recyclerview_list.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });

        my_likes_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyspaceActivity.this, FollowingActivity.class);
                intent.putExtra("mylist", like_charitylist1);
                intent.putExtra("type", "likes");
                startActivity(intent);
//                finish();

            }
        });

        follower_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyspaceActivity.this, FollowingActivity.class);
                Log.e("follow_charitylist11", "" + follow_charitylist1);
                intent.putExtra("mylist", follow_charitylist1);
                intent.putExtra("type", "follows");
                startActivity(intent);
//               finish();

            }
        });
        donation_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyspaceActivity.this, FollowingActivity.class);

                Log.e("payment_charitylist11", "" + DonatedCharityList1);
                Log.e("TEstSuccess", "" + DonatedCharityList1);

                intent.putExtra("mylist", HistoryCharityList1);
                intent.putExtra("type", "donate");
                startActivity(intent);
//                finish();
            }
        });
    }

    private void transaction_listAPI(String user_id) {


        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        Log.e("jsonObject112", "" + jsonObject1);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.transaction_list(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                DonatedCharityList1.clear();
                Log.e("transaction_listAPI", "" + response.body());
                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String message = jsonObject.getString("message");
                        String dotatedList = jsonObject.getString("data");

                        JSONArray jsonArray = new JSONArray(dotatedList);

                        Log.e("jsonArray", "" + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            DonatedCharityList DonatedCharityList11 = new DonatedCharityList();
                            JSONObject object = jsonArray.getJSONObject(i);

                            DonatedCharityList11.setDate(object.getString("date"));
                            DonatedCharityList11.setAmount(object.getString("amount"));
                            DonatedCharityList11.setPayment_type(object.getString("payment_type"));
                            DonatedCharityList11.setCharity_name(object.getString("charity_name"));
                            DonatedCharityList1.add(DonatedCharityList11);
                        }
                        Log.e("DonatedList1_size", "" + DonatedCharityList1.size());

                    } catch (JSONException e) {
                        Log.e("ER", "error1");
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.e("unitedstate", t.toString());

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnline()) {
            FollowLikedonationAPI();
        } else {
            Toast.makeText(this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
        }
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


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


}
