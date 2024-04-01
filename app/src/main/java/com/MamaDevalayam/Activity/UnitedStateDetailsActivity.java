package com.MamaDevalayam.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.CommonActivity.CommonMenuActivity;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.MamaDevalayam.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnitedStateDetailsActivity extends AppCompatActivity {
    private final String TAG = "TypesActivity";
    TextView name_tv, location_tv, like_count_tv, unlike_count_tv, follow_count_tv, unfollow_count_tv, description_tv, TvAbout, TvPrograms, TvPuja, TvSponsorship;
    ImageView back_icon_login_img, logo_img, IVLocationMapAndNearbyPlaces, IVTempleMahimaItihasa, IvTempleArchitecture, IvContact;
    static HashMap<String, String> userDetails;
    LinearLayout like_linear_layout, unlike_linear_layout, follow_linear_layout, unfollow_linear_layout;
    SessionManager session;
    String id;
    Integer followed;
    Integer liked;
    String searchname;
    ApiInterface apiService;
    LinearLayout donate_linear_layout;
    Dialog d;
    Toolbar toolbar;
    IDonateSharedPreference iDonateSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_united_state_details);

//        setView(R.layout.activity_united_state_details, TAG);

        toolbar = findViewById(R.id.commonMenuActivityToolbar);
//        toolbar.setVisibility(View.GONE);

        getWindow().setBackgroundDrawableResource(R.drawable.dashbord_background);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        listener();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        iDonateSharedPreference = new IDonateSharedPreference();
        iDonateSharedPreference.setdailoguepage(getApplicationContext(), "0");
        session = new SessionManager(UnitedStateDetailsActivity.this);
        userDetails = session.getUserDetails();
        Bundle bundle = getIntent().getExtras();
        Log.e("bundle", "" + bundle);
        String name = bundle.getString("name");
        String logo = bundle.getString("logo");
//        String street = bundle.getString("street");
        String city = bundle.getString("city");
        String state = bundle.getString("state");
        /*String likecount = bundle.getString("likecount")+"";
        String followedcount = bundle.getString("followed")+"";*/
        Integer likecount = bundle.getInt("likecount");
        Integer followedcount = bundle.getInt("followed");
        String description = bundle.getString("description");

        liked=likecount;
        followed=followedcount;

        id = bundle.getString("id");
//        liked = bundle.getString("liked");
//        followed = bundle.getString("followed");

        searchname = bundle.getString("searchname");
        back_icon_login_img = findViewById(R.id.back_icon_login_img);
        name_tv = findViewById(R.id.name_tv);
        logo_img = findViewById(R.id.logo_img);
        IVLocationMapAndNearbyPlaces = findViewById(R.id.IVLocationMapAndNearbyPlaces);
        IVTempleMahimaItihasa = findViewById(R.id.IVTempleMahimaItihasa);
        IvTempleArchitecture = findViewById(R.id.IvTempleArchitecture);
        IvContact = findViewById(R.id.IvContact);
        location_tv = findViewById(R.id.location_tv);
        like_count_tv = findViewById(R.id.like_count_tv);
        unlike_count_tv = findViewById(R.id.unlike_count_tv);
        follow_count_tv = findViewById(R.id.follow_count_tv);
        unfollow_count_tv = findViewById(R.id.unfollow_count_tv);
        description_tv = findViewById(R.id.description_tv);
        TvAbout = findViewById(R.id.TvAbout);
        TvPrograms = findViewById(R.id.TvPrograms);
        TvPuja = findViewById(R.id.TvPuja);
        TvSponsorship = findViewById(R.id.TvSponsorship);
        like_linear_layout = findViewById(R.id.like_linear_layout);
        unlike_linear_layout = findViewById(R.id.unlike_linear_layout);
        follow_linear_layout = findViewById(R.id.follow_linear_layout);
        unfollow_linear_layout = findViewById(R.id.unfollow_linear_layout);
        donate_linear_layout = findViewById(R.id.donate_linear_layout);
        name_tv.setText(name);

        if (city.equalsIgnoreCase("")){
            location_tv.setText(state);
        } else if ( state.equalsIgnoreCase("")) {
            location_tv.setText(city);
        }else {
            location_tv.setText(city + ", " + state);
        }
/*        location_tv.setText(city);
        location_tv.setText(state);*/

        like_count_tv.setText(likecount + " " + "Likes");
        unlike_count_tv.setText(likecount + " " + "Likes");
        if (!description.equalsIgnoreCase("null")) {
            description_tv.setText(description);
        }

//        if (liked.equalsIgnoreCase("1")) {
        if (liked == 0) {
            /*unlike_linear_layout.setVisibility(View.VISIBLE);
            like_linear_layout.setVisibility(View.GONE);*/

            unlike_linear_layout.setVisibility(View.GONE);
            like_linear_layout.setVisibility(View.VISIBLE);
        } else {
            /*unlike_linear_layout.setVisibility(View.GONE);
            like_linear_layout.setVisibility(View.VISIBLE);*/

            unlike_linear_layout.setVisibility(View.VISIBLE);
            like_linear_layout.setVisibility(View.GONE);
        }

//        if (followed.equalsIgnoreCase("1")) {
        if (followed == 0) {
            /*follow_linear_layout.setVisibility(View.VISIBLE);
            unfollow_linear_layout.setVisibility(View.GONE);
            follow_count_tv.setText("Following");
            unfollow_count_tv.setText("Following");*/

            follow_linear_layout.setVisibility(View.GONE);
            unfollow_linear_layout.setVisibility(View.VISIBLE);
            follow_count_tv.setText("Follow");
            unfollow_count_tv.setText("Follow");
        } else {
            /*follow_linear_layout.setVisibility(View.GONE);
            unfollow_linear_layout.setVisibility(View.VISIBLE);
            follow_count_tv.setText("Follow");
            unfollow_count_tv.setText("Follow");*/

            follow_linear_layout.setVisibility(View.VISIBLE);
            unfollow_linear_layout.setVisibility(View.GONE);
            follow_count_tv.setText("Following");
            unfollow_count_tv.setText("Following");
        }

        try {
            URL url = new URL(logo);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            logo_img.setImageBitmap(bmp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDeviceUniqueID(Activity activity) {
        @SuppressLint("HardwareIds") String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    @SuppressLint("SetTextI18n")
    private void listener() {
        back_icon_login_img.setOnClickListener(v -> onBackPressed());
        findViewById(R.id.linear_browse).setOnClickListener(v -> {

            ChangeActivity.changeActivity(this, BrowseActivity.class);
            ((TextView) findViewById(R.id.browse_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
            // browse_img.setImageTintMode();
            ((ImageView) findViewById(R.id.browse_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
            finish();
        });
        findViewById(R.id.linear_myspace).setOnClickListener(v -> {

            if (session.isLoggedIn()) {
                ChangeActivity.changeActivity(this, MyspaceActivity.class);
                ((TextView) findViewById(R.id.my_space_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
                ((ImageView) findViewById(R.id.my_space_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                finish();
            } else {
                LoginDialog();
                // ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                //finish();
            }
        });

        like_linear_layout.setOnClickListener(v -> {
            //todo login
            if (session.isLoggedIn()) {
                String like = "1";
                String user_id = userDetails.get(SessionManager.KEY_UID);
                Log.e("blike", "" + like);
                String token_id = userDetails.get(SessionManager.KEY_token);
                likeAPI(id, like, user_id, token_id);
            } else {
                LoginDialog();
            }
        });

        unlike_linear_layout.setOnClickListener(v -> {
            //todo login
            if (session.isLoggedIn()) {
                String like = "0";
                String user_id = userDetails.get(SessionManager.KEY_UID);
                Log.e("blike", "" + like);
                String token_id = userDetails.get(SessionManager.KEY_token);
                likeAPI(id, like, user_id, token_id);
            } else {
                LoginDialog();
            }
        });

        follow_linear_layout.setOnClickListener(v -> {
//todo login
            if (session.isLoggedIn()) {
                String like = "0";
                String user_id = userDetails.get(SessionManager.KEY_UID);
                Log.e("blike", "" + like);
                String token_id = userDetails.get(SessionManager.KEY_token);
                followAPI(id, like, user_id, token_id);
                follow_linear_layout.setVisibility(View.GONE);
                unfollow_linear_layout.setVisibility(View.VISIBLE);
                follow_count_tv.setText("Follow");
                unfollow_count_tv.setText("Follow");
            } else {
                LoginDialog();
            }
        });

        unfollow_linear_layout.setOnClickListener(v -> {
//todo login
            if (session.isLoggedIn()) {

                String like = "1";
                String user_id = userDetails.get(SessionManager.KEY_UID);
                Log.e("blike", "" + like);
                String token_id = userDetails.get(SessionManager.KEY_token);
                followAPI(id, like, user_id, token_id);
                follow_linear_layout.setVisibility(View.VISIBLE);
                unfollow_linear_layout.setVisibility(View.GONE);
                follow_count_tv.setText("Following");
                unfollow_count_tv.setText("Following");
            } else {
                LoginDialog();
            }
        });

        donate_linear_layout.setOnClickListener(v -> {
            if (session.isLoggedIn()) {
//                paymentdailogue();
                Intent intent = new Intent(UnitedStateDetailsActivity.this, MakeTypeScreenActivity.class);
                intent.putExtra("tvTopType", "HUNDI");
                intent.putExtra("tvWebsiteId", "www.Vishnavodevi.org");
                intent.putExtra("tvEmailId", "helpdesk@Vishnavodevi.org");
                intent.putExtra("tvContactId", "+91-8772277777");
                intent.putExtra("isHome", true);
                startActivity(intent);
            } else {
                LoginDialog();
            }
        });

        TvPrograms.setOnClickListener(view -> {
            Intent intent = new Intent(UnitedStateDetailsActivity.this, MakeTypeScreenActivity.class);
            intent.putExtra("tvTopType", "PROGRAMS");
            intent.putExtra("tvWebsiteId", "www.svst.org");
            intent.putExtra("tvEmailId", "helpdesk@svst.org");
            intent.putExtra("tvContactId", "+91-8772266666");
            intent.putExtra("isHome", false);
            startActivity(intent);
        });

        TvPuja.setOnClickListener(view -> {
            Intent intent = new Intent(UnitedStateDetailsActivity.this, MakeTypeScreenActivity.class);
            intent.putExtra("tvTopType", "PUJA");
            intent.putExtra("tvWebsiteId", "www.svst.org");
            intent.putExtra("tvEmailId", "helpdesk@svst.org");
            intent.putExtra("tvContactId", "+91-8772266666");
            intent.putExtra("isHome", false);
            startActivity(intent);
        });

        TvSponsorship.setOnClickListener(view -> {
            Intent intent = new Intent(UnitedStateDetailsActivity.this, MakeTypeScreenActivity.class);
            intent.putExtra("tvTopType", "SPONSORSHIP");
            intent.putExtra("tvWebsiteId", "www.svst.org");
            intent.putExtra("tvEmailId", "helpdesk@svst.org");
            intent.putExtra("tvContactId", "+91-8772266666");
            intent.putExtra("isHome", false);
            startActivity(intent);
        });

        IVLocationMapAndNearbyPlaces.setOnClickListener(view -> {
//            Intent intent = new Intent(UnitedStateDetailsActivity.this, MapsActivity.class);
//            intent.putExtra("Address", name_tv.getText().toString() + " " + location_tv.getText().toString());
//            intent.putExtra("latitude", getIntent().getDoubleExtra("latitude",0));
//            intent.putExtra("longitude", getIntent().getDoubleExtra("longitude",0));
//            startActivity(intent);
        });

        IVTempleMahimaItihasa.setOnClickListener(view -> {
            Intent intent = new Intent(UnitedStateDetailsActivity.this, MahimaIthihasaActivity.class);
            startActivity(intent);
        });

        IvTempleArchitecture.setOnClickListener(view -> {
            Intent intent = new Intent(UnitedStateDetailsActivity.this, TempleArchitectureActivity.class);
            startActivity(intent);
        });

        IvContact.setOnClickListener(view -> {
            Intent intent = new Intent(UnitedStateDetailsActivity.this, ContactActivity.class);
            startActivity(intent);
        });

    }

    private void LoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UnitedStateDetailsActivity.this);
        builder.setTitle("");
        builder.setMessage("For Advance Features Please Log-in/Register");
        builder.setPositiveButton("OK",
                (dialog, which) -> ChangeActivity.changeActivity(UnitedStateDetailsActivity.this, LoginActivity.class));
        builder.setNegativeButton(android.R.string.no, (dialog, which) -> {

        });
        builder.setCancelable(false);
        builder.show();
    }

    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility", "SetTextI18n", "DefaultLocale"})
    private void paymentdailogue() {

        d = new BottomSheetDialog(UnitedStateDetailsActivity.this, R.style.payment_dailog);
        d.setContentView(R.layout.payment_alert_dailog);
        LinearLayout payment_dailog_linear = d.findViewById(R.id.payment_dailog_linear);
        final EditText payment_et = d.findViewById(R.id.payment_et);
        TextView cancel_tv = d.findViewById(R.id.cancel_tv);
        Button payment_continue_btn = d.findViewById(R.id.payment_continue_btn);
        TextView textview_percentage = d.findViewById(R.id.textview_percentage);
        String code = "Merchant charges and processing fee will be added to whatever donation amount is entered. <img src ='addbutton.png'>";

        Spanned spanned = Html.fromHtml(code, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String arg0) {
                int id = 0;

                if (arg0.equals("addbutton.png")) {
                    id = R.drawable.ic_info;
                }
                LevelListDrawable d = new LevelListDrawable();
                @SuppressLint("UseCompatLoadingForDrawables") Drawable empty = getResources().getDrawable(id);
                d.addLevel(0, 0, empty);
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                return d;
            }
        }, null);
        textview_percentage.setText(spanned);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(R.color.trans_black));
        payment_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().startsWith(".")) {
                    payment_et.setText("");
                    Toast.makeText(getApplicationContext(), "Dot Not allowed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        payment_dailog_linear.setOnTouchListener((v, event) -> {
            InputMethodManager imm = (InputMethodManager) UnitedStateDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(payment_et.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);

            return false;
        });
        textview_percentage.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UnitedStateDetailsActivity.this, R.style.CustomAlertDialog);
            LayoutInflater inflater = getLayoutInflater();

            View dialogView = inflater.inflate(R.layout.percentage_detail_layout, null);
            dialogBuilder.setView(dialogView);
            final AlertDialog alertDialog = dialogBuilder.create();
            String payment_amt = payment_et.getText().toString().trim();
            TextView donationamt_tv = dialogView.findViewById(R.id.donationamt_tv);
            ImageView close_img = dialogView.findViewById(R.id.close_img);
            if (!payment_amt.isEmpty()) {
                donationamt_tv.setText("$ " + payment_amt);
            } else {
                donationamt_tv.setText("$ " + "10");
                payment_amt = "10";
            }
            Double amount = Double.valueOf(payment_amt);
            double processing_fee = ((amount / 100.0f) * 1);
            double total_amt = processing_fee + amount;
            double percentage = ((total_amt / 100.0f) * 2.9) + 0.30;

            Double payment_amt_total = amount + percentage + processing_fee;
            TextView merchantcharges_tv = dialogView.findViewById(R.id.merchantcharges_tv);
            merchantcharges_tv.setText("$ " + String.format(" %.2f", percentage));
            TextView processing_tv = dialogView.findViewById(R.id.processing_tv);
            processing_tv.setText("$ " + processing_fee);
            TextView totalamt_tv = dialogView.findViewById(R.id.totalamt_tv);
            totalamt_tv.setText("$ " + String.format("%.2f", payment_amt_total));
            close_img.setOnClickListener(v1 -> alertDialog.dismiss());

            alertDialog.show();
        });
        payment_continue_btn.setOnClickListener(v -> {
            String payment = payment_et.getText().toString().trim();
            if (!payment.isEmpty()) {
                float fpay = Float.parseFloat(payment);
                int pay = (int) fpay;
                if (pay >= 1) {

                    ProgressDialog progressDialog = ProgressDialog.show(UnitedStateDetailsActivity.this, "", "Please wait.", true);
                    progressDialog.show();
                    apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<String> call = apiService.getbraintree();
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                try {
                                    Log.e("Response_payment1", response.body().toString());
                                    Intent intent = new Intent(UnitedStateDetailsActivity.this, SelectPaymentActivity.class);
                                    Bundle bundle = new Bundle();

                                    iDonateSharedPreference.setdailogueamt(UnitedStateDetailsActivity.this, payment);
                                    bundle.putString("payment_amt", payment);
                                    bundle.putString("charity_name", name_tv.getText().toString());
                                    bundle.putString("charity_id", id);
                                    bundle.putString("cToken", response.body());
                                    iDonateSharedPreference.setcharity_id(getApplicationContext(), name_tv.getText().toString());
                                    iDonateSharedPreference.setcharity_name(getApplicationContext(), id);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    d.dismiss();
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            progressDialog.dismiss();
                            Log.e("Response_error", t.toString());
                        }
                    });
                } else {
                    Toast.makeText(UnitedStateDetailsActivity.this, "Please enter the amount greater than Zero", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UnitedStateDetailsActivity.this, "Please enter the amount", Toast.LENGTH_SHORT).show();
            }
        });
        cancel_tv.setOnClickListener(v -> {
            iDonateSharedPreference.setdailoguepage(getApplicationContext(), "0");
            d.dismiss();
        });
        d.setCancelable(true);
        d.show();
    }

    private void followAPI(String id, final String like, String user_id, String token_id) {

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("token", token_id);
        jsonObject1.addProperty("charity_id", id);
        jsonObject1.addProperty("status", like);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(UnitedStateDetailsActivity.this));
        Log.e("jsonObject112", "" + jsonObject1);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.charity_following(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {

                Log.e("likeresponse", "" + response.body());
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    Log.e("jsonObject", "" + jsonObject);

                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {

                        if (searchname.equalsIgnoreCase("namesearch")) {
                            NameSearchActivity.like();
                        } else if (searchname.equalsIgnoreCase("locationsearch")) {
                            UnitedStateActivity.like();
                        } else if (searchname.equalsIgnoreCase("international")) {
                            InternationalCharitiesActivity.like();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("unitedstate", t.toString());
            }
        });
    }

    private void likeAPI(String id, final String like, String user_id, String token_id) {

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("token", token_id);
        jsonObject1.addProperty("charity_id", id);
        jsonObject1.addProperty("status", like);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(UnitedStateDetailsActivity.this));
        Log.e("jsonObject112", "" + jsonObject1);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.charity_like_dislike(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {

                Log.e("likeresponse", "" + response.body());
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    Log.e("jsonObject", "" + jsonObject);

                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        if (searchname.equalsIgnoreCase("namesearch")) {
                            NameSearchActivity.like();
                        } else if (searchname.equalsIgnoreCase("locationsearch")) {
                            UnitedStateActivity.like();
                        } else if (searchname.equalsIgnoreCase("international")) {
                            InternationalCharitiesActivity.like();

                        }

                        String data = jsonObject.getString("data");
                        JSONObject jsonObject2 = new JSONObject(data);
                        Log.e("like_count", "" + jsonObject2.getString("like_count"));
                        like_count_tv.setText(jsonObject2.getString("like_count") + " " + "Likes");
                        unlike_count_tv.setText(jsonObject2.getString("like_count") + " " + "Likes");
                        if (like.equalsIgnoreCase("1")) {
                            Log.e("like", "" + like);
                            unlike_linear_layout.setVisibility(View.VISIBLE);
                            like_linear_layout.setVisibility(View.GONE);
                        } else {
                            Log.e("dislike", "" + like);
                            unlike_linear_layout.setVisibility(View.GONE);
                            like_linear_layout.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("unitedstate", t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String page = iDonateSharedPreference.getdailoguepage(getApplicationContext());
        if (page.equalsIgnoreCase("1")) {
            Log.e("pageback", "page");
            paymentdailogue();
        }
        session = new SessionManager(UnitedStateDetailsActivity.this);
        userDetails = session.getUserDetails();
        Log.e("userdetails01", "" + userDetails);
    }

    @Override
    protected void onPause() {
        super.onPause();
        session = new SessionManager(UnitedStateDetailsActivity.this);
        userDetails = session.getUserDetails();
        Log.e("userdetails12", "" + userDetails);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
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
}