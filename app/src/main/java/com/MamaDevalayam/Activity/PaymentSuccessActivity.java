package com.MamaDevalayam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.google.gson.JsonObject;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;
import com.MamaDevalayam.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentSuccessActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    String TAG = "PaymentSuccessActivity";
    TextView amount_tv, name_tv, payId, payStatus;
    IDonateSharedPreference iDonateSharedPreference;
    String charityId;
    static ApiInterface apiService;
    static SessionManager session;
    static HashMap<String, String> userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        init();
    }

    private void init() {
        iDonateSharedPreference = new IDonateSharedPreference();
        amount_tv = (TextView) findViewById(R.id.amount_tv);
        name_tv = (TextView) findViewById(R.id.charity_name);
        payId = (TextView) findViewById(R.id.payIdTextView);
        payStatus = (TextView) findViewById(R.id.payStatusTextView);
        Intent intent = getIntent();
        session = new SessionManager(getApplicationContext());

        try {
           // JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            //Displaying payment details
            showDetails( intent.getStringExtra("PaymentAmount"), intent.getStringExtra("PaymentName"), intent.getStringExtra("message"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // if (new Session(SplashActivity.this, TAG).getIsLogin())
                //       startActivity(new Intent(SplashActivity.this, ChooseTypeActivity.class));
                //   else
                //       startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                // ChangeActivity.changeActivity(PaymentSuccessActivity.this, WelcomeActivity.class);
                Intent intent = new Intent(PaymentSuccessActivity.this, BrowseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                iDonateSharedPreference.setdailoguepage(getApplicationContext(),"0");
                finish();
            }
        }, 5000);

    }
    private void showDetails(String paymentAmount, String paymentName, String status) throws JSONException {

      //  payId.setText(jsonDetails.getString("id"));
        payStatus.setText(status);
        amount_tv.setText("$ " + paymentAmount);
        name_tv.setText(paymentName);
        //charityId = charity_id;
        callWebservice();

    }

    private void callWebservice(){
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
        jsonObject1.addProperty("charity_id", charityId);
        jsonObject1.addProperty("charity_name", name_tv.getText().toString());
        jsonObject1.addProperty("transaction_id", payId.getText().toString());

        jsonObject1.addProperty("status" , payStatus.getText().toString());

//        Double payment_amt_total= Double.parseDouble(amount_tv.getText().toString());
        Double payment_amt_total= Double.parseDouble("1");
        Double payment_amt_total1= (payment_amt_total - 0.30);
        Double payment_amt_total2= (payment_amt_total1 / ((100.0f + 2.9 ) / 100));
        Double merchant_charges= payment_amt_total1 - payment_amt_total2;
        Double op= (payment_amt_total2 / ((100.0f + 1 ) / 100));
        Double processing_fee = payment_amt_total2 - op;

        Log.e("d_payment_amt_total",""+payment_amt_total);
        Log.e("d_payment_amt_total1",""+payment_amt_total1);
        Log.e("d_payment_amt_total2",""+ String.format("%.2f", merchant_charges));
        Log.e("d_payment_amt_total3",""+((100.0f + 2.9 ) / 100));


        jsonObject1.addProperty("amount" , String.format("%.2f", op));
        jsonObject1.addProperty("merchant_charges" , String.format("%.2f", merchant_charges));
        jsonObject1.addProperty("processing_fee" , String.format("%.2f", processing_fee));

        Log.e("jsonObject1", "" + jsonObject1);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiService.updatePayment(jsonObject1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    Log.e(TAG, response.body().toString());
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String message = jsonObject.getString("message");
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        Log.e(TAG, "The data : "+data);
                    }
                } catch (Exception e) {
                    e.getMessage();
                    Log.e("145", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }
}