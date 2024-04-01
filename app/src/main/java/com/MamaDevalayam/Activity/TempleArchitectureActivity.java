package com.MamaDevalayam.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.SessionManager;

public class TempleArchitectureActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back_icon_login_img;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_architecture);
//        setView(R.layout.activity_temple_architecture, SubCategoryActivity.TAG);

        toolbar = findViewById(R.id.commonMenuActivityToolbar);
//        toolbar.setVisibility(View.GONE);

        back_icon_login_img = findViewById(R.id.back_icon_login_img);
        back_icon_login_img.setOnClickListener(v -> onBackPressed());
        session = new SessionManager(this);
        findViewById(R.id.linear_browse).setOnClickListener(v -> {

            ChangeActivity.changeActivity(this, BrowseActivity.class);
            ((TextView)findViewById(R.id.browse_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
            // browse_img.setImageTintMode();
            ((ImageView)findViewById(R.id.browse_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
            finish();
        });
        findViewById(R.id.linear_myspace).setOnClickListener(v -> {

            if (session.isLoggedIn()) {
                ChangeActivity.changeActivity(this, MyspaceActivity.class);
                ((TextView)findViewById(R.id.my_space_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
                ((ImageView)findViewById(R.id.my_space_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                finish();
            } else {
                ConstantFunctions.LoginDialog(this);
                // ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                //finish();
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

}