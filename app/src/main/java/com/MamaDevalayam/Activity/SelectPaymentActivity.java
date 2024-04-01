package com.MamaDevalayam.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.UserCanceledException;
import com.google.gson.JsonObject;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPaymentActivity extends AppCompatActivity implements DropInListener {

    EditText selected_payment_et, cvv_et;
    Button payment_btn;
    String payment_amt;
    String payment_amount;
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    static ApiInterface apiService;
    static SessionManager session;
    static HashMap<String, String> userDetails;
    ImageView back_icon_payment_img;
    IDonateSharedPreference iDonateSharedPreference;
    private String paymentAmount, charityName, charityId;
    Button payment_to_btn;
    String clientToken = "";
    ProgressDialog progressDialog;
    DropInRequest dropInRequest;
    private DropInClient dropInClient;
    String TAG = SelectPaymentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        listener();
    }

    private void init() {

        iDonateSharedPreference = new IDonateSharedPreference();
        selected_payment_et = (EditText) findViewById(R.id.selected_payment_et);
        back_icon_payment_img = (ImageView) findViewById(R.id.back_icon_payment_img);
        payment_btn = (Button) findViewById(R.id.payment_btn);
        payment_to_btn = (Button) findViewById(R.id.payment_to_btn);
        cvv_et = (EditText) findViewById(R.id.cvv_et);
        Bundle bundle = getIntent().getExtras();
        payment_amount = bundle.getString("payment_amt");
        clientToken = bundle.getString("cToken");
        Double amount = Double.valueOf(payment_amount);
        double processing_fee = ((amount / 100.0f) * 1);
        double total_amt = processing_fee + amount;
        double percentage = ((total_amt / 100.0f) * 2.9) + 0.30;

        Double payment_amt_total = amount + percentage + processing_fee;


        payment_amt = String.format("%.2f", payment_amt_total);
        Log.e(TAG, "amount - " + amount);
        Log.e(TAG, "processing_fee - " + processing_fee);
        Log.e(TAG, "total_amt - " + total_amt);
        Log.e(TAG, "payment_amt - " + payment_amt);
        Log.e(TAG, "cToken - " + clientToken);


        charityName = bundle.getString("charity_name");
        charityId = bundle.getString("charity_id");
        charityName = iDonateSharedPreference.getcharity_name(getApplicationContext());
        charityId = iDonateSharedPreference.getcharity_id(getApplicationContext());
        Log.e("Select_charityId", charityName);
        Log.e("Select_charityId", charityId);
        selected_payment_et.setText(String.format(" %.2f", payment_amt_total));
//        callWebservice();


        dropInClient = new DropInClient(this, clientToken); // A 240523
        dropInClient.setListener(this);
        dropInRequest = new DropInRequest();

        progressDialog = ProgressDialog.show(this, "", "Please wait.", true);
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
                dropInClient.launchDropIn(dropInRequest); // A 240523
            }
        }, 2000);
    }

    private void listener() {
        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cvv_et.getText().toString().trim().isEmpty()) {
                    Intent intent = new Intent(SelectPaymentActivity.this, PaymentSuccessActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    String payment_amt1 = selected_payment_et.getText().toString().trim();
                    bundle.putString("payment_amt", payment_amt1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SelectPaymentActivity.this, "Enter Cvv number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        payment_to_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBraintreeSubmit();
            }
        });

        back_icon_payment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iDonateSharedPreference.setdailoguepage(getApplicationContext(), "1");
                String page = iDonateSharedPreference.getdailoguepage(getApplicationContext());
                Log.e("pageback12", "page" + " " + page);
                onBackPressed();
            }
        });
    }

    private void callWebservice() {
        progressDialog = new ProgressDialog(SelectPaymentActivity.this);
        progressDialog.show();
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<String> call = apiService.getbraintree();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        Log.e("Response_payment1", response.body().toString());
                        clientToken = response.body().toString();
                        Log.e("TAG", "clientToken: " + clientToken);
                        onBraintreeSubmit();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Response_error", t.toString());
            }
        });

    }

    public static String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void sendPaymentNonceToServer(String paymentNonce) {
        session = new SessionManager(getApplicationContext());
        progressDialog = new ProgressDialog(SelectPaymentActivity.this);
        progressDialog.show();
        userDetails = session.getUserDetails();
        Log.e("userDetails", "" + userDetails);
        Log.e("KEY_UID", "" + userDetails.get(SessionManager.KEY_UID));
        String user_id = "";
        //todo login
        if (session.isLoggedIn()) {
            user_id = userDetails.get(SessionManager.KEY_UID);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("user_id", user_id);
        jsonObject1.addProperty("token", clientToken);
        jsonObject1.addProperty("charity_id", charityId);
        jsonObject1.addProperty("charity_name", charityName);
        jsonObject1.addProperty("transaction_id", paymentNonce);
//        jsonObject1.addProperty("amount",payment_amt);
        jsonObject1.addProperty("device_id", getDeviceUniqueID(SelectPaymentActivity.this));
        jsonObject1.addProperty("status", "approved");

        Double payment_amt_total = Double.parseDouble(payment_amt);
        Double payment_amt_total1 = (payment_amt_total - 0.30);
        Double payment_amt_total2 = (payment_amt_total1 / ((100.0f + 2.9) / 100));
        Double merchant_charges = payment_amt_total1 - payment_amt_total2;
        Double op = (payment_amt_total2 / ((100.0f + 1) / 100));
        Double processing_fee = payment_amt_total2 - op;

        Log.e("d_payment_amt_total", "" + payment_amt_total);
        Log.e("d_payment_amt_total1", "" + payment_amt_total1);
        Log.e("d_payment_amt_total2", "" + String.format("%.2f", merchant_charges));
        Log.e("d_payment_amt_total3", "" + ((100.0f + 2.9) / 100));


        jsonObject1.addProperty("amount", String.format("%.2f", op));
        jsonObject1.addProperty("merchant_charges", String.format("%.2f", (merchant_charges + 0.30)));
        jsonObject1.addProperty("processing_fee", String.format("%.2f", processing_fee));


        Log.e(TAG, "jsonObject1 -- > " + jsonObject1);
        Call<JsonObject> call = apiService.sentamountbraintreeAPI(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    Log.e("Response_payment1", response.body().toString());
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String message = jsonObject.getString("message");
                    JSONObject jsonobjectdata = jsonObject.getJSONObject("data");
                    String PaymentName = jsonobjectdata.getString("i2D_D_CHARITY_NAME");
                    String PaymentAmount = jsonobjectdata.getString("i2D_D_DONATION_AMOUNT");
                    // String i2D_D_CHARITY_NAME=jsonobjectdata.getString("i2D_D_CHARITY_NAME");
                    // String i2D_D_AMOUNT=jsonobjectdata.getString("i2D_D_AMOUNT");
                    startActivity(new Intent(SelectPaymentActivity.this, PaymentSuccessActivity.class)
                            .putExtra("message", message)
                            .putExtra("PaymentAmount", "" + PaymentAmount)
                            .putExtra("PaymentName", PaymentName));
                    finish();
                } catch (Exception e) {
                    e.getMessage();
                    progressDialog.dismiss();
                    Log.e("258 SelectPAyment", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Response_error", t.toString());
            }
        });

    }

    public void onBraintreeSubmit() {

        dropInClient.launchDropIn(dropInRequest);


      /*  DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);*/
    }

    /* @SuppressLint("MissingSuperCall")
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode == BRAINTREE_REQUEST_CODE){
             if (RESULT_OK == resultCode){
                 DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                 String paymentNonce = result.getPaymentMethodNonce().getNonce();
                 //send to your server
                 Log.e("test", "Testing the app here"+paymentNonce);
                 sendPaymentNonceToServer(paymentNonce);
             }else if(resultCode == Activity.RESULT_CANCELED){
                 Log.e("error", "User cancelled payment");
             }else {
                 Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                 Log.e("error", " error exception"+error.toString());
             }
         }
     }*/
   /* private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
           //.environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

    private void getPayment() {
        //Getting the amount from editText
        paymentAmount = payment_amt;

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Total Amount",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        iDonateSharedPreference.setdailoguepage(getApplicationContext(), "1");
        finish();
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.e("paymentExample", ""+paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, PaymentSuccessActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount)
                                .putExtra("PaymentName",charityName)
                                .putExtra("CharityId", charityId));
                        finish();

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }*/

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
        String paymentMethodNonce = dropInResult.getPaymentMethodNonce().getString();
        paybypaypal(paymentMethodNonce, payment_amt + "");
        Log.e(TAG, " paymentMethodNonce -> " + paymentMethodNonce);
        sendPaymentNonceToServer(paymentMethodNonce);
    }

    @Override
    public void onDropInFailure(@NonNull Exception error) {
        if (error instanceof UserCanceledException) {
            // the user canceled
            Toast.makeText(this, "Cancel by user", Toast.LENGTH_SHORT).show();
        } else {
            // handle error
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void paybypaypal(@Nullable String nonce, @Nullable String amount) {
        final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
       /* final String json = "{"
                + "\"nonce\":true,"
                + "\"PaymentMethodNonce\":"  + "\"" + nonce + "\","
                + "\"amount\":" + "\"" + amount + "\","
                + "}";
        progressDialog = ProgressDialog.show(this, "", "Please wait.", true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "api/Paypal/Checkout")
                .post(body)
                .build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        ArrayList<Protocol> protocolList = new ArrayList<>();
        protocolList.add(Protocol.HTTP_2);
        protocolList.add(Protocol.HTTP_1_1);
        ConnectionPool connectionPool = new ConnectionPool(3, 5 * 60 * 1000, TimeUnit.MILLISECONDS);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(connectionPool);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        //builder.protocols(protocolList);
        builder.protocols(Arrays.asList(Protocol.HTTP_1_1));
        builder.addInterceptor(interceptor);


        builder.build().newCall(request)
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            String str = response.body().toString();
                            Log.e("response", str);
                            Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                            intent.putExtra("token", nonce);
                            //id is used to set that the payment is done via payment
                            intent.putExtra("id", "1");
                            setResult(RESULT_OK, intent);
//                            PaymentActivity.this.startActivity(intent);
                            PaymentActivity.this.finish();
                        }
                    }
                });*/
    }

}