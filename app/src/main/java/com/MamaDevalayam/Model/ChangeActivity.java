package com.MamaDevalayam.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.MamaDevalayam.R;

public class ChangeActivity {

    public static void changeActivity(Activity context, Class<?> c) {
        Intent i = new Intent(context, c);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public static void changeActivityData(Activity context, Class<?> c, String data){
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("data", data);
        context.startActivity(i);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public static void changeActivityback(Activity context, Class<?> c) {
        Intent i = new Intent(context, c);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public static void changeActivityText(Context context, Class<?> c, String data, String data1, String data2) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("data", data);
        i.putExtra("data1", data1);
        i.putExtra("data2", data2);
        context.startActivity(i);
    }
    public static void clearAllPreviousActivity(Activity context, Class<?> c) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        context.startActivity(i);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public static void changeActivitydata(Activity context, Class<?> c) {
        Intent i = new Intent(context, c);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public static void changeActivityfromRight(Activity context, Class<?> c) {
        Intent i = new Intent(context, c);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
        context.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_from_right);
    }
    public static void changeActivityfromLeft(Activity context, Class<?> c) {
        Intent i = new Intent(context, c);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
        context.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_left);
    }
    public static void showSnackbar(View view, String message, Activity activity) {
        try {
            hideKeyboard(activity);
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
