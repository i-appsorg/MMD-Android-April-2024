package com.MamaDevalayam.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.Commonmethod.SearchableSpinner;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.Model.CurrencyBean;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;
import com.MamaDevalayam.databinding.ActivityUpdateBinding;
import com.MamaDevalayam.utility.FileUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private static final String TAG = UpdateActivity.class.getSimpleName();
    private EditText update_name_et, update_email_et, update_mobile_et, business_reg_name_et;
    TextInputLayout name_input_layout_update;
    private TextView skip_btn;
    private ImageView back_icon_img, ic_edit_icon;
    private CircleImageView myprofile_edit_img;
    IDonateSharedPreference iDonateSharedPreference;
    private static final int REQUEST_READ_PERMISSION = 786;
    private int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/idonate";
    String editprofile;
    ArrayList<String> country2 = new ArrayList<>();
    ArrayList<String> country_name_id = new ArrayList<String>();
    ArrayList<CurrencyBean> country1 = new ArrayList<CurrencyBean>();
    List<CurrencyBean> country_name_list = new ArrayList<CurrencyBean>();
    ApiInterface apiService;
    JSONArray jsonArray;
    Button update_btn;
    SearchableSpinner country_spinner;
    private final static String API_KEY = "";
    String country_symbol = "";
    RadioButton radio_btn_male, radio_btn_female, radio_btn_orthers, radio_btn_individual, radio_btn_business;
    int index = 0;
    SessionManager sessionManager;
//    @BindView(R.id.business_name_input_layout)
    TextInputLayout business_name_input_layout;
    String gender, name, user_id, token, email, password, phone, country = "", base64img = "", terms = "";
    private LinearLayout gender_layout, terms_layout;
    CheckBox checkbox_btn;
    private TextView selected_business_tv;
    private RadioGroup business_radioGroup1;
    static HashMap<String, String> userDetails;
    SessionManager session;
    String business_name = "";
//    String type = "";
    String type = "Individual";
    String permission = "";
    ActivityUpdateBinding binding;
    String pathIncorpDoc = "", pathAllocDoc = "", pathStandDoc = "", pathOtherDoc = "";
    String base64PathIncorpDoc = "", base64PathAllocDoc = "", base64PathStandDoc = "", base64PathOtherDoc = "";
    boolean btnIncorpDocClicked = false, btnAllocDocClicked = false, btnStandDocClicked = false, btnOtherDocClicked = false;
    private static final int MY_REQUEST_CODE_PERMISSION = 1000;
    private static final int MY_RESULT_CODE_FILECHOOSER = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getWindow().setBackgroundDrawableResource(R.drawable.dashbord_background);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sessionManager = new SessionManager(getApplicationContext());
        reload();
    }

    private void reload() {
        init();
        listener();
    }


    //TODO 13 FEB 2024
    private void CountryAPI(final String country) {

        this.country2.clear();
        country_name_id.clear();
        country_name_list.clear();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("email", API_KEY);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Log.e(TAG, "CountryAPI: " + apiService);
        Call<JsonObject> call = apiService.countryAPI(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    Log.e(TAG, "123" + jsonObject);
                    String message = jsonObject.getString("message");
                    Log.e(TAG, "" + message);
                    if (jsonObject.getString("status").equals("1")) {
                        String data = jsonObject.getString("data");
                        jsonArray = new JSONArray(data);
                        Log.e(TAG, "" + jsonArray);
                        HashMap<String, String> map = new HashMap<>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject countryobject = jsonArray.getJSONObject(i);
                            CurrencyBean ctb = new CurrencyBean();
                            UpdateActivity.this.country2.add(countryobject.getString("name"));
                            //TODO 13 FEB 2024
//                            ctb.setCurrency_id(countryobject.getString("sortname"));
                            ctb.setCurrency_id(countryobject.getString("iso3"));
                            ctb.setCurrency_name(countryobject.getString("name"));
//                            country_name_id.add(countryobject.getString("sortname"));
                            country_name_id.add(countryobject.getString("iso3"));
                            country_name_list.add(ctb);
                            country1.add(ctb);
//                            if (countryobject.getString("sortname").equalsIgnoreCase(country)) {
                            if (countryobject.getString("iso3").equalsIgnoreCase(country)) {
                                index = 1;
                                country_spinner.setDefaultText(countryobject.getString("name"));
//                                country_symbol = countryobject.getString("sortname");
                                country_symbol = countryobject.getString("iso3");
                                //TODO 13 FEB 2024
//                            } else if (countryobject.getString("name").equalsIgnoreCase("United States")) {
                            } else if (countryobject.getString("name").equalsIgnoreCase("India")) {
                                if (index == 0) {
                                    country_spinner.setDefaultText(countryobject.getString("name"));
//                                    country_symbol = countryobject.getString("sortname");
                                    country_symbol = countryobject.getString("iso3");
                                }
                            }
                        }
                        country_spinner.setData(UpdateActivity.this.country2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void init() {
        base64img = "";
        session = new SessionManager(getApplicationContext());
        iDonateSharedPreference = new IDonateSharedPreference();
        update_name_et = (EditText) findViewById(R.id.update_name_et);
        update_email_et = (EditText) findViewById(R.id.update_email_et);
        update_mobile_et = (EditText) findViewById(R.id.update_mobile_et);
        back_icon_img = (ImageView) findViewById(R.id.back_icon_img);
        ic_edit_icon = (ImageView) findViewById(R.id.ic_edit_icon);
        radio_btn_male = (RadioButton) findViewById(R.id.radio_btn_male);
        radio_btn_female = (RadioButton) findViewById(R.id.radio_btn_female);
        radio_btn_orthers = (RadioButton) findViewById(R.id.radio_btn_orthers);
        myprofile_edit_img = (CircleImageView) findViewById(R.id.myprofile_edit_img);
        name_input_layout_update = (TextInputLayout) findViewById(R.id.name_input_layout_update);
        skip_btn = (TextView) findViewById(R.id.skip_btn);
        update_btn = (Button) findViewById(R.id.update_btn);
        country_spinner = (SearchableSpinner) findViewById(R.id.spin_country);
        business_reg_name_et = (EditText) findViewById(R.id.business_reg_name_et);
        business_name_input_layout = (TextInputLayout) findViewById(R.id.business_name_input_layout);
        radio_btn_individual = (RadioButton) findViewById(R.id.radio_btn_individual);
        radio_btn_business = (RadioButton) findViewById(R.id.radio_btn_business);
        gender_layout = (LinearLayout) findViewById(R.id.gender_layout);
        terms_layout = (LinearLayout) findViewById(R.id.terms_layout);
        checkbox_btn = (CheckBox) findViewById(R.id.checkbox_btn);
        selected_business_tv = (TextView) findViewById(R.id.selected_business_tv);
        business_radioGroup1 = (RadioGroup) findViewById(R.id.business_radioGroup1);
        Log.e("data232", "" + iDonateSharedPreference.getprofiledata(getApplicationContext()));
        String updateauth = iDonateSharedPreference.getprofiledata(getApplicationContext());
        Log.e("update23", updateauth);
        userDetails = session.getUserDetails();
        Log.e("KEY_BUSINESS", "" + userDetails.get(SessionManager.KEY_BUSINESS));
        type = userDetails.get(SessionManager.KEY_type);
        business_name = userDetails.get(SessionManager.KEY_BUSINESS);
//        if (!type.isEmpty()) {
//            String typeupper = type.substring(0, 1).toUpperCase() + type.substring(1);
//            selected_business_tv.setText(typeupper);
//            if (type.equalsIgnoreCase("business")) {
//                business_name_input_layout.setVisibility(View.VISIBLE);
//                gender_layout.setVisibility(View.GONE);
//                selected_business_tv.setVisibility(View.VISIBLE);
//                business_radioGroup1.setVisibility(View.GONE);
//                if (!business_name.isEmpty()) {
//                    business_reg_name_et.setText(business_name.substring(0, 1).toUpperCase() + business_name.substring(1));
//                }
//            } else if (type.equalsIgnoreCase("individual")) {
                business_name_input_layout.setVisibility(View.GONE);
                gender_layout.setVisibility(View.VISIBLE);
                business_radioGroup1.setVisibility(View.GONE);
                selected_business_tv.setVisibility(View.VISIBLE);
//            } else {
//                business_name_input_layout.setVisibility(View.GONE);
//                gender_layout.setVisibility(View.VISIBLE);
//                selected_business_tv.setVisibility(View.GONE);
//                business_radioGroup1.setVisibility(View.VISIBLE);
//            }
//        }


        try {
            JSONObject jsonObject1 = new JSONObject(updateauth);
            Log.e("jsonObject1", "" + jsonObject1);
            update_name_et.setText(jsonObject1.getString("name"));
            update_email_et.setText(jsonObject1.getString("email"));
            update_mobile_et.setText(jsonObject1.getString("phone_number"));
            user_id = jsonObject1.getString("user_id");
            token = jsonObject1.getString("token");
            name = jsonObject1.getString("name");
            email = jsonObject1.getString("email");
            phone = jsonObject1.getString("phone_number");
            country = jsonObject1.getString("country");
            gender = jsonObject1.getString("gender");
            terms = jsonObject1.getString("terms");
            terms="yes";
            try {
                String image = jsonObject1.getString("photo");
                if (image.isEmpty()) {
                    Picasso.get().load(R.drawable.ic_profile_holder).placeholder(R.drawable.ic_profile_holder).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_profile_holder).into(myprofile_edit_img);
                } else {
                    Picasso.get().load(image).placeholder(R.drawable.ic_profile_holder).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_profile_holder).into(myprofile_edit_img);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (gender.equalsIgnoreCase("F")) {
                radio_btn_female.setChecked(true);
            } else if (gender.equalsIgnoreCase("M")) {
                radio_btn_male.setChecked(true);
            } else if (gender.equalsIgnoreCase("O")) {
                radio_btn_orthers.setChecked(true);
            }
            if (country.isEmpty()) {
                country = "US";
            }
            if (terms.equalsIgnoreCase("Yes")) {
                terms_layout.setVisibility(View.GONE);
                skip_btn.setVisibility(View.VISIBLE);
                checkbox_btn.setChecked(true);
            } else {
                skip_btn.setVisibility(View.GONE);
//                terms_layout.setVisibility(View.VISIBLE);
                terms_layout.setVisibility(View.GONE);
            }
            Log.e("countryupdate", country);
            if (isOnline()) {
                CountryAPI(country);
            } else {
                Toast.makeText(UpdateActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editprofile = iDonateSharedPreference.geteditprofile(getApplicationContext());
        if (editprofile.equalsIgnoreCase("1")) {
            business_radioGroup1.setVisibility(View.GONE);
//            update_btn.setVisibility(View.GONE);
            update_btn.setVisibility(View.VISIBLE);
            if (terms.equalsIgnoreCase("Yes")) {
                terms_layout.setVisibility(View.GONE);
                skip_btn.setVisibility(View.VISIBLE);
                checkbox_btn.setChecked(true);
            } else {
                skip_btn.setVisibility(View.GONE);
//                terms_layout.setVisibility(View.VISIBLE);
                terms_layout.setVisibility(View.GONE);
            }
            String image = iDonateSharedPreference.getsocialProfileimg(getApplicationContext());

            Log.e("image11", "" + image);
            if (!iDonateSharedPreference.getsocialProfileimg(getApplicationContext()).equalsIgnoreCase("null") || !iDonateSharedPreference.getsocialProfileimg(getApplicationContext()).isEmpty()) {
                try {

                    //  Picasso.with(this).load(image).placeholder(R.drawable.ic_profile_holder).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_profile_holder).into(myprofile_edit_img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            myprofile_edit_img.setVisibility(View.VISIBLE);
            back_icon_img.setVisibility(View.VISIBLE);
            ic_edit_icon.setVisibility(View.VISIBLE);
//            ic_edit_icon.setVisibility(View.GONE);
        } else if (editprofile.equalsIgnoreCase("2")) {
            skip_btn.setVisibility(View.GONE);
            String image = iDonateSharedPreference.getsocialProfileimg(getApplicationContext());

            Log.e("image11", "" + image);
            if (!iDonateSharedPreference.getsocialProfileimg(getApplicationContext()).equalsIgnoreCase("null") || !iDonateSharedPreference.getsocialProfileimg(getApplicationContext()).isEmpty()) {
                try {

                    //   Picasso.with(this).load(image).placeholder(R.drawable.ic_profile_holder).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_profile_holder).into(myprofile_edit_img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            myprofile_edit_img.setVisibility(View.VISIBLE);
            back_icon_img.setVisibility(View.GONE);
            ic_edit_icon.setVisibility(View.VISIBLE);
//            ic_edit_icon.setVisibility(View.GONE);

        } else {
            skip_btn.setVisibility(View.GONE);
            myprofile_edit_img.setVisibility(View.GONE);
            back_icon_img.setVisibility(View.GONE);
            ic_edit_icon.setVisibility(View.GONE);
        }
    }

    private void ManageVisibilityOfDocs(Boolean isHide) {
        if (isHide) {
            binding.layIncorpDoc.setVisibility(View.GONE);
            binding.layAllocDoc.setVisibility(View.GONE);
            binding.layStandDoc.setVisibility(View.GONE);
            binding.layOtherDoc.setVisibility(View.GONE);
        } else {
            binding.layIncorpDoc.setVisibility(View.VISIBLE);
            binding.layAllocDoc.setVisibility(View.VISIBLE);
            binding.layStandDoc.setVisibility(View.VISIBLE);
            binding.layOtherDoc.setVisibility(View.VISIBLE);
        }
    }

    private void listener() {
        checkbox_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    update_btn.setVisibility(View.VISIBLE);
                } else {

                }
            }
        });
        update_name_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                update_btn.setVisibility(View.VISIBLE);
                return false;
            }
        });
        update_mobile_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                update_btn.setVisibility(View.VISIBLE);
                return false;
            }
        });
        radio_btn_male.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                update_btn.setVisibility(View.VISIBLE);
                return false;
            }
        });
        radio_btn_female.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                update_btn.setVisibility(View.VISIBLE);
                return false;
            }
        });
        radio_btn_orthers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                update_btn.setVisibility(View.VISIBLE);
                return false;
            }
        });

        radio_btn_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_btn_business.isChecked()) {
                    business_name_input_layout.setVisibility(View.VISIBLE);
                    business_name = business_reg_name_et.getText().toString();
                    gender_layout.setVisibility(View.GONE);
                    type = "business";
                    ManageVisibilityOfDocs(false);
                } else {
                    gender_layout.setVisibility(View.VISIBLE);
                    business_reg_name_et.setText("");
                    type = "individual";
                    business_name_input_layout.setVisibility(View.GONE);
                    ManageVisibilityOfDocs(true);
                }
            }
        });
        radio_btn_individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_btn_individual.isChecked()) {
                    business_reg_name_et.setText("");
                    type = "individual";
                    business_name_input_layout.setVisibility(View.GONE);
                    gender_layout.setVisibility(View.VISIBLE);
                    ManageVisibilityOfDocs(true);
                } else {
                    type = "business";
                    gender_layout.setVisibility(View.GONE);
                    business_name = business_reg_name_et.getText().toString();
                    business_name_input_layout.setVisibility(View.VISIBLE);
                    ManageVisibilityOfDocs(false);
                }
            }
        });
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back_icon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        terms_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity.changeActivityData(UpdateActivity.this, TermsAndConditionActivity.class, "");
            }
        });
        myprofile_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_btn.setVisibility(View.VISIBLE);
                if (isPermissionGranted()) {
                    getGallery();
                } else {
                    isPermissionGranted();
                }
            }
        });

        country_spinner.setSelectionListener(new SearchableSpinner.OnSelectionListener() {
            @Override
            public void onSelect(int spinnerId, int i, String value) {
                update_btn.setVisibility(View.VISIBLE);
                Log.e("Select2", "Position : " + i + " : Value : " + value + " : " + spinnerId);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(country_spinner.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject countryobject = null;
                    try {
                        countryobject = jsonArray.getJSONObject(i1);
                        if (countryobject.getString("name").equals(value)) {
                            //TODO 13 FEB 2024
//                            country = countryobject.getString("sortname");
                            country = countryobject.getString("iso3");
                            Log.e(TAG, country);
                        }
                    } catch (JSONException e) {

                    }
                }
            }
        });
        update_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().startsWith(" ")) {
                    update_name_et.setText("");
                    Toast.makeText(getApplicationContext(), "Space Not allowed", Toast.LENGTH_LONG).show();
                    name_input_layout_update.setError("Required name");
                } else {
                    if (update_name_et.getText().toString().trim().length() <= 0) {
                        name_input_layout_update.setError("Required name");
                    } else {
                        name_input_layout_update.setError("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiofn();
                if (!isEmpty(update_name_et)) {
                    if (isOnline()) {
                        UpadteAPI();
//                        if (checkbox_btn.isChecked()) {
//
//                        } else {
//                            Toast.makeText(UpdateActivity.this, "Please accept our Terms and Conditions", Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        ConstantFunctions.showSnackbar(update_name_et, "Please check internet connection", UpdateActivity.this);
                    }
                } else {
                    Toast.makeText(UpdateActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.uploadBtnIncorpDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIncorpDocClicked = true;
                btnAllocDocClicked = false;
                btnStandDocClicked = false;
                btnOtherDocClicked = false;
                askPermissionAndBrowseFile();
            }
        });

        binding.uploadBtnAllocDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIncorpDocClicked = false;
                btnAllocDocClicked = true;
                btnStandDocClicked = false;
                btnOtherDocClicked = false;
                askPermissionAndBrowseFile();
            }
        });

        binding.uploadBtnStandDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIncorpDocClicked = false;
                btnAllocDocClicked = false;
                btnStandDocClicked = true;
                btnOtherDocClicked = false;
                askPermissionAndBrowseFile();
            }
        });

        binding.uploadBtnOtherDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIncorpDocClicked = false;
                btnAllocDocClicked = false;
                btnStandDocClicked = false;
                btnOtherDocClicked = true;
                askPermissionAndBrowseFile();
            }
        });
    }

    private void askPermissionAndBrowseFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            int permisson = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_CODE_PERMISSION);
                return;
            }
        }
        doBrowseFile();
    }

    private void doBrowseFile() {
        Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFileIntent.setType("*/*");
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file");
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILECHOOSER);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("TAG", "Permission is granted *********");
                return true;
            } else {
                Log.e("TAG", "Permission is not present * * * * ** *");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 99);
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 99) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isPermissionGranted()) {
                    getGallery();
                } else {
                    isPermissionGranted();
                }
            }
        }
    }

    private void radiofn() {
        if (radio_btn_male.isChecked()) {
            gender = "M";
        } else if (radio_btn_female.isChecked()) {
            gender = "F";
        } else if (radio_btn_orthers.isChecked()) {
            gender = "O";
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence charSequence = text.getText().toString();
        return TextUtils.isEmpty(charSequence);
    }

    public static String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void UpadteAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String image_url = "";
        name = update_name_et.getText().toString().trim();

        Log.e(TAG, "UpadteAPI:---- " + base64img);
        String phone = update_mobile_et.getText().toString().trim();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("token", token);
        jsonObject1.addProperty("name", name);
        jsonObject1.addProperty("email", email);
        jsonObject1.addProperty("phone", phone);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(UpdateActivity.this));
        jsonObject1.addProperty("country", country);
        jsonObject1.addProperty("gender", gender);
        jsonObject1.addProperty("photo", base64img);
        jsonObject1.addProperty("business_name", business_name);
        jsonObject1.addProperty("type", type);
        jsonObject1.addProperty("terms", "Yes");

        /*if (type.equalsIgnoreCase("business")) {
            if (pathIncorpDoc.length() > 0) {
                jsonObject1.addProperty("incorp_doc", base64PathIncorpDoc);
                jsonObject1.addProperty("incorp_doc_type", pathIncorpDoc.substring(pathIncorpDoc.lastIndexOf(".")));
            }
            if (pathAllocDoc.length() > 0) {
                jsonObject1.addProperty("tax_id_doc", base64PathAllocDoc);
                jsonObject1.addProperty("tax_id_doc_type", pathAllocDoc.substring(pathAllocDoc.lastIndexOf(".")));
            }
            if (pathStandDoc.length() > 0) {
                jsonObject1.addProperty("good_standing_doc", base64PathStandDoc);
                jsonObject1.addProperty("good_standing_doc_type", pathStandDoc.substring(pathStandDoc.lastIndexOf(".")));
            }
            if (pathOtherDoc.length() > 0) {
                jsonObject1.addProperty("oth_doc", base64PathOtherDoc);
                jsonObject1.addProperty("oth_doc_type", pathOtherDoc.substring(pathOtherDoc.lastIndexOf(".")));
            }
        }*/

        Log.e("jsonObject1", "" + jsonObject1);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.update_profile(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.e(TAG, "onResponse:123 update_profileAPIb = "+response );
                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        Log.e(TAG, "123" + jsonObject);
                        String message = jsonObject.getString("message");
                        Log.e(TAG, "" + message);
                        if (jsonObject.getString("status").equals("1")) {
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            sessionManager.createLoginSession(jsonObject1.getString("user_id"), jsonObject1.getString("email"), jsonObject1.getString("name"), jsonObject1.getString("phone_number"), jsonObject1.getString("photo"), jsonObject1.getString("token"), jsonObject1.getString("business_name"), jsonObject1.getString("country"), jsonObject1.getString("gender"), jsonObject1.getString("type"));
                            iDonateSharedPreference.setprofiledata(getApplicationContext(), data);
                            iDonateSharedPreference.setSocialProfileimg(getApplicationContext(), image_url);
                            if (iDonateSharedPreference.geteditprofile(getApplicationContext()).equalsIgnoreCase("1")) {
                                iDonateSharedPreference.seteditprofile(getApplicationContext(), "1");
                                reload();
//                                update_btn.setVisibility(View.GONE);
                                update_btn.setVisibility(View.VISIBLE);
                            } else {
                                finish();
                            }
                            Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else if (jsonObject.getString("status").equals("0")) {
                            ConstantFunctions.showSnackbar(update_name_et, message, UpdateActivity.this);
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

    private void selectFile() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setTitle("Permission Alert");
            builder.setMessage("Camera permission is required to in order to provide photo capture features.");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
                        }
                    });
            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),
                            android.R.string.no, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setCancelable(false);
            builder.show();

        } else {
            getGallery();
        }
    }


    public void getGallery() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(UpdateActivity.this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera",
                "Remove Photo"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:

                                myprofile_edit_img.setImageDrawable(getDrawable(R.drawable.ic_profile_holder));
                                base64img = "null";
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_RESULT_CODE_FILECHOOSER:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Uri fileUri = data.getData();

                        String filePath = null;
                        try {
                            filePath = FileUtils.getPath(this, fileUri);
                        } catch (Exception e) {
                            Log.e(TAG, "Error: " + e);
                            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                        }

                        assert filePath != null;
                        File file = new File(filePath);
                        long fileSizeInBytes = file.length(); // Get length of file in bytes
                        long fileSizeInKB = fileSizeInBytes / 1024; // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                        long fileSizeInMB = fileSizeInKB / 1024; // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                        if (fileSizeInMB <= 1) {
                            String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                            if (btnIncorpDocClicked) {
                                binding.txtIncorpDocName.setText(filename);
                                binding.txtIncorpDocName.setTextColor(getResources().getColor(R.color.file_choose_color));
                                pathIncorpDoc = filePath;
                                base64PathIncorpDoc = getStringFile(file);
                            } else if (btnAllocDocClicked) {
                                binding.txtAllocDocName.setText(filename);
                                binding.txtAllocDocName.setTextColor(getResources().getColor(R.color.file_choose_color));
                                pathAllocDoc = filePath;
                                base64PathAllocDoc = getStringFile(file);
                            } else if (btnStandDocClicked) {
                                binding.txtStandDocName.setText(filename);
                                binding.txtStandDocName.setTextColor(getResources().getColor(R.color.file_choose_color));
                                pathStandDoc = filePath;
                                base64PathStandDoc = getStringFile(file);
                            } else if (btnOtherDocClicked) {
                                binding.txtOtherDocName.setText(filename);
                                binding.txtOtherDocName.setTextColor(getResources().getColor(R.color.file_choose_color));
                                pathOtherDoc = filePath;
                                base64PathOtherDoc = getStringFile(file);
                            }
                        } else {
                            Toast.makeText(this, "Please choose file size less than 1mb", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                /*   String PdfPathHolder2 = modelclass.getPath(this, pickedImage);*/
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.e(TAG, "onActivityResult: ----------" + path);
                    Toast.makeText(UpdateActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    myprofile_edit_img.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    base64img = encoded;
                    Log.e("base64", "" + base64img);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            myprofile_edit_img.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            String path = saveImageCam(thumbnail, getApplicationContext(), IMAGE_DIRECTORY);
//            base64img = convertToString(path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            base64img = encoded;
            Log.e("base64", "" + base64img);
            Toast.makeText(UpdateActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    // Converting File to Base64.encode String type using Method
    private String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    public String saveImageCam(Bitmap myBitmap, Context applicationContext, String imageDirectory) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + imageDirectory);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(applicationContext,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String convertToString(String path) {
        String base_64_image;
        Bitmap bitmapOrg = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.PNG, 50, bao);
        byte[] ba = bao.toByteArray();
        base_64_image = Base64.encodeToString(ba, Base64.DEFAULT);
        return base_64_image;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Permission Alert");
        builder.setMessage("Camera permission is required to in order to provide photo capture features.");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Dexter.withActivity(UpdateActivity.this)
                                .withPermissions(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                                        // check if all permissions are granted
                                        if (report.areAllPermissionsGranted()) {
                                            permission = "granted";
                                            new IDonateSharedPreference().setpermission(getApplicationContext(), "granted");
                                            //   Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                                        }

                                        // check for permanent denial of any permission
                                        if (report.isAnyPermissionPermanentlyDenied()) {
                                            // show alert dialog navigating to Settings
                                            //openSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).
                                withErrorListener(new PermissionRequestErrorListener() {
                                    @Override
                                    public void onError(DexterError error) {
                                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .onSameThread()
                                .check();
                    }
                });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        android.R.string.no, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (editprofile.equalsIgnoreCase("1")) {
            //ChangeActivity.changeActivity(UpdateActivity.this, BrowseActivity.class);
            finish();
        } else {
            super.onBackPressed();
            finish();
        }
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
