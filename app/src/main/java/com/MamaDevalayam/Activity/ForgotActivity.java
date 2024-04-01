package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.MamaDevalayam.Validation.Validation;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends CommonBackActivity {
    private static final String TAG = ForgotActivity.class.getSimpleName();
    Button mail_send_btn;
    TextInputLayout email_input_layout;
    EditText reg_email_et;
    ImageView back_icon_login_img;
    ApiInterface apiService;
    AlertDialog.Builder builder;
    TextView forgot_tv;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_forgot,TAG);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        listener();

    }



    private void init() {
        mail_send_btn=(Button)findViewById(R.id.mail_send_btn);
        email_input_layout=(TextInputLayout)findViewById(R.id.email_input_layout);
        reg_email_et=(EditText)findViewById(R.id.reg_email_et);
//        back_icon_login_img=(ImageView)findViewById(R.id.back_icon_login_img);
        forgot_tv=(TextView)findViewById(R.id.forgot_tv);
        builder = new AlertDialog.Builder(this);
    }
    private void listener() {
        mail_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reg_email_et.getText().toString().trim().isEmpty()){
                    if (reg_email_et.getText().toString().trim().matches(Validation.emailPattern)){
                        forgotpasswordAPI();
                        //ChangeActivity.changeActivityText(ForgotActivity.this, OtpActivity.class,"",reg_email_et.getText().toString().trim(),"forgotPasswd");
                    }else {
                        ConstantFunctions.showSnackbar(reg_email_et,"Enter valid mail id",ForgotActivity.this);
                    }

                }else {
                   // Toast.makeText(ForgotActivity.this, "Enter  your registered email address", Toast.LENGTH_SHORT).show();
                    ConstantFunctions.showSnackbar(reg_email_et,"Enter  your registered email address",ForgotActivity.this);
                }

            }
        });
        reg_email_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if (charSequence.toString().startsWith(" ")) {
                    reg_email_et.setText("");
                    Toast.makeText(getApplicationContext(), "Space Not allowed", Toast.LENGTH_LONG).show();
                    email_input_layout.setError("Required email");
                    //disableButton(...)
                } else {
                    email_input_layout.setError("");

                            if (reg_email_et.getText().toString().trim().matches(Validation.emailPattern)) {
                                email_input_layout.setError("");
                            } else {
                                email_input_layout.setError("Required email");
                            }




                    //Toast.makeText(getApplicationContext(), " allowed", Toast.LENGTH_LONG).show();
                    //enableButton(...)
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        back_icon_login_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               dailogue_forgot();
//            }
//        });

    }

    private void dailogue_forgot() {
        builder.setMessage(R.string.dialog_message);

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.dialog_message)
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
    public static String getDeviceUniqueID(Activity activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }
    private void forgotpasswordAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String email_id=reg_email_et.getText().toString().trim();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("email", email_id);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(ForgotActivity.this));
        Log.e("jsonObject1", "" + jsonObject1);
        /*   ApiInterface jsonPostService = ApiClient.createService(ApiInterface.class, "http://project975.website/i2-donate/api/");*/
        final String image_url = "";
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.forgot_password(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    try {
                        JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));
                        String status=jsonObject.getString("status");
                        String message=jsonObject.getString("message");
                        String data=jsonObject.getString("data");

                        if (status.equalsIgnoreCase("1")){
                            final JSONObject jsonObject2=new JSONObject(data);
                            ChangeActivity.changeActivityText(ForgotActivity.this, OtpActivity.class,jsonObject2.getString("user_id"),reg_email_et.getText().toString().trim(),"forgotPasswd");
                         /*   builder.setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            try {
                                                ChangeActivity.changeActivityText(ForgotActivity.this, OtpActivity.class,jsonObject2.getString("user_id"),reg_email_et.getText().toString().trim(),"forgotPasswd");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    })
                                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();

                                        }
                                    });

                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setCanceledOnTouchOutside(false);
                            alert.show();*/
                            /*ChangeActivity.changeActivityText(ForgotActivity.this, OtpActivity.class,jsonObject2.getString("user_id"),reg_email_et.getText().toString().trim(),"forgotPasswd");
                            Toast.makeText(ForgotActivity.this, message, Toast.LENGTH_SHORT).show();*/
                        }else {
                            ConstantFunctions.showSnackbar(reg_email_et,message,ForgotActivity.this);
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
    @Override
    public void onBackPressed() {
       dailogue_forgot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (i==1){
            forgot_tv.setText("Change Email");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        i=1;

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
