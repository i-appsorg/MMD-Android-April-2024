package com.MamaDevalayam;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.MamaDevalayam.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //todo main screen
    private static final String TAG = MainActivity.class.getSimpleName();

    //    private static final String PATH_TO_SERVER = "https://admin.i2-donate.com/webservice/braintree_client_token";
    private static final String PATH_TO_SERVER = "https://imaginetventures.org/MamaDevalayam/webservice/braintree_client_token";
    static ApiInterface apiService;
    private String clientToken = ""; // A 240523
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    DropInClient dropInClient; // A 240523

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        callWebservice();
        Button buyNowButton = (Button) findViewById(R.id.buy_now);
        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clientToken.length() > 0) // A 240523
                    onBraintreeSubmit(clientToken);
                else // A 240523
                    Toast.makeText(MainActivity.this, "No Token to make payments", Toast.LENGTH_LONG).show(); // A 240523
            }
        });
    }

    private void callWebservice() {

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<String> call = apiService.getbraintree();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.e("Response_payment1", response.body().toString());
                    clientToken = response.body().toString();
                    onBraintreeSubmit(clientToken);
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Response_error", t.toString());
            }
        });
    }

    public void onBraintreeSubmit(String clientToken) {
//        DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
        //        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);

        DropInRequest dropInRequest = new DropInRequest(); // A 240523
        dropInClient = new DropInClient(this, "sandbox_38p29vxg_m7bfbztpgz6yfyk7"); // A 240523
        dropInClient.launchDropIn(dropInRequest); // A 240523
        dropInClient.setListener(new DropInListener() { // A 240523
            @Override
            public void onDropInSuccess(@NonNull DropInResult dropInResult) {
                String paymentNonce = Objects.requireNonNull(dropInResult.getPaymentMethodNonce()).toString();
            }

            @Override
            public void onDropInFailure(@NonNull Exception error) {
                Log.d(TAG, " error exception - " + error.getMessage());
            }
        });
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BRAINTREE_REQUEST_CODE) {
            if (RESULT_OK == resultCode) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                //send to your server
                Log.d(TAG, "Testing the app here");
                //sendPaymentNonceToServer(paymentNonce);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "User cancelled payment");
            } else {
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//                Log.d(TAG, " error exception");
            }
        }
    }


}