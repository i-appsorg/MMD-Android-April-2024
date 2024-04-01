package com.MamaDevalayam.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.MamaDevalayam.Activity.BrowseActivity;
import com.MamaDevalayam.Activity.LoginActivity;

import java.util.HashMap;

/**
 * Created by Gowrishankar on 29/5/2019.
 */

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AgentAssist";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_MAIL = "mail";
    public static final String KEY_MOBILE = "mobile_number";
    public static final String KEY_UID = "u_id";
    public static final String KEY_PIC = "img";
    public static final String KEY_AUTH = "auth";
    public static final String KEY_token = "token";
    public static final String KEY_BUSINESS = "businessname";
    public static final String KEY_country = "country";
    public static final String KEY_gender= "gender";
    public static final String KEY_type= "type";
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */
    public void createLoginSession(String u_id, String email, String name,String mobilenumber,String profileimg,String token,String businessname,String country,String gender,String type){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref

        // Storing email in Pref
        editor.putString(KEY_UID,u_id);
        editor.putString(KEY_MAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobilenumber);
        editor.putString(KEY_PIC, profileimg);
        editor.putString(KEY_token, token);
        editor.putString(KEY_BUSINESS, businessname);
        editor.putString(KEY_country, country);
        editor.putString(KEY_gender, gender);
        editor.putString(KEY_type, type);



        // commit changes
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_UID, pref.getString(KEY_UID, null));
        user.put(KEY_MAIL, pref.getString(KEY_MAIL, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_MOBILE,pref.getString(KEY_MOBILE,null));
        user.put(KEY_PIC,pref.getString(KEY_PIC,null));
        user.put(KEY_token,pref.getString(KEY_token,null));
        user.put(KEY_BUSINESS,pref.getString(KEY_BUSINESS,null));
        user.put(KEY_country,pref.getString(KEY_country,null));
        user.put(KEY_gender,pref.getString(KEY_gender,null));
        user.put(KEY_type,pref.getString(KEY_type,null));
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, BrowseActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);


    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}