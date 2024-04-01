package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class SettingActivity extends CommonBackActivity {
    private String TAG = "SettingActivity";
    Toolbar toolbar;
    IDonateSharedPreference iDonateSharedPreference;
    RelativeLayout relative_layout_changepassword;
    String logintype="";
    SwitchCompat notification_status;
    SwitchCompat sound_on_off;
    ApiInterface apiService;
    static HashMap<String, String> userDetails;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_setting,TAG);
        init();
        listener();
        setTitle("");
        toolbar = findViewById(R.id.commonMenuActivityToolbar);
    }



    private void init() {
        sessionManager = new SessionManager(getApplicationContext());
        iDonateSharedPreference = new IDonateSharedPreference();
        relative_layout_changepassword=(RelativeLayout)findViewById(R.id.relative_layout_changepassword);
        notification_status=(SwitchCompat)findViewById(R.id.notification_status);
        sound_on_off=(SwitchCompat)findViewById(R.id.sound_on_off);
        logintype=iDonateSharedPreference.getlogintype(getApplicationContext());
        if (iDonateSharedPreference.getNotificationstatus(getApplicationContext())==1){
            notification_status.setChecked(true);
        }else {
            notification_status.setChecked(false);
        }
        if (iDonateSharedPreference.getNotificationsoundstatus(getApplicationContext())==1){
            sound_on_off.setChecked(true);
            ringtone();
        }else {
            ringtonenotification();
            sound_on_off.setChecked(false);
        }
        if (logintype.equalsIgnoreCase("registerlogin")){
            Log.e("registerlogin","registerlogin");
            relative_layout_changepassword.setVisibility(View.VISIBLE);
        }else if (logintype.equalsIgnoreCase("sociallogin")){
            Log.e("sociallogin","sociallogin");
            relative_layout_changepassword.setVisibility(View.VISIBLE);
        }else {
            Log.e("non","non");
            relative_layout_changepassword.setVisibility(View.VISIBLE);
        }

    }

    private void listener() {
        relative_layout_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity.changeActivity(SettingActivity.this, ChangePasswordActivity.class);

            }
        });
        notification_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notification_status.isChecked()){
                    int enable_notification=1;
                    iDonateSharedPreference.setNotificationstatus(getApplicationContext(),enable_notification);
                    sentdevicetoken(enable_notification);
                    Log.e("activate","activate");
                }else {
                    int enable_notification=0;
                    iDonateSharedPreference.setNotificationstatus(getApplicationContext(),enable_notification);
                    sentdevicetoken( enable_notification);
                    Log.e("deactivate","deactivate");
                }
            }
        });
        sound_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_on_off.isChecked()){
                    ringtone();
                    iDonateSharedPreference.setNotificationsoundstatus(getApplicationContext(),1);
                }else {
                    iDonateSharedPreference.setNotificationsoundstatus(getApplicationContext(),0);
                    ringtonenotification();
                }
            }
        });
    }
    public void ringtone(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ringtonenotification(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getDeviceUniqueID(Activity activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }
    private void sentdevicetoken(int enable_notification) {
        userDetails = sessionManager.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id="";
        //todo login
        if (sessionManager.isLoggedIn()){
            user_id= userDetails.get(SessionManager.KEY_UID);
        }

        String device_token=iDonateSharedPreference.gettoken(getApplicationContext());
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("device_id", device_token);
        jsonObject1.addProperty("enable_notification", enable_notification);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(SettingActivity.this));
        Log.e("jsonObject1", "" + jsonObject1);
        /*   ApiInterface jsonPostService = ApiClient.createService(ApiInterface.class, "http://project975.website/i2-donate/api/");*/
        final String image_url = "";
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.device_update(jsonObject1);
            call.enqueue(new retrofit2.Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.e(TAG, "" + response.body());
                    try {
                        JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));
                        String status=jsonObject.getString("status");
                        String message=jsonObject.getString("message");
                        String data=jsonObject.getString("data");

                        if (status.equalsIgnoreCase("1")){
                            final JSONObject jsonObject2=new JSONObject(data);



                        }else {
                            // ConstantFunctions.showSnackbar(reg_email_et,message,ForgotActivity.this);
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
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
