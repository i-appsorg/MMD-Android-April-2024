package com.MamaDevalayam.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.MamaDevalayam.Interwork.MyApplication;
import com.MamaDevalayam.Model.ChangeActivity;

import com.MamaDevalayam.Model.DeityModel;
import com.MamaDevalayam.Model.GetParmsDataModel;
import com.MamaDevalayam.Model.GetParmsModel;
import com.MamaDevalayam.Model.TempleListDataModel;
import com.MamaDevalayam.Model.TempleListModel;
import com.MamaDevalayam.R;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.MamaDevalayam.Session.PrefManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcome_better_tv, giving_tv;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private TextView btnNext;
    CheckBox checkbox_welcome;
    LinearLayout bypass_linearlayout;
    private PrefManager prefManager;
    private Handler handler = new Handler();
    int index = 0;
    int index1 = 0;
    int indexj = 0;
    JSONArray jsonArrayValues;
    String TAG = WelcomeActivity.class.getSimpleName();

    static GetParmsModel getParmsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.e("demochecking", "demochecking");
        init();
        getParmsApi();
        listener();
    }

    private void getParmsApi() {

        if (isOnline()) {
            Log.e(TAG, "onClick:--->> ");

            final ProgressDialog progressDialog = new ProgressDialog(WelcomeActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody requestBody = RequestBody.create(mediaType, "gparms");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://devmmdmob.i-apps.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<GetParmsModel> call = apiInterface.getParms(requestBody);
            call.enqueue(new Callback<GetParmsModel>() {
                @Override
                public void onResponse(Call<GetParmsModel> call, retrofit2.Response<GetParmsModel> response) {
                    Log.e(TAG, "123 GetParmsAPI = " + response);

                    progressDialog.dismiss();

                    getParmsModel = response.body();
                    List<GetParmsDataModel> getParmsDataModels = getParmsModel.getData();
                    Log.e(TAG, "onResponse:getParmsDataModels --->> " + getParmsDataModels);

                }

                @Override
                public void onFailure(Call<GetParmsModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e(TAG, t.toString());
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Log.e(TAG, "onFailure: " + t.toString());
                    Log.e(TAG, "onFailure: error - " + t.getLocalizedMessage());
                    Log.e(TAG, "onFailure: Failed with error msg:\t" + t.getMessage());
                    Log.e(TAG, "onFailure: Error StackTrace: \t" + t.getStackTrace());

                }

            });

        } else {
            Toast.makeText(WelcomeActivity.this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onClick:No Internet");
        }

    }

    private void init() {
        welcome_better_tv = (TextView) findViewById(R.id.welcome_better_tv);
        giving_tv = (TextView) findViewById(R.id.giving_tv);
        btnNext = (TextView) findViewById(R.id.btn_next);
        checkbox_welcome = (CheckBox) findViewById(R.id.checkbox_welcome);
        bypass_linearlayout = (LinearLayout) findViewById(R.id.bypass_linearlayout);
        /*welcome_better_tv.setTypeface(Typeface.createFromAsset(getAssets(), "commercialscriptbt.ttf"));
        giving_tv.setTypeface(Typeface.createFromAsset(getAssets(), "Chunkfive.otf"));*/
        prefManager = new PrefManager(this);
//        if (!prefManager.isFirstTimeLaunch()) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run:---> ");

                btnNext.performClick();
            }
        }, 5000);
//
//        }else {
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            }
//
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                /*R.layout.tutorial_slide1,*/
//                R.layout.tutorial_slide2};
                R.layout.tutorial_slide2_new};

        // adding bottom dots
//        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        int current = getItem(+1);
        if (current < layouts.length) {
            // move to next screen
            viewPager.setCurrentItem(current);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // if (new Session(SplashActivity.this, TAG).getIsLogin())
                    //       startActivity(new Intent(SplashActivity.this, ChooseTypeActivity.class));
                    //   else
                    //       startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    if (index == 0) {
                        if (!prefManager.isFirstTimeLaunch()) {
                            if (index1 != 1) {
//                                    launchHomeScreen1();
//                                    finish();
                            }

                        } else {
                            if (index1 != 1) {
//                                    launchHomeScreen();
                            }

                        }

                    }

                }
            }, 5000);
            //launchHomeScreen();
        }
//        }

        // Making notification bar transparent
//        launchHomeScreen();
    }

    private void listener() {
        btnNext.setOnClickListener(new View.OnClickListener() { // A 240523
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Log.e(TAG, "onClick:--->> ");

                    final ProgressDialog progressDialog = new ProgressDialog(WelcomeActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                    List<GetParmsDataModel> getParmsDataModels = getParmsModel.getData();
                    String google_sheet_id = getParmsDataModels.get(0).getMetaValue().toString();
                    String google_sheet_key = getParmsDataModels.get(1).getMetaValue().toString();
                    String google_sheet_tab = getParmsDataModels.get(2).getMetaValue().toString();
                    Log.e(TAG, "onClick:google_sheet_id === " + google_sheet_id);
                    Log.e(TAG, "onClick:google_sheet_key === " + google_sheet_key);
                    Log.e(TAG, "onClick:google_sheet_tab === " + google_sheet_tab);

                    String urls = "https://sheets.googleapis.com/v4/spreadsheets/" + google_sheet_id + "/values/" + google_sheet_tab + "?key=" + google_sheet_key;

                    String sheetID = MyApplication.getSheetId();
                    String apiKEY = MyApplication.getSheetApiKey();
//                    String sheetTabName = "i2D-Dev";
//                    String sheetTabName = "MMD-Dev";
//                    TODO new
                    String sheetTabName = "MMD-Dev";
//                    TODO old
//                    String sheetTabName = "i2D-Prod";

//                    String urls = "https://sheets.googleapis.com/v4/spreadsheets/" + sheetID + "/values/" + sheetTabName + "?key=" + apiKEY;

//                    String urls = "https://sheets.googleapis.com/v4/spreadsheets/1ZjrKWAsBuB0-P9A3-TePPUa3_t1iQxvY_FrH79fc5eQ/values/MMD-Dev?key=AIzaSyA9Cjc0gdK_CYnXSq1P_z3kG1dWg27h7c0";


                    Log.e(TAG, "onClick:urls === " + urls);
                    RequestQueue queue = Volley.newRequestQueue(WelcomeActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urls, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, " response - " + response);

                            progressDialog.dismiss();
                            try {
                                jsonArrayValues = response.getJSONArray("values");

                                for (int i = 0; i < jsonArrayValues.length(); i++) {
                                    JSONArray jsonArrayPos = jsonArrayValues.getJSONArray(i);
                                    for (int j = 0; j < jsonArrayPos.length(); j++) {
                                        if (jsonArrayPos.get(j).toString().contains("Server_URL")) {
                                            Log.e(TAG, " Server_URL - " + jsonArrayPos.get(j + 1).toString());

                                            String url = jsonArrayPos.get(j + 1).toString() + "/";
//                                            String fixedUrl = url.replaceFirst("^https?://https://", "https://");
//                                            Log.e(TAG, "onResponse:fixedUrl - " + fixedUrl);
                                            ApiClient.Server_URL = url.replaceFirst("^https?://https://", "https://");

//                                            ApiClient.Server_URL = jsonArrayPos.get(j + 1).toString() + "/";
                                            Log.e(TAG, "onResponse:ApiClient.Server_URL - " + ApiClient.Server_URL);

                                            //TODO 13 FEB 2024
//                                            ApiClient.Server_URL = "https://devmmd.i-apps.org/MMD_mob/webservice/";
                                        }
                                        if (jsonArrayPos.get(j).toString().contains("TC_URL")) {
                                            Log.e(TAG, " TC_URL - " + jsonArrayPos.get(j + 1).toString());
                                            ApiClient.TC_URL = jsonArrayPos.get(j + 1).toString();
                                        }
                                        if (jsonArrayPos.get(j).toString().contains("Privacy_URL")) {
                                            Log.e(TAG, " Privacy_URL - " + jsonArrayPos.get(j + 1).toString());
                                            ApiClient.Privacy_URL = jsonArrayPos.get(j + 1).toString();
                                        }
                                        if (jsonArrayPos.get(j).toString().contains("Help_URL")) {
                                            Log.e(TAG, " Help_URL - " + jsonArrayPos.get(j + 1).toString());
                                            ApiClient.Help_URL = jsonArrayPos.get(j + 1).toString();
                                        }
                                        if (jsonArrayPos.get(j).toString().contains("About_URL")) {
                                            Log.e(TAG, " About_URL - " + jsonArrayPos.get(j + 1).toString());
                                            ApiClient.About_URL = jsonArrayPos.get(j + 1).toString();
                                        }
                                    }
                                }
                                if (ApiClient.Server_URL.length() > 0) {
                                    index1 = 1;
                                    launchHomeScreen1();
                                    finishAffinity();
                                    indexj = 1;
                                } else {
                                    Toast.makeText(WelcomeActivity.this, "Something went wrong, please try after some time", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e(TAG, " Exception - " + e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e(TAG, "onErrorResponse: error - " + error.getLocalizedMessage());
                            Log.e(TAG, "onErrorResponse: error1 - " + error.getMessage());
                            Log.e(TAG, " error2 - " + error.toString());

                            Log.e(TAG, "onErrorResponse: Failed with error msg:\t" + error.getMessage());
                            Log.e(TAG, "onErrorResponse: Error StackTrace: \t" + error.getStackTrace());
                            try {
                                byte[] htmlBodyBytes = error.networkResponse.data;
                                Log.e(TAG, new String(htmlBodyBytes), error);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            if (error.getMessage() == null) {
                                Log.e(TAG, "onErrorResponse:---->>> ");
                            }

                        }
                    });
                    queue.add(jsonObjectRequest);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick:No Internet");
                }


//                index1 = 1;
//                launchHomeScreen1();
//                finishAffinity();
//                indexj = 1;
                // checking for last page
                // if last page home screen will be launched
              /*
                if (checkbox_welcome.isChecked()){
                    index1=1;
                    launchHomeScreen1();
                }else {
                    launchHomeScreen1();
                }*/
            }
        });
        bypass_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_welcome.isChecked()) {
                    prefManager.setFirstTimeLaunch(false);
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finishAffinity();
                    indexj = 1;
                }
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void addBottomDots(int currentPage) {


        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        index = 1;
        if (indexj != 1) {
            //   prefManager.setFirstTimeLaunch(false);
            if (checkbox_welcome.isChecked()) {
                if (checkbox_welcome.isChecked()) {
                    prefManager.setFirstTimeLaunch(false);
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                } else {
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                }
            } else {
                if (checkbox_welcome.isChecked()) {
                    prefManager.setFirstTimeLaunch(false);
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                } else {
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                }
            }
        }

    }

    private void launchHomeScreen1() {
        if (indexj != 1) {
            if (index1 == 1) {
                if (checkbox_welcome.isChecked()) {
                    prefManager.setFirstTimeLaunch(false);
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                } else {
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                }

            } else {
                if (checkbox_welcome.isChecked()) {
                    prefManager.setFirstTimeLaunch(false);
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                } else {
                    ChangeActivity.changeActivity(WelcomeActivity.this, BrowseActivity.class);
                    finish();
                }
            }
        }


    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'

            if (position == layouts.length - 1) {

                btnNext.setText("Got it");
            } else {

                btnNext.setText("Skip");
                // still pages are left

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}