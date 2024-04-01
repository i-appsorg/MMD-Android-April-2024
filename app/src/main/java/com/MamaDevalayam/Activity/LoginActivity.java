package com.MamaDevalayam.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.R;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;
import com.MamaDevalayam.Validation.Validation;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    CallbackManager callbackManager;
    CoordinatorLayout coordinator_layout;
    LoginButton facebook_login_btn;
    TwitterLoginButton twitter_login_btn;
    Button login_btn;
    TextView register_btn_tv;
    ApiInterface apiService;
    TextInputLayout email_layout_input, password_layout_input;
    EditText login_username, login_password;
    ImageView back_icon_login_img, google_sign_btn, facebook_login, twitter_login;
    private ProgressDialog mProgressDialog;
    private GoogleApiClient mGoogleApiClient;
    IDonateSharedPreference iDonateSharedPreference;
    SessionManager sessionManager;
    private final String PACKAGE = "com.MamaDevalayam";
    TextView forgot_btn_tv;
    private TwitterAuthClient client;
    String device_token;
    int user_id;
    private final int STORAGE_PERMISSION_CODE = 1;
    /*private final String host = "api.linkedin.com";
    private final String topCardUrl = "https://" + host + "/v1/people/~:(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";
    */

    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e("126", getResources().getString(R.string.twitter_API_key));
//        TwitterConfig config = new TwitterConfig.Builder(this)
//                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
//                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.twitter_API_key), getResources().getString(R.string.twitter_secret_key)))//pass the created app Consumer KEY and Secret also called API Key and Secret
//                .debug(true)//enable debug mode
//                .build();
        //finally initialize twitter with created configs
        Twitter.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        calculateHashKey(getApplicationContext().getPackageName());
        //  getWindow().setBackgroundDrawableResource(R.drawable.dashbord_background);

//        Log.e("a-136","" + config);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sessionManager = new SessionManager(getApplicationContext());

        init();
        listener();
        /*generateHashkey();facebook_login*/

      /*  if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();
        }*/

        String profileUrl = "https://api.linkedin.com/v2/me";

        // Access token for the r_liteprofile permission
//        String accessToken = "vZn+i17vbXU1IpoP9Vw7xeyvsD4=";
        String accessToken = "7w1b1EG4ZAFTlnxpf0AlsjB/RSE=";
/*
        try {
            javax.json.JsonObject profileData = ProfileExample.sendGetRequest(profileUrl, accessToken);
            System.out.println(profileData.toString());
            Log.e("profileData", "" + profileData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    private void calculateHashKey(String yourPackageName) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    yourPackageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e(TAG, "KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                Log.d("KeyHash:",
//                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Alert");
            builder.setMessage("Camera permission is required to in order to provide photo capture features in profile update and donation pages.");
            builder.setPositiveButton("OK",
                    (dialog, which) -> ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE));
            builder.setNegativeButton(android.R.string.no, (dialog, which) -> Toast.makeText(getApplicationContext(),
                    android.R.string.no, Toast.LENGTH_SHORT).show());
            builder.setCancelable(false);
            builder.show();

          /*  new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();*/
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Alert");
            builder.setMessage("Camera permission is required to in order to provide photo capture features in profile update and donation pages.");
            builder.setPositiveButton("OK",
                    (dialog, which) -> ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE));
            builder.setNegativeButton(android.R.string.no, (dialog, which) -> Toast.makeText(getApplicationContext(),
                    android.R.string.no, Toast.LENGTH_SHORT).show());
            builder.setCancelable(false);
            builder.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        iDonateSharedPreference = new IDonateSharedPreference();
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn_tv = (TextView) findViewById(R.id.register_btn_tv);
        back_icon_login_img = (ImageView) findViewById(R.id.back_icon_login_img);
        google_sign_btn = (ImageView) findViewById(R.id.google_sign_btn);
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        facebook_login = (ImageView) findViewById(R.id.facebook_login);

        facebook_login_btn = (LoginButton) findViewById(R.id.facebook_login_btn);
        email_layout_input = (TextInputLayout) findViewById(R.id.email_layout_input);
        twitter_login = (ImageView) findViewById(R.id.twitter_login);
        twitter_login_btn = (TwitterLoginButton) findViewById(R.id.twitter_login_btn);
        password_layout_input = (TextInputLayout) findViewById(R.id.password_layout_input);
        forgot_btn_tv = (TextView) findViewById(R.id.forgot_btn_tv);
        mProgressDialog = new ProgressDialog(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();

        getToken();
        /*Facebook login*/

        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

      /*  if (!loggedOut) {

            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());


            getUserProfile(AccessToken.getCurrentAccessToken());
        }
*/

        facebook_login_btn.setReadPermissions("email", "public_profile","user_friends");
//        facebook_login_btn.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        client = new TwitterAuthClient();

       /* facebook_login_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                getUserProfile(AccessToken.getCurrentAccessToken());
                Log.e("click", "click");
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
               Log.d("API123", loggedIn + " ??");

            }

            @Override
            public void onCancel() {

                Log.e("click", "click1");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {

                Log.e("click", "click2" + exception);

                // App code
            }
        });*/

        facebook_login_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                getUserProfile(AccessToken.getCurrentAccessToken());
                Log.e("click12", "click");
                // App code
            }

            @Override
            public void onCancel() {
                Log.e("click", "click");
                // App code
            }

            @Override
            public void onError(@NonNull FacebookException exception) {
                Log.e("click", "click---------" + exception);
                // App code
            }
        });


//        facebook_login_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.e(TAG, "onSuccess: " + "Login Success \n" +
//                        "User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
//                        "Access Token: " + loginResult.getAccessToken().getToken());
////                tvStatus.setText("Login Success \n" +
////                        "User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
////                        "Access Token: " + loginResult.getAccessToken().getToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.e(TAG, "onCancel: ");
////                tvStatus.setText("Login cancelled");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                Log.e(TAG, "onError:facebook+login_btn " + e);
//            }
//        });


    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {

        login_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().startsWith(" ")) {
                    login_username.setText("");
                    Toast.makeText(getApplicationContext(), "Space Not allowed", Toast.LENGTH_LONG).show();
                    email_layout_input.setError("Required username");
                    //disableButton(...)
                } else {
                    if (login_username.getText().toString().trim().length() <= 0) {
                        email_layout_input.setError("Required email");
                    } else {
                        if (login_username.getText().toString().trim().matches(Validation.emailPattern)) {
                            email_layout_input.setError("");
                        } else {
                            email_layout_input.setError("Required email");
                        }

                    }
                    //Toast.makeText(getApplicationContext(), " allowed", Toast.LENGTH_LONG).show();
                    //enableButton(...)
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login_password.setOnTouchListener((v, event) -> {
            if (login_username.getText().toString().trim().length() <= 0) {
                email_layout_input.setError("Required password");
            } else {
                if (login_username.getText().toString().trim().matches(Validation.emailPattern)) {
                    email_layout_input.setError("");
                } else {
                    email_layout_input.setError("Required email");
                }
            }
            return false;
        });

        login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().startsWith(" ")) {
                    login_password.setText("");
                    Toast.makeText(getApplicationContext(), "Space Not allowed", Toast.LENGTH_LONG).show();
                    password_layout_input.setError("Required password");

                } else {
                    password_layout_input.setError("");
                    if (login_username.getText().toString().trim().length() <= 0) {
                        email_layout_input.setError("Required email");
                    } else {
                        if (login_username.getText().toString().trim().matches(Validation.emailPattern)) {
                            email_layout_input.setError("");
                        } else {
                            email_layout_input.setError("Required email");
                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwordvalidation = s.toString();
                if (passwordvalidation.length() >= 8 && passwordvalidation.matches(Validation.PASSWORD_PATTERN)) {
                    password_layout_input.setError("");
                } else {
                    password_layout_input.setError("Password is invalid");
                }
            }
        });

        login_btn.setOnClickListener(v -> {
            if (!isEmpty(login_username) && !isEmpty(login_password)) {
                if (login_username.getText().toString().trim().matches(Validation.emailPattern)) {
                    if (login_password.getText().toString().trim().matches(Validation.PASSWORD_PATTERN) && login_password.getText().toString().trim().length() >= 8) {
                        if (isOnline()) {

                            LoginAPI();

                        } else {
                            //Toast.makeText(LoginActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                            // ConstantFunctions.showSnakBar("Please check internet connection", v);
                            ConstantFunctions.showSnackbar(login_username, "Please check internet connection", LoginActivity.this);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(LoginActivity.this, "Enter correct mail", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(LoginActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
            }

        });
        register_btn_tv.setOnClickListener(v -> {
            ChangeActivity.changeActivity(LoginActivity.this, RegisterActivity.class);
            finish();
        });
        back_icon_login_img.setOnClickListener(v -> onBackPressed());
        forgot_btn_tv.setOnClickListener(v -> ChangeActivity.changeActivity(LoginActivity.this, ForgotActivity.class));
        google_sign_btn.setOnClickListener(v -> {
            if (isOnline()) {

                signIn();

            } else {
                //Toast.makeText(LoginActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                ConstantFunctions.showSnakBar("Please check internet connection", v);
            }

        });

        facebook_login.setOnClickListener(v -> {
            if (isOnline()) {

                facebook_login_btn.performClick();
                Log.e("click", "click243");

            } else {
                //Toast.makeText(LoginActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                ConstantFunctions.showSnakBar("Please check internet connection", v);
            }

        });

        twitter_login.setOnClickListener(v -> {

            if (isOnline()) {
                defaultLoginTwitter();
                Log.e("click-511", "click243");

            } else {
                //Toast.makeText(LoginActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                ConstantFunctions.showSnakBar("Please check internet connection", v);
            }

        });


    }

    private void defaultLoginTwitter() {
        //check if user is already authenticated or not

        Log.e("527", "51111111");
//        if (getTwitterSession() == null) {
        Log.e(TAG, "getTwitterSession is null");

        //if user is not authenticated start authenticating
        twitter_login_btn.setCallback(new Callback<TwitterSession>() {


            @Override
            public void success(Result<TwitterSession> result) {

                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession twitterSession = result.data;
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();

                Log.e("543", "Success : " + result.data);
                Log.e("543", "Success : " + session);
                Log.e("543", "Success : " + session.getUserName());
                Log.e("543", "Success : " + authToken);

//                    User user = session.get();
//                    String imageProfileUrl = user.profileImageUrl;
//                    Log.e(TAG, "Data : " + imageProfileUrl);
//                    if(user.email == null){
                gmailfacebookloginAPI(session.getUserName(), "", "twitter", "");
//                    } else gmailfacebookloginAPI(user.name, user.email, "twitter", user.profileImageUrl);

                //call fetch email only when permission is granted
//                    fetchTwitterEmail(twitterSession);

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(LoginActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
        twitter_login_btn.performClick();
//        } else {

        //if user is already authenticated direct call fetch twitter email api
//            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
//            fetchTwitterEmail(getTwitterSession());
//        }

    }
    /*public void customLoginTwitter(View view) {
        //check if user is already authenticated or not
        if (getTwitterSession() == null) {

            //if user is not authenticated start authenticating
            client.authorize(this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                    TwitterSession twitterSession = result.data;

                    //call fetch email only when permission is granted
                    fetchTwitterEmail(twitterSession);
                }

                @Override
                public void failure(TwitterException e) {
                    // Do something on failure
                    Toast.makeText(LoginActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
            fetchTwitterEmail(getTwitterSession());
        }
    }
*/

    /**
     * Before using this feature, ensure that “Request email addresses from users” is checked for your Twitter app.
     *
     * @param twitterSession user logged in twitter session
     */
    public void fetchTwitterEmail(final TwitterSession twitterSession) {
        client.requestEmail(twitterSession, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                //here it will give u only email and rest of other information u can get from TwitterSession
//                Log.e(TAG, result.data);
                /*Toast.makeText(LoginActivity.this, "User Id : " + twitterSession.getUserId() + "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result.data, Toast.LENGTH_SHORT).show();
                Log.e(TAG,"User Id : " + twitterSession.getUserId()+ "\nUser Name : " + twitterSession.getUserName() + "\nEmail Id : " +  result.data);*/
                fetchTwitterImage();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("609", "" + exception.getMessage());
                Toast.makeText(LoginActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * call Verify Credentials API when Twitter Auth is successful else it will go in exception block
     * this metod will provide you User model which contain all user information
     */
    public void fetchTwitterImage() {
        //check if user is already authenticated or not
        if (getTwitterSession() != null) {

            //fetch twitter image with other information if user is already authenticated

            //initialize twitter api client
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

            //Link for Help : https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/get-account-verify_credentials

            //pass includeEmail : true if you want to fetch Email as well
            Call<User> call = twitterApiClient.getAccountService().verifyCredentials(true, false, true);
            call.enqueue(new Callback<User>() {
                @Override
                public void success(Result<User> result) {
                    User user = result.data;
                    Log.e(TAG, "User Id : " + user.id + "\nUser Name : " + user.name + "\nEmail Id : " + user.email + "\nScreen Name : " + user.screenName);

                    String imageProfileUrl = user.profileImageUrl;
                    Log.e(TAG, "Data : " + imageProfileUrl);
                    if (user.email == null) {
                        gmailfacebookloginAPI(user.name, "", "twitter", user.profileImageUrl);
                    } else
                        gmailfacebookloginAPI(user.name, user.email, "twitter", user.profileImageUrl);
                    //NOTE : User profile provided by twitter is very small in size i.e 48*48
                    //Link : https://developer.twitter.com/en/docs/accounts-and-users/user-profile-images-and-banners
                    //so if you want to get bigger size image then do the following:
                    imageProfileUrl = imageProfileUrl.replace("_normal", "");

                    ///load image using Picasso
                   /* Picasso.with(MainActivity.this)
                            .load(imageProfileUrl)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .into(userProfileImageView);*/
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(LoginActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is not authenticated first ask user to do authentication
            Toast.makeText(this, "First to Twitter auth to Verify Credentials.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * get authenticates user session
     *
     * @return twitter session
     */
    private TwitterSession getTwitterSession() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        //NOTE : if you want to get token and secret too use uncomment the below code
        /* TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret; */
        Log.e("679", "" + session);
        return session;
    }

    public void generateHashkey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(PACKAGE, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                //___________set package name
                //tvPackage.setText(PACKAGE);

                //___________set keyhash
                //tvKeyHash.setText(Base64.encodeToString(md.digest(), Base64.NO_WRAP));

                //___________add package name and key hash into log
                Log.e(TAG, "_____________________________________________");
                Log.e("Package", PACKAGE);
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                Log.e(TAG, "_____________________________________________");

                Toast.makeText(this, "Check your logcate for keyhash", Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    public static String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void LoginAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String user_name = login_username.getText().toString().trim();
        String user_password = login_password.getText().toString().trim();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("email", user_name);
        jsonObject1.addProperty("password", user_password);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(LoginActivity.this));
        Log.e("jsonObject1", "" + jsonObject1);
        /*   ApiInterface jsonPostService = ApiClient.createService(ApiInterface.class, "http://project975.website/i2-donate/api/");*/
        final String image_url = "";
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.userLogin(jsonObject1);
            call.enqueue(new retrofit2.Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    Log.e(TAG, "123 LoginAPI = " + response);

                    progressDialog.dismiss();
                    Log.e(TAG, "" + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        Log.e(TAG, "123" + jsonObject);
                        String message = jsonObject.getString("message");
                        Log.e(TAG, "" + message);
                        if (jsonObject.getString("status").equals("1")) {
                            String data = jsonObject.getString("data");
                            Log.e("data232", "" + data);
                            JSONObject jsonObject1 = new JSONObject(data);
                            Log.e("data232", "" + jsonObject1);
                            user_id = Integer.parseInt(jsonObject1.getString("user_id"));
                            sessionManager.createLoginSession(jsonObject1.getString("user_id"), jsonObject1.getString("email"), jsonObject1.getString("name"), jsonObject1.getString("phone_number"), jsonObject1.getString("photo"), jsonObject1.getString("token"), jsonObject1.getString("business_name"), jsonObject1.getString("country"), jsonObject1.getString("gender"), jsonObject1.getString("type"));
                            iDonateSharedPreference.setprofiledata(getApplicationContext(), data);
                            Log.e("updatedata", "" + iDonateSharedPreference.getprofiledata(getApplicationContext()));
                            iDonateSharedPreference.setSocialProfileimg(getApplicationContext(), image_url);
                            iDonateSharedPreference.setlogintype(getApplicationContext(), "registerlogin");
                            //ChangeActivity.clearAllPreviousActivity(LoginActivity.this, BrowseActivity.class);
                            sentdevicetoken("login");
                            // iDonateSharedPreference.seteditprofile(getApplicationContext(), "0");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else if (jsonObject.getString("status").equals("0")) {
                            // Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            ConstantFunctions.showSnackbar(login_username, message, LoginActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    Log.e("error ", t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + e);
        }


    }

    private void GetKeyHash() {

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyhash = new String(Base64.encode(md.digest(), 0));
                // String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("keyhash", "keyhash= " + keyhash);
                System.out.println("keyhash= " + keyhash);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("827", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("acct", "" + acct);
            Log.e(TAG, "display name: " + acct.getDisplayName());
            try {
                String personName = acct.getDisplayName();
                String personPhotoUrl = "";
                try {
                    personPhotoUrl = acct.getPhotoUrl().toString();
                } catch (Exception e) {
                    e.printStackTrace();

                }

                String email = acct.getEmail();


                Log.e(TAG, "Name: " + personName + ", email: " + email
                        + ", Image: " + "" + personPhotoUrl);
                String socialmedia = "email";
                gmailfacebookloginAPI(personName, email, socialmedia, personPhotoUrl);


            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "handleSignInResult: "+e.getMessage() );
            }


        } else {

            // Signed out, show unauthenticated UI.

        }
    }

    private void getToken() {

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("token", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        device_token = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("device_token", "" + device_token);
                        //  Toast.makeText(SplashActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sentdevicetoken(final String type) {
        int status = iDonateSharedPreference.getNotificationstatus(getApplicationContext());
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("device_id", device_token);
        jsonObject1.addProperty("enable_notification", status);
        Log.e("jsonObject1", "" + jsonObject1);
        /*   ApiInterface jsonPostService = ApiClient.createService(ApiInterface.class, "http://project975.website/i2-donate/api/");*/
        final String image_url = "";
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.device_update(jsonObject1);
            call.enqueue(new retrofit2.Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    Log.e(TAG, "" + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        String data = jsonObject.getString("data");
                        iDonateSharedPreference.settoken(getApplicationContext(), device_token);
                        if (status.equalsIgnoreCase("1")) {
                            final JSONObject jsonObject2 = new JSONObject(data);
                            if (type.equalsIgnoreCase("social")) {
                                ChangeActivity.changeActivity(LoginActivity.this, UpdateActivity.class);
                                finish();
                            } else {
                                finish();
                            }


                        } else {
                            // ConstantFunctions.showSnackbar(reg_email_et,message,ForgotActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + e);
        }
    }

    private void gmailfacebookloginAPI(final String name, final String personEmail, final String socialmedia, final String image_url) {
        Log.e("socialmedia", "" + image_url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("name", name);
        jsonObject1.addProperty("email", personEmail);
        jsonObject1.addProperty("login_type", socialmedia);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(LoginActivity.this));
        jsonObject1.addProperty("photo", image_url);
        jsonObject1.addProperty("terms", "");

        Log.e("jsonObject1", "" + jsonObject1);
        /*   ApiInterface jsonPostService = ApiClient.createService(ApiInterface.class, "http://project975.website/i2-donate/api/");*/
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<JsonObject> call = apiService.socialmedialogin(jsonObject1);
            call.enqueue(new retrofit2.Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    Log.e(TAG, "123 gmailfacebookloginAPI = " + response);

                    progressDialog.dismiss();

                    Log.e(TAG, "" + response.body());
                    Log.e("response", "" + response);
                    iDonateSharedPreference.setsocialmedia(getApplicationContext(), socialmedia);
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        String message = jsonObject.getString("message");
                        Log.e("response", "" + message);
                        if (jsonObject.getString("status").equals("1")) {
                            Log.e("socialmedia11", "" + socialmedia);
                            iDonateSharedPreference.setName(getApplicationContext(), name);
                            iDonateSharedPreference.setMail(getApplicationContext(), personEmail);
                            iDonateSharedPreference.setsocialmedia(getApplicationContext(), socialmedia);
                            iDonateSharedPreference.setSocialProfileimg(getApplicationContext(), image_url);
                            String data = jsonObject.getString("data");
                            Log.e("data232", "" + data);
                            JSONObject jsonObject1 = new JSONObject(data);
                            Log.e("data232", "" + jsonObject1);
                            user_id = Integer.parseInt(jsonObject1.getString("user_id"));
                            iDonateSharedPreference.seteditprofile(getApplicationContext(), "2");
                            iDonateSharedPreference.setprofiledata(getApplicationContext(), data);
                            iDonateSharedPreference.setlogintype(getApplicationContext(), "sociallogin");
                            sessionManager.createLoginSession(jsonObject1.getString("user_id"), jsonObject1.getString("email"), jsonObject1.getString("name"), jsonObject1.getString("phone_number"), jsonObject1.getString("photo"), jsonObject1.getString("token"), jsonObject1.getString("business_name"), jsonObject1.getString("country"), jsonObject1.getString("gender"), jsonObject1.getString("type"));
                            Log.e("response_name", "" + iDonateSharedPreference.getName(getApplicationContext()));
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            sentdevicetoken("social");
                            // iDonateSharedPreference.seteditprofile(getApplicationContext(), "0");
                          /*  ChangeActivity.changeActivity(LoginActivity.this, UpdateActivity.class);
                            finish();*/
                        } else if (jsonObject.getString("status").equals("0")) {
                            // Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            ConstantFunctions.showSnackbar(login_username, message, LoginActivity.this);
                            if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("email")) {
                                Log.e("data232email", "" + "email");
                                signOut();
                                // Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("facebook")) {
                                Log.e("data232facebook", "" + "facebook");
                                FacebookSdk.sdkInitialize(getApplicationContext());
                                LoginManager.getInstance().logOut();

                            }

                        }
                    } catch (JSONException e) {
                        Log.e("990", "error" + e.getMessage());

                    }

                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    Log.e("999", "error" + t.toString());
                    if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("email")) {
                        Log.e("data232email", "" + "email");
                        signOut();
                        // Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (iDonateSharedPreference.getsocialMedia(getApplicationContext()).equalsIgnoreCase("facebook")) {
                        Log.e("data232facebook", "" + "facebook");
                        FacebookSdk.sdkInitialize(getApplicationContext());
                        LoginManager.getInstance().logOut();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + e);
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                status -> {
                    Log.e("logout", "logout");
                    ChangeActivity.changeActivity(LoginActivity.this, LoginActivity.class);
                    finish();
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Log.e("1055", "" + result);
            Log.e(TAG, "Got cached sign-in1");
        }

        // Pass the activity result to the twitterAuthClient.
        if (client != null)
            client.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitter_login_btn.onActivityResult(requestCode, resultCode, data);
    }


    /*@Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
            Log.e(TAG, "Got cached sign-in1");
        } else {

            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                    Log.e(TAG, "Got cached sign-in1");

                }
            });
        }
    }*/
    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, (object, response) -> {
                    Log.e("objectresponse", "" + object.toString());
                    try {
                        String personName = object.getString("first_name");
                        String last_name = object.getString("last_name");
                        String email = "";
//                            if(object.getString("email") != "") {
//                                email = object.getString("email");
//                            }

                        String id = object.getString("id");
                        String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                        Log.e("image_url", "" + image_url);
                        Log.e("first_name", "" + personName);
                        String socialname = "facebook";
                        gmailfacebookloginAPI(personName, email, socialname, image_url);

                        //   txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                        //  txtEmail.setText(email);
                        // Picasso.with(MainActivity.this).load(image_url).into(imageView);

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, "Please give email permission", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {

            mProgressDialog.setMessage(getString(R.string.login_loading));
            // mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    boolean isEmpty(EditText text) {
        CharSequence charSequence = text.getText().toString();
        return TextUtils.isEmpty(charSequence);
    }

    @Override
    public void onBackPressed() {
        iDonateSharedPreference.setlogintype(getApplicationContext(), "non");
        Intent i = new Intent(LoginActivity.this, BrowseActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.putExtra("IsBack", true);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();

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
