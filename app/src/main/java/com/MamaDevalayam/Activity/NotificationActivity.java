package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.CommonActivity.CommonMenuActivity;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Adapter.NotificationAdapterList;
import com.MamaDevalayam.Model.Notification;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends CommonBackActivity {

    private String TAG = "TypesActivity";
    Toolbar toolbar;
    RecyclerView notification_recyclerview;
    List<Map<String, String>> listOfdate = new ArrayList<Map<String, String>>();
    private RecyclerView.LayoutManager layoutManager;
    NotificationAdapterList notificationAdapterList;
    static SessionManager session;
    static HashMap<String, String> userDetails;
    static ApiInterface apiService;
    static ShimmerFrameLayout shimmer_view_container;
    LinearLayout no_data_linear;
    static ArrayList<Notification> notificationlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_notification,TAG);
        toolbar = findViewById(R.id.commonMenuActivityToolbar);
        init();
        listener();
    }

    private void init() {
        session = new SessionManager(getApplicationContext());
        shimmer_view_container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        no_data_linear = (LinearLayout) findViewById(R.id.no_data_linear);
        shimmer_view_container.setVisibility(View.VISIBLE);
        shimmer_view_container.startShimmer();
        notification_recyclerview=(RecyclerView)findViewById(R.id.notification_recyclerview);

        NotificationAPI();
//todo login


        if (session.isLoggedIn()) {

            String like = "1";
            String user_id = userDetails.get(SessionManager.KEY_UID);
            Log.e("blike", "" + like);
            String token_id = userDetails.get(SessionManager.KEY_token);
            transaction_listAPI(user_id);
        } else {
//            ChangeActivity.changeActivity(mContext, LoginActivity.class);
            //  mContext.finish();
        }


        layoutManager = new LinearLayoutManager(this);
        notification_recyclerview.setLayoutManager(layoutManager);
        notification_recyclerview.setItemAnimator(new DefaultItemAnimator());

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

                Log.e("likeresponse", "" + response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.e("unitedstate", t.toString());
            }
        });

    }



    public static String getDeviceUniqueID(Activity activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }
    private void NotificationAPI() {
        session=new SessionManager(getApplicationContext());
        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id = "";
        //todo login
        if (session.isLoggedIn()) {
            user_id = userDetails.get(SessionManager.KEY_UID);
        }
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(NotificationActivity.this));
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiService.notification(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.GONE);
                notification_recyclerview.setVisibility(View.VISIBLE);
                notificationlist.clear();
                try {
                    Log.e(TAG, response.body().toString());
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String message = jsonObject.getString("message");
                    if (message.equalsIgnoreCase("No data")){
                        no_data_linear.setVisibility(View.VISIBLE);
                        notification_recyclerview.setVisibility(View.GONE);
                    }else {
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            String data = jsonObject.getString("data");

                            JSONArray jsonArray = new JSONArray(data);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Notification notification=new Notification();
                                JSONObject object = jsonArray.getJSONObject(i);
                                notification.setUser_id(object.getString("user_id"));
                                notification.setTitle(object.getString("title"));
                                notification.setMessage(object.getString("message"));
                                notification.setDate(object.getString("date"));
                                notificationlist.add(notification);
                            }
                            if (notificationlist.size()!=0){
                                notificationAdapterList = new NotificationAdapterList(NotificationActivity.this,notificationlist);
                                notification_recyclerview.setAdapter(notificationAdapterList);
                            }else {
                                no_data_linear.setVisibility(View.VISIBLE);
                                notification_recyclerview.setVisibility(View.GONE);
                            }
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, t.toString());
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
                no_data_linear.setVisibility(View.VISIBLE);
                notification_recyclerview.setVisibility(View.GONE);
            }
        });

    }

    private void listener() {


    }


    private void data() {
        Map<String,String> map = new HashMap<String, String>();
        for (int i=0;i<10;i++){
           /* map.put("date","my donation");
            map.put("date","my donation");
            map.put("date","my donation");*/
            map.put("date","my donationdonationdonation");
            map.put("date","my donation");
            map.put("Follower","Follower");
            map.put("date","my donation");
            listOfdate.add(map);
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
