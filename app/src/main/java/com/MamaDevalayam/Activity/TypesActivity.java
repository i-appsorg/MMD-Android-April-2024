package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.Adapter.CategorylistAdapter;
import com.MamaDevalayam.Adapter.TypeSearchAdapter;
import com.MamaDevalayam.Model.Category_new;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.Model.Typenamelist;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypesActivity extends AppCompatActivity {
    private String TAG = "TypesActivity";
    static ArrayList<Typenamelist> typenamelists = new ArrayList<>();
    ArrayList tynamelist = new ArrayList<>(Arrays.asList("Arts,culture & Humanites", "Education", "Environment", "Animal-related", "Health care", "Diseases & Medical Disciplines", "crime & legal-related", "housing & shelter", "Civil rights,social action & Advocacy", "social science", "Mutual & Membership benef"));
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview_types;
    TypeSearchAdapter typeSearchAdapter;
    static String data;
    TextView advance_search_text;
    static LinearLayout bottom_layout, no_data_linear;
    Button reset_button, apply_button;
    ApiInterface apiService;
    ImageView back_icon_login_img;
    static ArrayList<Category_new> category_newArrayList = new ArrayList<>();
    static SessionManager session;
    static HashMap<String, String> userDetails;
    static Context context;
    AlertDialog.Builder builder;
    static ArrayList<String> listOfdate = new ArrayList<>();
    IDonateSharedPreference iDonateSharedPreference;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        init();
        listener();
    }

    private void init() {
        iDonateSharedPreference = new IDonateSharedPreference();
        session = new SessionManager(getApplicationContext());
        recyclerview_types = (RecyclerView) findViewById(R.id.recyclerview_types);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        reset_button = (Button) findViewById(R.id.reset_button);
        advance_search_text = (TextView) findViewById(R.id.advance_search_text);
        apply_button = (Button) findViewById(R.id.apply_button);
        back_icon_login_img = (ImageView) findViewById(R.id.back_icon_login_img);
        no_data_linear = (LinearLayout) findViewById(R.id.no_data_linear);
        context = TypesActivity.this;
        builder = new AlertDialog.Builder(this);
        data = getIntent().getStringExtra("data");
        Log.e(TAG, data);
//        data();
        if (isOnline()) {
            AdvanceCatAPI();
        } else {
            Toast.makeText(TypesActivity.this, "Please check interconnection", Toast.LENGTH_SHORT).show();
        }
        layoutManager = new LinearLayoutManager(this);
        recyclerview_types.setLayoutManager(layoutManager);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerview_types.setLayoutManager(manager);
        recyclerview_types.setItemAnimator(new DefaultItemAnimator());


    }

    private void listener() {

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfdate.clear();
                category_newArrayList.clear();
                iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);

                /*typeSearchAdapter = new TypeSearchAdapter((TypesActivity) context, typenamelists);
                recyclerview_types.setAdapter(typeSearchAdapter);*/

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Category_new category_new = new Category_new();
                        category_new.setCategory_id(object.getString("category_id"));
                        category_new.setCategory_code(object.getString("category_code"));
                        category_new.setCategory_name(object.getString("category_name"));
                        category_new.setSelected(false);
                        category_newArrayList.add(category_new);
                        Log.e("category_newArrayList", "" + category_newArrayList);
                    }
                    CategorylistAdapter.categoty_item.clear();
                    typeSearchAdapter = new TypeSearchAdapter((TypesActivity) context, category_newArrayList);
                    recyclerview_types.setAdapter(typeSearchAdapter);
                    bottom_layout.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "apply_button: " );
                Log.e(TAG, "dhruvi " + iDonateSharedPreference.getAdvancepage(TypesActivity.this));
                if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("unitedstate")) {
                    Intent intent = new Intent(TypesActivity.this, UnitedStateActivity.class);
                    intent.putExtra("data", "1");
                    /*if(data.equalsIgnoreCase("1")){
                        intent.putExtra("data", "1");
                    } else {
                        intent.putExtra("data", "0");
                    }*/
                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.settype(getApplicationContext(), "1");
                    startActivity(intent);
                    finish();
                } else if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("international")) {
                    Intent intent = new Intent(TypesActivity.this, InternationalCharitiesActivity.class);
                    intent.putExtra("data", "1");
                   /* if(data.equalsIgnoreCase("1")){
                        intent.putExtra("data", "1");
                    } else {
                        intent.putExtra("data", "0");
                    }*/
                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.settype(getApplicationContext(), "1");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(TypesActivity.this, NameSearchActivity.class);
                    intent.putExtra("data", "1");
                   /* if(data.equalsIgnoreCase("1")){
                        intent.putExtra("data", "1");
                    } else {
                        intent.putExtra("data", "0");
                    }*/
                    iDonateSharedPreference.setselectedtypedata(getApplicationContext(), listOfdate);
                    iDonateSharedPreference.settype(getApplicationContext(), "1");
                    startActivity(intent);
                    finishAffinity();
                }

            }
        });
        advance_search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo login
//                if (session.isLoggedIn()) {
                    ChangeActivity.changeActivity(TypesActivity.this, AdvanceCompletedNewActivity.class);
                    // finish();
//                } else {
//
//                    ChangeActivity.changeActivity(TypesActivity.this, LoginActivity.class);
//                    //  finish();
//                }
            }
        });
        back_icon_login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailogue_forgot();
            }
        });
    }

    public static void selecteddata(ArrayList<String> listOfdate1) {

        listOfdate = listOfdate1;
        if (listOfdate.size() != 0) {
            bottom_layout.setVisibility(View.VISIBLE);
            Log.e("listOfdate1b", "" + listOfdate1);
            Log.e("listOfdatebg", "" + listOfdate);
        } else {
            bottom_layout.setVisibility(View.GONE);
            Log.e("listOfdatebgm", "" + listOfdate);
        }

    }

    private void dailogue_forgot() {
        builder.setMessage(R.string.alert_message);

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.alert_message)
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

    public static String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void AdvanceCatAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
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

        Log.e("jsonObject1", "" + jsonObject1);


        apiService = ApiClient.getClient().create(ApiInterface.class);

        try {
// TODO not use
            Call<JsonObject> call = apiService.Adavncecategories(jsonObject1);
            call.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    no_data_linear.setVisibility(View.GONE);
                    category_newArrayList.clear();
                    Log.e(TAG, "" + response.body());
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                            Log.e("jsonObject", "" + jsonObject);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            String data = jsonObject.getString("data");
                            if (status.equalsIgnoreCase("1")) {
                                jsonArray = new JSONArray(data);
                                Log.e("1232", "" + jsonArray.length());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Category_new category_new = new Category_new();
                                    category_new.setCategory_id(object.getString("category_id"));
                                    category_new.setCategory_code(object.getString("category_code"));
                                    category_new.setCategory_name(object.getString("category_name"));
                                    category_new.setSelected(false);
                                    category_newArrayList.add(category_new);
                                    Log.e("category_newArrayList", "" + category_newArrayList);
                                }
                                recyclerview_types.setVisibility(View.VISIBLE);
                                no_data_linear.setVisibility(View.GONE);
                                layoutManager = new LinearLayoutManager(TypesActivity.this);
                                recyclerview_types.setLayoutManager(layoutManager);
                           /* GridLayoutManager manager = new GridLayoutManager(TypesActivity.this, , GridLayoutManager.VERTICAL, false);
                            recyclerview_types.setLayoutManager(manager);*/
                                recyclerview_types.setItemAnimator(new DefaultItemAnimator());
                                typeSearchAdapter = new TypeSearchAdapter((TypesActivity) context, category_newArrayList);
                                recyclerview_types.setAdapter(typeSearchAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        recyclerview_types.setVisibility(View.GONE);
                        no_data_linear.setVisibility(View.VISIBLE);
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    recyclerview_types.setVisibility(View.GONE);
                    no_data_linear.setVisibility(View.VISIBLE);
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("Exception", "" + e);
        }


    }


    private void data() {
        typenamelists.clear();
        JSONArray jsonArray = new JSONArray(tynamelist);
        Log.e("tynamelist", "" + jsonArray);
        Log.e("tynamelist", "" + jsonArray.length());
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Log.e("tynamelist", "" + tynamelist.get(i));
            Typenamelist typenamelist = new Typenamelist();
            typenamelist.setTypename(String.valueOf(tynamelist.get(i)));
            typenamelist.setSelected(false);
            typenamelists.add(typenamelist);
            Log.e("typenamelists", "" + typenamelists.get(i).getTypename());
        }

       /* typeSearchAdapter = new TypeSearchAdapter((TypesActivity) context, typenamelists);
        recyclerview_types.setAdapter(typeSearchAdapter);*/
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
    public void onBackPressed() {
        // ChangeActivity.changeActivity(TypesActivity.this, NameSearchActivity.class);
        dailogue_forgot();
    }
}
