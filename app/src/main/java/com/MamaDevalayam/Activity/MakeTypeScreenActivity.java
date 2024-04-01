package com.MamaDevalayam.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.Commonmethod.ConstantFunctions;
import com.MamaDevalayam.Model.ChangeActivity;
import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.SessionManager;

public class MakeTypeScreenActivity extends AppCompatActivity {

    TextView tvTopType, tvWebsiteId, tvEmailId, tvContactId;
    Button register_btn;
    ImageView back_icon_login_img;
    Toolbar toolbar;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_type_screen);
//        setView(R.layout.activity_make_type_screen, SubCategoryActivity.TAG);

        toolbar = findViewById(R.id.commonMenuActivityToolbar);
//        toolbar.setVisibility(View.GONE);

        tvTopType = (TextView) findViewById(R.id.tvTopType);
        tvWebsiteId = (TextView) findViewById(R.id.tvWebsiteId);
        tvEmailId = (TextView) findViewById(R.id.tvEmailId);
        tvContactId = (TextView) findViewById(R.id.tvContactId);
        register_btn = (Button) findViewById(R.id.register_btn);
        back_icon_login_img = (ImageView) findViewById(R.id.back_icon_login_img);


        tvTopType.setText(getIntent().getStringExtra("tvTopType"));
        tvWebsiteId.setText(getIntent().getStringExtra("tvWebsiteId"));
        tvEmailId.setText(getIntent().getStringExtra("tvEmailId"));
        tvContactId.setText(getIntent().getStringExtra("tvContactId"));
        if (getIntent().getBooleanExtra("isHome", false)) {
            register_btn.setVisibility(View.VISIBLE);
        } else {
            register_btn.setVisibility(View.GONE);
        }
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeActivity.changeActivity(MakeTypeScreenActivity.this, BrowseActivity.class);
                ((TextView) findViewById(R.id.browse_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
                // browse_img.setImageTintMode();
                ((ImageView) findViewById(R.id.browse_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                finish();
            }
        });
        back_icon_login_img.setOnClickListener(view -> onBackPressed());
        session = new SessionManager(this);
        findViewById(R.id.linear_browse).setOnClickListener(v -> {

            ChangeActivity.changeActivity(this, BrowseActivity.class);
            ((TextView) findViewById(R.id.browse_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
            // browse_img.setImageTintMode();
            ((ImageView) findViewById(R.id.browse_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
            finish();
        });

        findViewById(R.id.linear_myspace).setOnClickListener(v -> {

            if (session.isLoggedIn()) {
                ChangeActivity.changeActivity(this, MyspaceActivity.class);
                ((TextView) findViewById(R.id.my_space_tv)).setTextColor(getResources().getColor(R.color.quantum_white_text));
                ((ImageView) findViewById(R.id.my_space_img)).setColorFilter(getApplicationContext().getResources().getColor(R.color.quantum_white_text));
                finish();
            } else {
                ConstantFunctions.LoginDialog(this);
                // ChangeActivity.changeActivity(CommonMenuActivity.this, LoginActivity.class);
                //finish();
            }
        });
    }
}