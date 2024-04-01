package com.MamaDevalayam.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.Adapter.LoadmoreInternationlocationAdapterList;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

public class PlaceSearchActivity extends AppCompatActivity {
    EditText autocomplete_places;
    RecyclerView placesRecyclerView;
    ImageView back;
    String data;
    IDonateSharedPreference iDonateSharedPreference;
    static String latlanvalue = "";
    static Context context;
    static HashMap<String, String> userDetails;
    static SessionManager session;
    static ArrayList<String> listOfdate = new ArrayList<>();
    static ArrayList<String> listofsubCategory = new ArrayList<>();
    static ArrayList<String> listofchilCategory = new ArrayList<>();
    static LinearLayout no_data_linear;
    static TextView no_data_tv;
    ArrayList<Charitylist> charitylist1 = new ArrayList<>();
    LoadmoreInternationlocationAdapterList internationlocationAdapterList;
    static ApiInterface apiService;
    static int arrayListsize = 0;
    static JSONArray jsonArray1;
    static JSONArray jsonArray2;
    static JSONArray jsonArray;
    static String page = "1";
    static List<HashMap<String, String>> charitylist = new ArrayList<HashMap<String, String>>();
    ArrayList<CurrencyBean> userDataArrayList = new ArrayList<>();
    ImageView search_icon, close_img;
    NestedScrollView nestedscrollview;
    static ShimmerFrameLayout shimmer_view_container;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    Boolean loading = false;
    private static LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);

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

        context = PlaceSearchActivity.this;
        iDonateSharedPreference = new IDonateSharedPreference();
        session = new SessionManager(getApplicationContext());
        listOfdate = iDonateSharedPreference.getselectedtypedata(getApplicationContext());
        listofsubCategory = iDonateSharedPreference.getselectedsubcategorydata(getApplicationContext());
        listofchilCategory = iDonateSharedPreference.getselectedchildcategorydata(getApplicationContext());


        autocomplete_places = (EditText) findViewById(R.id.autocomplete_places);
        placesRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        search_icon = findViewById(R.id.search_icon);
        close_img = findViewById(R.id.close_img);
        no_data_linear = (LinearLayout) findViewById(R.id.no_data_linear);
        no_data_tv = findViewById(R.id.no_data_tv);
        nestedscrollview = (NestedScrollView) findViewById(R.id.nestedscrollview);
        shimmer_view_container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);

        back = (ImageView) findViewById(R.id.back_icon_autoplace_img);

        data = getIntent().getStringExtra("data");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        autocomplete_places.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = autocomplete_places.getText().toString();
                if (text.length() > 0) {
                    search_icon.setVisibility(View.GONE);
                    close_img.setVisibility(View.VISIBLE);
                } else {
                    search_icon.setVisibility(View.VISIBLE);
                    close_img.setVisibility(View.GONE);
                }

                if (text.length() > 2) {
                    page = "1";
                    CharityAPI(page);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocomplete_places.setText("");
                page = "1";
                placesRecyclerView.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.VISIBLE);
            }
        });

        nestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int i, int i1, int i2, int i3) {
                int x = i1 - i3;
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
    }

    private void loadMore() {
        charitylist1.add(null);
        internationlocationAdapterList.notifyItemInserted(charitylist1.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                charitylist1.remove(charitylist1.size() - 1);
                int scrollPosition = charitylist1.size();
                internationlocationAdapterList.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                Log.e("currentSize", "" + currentSize);
                int nextLimit = currentSize + 20;
                Log.e("nextLimit", "" + nextLimit);
                Log.e("charitylist1232", "" + arrayListsize);

                if (nextLimit >= arrayListsize) {
                    CharityAPI(page);
                    loading = false;
                }
                try {
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

                        charitylistm.setCountry(object.getString("country"));
                        map.put("id", object.getString("id"));
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
                        charitylist.add(map);
                        charitylist1.add(charitylistm);
                        loading = false;

                    }

                    layoutManager = new LinearLayoutManager(context);
                    placesRecyclerView.setLayoutManager(layoutManager);
                    placesRecyclerView.setHasFixedSize(true);
                    placesRecyclerView.setNestedScrollingEnabled(true);
                    internationlocationAdapterList.notifyDataSetChanged();
                    notifyAll();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (data.equalsIgnoreCase("1")) {
            ChangeActivity.changeActivityData(PlaceSearchActivity.this, UnitedStateActivity.class, "1");
            finish();
        }
        if (data.equalsIgnoreCase("2")) {
            ChangeActivity.changeActivityData(PlaceSearchActivity.this, InternationalCharitiesActivity.class, "1");
            finish();
        } else {
            finish();
        }
    }

    public static String getDeviceUniqueID(Context activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void CharityAPI(final String page) {
        shimmer_view_container.startShimmer();
        shimmer_view_container.setVisibility(View.VISIBLE);

        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id = "";
//todo login
        if (session.isLoggedIn()) {
            user_id = userDetails.get(SessionManager.KEY_UID);
        }

        String lat = "", lng = "";
        String location = iDonateSharedPreference.getLocation(context);
        Log.e("location321", "" + location);
        latlanvalue = location;
        if (location.equalsIgnoreCase(null) || location.equalsIgnoreCase("")) {
            location = "";
        }
        if (location.length() > 0) {
            LatLng loc = Constants.getFromLocation(context, location);
            lat = String.valueOf(loc.latitude);
            lng = String.valueOf(loc.longitude);
        }

        String searchName = "";
        String from_income = "";
        String to_income = "";
        String searchDeductible = "";
        String searchCity = "";

        JsonArray category_Array = new JsonArray();
        JsonArray subCategory_Array = new JsonArray();
        JsonArray childCategory_Array = new JsonArray();
        String countrycode = "";

        if (autocomplete_places.getText().length() > 2) {
            searchCity = autocomplete_places.getText().toString().trim();
        }

        String searchRevenue = iDonateSharedPreference.getRevenue(context);

        searchDeductible = iDonateSharedPreference.getDeductible(context);

        if (searchRevenue.equalsIgnoreCase("")) {
//                Do Nothing
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

        for (int i = 0; i < listOfdate.size(); i++) {
            category_Array.add(listOfdate.get(i));
        }

        for (int j = 0; j < listofsubCategory.size(); j++) {
            subCategory_Array.add(listofsubCategory.get(j));
        }

        for (int k = 0; k < listofchilCategory.size(); k++) {
            childCategory_Array.add(listofchilCategory.get(k));
        }

        countrycode = "INT";

        String device_id = getDeviceUniqueID(context);
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("name", searchName);
        jsonObject1.addProperty("city", searchCity);
        jsonObject1.addProperty("latitude", lat);
//        jsonObject1.addProperty("page", page);
        jsonObject1.addProperty("longitude", lng);
        jsonObject1.addProperty("address", location);
        jsonObject1.addProperty("device_id", device_id);
        jsonObject1.add("category_code", category_Array);
        jsonObject1.addProperty("deductible", searchDeductible);
        jsonObject1.addProperty("income_from", from_income);
        jsonObject1.addProperty("income_to", to_income);
        jsonObject1.addProperty("country_code", countrycode);
        jsonObject1.add("sub_category_code", subCategory_Array);
        jsonObject1.add("child_category_code", childCategory_Array);
        jsonObject1.addProperty("user_id", user_id);
        Log.e("jsonObject1", "" + jsonObject1);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiService.Charitylist(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.GONE);
                if (page.equalsIgnoreCase("1")) {
                    charitylist.clear();
                    charitylist1.clear();
                    arrayListsize = 0;
                    jsonArray1 = new JSONArray();
                    jsonArray2 = new JSONArray();
                }

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String message = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            String data = jsonObject.getString("data");
                            jsonArray = new JSONArray(data);
                            Log.e("TAG", "status: " + jsonArray);
                            int maxvalue = 10;
                            if (jsonArray.length() > 10) {
                                maxvalue = 10;
                            } else {
                                maxvalue = jsonArray.length();
                            }
                            Log.e("jsonArraylength", "" + jsonArray.length());
                            arrayListsize = arrayListsize + jsonArray.length();
                            if (page.equalsIgnoreCase("1")) {
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

                                map.put("id", object.getString("id"));
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
                                charitylist.add(map);
                                charitylist1.add(charitylistm);

                            }

                            if (charitylist.size() != 0) {
                                placesRecyclerView.setVisibility(View.VISIBLE);
                                layoutManager = new LinearLayoutManager(context);
                                placesRecyclerView.setLayoutManager(layoutManager);
                                placesRecyclerView.setHasFixedSize(true);
                                placesRecyclerView.setNestedScrollingEnabled(true);
                                internationlocationAdapterList = new LoadmoreInternationlocationAdapterList((PlaceSearchActivity) context, charitylist1);
                                internationlocationAdapterList.notifyDataSetChanged();
                                placesRecyclerView.setNestedScrollingEnabled(true);
                                placesRecyclerView.setItemAnimator(null);
                                internationlocationAdapterList = new LoadmoreInternationlocationAdapterList((PlaceSearchActivity) context, charitylist1);
                                placesRecyclerView.setAdapter(internationlocationAdapterList);

                            } else {
                                placesRecyclerView.setVisibility(View.GONE);
                                no_data_linear.setVisibility(View.VISIBLE);
                                no_data_tv.setText(message);
                            }
                        } else {
                            if (page.equalsIgnoreCase("1")) {
                                placesRecyclerView.setVisibility(View.GONE);
                                no_data_linear.setVisibility(View.VISIBLE);
                                no_data_tv.setText(message);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    placesRecyclerView.setVisibility(View.GONE);
                    no_data_linear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                placesRecyclerView.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.VISIBLE);
            }
        });
    }

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
}
