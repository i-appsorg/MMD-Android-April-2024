package com.MamaDevalayam.CommonActivity;

import static com.MamaDevalayam.Model.ChangeActivity.showSnackbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.MamaDevalayam.Activity.BrowseActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.MamaDevalayam.Activity.AboutNewActivity;
import com.MamaDevalayam.Activity.HelpSupportActivity;
import com.MamaDevalayam.Activity.LoginActivity;
import com.MamaDevalayam.Activity.MyspaceActivity;
import com.MamaDevalayam.Activity.NotificationActivity;
import com.MamaDevalayam.Activity.SettingActivity;
import com.MamaDevalayam.Activity.UpdateActivity;
import com.MamaDevalayam.Adapter.CommonActionBarListAdapter;
import com.MamaDevalayam.Interwork.ConnectivityReceiver;
import com.MamaDevalayam.Interwork.MyApplication;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.Model.Selected;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.HashMap;


/**
 * Created by IM028 on 8/2/16.
 */
public class CommonMenuActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MenuCommonActivity";
    private Toolbar toolbar;
    private FrameLayout frameLayout, menuActivityFrameLayout;
    private DrawerLayout drawerLayout;
    public ImageView menuImageView, sosImg, refreshMenu;
//    private CircleImageView myprofile_img;
    private ImageView myprofile_img;
    private TextView myaccount_name_tv;
    private ListView menuListView;
    TextView browse_tv, myspace_tv;
    LinearLayout linear_browse, linear_myspace, myprofile_name_tv, edit_linear;
    ImageView browse_img, myspace_img, menu_back_img;
    private View headerView;
    private Selected select;
    boolean doubleclickToClose = false;
    SessionManager session;
    IDonateSharedPreference iDonateSharedPreference;
    private GoogleApiClient mGoogleApiClient;
    static HashMap<String, String> userDetails;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        setContentView(R.layout.activity_common_menu);
        toolbar = findViewById(R.id.commonMenuActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        drawerLayout = findViewById(R.id.commonMenuActivityDrawerLayout);
        menuImageView = findViewById(R.id.menu);
        refreshMenu = findViewById(R.id.refreshMenu);
        browse_img = (ImageView) findViewById(R.id.browse_img);
        myspace_img = (ImageView) findViewById(R.id.my_space_img);
        menu_back_img = (ImageView) findViewById(R.id.menu_back_img);
        browse_tv = (TextView) findViewById(R.id.browse_tv);
        myspace_tv = (TextView) findViewById(R.id.my_space_tv);
        linear_browse = (LinearLayout) findViewById(R.id.linear_browse);
        linear_myspace = (LinearLayout) findViewById(R.id.linear_myspace);
        menuListView = findViewById(R.id.commonMenuActivityDrawerListView);
        menuActivityFrameLayout = findViewById(R.id.menuActivityFrameLayout);
        iDonateSharedPreference = new IDonateSharedPreference();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        linear_browse.setOnClickListener(this);
        linear_myspace.setOnClickListener(this);
        //todo login
        if (session.isLoggedIn()) {
            userDetails = session.getUserDetails();
            Log.e("userDetails", "" + userDetails);
            Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
            Log.e("KEY_username", "" + userDetails.get(SessionManager.KEY_NAME));
        }

        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        menu_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        headerView = getLayoutInflater().inflate(R.layout.activity_common_navigation_heading, null, false);
        // footerView = getLayoutInflater().inflate(R.layout.navigation_footer, null, false);
        myprofile_img = findViewById(R.id.myprofile_img);
        myaccount_name_tv = findViewById(R.id.myaccount_name_tv);
        edit_linear = findViewById(R.id.edit_linear);
        menuListView.setAdapter(new CommonActionBarListAdapter(this));
        //menuListView.addHeaderView(headerView);
        edit_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo login
//                if (session.isLoggedIn()) {
                    iDonateSharedPreference.seteditprofile(getApplicationContext(), "1");
                    ChangeActivity.changeActivity(CommonMenuActivity.this, UpdateActivity.class);
                    // finish();
                    ;
//                } else {
//                    LoginDialog();
//                    //ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
//                    //  finish();
//                    ;
//                }

            }
        });
//todo login
        if (session.isLoggedIn()) {
            userDetails = session.getUserDetails();
            Log.e("userDetails---", "" + userDetails);
            Log.e("KEY_UID---", "" + userDetails.get(SessionManager.KEY_UID));
            Log.e("KEY_username----", "" + userDetails.get(SessionManager.KEY_NAME));
            Log.e("KEY_profile----", "" + userDetails.get(SessionManager.KEY_PIC));
            //  Log.e("KEY_username", "" + userDetails.get(SessionManager.KEY_PIC));
            try {
                String image = userDetails.get(SessionManager.KEY_PIC);
                Log.e("img", image);
                Picasso.get().load(image).placeholder(R.drawable.ic_profile_holder).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_profile_holder).into(myprofile_img);

//                if (!image.isEmpty()) {
//
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            myaccount_name_tv.setText(userDetails.get(SessionManager.KEY_NAME));
        } else {

        }

       /* String image = iDonateSharedPreference.getsocialProfileimg(getApplicationContext());
        Log.e("image11", "" + image);
        if (!iDonateSharedPreference.getsocialProfileimg(getApplicationContext()).equalsIgnoreCase("null") || !iDonateSharedPreference.getsocialProfileimg(getApplicationContext()).isEmpty()) {
            try {
                Picasso.with(this).load(image).placeholder(R.drawable.ic_profile_holder).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_profile_holder).into(myprofile_img);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }*/
        //menuListView.addFooterView(footerView);

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("demotestingsss", "testing" + position);
                if (position != menuListView.getCount()) {
                    /*position != 0 &&*/
                    TextView nameTextView = (TextView) view.findViewById(R.id.commonNavigationItemTextView);
                    Log.e("demotestingsss", "testing" + nameTextView);
//                    Toast.makeText(NavigationCommonActivity.this, nameTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (!nameTextView.getText().toString().equalsIgnoreCase(""))
                        switch (nameTextView.getText().toString().toLowerCase().trim()) {

                            case "my notifications":
                                Log.e("demotestingsss", "testing");
                                //todo login
                                if (session.isLoggedIn()) {
                                    ChangeActivity.changeActivity(CommonMenuActivity.this, NotificationActivity.class);
                                    finish();
                                } else {
                                    LoginDialog();
                                    // ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                                }
                                break;
                            case "my settings":
                                Log.e("settings", "settings");
                                ChangeActivity.changeActivity(CommonMenuActivity.this, SettingActivity.class);
                                finish();
                                break;
                            case "about mama devalayam":
                                Log.e("settings", "settings");
                                ChangeActivity.changeActivity(CommonMenuActivity.this, AboutNewActivity.class);
                                finish();
                                break;
//                            case "about i2-donate":
//                                Log.e("settings", "settings");
//                                ChangeActivity.changeActivity(CommonMenuActivity.this, AboutNewActivity.class);
//                                finish();
//                                break;
                            case "help/support":
                                Log.e("settings", "settings");
                                ChangeActivity.changeActivity(CommonMenuActivity.this, HelpSupportActivity.class);
                                finish();
                                break;
                            case "logout":
                                iDonateSharedPreference.setlogintype(getApplicationContext(), "non");
                                Log.e("settings", "settings");
                                //todo login
                                if (session.isLoggedIn()) {
                                    signOut1();
                                    FacebookSdk.sdkInitialize(getApplicationContext());
                                    LoginManager.getInstance().logOut();
                                    if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("email")) {
                                        iDonateSharedPreference.setlogintype(getApplicationContext(), "non");
                                        signOut();
                                    } else if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("facebook")) {
                                        iDonateSharedPreference.setlogintype(getApplicationContext(), "non");
                                        FacebookSdk.sdkInitialize(getApplicationContext());
                                        LoginManager.getInstance().logOut();
                                        session.logoutUser();
                                        finish();

                                    } else {
                                        iDonateSharedPreference.setlogintype(getApplicationContext(), "non");
                                        session.logoutUser();
                                        finish();
                                    }
                                } else {
                                    if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("email")) {
                                        signOut();
                                        iDonateSharedPreference.setlogintype(getApplicationContext(), "non");
                                    } else if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("facebook")) {
                                        FacebookSdk.sdkInitialize(getApplicationContext());
                                        LoginManager.getInstance().logOut();
                                        LoginDialog();
                                        //ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                                        // finish();
                                    } else {
                                        iDonateSharedPreference.setlogintype(getApplicationContext(), "non");
                                        session.logoutUser();
                                        finish();
                                    }
                                }
                                break;
                            case "login":
                                Log.e("settings", "settings");
                                //todo login
                                if (session.isLoggedIn()) {

                                    Log.e("email", "" + iDonateSharedPreference.getsocialMedia(getApplicationContext()));
                                    if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("email")) {
                                        signOut();
                                    } else if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("facebook")) {
                                        FacebookSdk.sdkInitialize(getApplicationContext());
                                        LoginManager.getInstance().logOut();
                                        //LoginDialog();
                                        ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                                        //  finish();
                                    } else if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("twitter")) {
                                        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                                        if (twitterSession != null) {
                                            CookieSyncManager.createInstance(getApplicationContext());
                                            CookieManager cookieManager = CookieManager.getInstance();
                                            cookieManager.removeSessionCookie();
                                            TwitterCore.getInstance().getSessionManager().clearActiveSession();
                                        }
                                    } else {
                                        session.logoutUser();
                                    }

                                } else {
                                    Log.e("email", "" + iDonateSharedPreference.getsocialMedia(getApplicationContext()));
                                    signOut1();
                                    //LoginDialog();
                                    ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                                    //  finish();
                                   /* if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("email")) {
                                        signOut();
                                    } else if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("facebook")) {
                                        FacebookSdk.sdkInitialize(getApplicationContext());
                                        LoginManager.getInstance().logOut();
                                        ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                                        finish();
                                    }*/

                                }
                                break;
                            default:
                                break;
                        }

                    if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                        drawerLayout.closeDrawer(Gravity.LEFT);
                } else drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }
    public void showDrawer(){
        menuImageView.performClick();
    }

    private void LoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CommonMenuActivity.this);
        builder.setTitle("");
        builder.setMessage("For Advance Features Please Log-in/Register");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                    }
                });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    /* */
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.e("logout", "logout");
                        signOut1();
                        session.logoutUser();
                        finish();
                       /* ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                        finish();*/
                    }
                });
    }

    private void signOut1() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.e("logout", "logout");

                       /* ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                        finish();*/
                    }
                });
    }

    public void setView(int viewLayout, String activity) {
        frameLayout = (FrameLayout) findViewById(R.id.commonMenuActivityFrameLayout);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(viewLayout, null, false);
        if (activity.equalsIgnoreCase("DashboardActivity")) {
            refreshMenu.setVisibility(View.VISIBLE);
        } else {
            refreshMenu.setVisibility(View.GONE);
        }
        frameLayout.addView(activityView);
    }

    /* public void setTitle(String title) {
         titleTextView.setText(title);
     }
 */


    private void showSnack(boolean isConnected) {
        String message = null;

        if (!isConnected) {

            message = "Sorry! Not connected to internet";
            Log.e("sus", "" + message);

        } else {
            message = "Good! Connected to Internet";
            Log.e("sus", "" + message);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (index == 1) {
            Log.e("onresum", "onresumne");
            referesh();
        }
        Log.e("onresum", "onresumne11");
        index = 1;

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

     private void referesh() {
        menuListView.setAdapter(new CommonActionBarListAdapter(this));
         //todo login
        if (session.isLoggedIn()) {
            userDetails = session.getUserDetails();
            Log.e("userDetails", "" + userDetails);
            Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
            Log.e("KEY_username", "" + userDetails.get(SessionManager.KEY_NAME));
            Log.e("KEY_profile", "" + userDetails.get(SessionManager.KEY_PIC));
            //  Log.e("KEY_username", "" + userDetails.get(SessionManager.KEY_PIC));
            try {
                String image = userDetails.get(SessionManager.KEY_PIC);
                Picasso.get().load(image).placeholder(R.drawable.ic_profile_holder).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.ic_profile_holder).into(myprofile_img);

                Log.e("img", image);
            } catch (Exception e) {
                e.printStackTrace();
            }

            myaccount_name_tv.setText(userDetails.get(SessionManager.KEY_NAME));
        } else {

        }
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Log.e("network", "checking");
        showSnack(isConnected);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.linear_browse) {
            if (Selected.Browse != select) {
                Log.e("Browse", "Browse");
                ChangeActivity.changeActivity(CommonMenuActivity.this, BrowseActivity.class);
                browse_tv.setTextColor(getResources().getColor(R.color.quantum_white_text));
                // browse_img.setImageTintMode();
                browse_img.setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                finish();
            } else {
                Log.e("Browse1", "Browse2");
                    /*ChangeActivity.changeActivity(CommonMenuActivity.this, MyspaceActivity.class);
                    myspace_tv.setTextColor(getResources().getColor(R.color.colorAccent));
                    finish();*/
            }
        } else if (id == R.id.linear_myspace) {
            if (Selected.Myspace != select) {
                Log.e("Myspace", "Myspace");
                //todo login
                if (session.isLoggedIn()) {
                    ChangeActivity.changeActivity(CommonMenuActivity.this, MyspaceActivity.class);
                    myspace_tv.setTextColor(getResources().getColor(R.color.quantum_white_text));
                    myspace_img.setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                    finish();
                } else {
                    LoginDialog();
                    // ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                    //finish();
                }

            } else {
                Log.e("Myspace2", "Myspace2");
                   /* ChangeActivity.changeActivity(CommonMenuActivity.this, BrowseActivity.class);
                    browse_tv.setTextColor(getResources().getColor(R.color.colorAccent));
                    finish();*/
            }
        }


    }

    public void setSelected(Selected select) {
        this.select = select;
        int textColor;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            textColor = getResources().getColor(R.color.quantum_white_text, getTheme());
        } else {
            textColor = getResources().getColor(R.color.quantum_white_text);
        }
        switch (select) {
            case Browse:
                // home.setImageResource(R.drawable.logo);
                browse_img.setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                browse_tv.setTextColor(textColor);
                break;
            case Myspace:
                // store.setImageResource(R.drawable.shopping_color);
                myspace_img.setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                myspace_tv.setTextColor(textColor);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            if (Selected.Browse == select) {
                if (doubleclickToClose) {
                    super.onBackPressed();
                    return;
                }
                this.doubleclickToClose = true;
                showSnackbar(drawerLayout, "Please click BACK again to exit", CommonMenuActivity.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleclickToClose = false;
                    }
                }, 2000);

            } else {
                ChangeActivity.clearAllPreviousActivity(CommonMenuActivity.this, BrowseActivity.class);
                browse_tv.setTextColor(getResources().getColor(R.color.quantum_white_text));
                finishAffinity();
                // super.onBackPressed();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


}


