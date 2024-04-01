package com.MamaDevalayam.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.R;
import com.MamaDevalayam.Session.IDonateSharedPreference;

public class AnnualRevenueActivity extends AppCompatActivity {

    Button apply, reset;
    LinearLayout bottomLayout;
    ImageView back;
    AlertDialog.Builder builder;
    IDonateSharedPreference iDonateSharedPreference;
    TextView nintyTextView, greaternintyTextView, twohundredTextview, fivehundredTextView, greaterthousandTextView;
    Boolean isSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_revenue);
        iDonateSharedPreference = new IDonateSharedPreference();
        back = (ImageView) findViewById(R.id.back_icon_revenue_img);
        reset = (Button) findViewById(R.id.annual_reset_button);
        apply = (Button) findViewById(R.id.annual_apply_button);
        bottomLayout = (LinearLayout) findViewById(R.id.annual_bottom_layout);
        nintyTextView = (TextView) findViewById(R.id.ninty_tv);
        greaternintyTextView = (TextView) findViewById(R.id.greaterninty_deselect_tv);
        twohundredTextview = (TextView) findViewById(R.id.twohund_tv_deselect);
        fivehundredTextView = (TextView) findViewById(R.id.fivehund_deselect_tv);
        greaterthousandTextView = (TextView) findViewById(R.id.greaterthousand_deselect_tv);
        builder = new AlertDialog.Builder(this);

        nintyTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                iDonateSharedPreference.setRevenue(getApplicationContext(), "90");
                nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
                greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
                fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                nintyTextView.setTextColor(getResources().getColor(R.color.title_text_color));
                greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
                fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
                greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
                bottomLayout.setVisibility(View.VISIBLE);
            }
        });

        greaternintyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDonateSharedPreference.setRevenue(getApplicationContext(), "200");
                nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
                twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
                fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaternintyTextView.setTextColor(getResources().getColor(R.color.title_text_color));
                nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
                fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
                greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
                bottomLayout.setVisibility(View.VISIBLE);
            }
        });

        twohundredTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDonateSharedPreference.setRevenue(getApplicationContext(), "500");
                nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout_color);
                fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                twohundredTextview.setTextColor(getResources().getColor(R.color.title_text_color));
                nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
                greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
                bottomLayout.setVisibility(View.VISIBLE);
            }
        });

        fivehundredTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDonateSharedPreference.setRevenue(getApplicationContext(), "1000");
                nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
                fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
                greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                fivehundredTextView.setTextColor(getResources().getColor(R.color.title_text_color));
                nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
                greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
                bottomLayout.setVisibility(View.VISIBLE);
            }
        });

        greaterthousandTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDonateSharedPreference.setRevenue(getApplicationContext(), "2000");
                nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
                fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
                greaterthousandTextView.setTextColor(getResources().getColor(R.color.title_text_color));
                nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
                greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
                bottomLayout.setVisibility(View.VISIBLE);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("unitedstate")) {
                    Intent intent = new Intent(AnnualRevenueActivity.this, UnitedStateActivity.class);
                    iDonateSharedPreference.setadvance(getApplicationContext(), "1");
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    finishAffinity();
                } else if (iDonateSharedPreference.getAdvancepage(getApplicationContext()).equalsIgnoreCase("international")) {
                    Intent intent = new Intent(AnnualRevenueActivity.this, InternationalCharitiesActivity.class);
                    iDonateSharedPreference.setadvance(getApplicationContext(), "1");
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Intent intent = new Intent(AnnualRevenueActivity.this, NameSearchActivity.class);
                    iDonateSharedPreference.setadvance(getApplicationContext(), "1");
                    intent.putExtra("data", "1");
                    startActivity(intent);
                    finishAffinity();
                }

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
                nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
                greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
                fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
                nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
                fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
                iDonateSharedPreference.setRevenue(getApplicationContext(), "");
                bottomLayout.setVisibility(View.GONE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailogue_forgot();
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dailogue_forgot();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String selected = iDonateSharedPreference.getRevenue(getApplicationContext());
        if (selected.equalsIgnoreCase("90")) {
            nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
            greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
            fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            nintyTextView.setTextColor(getResources().getColor(R.color.title_text_color));
            greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
            fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
            greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
        } else if (selected.equalsIgnoreCase("200")) {
            nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
            twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
            fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaternintyTextView.setTextColor(getResources().getColor(R.color.title_text_color));
            nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
            fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
            greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
        } else if (selected.equalsIgnoreCase("500")) {
            nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout_color);
            fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            twohundredTextview.setTextColor(getResources().getColor(R.color.title_text_color));
            nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
            greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));
        } else if (selected.equalsIgnoreCase("1000")) {
            nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
            fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
            greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            fivehundredTextView.setTextColor(getResources().getColor(R.color.title_text_color));
            nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
            greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            greaterthousandTextView.setTextColor(getResources().getColor(R.color.textInside));

        } else if (selected.equalsIgnoreCase("2000")) {
            nintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaternintyTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            twohundredTextview.setBackgroundResource(R.drawable.advanced_border_layout);
            fivehundredTextView.setBackgroundResource(R.drawable.advanced_border_layout);
            greaterthousandTextView.setBackgroundResource(R.drawable.advanced_border_layout_color);
            greaterthousandTextView.setTextColor(getResources().getColor(R.color.title_text_color));
            nintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            twohundredTextview.setTextColor(getResources().getColor(R.color.textInside));
            greaternintyTextView.setTextColor(getResources().getColor(R.color.textInside));
            fivehundredTextView.setTextColor(getResources().getColor(R.color.textInside));
        }

        if (selected.equalsIgnoreCase("")) {
            bottomLayout.setVisibility(View.GONE);
        } else bottomLayout.setVisibility(View.VISIBLE);
    }
}
