package com.MamaDevalayam.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.RetrofitAPI.ApiInterface;
import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.PayPalAccountNonce;
import com.braintreepayments.api.PayPalCheckoutRequest;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.PayPalListener;
import com.braintreepayments.api.PayPalPaymentIntent;
import com.MamaDevalayam.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrainTreePaymentActivity extends AppCompatActivity {
    final int REQUEST_CODE = 4949;
    static ApiInterface apiService;
    ProgressDialog progressDialog;
    //    BraintreeFragment mBraintreeFragment;
//    String token = "eyJ2ZXJzaW9uIjoyLCJlbnZpcm9ubWVudCI6InNhbmRib3giLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpGVXpJMU5pSXNJbXRwWkNJNklqSXdNVGd3TkRJMk1UWXRjMkZ1WkdKdmVDSXNJbWx6Y3lJNkltaDBkSEJ6T2k4dllYQnBMbk5oYm1SaWIzZ3VZbkpoYVc1MGNtVmxaMkYwWlhkaGVTNWpiMjBpZlEuZXlKbGVIQWlPakUxT1RZd09EVTRORGNzSW1wMGFTSTZJbVJoWkRkbVkySTFMVFkxWkdJdE5EVmtaQzFoTWpZMkxUQTVabVJsT1Rrd01UTm1ZU0lzSW5OMVlpSTZJbkU1WWpjemVXTnRPVGh0Y0hkb1pEZ2lMQ0pwYzNNaU9pSm9kSFJ3Y3pvdkwyRndhUzV6WVc1a1ltOTRMbUp5WVdsdWRISmxaV2RoZEdWM1lYa3VZMjl0SWl3aWJXVnlZMmhoYm5RaU9uc2ljSFZpYkdsalgybGtJam9pY1RsaU56TjVZMjA1T0cxd2QyaGtPQ0lzSW5abGNtbG1lVjlqWVhKa1gySjVYMlJsWm1GMWJIUWlPbVpoYkhObGZTd2ljbWxuYUhSeklqcGJJbTFoYm1GblpWOTJZWFZzZENKZExDSnpZMjl3WlNJNld5SkNjbUZwYm5SeVpXVTZWbUYxYkhRaVhTd2liM0IwYVc5dWN5STZlMzE5LmtSTk81OEVGQkRPUElhSjUycHc4RVA4bHZUWUdpN2d1OHYzaUZCZlhkRklsb1pDNjNMRU9KU29qUmpkbGw1VUliOXpTa29mUjVPZWV0QllUZE0tUnlBIiwiY29uZmlnVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzL3E5YjczeWNtOThtcHdoZDgvY2xpZW50X2FwaS92MS9jb25maWd1cmF0aW9uIiwiZ3JhcGhRTCI6eyJ1cmwiOiJodHRwczovL3BheW1lbnRzLnNhbmRib3guYnJhaW50cmVlLWFwaS5jb20vZ3JhcGhxbCIsImRhdGUiOiIyMDE4LTA1LTA4In0sImNoYWxsZW5nZXMiOltdLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvcTliNzN5Y205OG1wd2hkOC9jbGllbnRfYXBpIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhdXRoVXJsIjoiaHR0cHM6Ly9hdXRoLnZlbm1vLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9vcmlnaW4tYW5hbHl0aWNzLXNhbmQuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9xOWI3M3ljbTk4bXB3aGQ4In0sInRocmVlRFNlY3VyZUVuYWJsZWQiOnRydWUsInBheXBhbEVuYWJsZWQiOnRydWUsInBheXBhbCI6eyJkaXNwbGF5TmFtZSI6IkRvbmF0ZSIsImNsaWVudElkIjoiQWU3LTQwbW5pSUNtcVpRRVBPeEhfVGhBWGx4RTlDenFWYXBhNnBkR1dwOUhickVMdVNlWVN0dlpaSllnM1k5NXFseFIzRExBdG95LVpib3AiLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjpmYWxzZSwiZW52aXJvbm1lbnQiOiJvZmZsaW5lIiwidW52ZXR0ZWRNZXJjaGFudCI6ZmFsc2UsImJyYWludHJlZUNsaWVudElkIjoibWFzdGVyY2xpZW50MyIsImJpbGxpbmdBZ3JlZW1lbnRzRW5hYmxlZCI6dHJ1ZSwibWVyY2hhbnRBY2NvdW50SWQiOiJkb25hdGUiLCJjdXJyZW5jeUlzb0NvZGUiOiJVU0QifSwibWVyY2hhbnRJZCI6InE5YjczeWNtOThtcHdoZDgiLCJ2ZW5tbyI6Im9mZiJ9";
    DropInClient dropInClient;
    String TAG = BrainTreePaymentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_tree_payment);
        callWebservice();
//        try {
//            mBraintreeFragment = BraintreeFragment.newInstance(this, "eyJ2ZXJzaW9uIjoyLCJlbnZpcm9ubWVudCI6InNhbmRib3giLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpGVXpJMU5pSXNJbXRwWkNJNklqSXdNVGd3TkRJMk1UWXRjMkZ1WkdKdmVDSXNJbWx6Y3lJNkltaDBkSEJ6T2k4dllYQnBMbk5oYm1SaWIzZ3VZbkpoYVc1MGNtVmxaMkYwWlhkaGVTNWpiMjBpZlEuZXlKbGVIQWlPakUxT1RZd09EVTRORGNzSW1wMGFTSTZJbVJoWkRkbVkySTFMVFkxWkdJdE5EVmtaQzFoTWpZMkxUQTVabVJsT1Rrd01UTm1ZU0lzSW5OMVlpSTZJbkU1WWpjemVXTnRPVGh0Y0hkb1pEZ2lMQ0pwYzNNaU9pSm9kSFJ3Y3pvdkwyRndhUzV6WVc1a1ltOTRMbUp5WVdsdWRISmxaV2RoZEdWM1lYa3VZMjl0SWl3aWJXVnlZMmhoYm5RaU9uc2ljSFZpYkdsalgybGtJam9pY1RsaU56TjVZMjA1T0cxd2QyaGtPQ0lzSW5abGNtbG1lVjlqWVhKa1gySjVYMlJsWm1GMWJIUWlPbVpoYkhObGZTd2ljbWxuYUhSeklqcGJJbTFoYm1GblpWOTJZWFZzZENKZExDSnpZMjl3WlNJNld5SkNjbUZwYm5SeVpXVTZWbUYxYkhRaVhTd2liM0IwYVc5dWN5STZlMzE5LmtSTk81OEVGQkRPUElhSjUycHc4RVA4bHZUWUdpN2d1OHYzaUZCZlhkRklsb1pDNjNMRU9KU29qUmpkbGw1VUliOXpTa29mUjVPZWV0QllUZE0tUnlBIiwiY29uZmlnVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzL3E5YjczeWNtOThtcHdoZDgvY2xpZW50X2FwaS92MS9jb25maWd1cmF0aW9uIiwiZ3JhcGhRTCI6eyJ1cmwiOiJodHRwczovL3BheW1lbnRzLnNhbmRib3guYnJhaW50cmVlLWFwaS5jb20vZ3JhcGhxbCIsImRhdGUiOiIyMDE4LTA1LTA4In0sImNoYWxsZW5nZXMiOltdLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvcTliNzN5Y205OG1wd2hkOC9jbGllbnRfYXBpIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhdXRoVXJsIjoiaHR0cHM6Ly9hdXRoLnZlbm1vLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9vcmlnaW4tYW5hbHl0aWNzLXNhbmQuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9xOWI3M3ljbTk4bXB3aGQ4In0sInRocmVlRFNlY3VyZUVuYWJsZWQiOnRydWUsInBheXBhbEVuYWJsZWQiOnRydWUsInBheXBhbCI6eyJkaXNwbGF5TmFtZSI6IkRvbmF0ZSIsImNsaWVudElkIjoiQWU3LTQwbW5pSUNtcVpRRVBPeEhfVGhBWGx4RTlDenFWYXBhNnBkR1dwOUhickVMdVNlWVN0dlpaSllnM1k5NXFseFIzRExBdG95LVpib3AiLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjpmYWxzZSwiZW52aXJvbm1lbnQiOiJvZmZsaW5lIiwidW52ZXR0ZWRNZXJjaGFudCI6ZmFsc2UsImJyYWludHJlZUNsaWVudElkIjoibWFzdGVyY2xpZW50MyIsImJpbGxpbmdBZ3JlZW1lbnRzRW5hYmxlZCI6dHJ1ZSwibWVyY2hhbnRBY2NvdW50SWQiOiJkb25hdGUiLCJjdXJyZW5jeUlzb0NvZGUiOiJVU0QifSwibWVyY2hhbnRJZCI6InE5YjczeWNtOThtcHdoZDgiLCJ2ZW5tbyI6Im9mZiJ9");
//            // mBraintreeFragment is ready to use!
//        } catch (InvalidArgumentException e) {
//            // There was an issue with your authorization string.
//        }

//        DropInRequest dropInRequest = new DropInRequest()
//                .clientToken(token);
//        dropInRequest.isPayPalEnabled();
//        dropInRequest.collectDeviceData(true);
//        startActivityForResult(dropInRequest.getIntent(BrainTreePaymentActivity.this), REQUEST_CODE);
        DropInRequest dropInRequest = new DropInRequest(); // A 240523
        dropInClient = new DropInClient(this, "sandbox_38p29vxg_m7bfbztpgz6yfyk7"); // A 240523
        dropInClient.launchDropIn(dropInRequest); // A 240523
        dropInClient.setListener(new DropInListener() { // A 240523
            @Override
            public void onDropInSuccess(@NonNull DropInResult dropInResult) {
                String stringNonce = Objects.requireNonNull(dropInResult.getPaymentMethodNonce()).toString();
                Log.e(TAG, " stringNonce - " + stringNonce);
            }

            @Override
            public void onDropInFailure(@NonNull Exception error) {
                Log.d(TAG, " Error : " + error.getMessage());
            }
        });
    }

    private void callWebservice() {
        progressDialog = new ProgressDialog(BrainTreePaymentActivity.this); // A 240523
        progressDialog.show(); // A 240523

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<String> call = apiService.getbraintree();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss(); // A 240523
                try {
                    Log.e("Response_payment1", response.body().toString());
                    String token = response.body().toString();

                    setupBraintreeAndStartExpressCheckout(token); // A 240523
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss(); // A 240523
                Log.e("Response_error", t.toString());
            }
        });
    }

    public void setupBraintreeAndStartExpressCheckout(String token) {
//        PayPalRequest request = new PayPalRequest("1")
//                .currencyCode("USD")
//                .intent(PayPalRequest.INTENT_AUTHORIZE);
//
//        PayPal.requestOneTimePayment(mBraintreeFragment, request);

        BraintreeClient braintreeClient = new BraintreeClient(this, token);// A 240523
        PayPalClient payPalClient = new PayPalClient(this, braintreeClient);// A 240523
        PayPalCheckoutRequest request = new PayPalCheckoutRequest("1.00");// A 240523
        request.setCurrencyCode("USD");// A 240523
        request.setIntent(PayPalPaymentIntent.AUTHORIZE);// A 240523
        payPalClient.tokenizePayPalAccount(this, request);// A 240523
        payPalClient.setListener(new PayPalListener() {// A 240523
            @Override
            public void onPayPalSuccess(@NonNull PayPalAccountNonce payPalAccountNonce) {
                Log.e(TAG, " onPayPalSuccess - " + payPalAccountNonce.getAuthenticateUrl());
            }

            @Override
            public void onPayPalFailure(@NonNull Exception error) {
                Log.e(TAG, " onPayPalFailure - " + error.getMessage());
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
//                String stringNonce = nonce.getNonce();
//                Log.e("mylog", " " + stringNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.e("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//                Log.d("mylog", "Error : " + error.toString());
            }
        }
    }
}