package com.MamaDevalayam.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.balsikandar.crashreporter.CrashReporter;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.R;

public class SplashScreenActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        CrashReporter.initialize(this);

        if (getIntent().getExtras() != null) {
            Log.e("test","test");
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d("SplashScreenActivity", "Key: " + key + " Value: " + value);
            }
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // if (new Session(SplashActivity.this, TAG).getIsLogin())
                //       startActivity(new Intent(SplashActivity.this, ChooseTypeActivity.class));
                //   else
                //       startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                ChangeActivity.changeActivity(SplashScreenActivity.this, WelcomeActivity.class);
                finish();
            }
        }, 3000);


    }
}
