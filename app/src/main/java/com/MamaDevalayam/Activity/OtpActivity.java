package com.MamaDevalayam.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.MamaDevalayam.Validation.Validation;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.MamaDevalayam.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    private static final String TAG = OtpActivity.class.getSimpleName();
    TextView otp_received_tv, change_mail_btn_tv, resend_btn_tv, title_tool_tv;
    ImageView back_icon_otp_img;
    EditText new_password, confirm_password;
    String user_id;
    String email;
    LinearLayout updatepassword_linear, otp_linear;
    Button update_submit_btn;
    TextInputLayout new_password_layout_input, confirm_password_layout_input;
    ApiInterface apiService;
    private EditText mPinFirstDigitEditText, mPinSecondDigitEditText, mPinThirdDigitEditText, mPinForthDigitEditText, mPinHiddenEditText;
    int index = 0;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MainLayout(this, null));
        init();
        setPINListeners();
        listener();
    }


    private void init() {
        otp_received_tv = (TextView) findViewById(R.id.otp_received_tv);
        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinHiddenEditText = (EditText) findViewById(R.id.pin_hidden_edittext);
        back_icon_otp_img = (ImageView) findViewById(R.id.back_icon_otp_img);
        change_mail_btn_tv = (TextView) findViewById(R.id.change_mail_btn_tv);
        resend_btn_tv = (TextView) findViewById(R.id.resend_btn_tv);
        title_tool_tv = (TextView) findViewById(R.id.title_tool_tv);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        new_password_layout_input = (TextInputLayout) findViewById(R.id.new_password_layout_input);
        confirm_password_layout_input = (TextInputLayout) findViewById(R.id.confirm_password_layout_input);
        update_submit_btn = (Button) findViewById(R.id.update_submit_btn);
        updatepassword_linear = (LinearLayout) findViewById(R.id.updatepassword_linear);
        otp_linear = (LinearLayout) findViewById(R.id.otp_linear);
        user_id = getIntent().getStringExtra("data");
        email = getIntent().getStringExtra("data1");
        otp_received_tv.setText(Html.fromHtml("Please enter the OTP you received in \n" + "<b>" + email + "</b>"));
        builder = new AlertDialog.Builder(this);

    }

    private void listener() {

        new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().startsWith(" ")) {
                    new_password.setText("");
                    Toast.makeText(getApplicationContext(), "Space Not allowed", Toast.LENGTH_LONG).show();
                    new_password_layout_input.setError("Required password");
                } else {
                    if (new_password.getText().toString().trim().length() <= 0) {
                        new_password_layout_input.setError("Required password");
                    } else {
                        new_password_layout_input.setError("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwordvalidation = s.toString();

                if (passwordvalidation.length() >= 8 && Validation.CheckPasswordPattern(passwordvalidation)) {
                    new_password_layout_input.setError("");
                } else {
                    new_password_layout_input.setError("Password is invalid");
                }
            }
        });

        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().startsWith(" ")) {
                    confirm_password.setText("");
                    Toast.makeText(getApplicationContext(), "Space Not allowed", Toast.LENGTH_LONG).show();
                    confirm_password_layout_input.setError("Required password");

                } else {
                    confirm_password_layout_input.setError("");
                    if (new_password.getText().toString().trim().length() <= 0) {
                        new_password_layout_input.setError("Required password");
                    } else {
                        if (new_password.getText().toString().trim().matches(Validation.PASSWORD_PATTERN)) {
                            new_password_layout_input.setError("");
                        } else {
                            new_password_layout_input.setError("Required password");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwordvalidation = s.toString();
                if (passwordvalidation.length() >= 8 && Validation.CheckPasswordPattern(passwordvalidation)) {
                    confirm_password_layout_input.setError("");
                    if (new_password.getText().toString().trim().equals(confirm_password.getText().toString().trim())) {
                        confirm_password_layout_input.setError("");
                    } else {
                        confirm_password_layout_input.setError("Password doesn't match");
                    }
                } else {
                    confirm_password_layout_input.setError("Password doesn't match");
                }
            }
        });

        update_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Validation.CheckPasswordPattern(new_password.getText().toString())) {
                    new_password_layout_input.setError("Password is invalid");
                    return;
                }
                if (!Validation.CheckPasswordPattern(confirm_password.getText().toString()) || !new_password.getText().toString().trim().equals(confirm_password.getText().toString().trim())) {
                    confirm_password_layout_input.setError("Password doesn't match");
                    return;
                }

                UpadtePasswordAPI();
            }
        });
    }

    private void UpadtePasswordAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String password = confirm_password.getText().toString().trim();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("password", password);

        Log.e("jsonObject1", "" + jsonObject1);
        /*   ApiInterface jsonPostService = ApiClient.createService(ApiInterface.class, "http://project975.website/i2-donate/api/");*/
        final String image_url = "";
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.update_password(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        if (status.equalsIgnoreCase("1")) {
                            builder.setMessage(R.string.change_password);

                            //Setting message manually and performing action on button click
                            builder.setMessage(R.string.change_password_msg)
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);

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
                        } else {
                            ConstantFunctions.showSnackbar(mPinFirstDigitEditText, message, OtpActivity.this);
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
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        if (id == R.id.pin_first_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }
        } else if (id == R.id.pin_second_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }
        } else if (id == R.id.pin_third_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }
        } else if (id == R.id.pin_forth_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            if (id == R.id.pin_hidden_edittext) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (mPinHiddenEditText.getText().length() == 4)
                        mPinForthDigitEditText.setText("");
                    else if (mPinHiddenEditText.getText().length() == 3)
                        mPinThirdDigitEditText.setText("");
                    else if (mPinHiddenEditText.getText().length() == 2)
                        mPinSecondDigitEditText.setText("");
                    else if (mPinHiddenEditText.getText().length() == 1)
                        mPinFirstDigitEditText.setText("");

                    if (mPinHiddenEditText.length() > 0)
                        mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));

                    return true;
                }
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setDefaultPinBackground(mPinFirstDigitEditText);
        setDefaultPinBackground(mPinSecondDigitEditText);
        setDefaultPinBackground(mPinThirdDigitEditText);
        setDefaultPinBackground(mPinForthDigitEditText);

        if (s.length() == 0) {
            setFocusedPinBackground(mPinFirstDigitEditText);
            mPinFirstDigitEditText.setText("");
        } else if (s.length() == 1) {
            setFocusedPinBackground(mPinSecondDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
        } else if (s.length() == 2) {
            setFocusedPinBackground(mPinThirdDigitEditText);
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
        } else if (s.length() == 3) {
            setFocusedPinBackground(mPinForthDigitEditText);
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText("");
        } else if (s.length() == 4) {
            setDefaultPinBackground(mPinForthDigitEditText);
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            hideSoftKeyboard(mPinForthDigitEditText);
            verifypasswordAPI();
        }
    }

    private void sample() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Log.e("sucess", "demosuccess");
    }

    private void verifypasswordAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String otp = mPinFirstDigitEditText.getText().toString().trim() + mPinSecondDigitEditText.getText().toString().trim() + mPinThirdDigitEditText.getText().toString().trim() + mPinForthDigitEditText.getText().toString().trim();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("otp", otp);
        Log.e("jsonObject1", "" + jsonObject1);
        /*   ApiInterface jsonPostService = ApiClient.createService(ApiInterface.class, "http://project975.website/i2-donate/api/");*/
        final String image_url = "";
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.verify_otp(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        String data = jsonObject.getString("data");
                        if (status.equalsIgnoreCase("1")) {
                            JSONObject jsonObject2 = new JSONObject(data);
                            user_id = jsonObject2.getString("user_id");
                            title_tool_tv.setText("Update password");
                            otp_linear.setVisibility(View.GONE);
                            updatepassword_linear.setVisibility(View.VISIBLE);
                            back_icon_otp_img.setVisibility(View.VISIBLE);
                            index = 1;
                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            ConstantFunctions.showSnackbar(mPinFirstDigitEditText, message, OtpActivity.this);
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

    private void setDefaultPinBackground(EditText editText) {

        //setViewBackground(editText, getResources().getDrawable(R.drawable.textfield_default_holo_light));
    }

    /**
     * Sets focus on a specific EditText field.
     *
     * @param editText EditText to set focus on
     */
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /**
     * Sets focused PIN field background.
     *
     * @param editText edit text to change
     */
    private void setFocusedPinBackground(EditText editText) {
        editText.setCursorVisible(true);
        //setViewBackground(editText, getResources().getDrawable(R.drawable.login_botton_border));
        //  setViewBackground(editText, getResources().getDrawable(R.color.colorAccent));
    }

    /**
     * Sets listeners for EditText fields.
     */
    private void setPINListeners() {
        mPinHiddenEditText.addTextChangedListener(this);

        mPinFirstDigitEditText.setOnFocusChangeListener(this);
        mPinSecondDigitEditText.setOnFocusChangeListener(this);
        mPinThirdDigitEditText.setOnFocusChangeListener(this);
        mPinForthDigitEditText.setOnFocusChangeListener(this);

        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinHiddenEditText.setOnKeyListener(this);
        back_icon_otp_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dailogue();

            }
        });
        change_mail_btn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resend_btn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendAPI();
            }
        });

    }

    private void dailogue() {
        builder.setMessage(R.string.dialog_message);

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.dialog_message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

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

    private void resendAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("email", email);
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
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        String data = jsonObject.getString("data");

                        if (status.equalsIgnoreCase("1")) {
                            JSONObject jsonObject2 = new JSONObject(data);
                            Toast.makeText(OtpActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                           /* builder.setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();

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
                        } else {
                            ConstantFunctions.showSnackbar(mPinFirstDigitEditText, message, OtpActivity.this);
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

    /**
     * Sets background of the view.
     * This method varies in implementation depending on Android SDK version.
     *
     * @param view       View to which set background
     * @param background Background to set to view
     */
    @SuppressWarnings("deprecation")
    public void setViewBackground(View view, Drawable background) {
        if (view == null || background == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    /**
     * Custom LinearLayout with overridden onMeasure() method
     * for handling software keyboard show and hide events.
     */
    public class MainLayout extends LinearLayout {

        public MainLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_otp, this);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
            final int actualHeight = getHeight();

            Log.e("TAG", "proposed: " + proposedHeight + ", actual: " + actualHeight);

            if (actualHeight >= proposedHeight) {
                // Keyboard is shown
                if (mPinHiddenEditText.length() == 0)
                    setFocusedPinBackground(mPinFirstDigitEditText);
                else
                    setDefaultPinBackground(mPinFirstDigitEditText);
            }

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (index == 1) {
            updatekey(event);
        }

        boolean ret = super.dispatchTouchEvent(event);
        return ret;
    }

    private void updatekey(MotionEvent event) {
        View view = getCurrentFocus();


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

    }

   /* @Override
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
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dailogue();
    }

}
