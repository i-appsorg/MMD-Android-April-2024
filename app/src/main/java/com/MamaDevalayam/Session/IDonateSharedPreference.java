package com.MamaDevalayam.Session;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.MamaDevalayam.Model.subcategorynew;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IDonateSharedPreference extends Application {

    /*Register_Page*/

    public void setName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", name);
        editor.commit();
    }

    public String getName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("Name", "Name");
        return name;
    }
    public void setpermission(Context context, String permission) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("permission", permission);
        editor.commit();
    }

    public String getpermission(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String permission = sharedPreferences.getString("permission", "");
        return permission;
    }
    public void setpaymentvalue(Context context, String paymentvalue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("paymentvalue", paymentvalue);
        editor.commit();
    }

    public String getpaymentvalue(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String paymentvalue = sharedPreferences.getString("paymentvalue", "paymentvalue");
        return paymentvalue;
    }
    public void setcountrycode(Context context, String countrycode) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("countrycode", countrycode);
        editor.commit();
    }

    public String getcountrycode(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String countrycode = sharedPreferences.getString("countrycode", "countrycode");
        return countrycode;
    }
    public void setAdvancepage(Context context, String Advancepage) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Advancepage", Advancepage);
        editor.commit();
    }

    public String getAdvancepage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("Advancepage", "Advancepage");
        return name;
    }
    public void setnotificationpage(Context context, String notificationpage) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notificationpage", notificationpage);
        editor.commit();
    }

    public String getnotificationpage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String notificationpage = sharedPreferences.getString("notificationpage", "Name");
        return notificationpage;
    }
    public void setNotificationstatus(Context context, int status) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Notificationstatus", status);
        editor.commit();
    }

    public int getNotificationstatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int status = sharedPreferences.getInt("Notificationstatus", 1);
        return status;
    }
    public void setNotificationsoundstatus(Context context, int sound) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("sound", sound);
        editor.commit();
    }

    public int getNotificationsoundstatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int status = sharedPreferences.getInt("sound", 1);
        return status;
    }
    public void settoken(Context context, String token) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public String gettoken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("token", "token");
        return name;
    }
    public void setMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", name);
        editor.commit();
    }

    public String getEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("Email", "Email");
        return name;
    }


    public void setsocialmedia(Context context, String social) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SocialMedia", social);
        editor.commit();
    }

    public String getsocialMedia(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("SocialMedia", "SocialMedia");
        return name;
    }

    public void setlogintype(Context context, String social) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SocialMedia", social);
        editor.commit();
    }

    public String getlogintype(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("SocialMedia", "SocialMedia");
        return name;
    }

    public void setdailogueamt(Context context, String amt) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amt", amt);
        editor.commit();
    }

    public String getdailogueamt(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("amt", "amt");
        return name;
    }
    public void setcharity_id(Context context, String charity_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("charity_id", charity_id);
        editor.commit();
    }

    public String getcharity_id(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String charity_id = sharedPreferences.getString("charity_id", "charity_id");
        return charity_id;
    }
    public void setcharity_name(Context context, String charity_name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("charity_name", charity_name);
        editor.commit();
    }

    public String getcharity_name(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String charity_name = sharedPreferences.getString("charity_name", "charity_name");
        return charity_name;
    }
    public void setdailoguepage(Context context, String dailogue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dailogue", dailogue);
        editor.commit();
    }

    public String getdailoguepage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("dailogue", "dailogue");
        return name;
    }

    public void setSocialProfileimg(Context context, String profileimg) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileimg", profileimg);
        editor.commit();
    }

    public String getsocialProfileimg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String selectedprofileimg = sharedPreferences.getString("profileimg", "profileimg");
        return selectedprofileimg;
    }


    public void seteditprofile(Context context, String edit) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("edit", edit);
        editor.commit();
    }

    public String geteditprofile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("edit", "edit");
        return name;
    }

    public void settype(Context context, String edit) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("edit", edit);
        editor.commit();
    }

    public String gettype(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("edit", "edit");
        return name;
    }

    public void setadvance(Context context, String edit) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("advance", edit);
        editor.commit();
    }

    public String getadvance(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("advance", "advance");
        return name;
    }

    public void setSelected(Context context, String select){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected", select);
        editor.commit();
    }

    public String getSelected(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("selected", "false");
        return name;
    }

    public void setprofiledata(Context context, String profiledata) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profiledata", profiledata);
        editor.commit();
    }

    public String getprofiledata(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("profiledata", "profiledata");
        return name;
    }


    public static void setsubcategory(Context context, ArrayList<HashMap<String, String>> parentItems) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(parentItems);
        sharedpreferenceeditor.putString("subcategory", json);
        sharedpreferenceeditor.commit();
    }

    public void setSearchName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("searchName", name);
        editor.commit();
    }

    public String getSearchName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("searchName", "");
        return name;
    }

    public static ArrayList<HashMap<String, String>> getsubcategory(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("subcategory", null);
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void setcategory(Context context, ArrayList<subcategorynew> subcategory) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(subcategory);
        sharedpreferenceeditor.putString("subcategory1", json);
        sharedpreferenceeditor.commit();
    }

    ///
    public static ArrayList<String>  getselecteditem(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("arraychecked_item1", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void setselecteditem(Context context, ArrayList<String> arraychecked_item) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraychecked_item);
        sharedpreferenceeditor.putString("arraychecked_item1", json);
        sharedpreferenceeditor.commit();
    }
    //
    public static ArrayList<subcategorynew> getcategory(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("subcategory1", null);
        Type type = new TypeToken<ArrayList<subcategorynew>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /*  public static void setcategory_type(Context context, ArrayList<Category_new> category_newArrayList) {
          SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
          SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
          Gson gson = new Gson();
          String json = gson.toJson(category_newArrayList);
          sharedpreferenceeditor.putString("subcategory0",json);
          sharedpreferenceeditor.commit();
      }

      public static ArrayList<Category_new>  getcategory_type(Context context){
          SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
          Gson gson = new Gson();
          String json = prefs.getString("subcategory0", null);
          Type type = new TypeToken<ArrayList<Category_new>>() {
          }.getType();
          return gson.fromJson(json, type);
      }*/
    public static void setselectedtypedata(Context context, ArrayList<String> listOfdate) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listOfdate);
        sharedpreferenceeditor.putString("listOfdate", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<String> getselectedtypedata(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("listOfdate", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void setselectedcategorydata(Context context, ArrayList<String> listOfdate) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listOfdate);
        sharedpreferenceeditor.putString("listOfcategory", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<String> getselectedcategorydata(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("listOfcategory", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void setselectedsubcategorydata(Context context, ArrayList<String> listOfdate) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listOfdate);
        sharedpreferenceeditor.putString("listOfsubcategory", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<String> getselectedsubcategorydata(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("listOfsubcategory", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void setselectedchildcategorydata(Context context, ArrayList<String> listOfdate) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listOfdate);
        sharedpreferenceeditor.putString("listOfchildcategory", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<String> getselectedchildcategorydata(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("listOfchildcategory", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void setSelectedItems(Context context, ArrayList<String> listOfdate) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listOfdate);
        sharedpreferenceeditor.putString("listofItems", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<String> getSelectedItems(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("listofItems", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public static void setLocation(Context context, String location){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location", location);
        editor.commit();
    }

    public static String getLocation(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("location", "");
        return name;
    }

    public static void setRevenue(Context context, String revenue){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("revenue", revenue);
        editor.commit();
    }

    public static String getRevenue(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("revenue", "");
        return name;
    }

    public static void setDeductible (Context context, String deductible){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("deductible", deductible);
        editor.commit();
    }

    public static String getDeductible (Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("deductible", "");
        return name;
    }

    public static void setsubchild(Context context, ArrayList<ArrayList<HashMap<String, String>>> childItems) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(childItems);
        sharedpreferenceeditor.putString("subchild", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<ArrayList<HashMap<String, String>>> getsubchild(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("subchild", null);
        Type type = new TypeToken<ArrayList<ArrayList<HashMap<String, String>>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void setselected_iem_list(Context context, ArrayList<String> arraychecked_item) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraychecked_item);
        sharedpreferenceeditor.putString("arraychecked_item", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<String> getselected_iem_list(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("arraychecked_item", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
    public static void setchildselected_iem_list(Context context, ArrayList<String> arraychildchecked_item) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedpreferenceeditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraychildchecked_item);
        sharedpreferenceeditor.putString("arraychildchecked_item", json);
        sharedpreferenceeditor.commit();
    }

    public static ArrayList<String> getchildselected_iem_list(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("arraychildchecked_item", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static List<HashMap<String, String>> loaditems(Context context) {
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = mySharedPreferences.getString("saveallitems", "");
        Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void setispid(Context context, String Vendorname) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ispid", Vendorname);
        editor.commit();
    }

    public String getispuserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("ispuserid", "ispuserid");
        return name;
    }

    public void setiispuserid(Context context, String Vendorname) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ispuserid", Vendorname);
        editor.commit();
    }

    public String getuserASIid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String profileimg = sharedPreferences.getString("userASIid", "userASIid");
        return profileimg;
    }

    public void setuserASIid(Context context, String Vendorname) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userASIid", Vendorname);
        editor.commit();
    }

    public String getuserASIusername(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String profileimg = sharedPreferences.getString("ASIusername", "ASIusername");
        return profileimg;
    }

    public void setuserASIusername(Context context, String Vendorname) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ASIusername", Vendorname);
        editor.commit();
    }

    public String getProfileimg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String profileimg = sharedPreferences.getString("profileimg", "profileimg");
        return profileimg;
    }


    public void setSelectedProfileimg(Context context, String selectedprofileimg) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedprofileimg", selectedprofileimg);
        editor.commit();
    }

    public String getProimg64(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String proimg64 = sharedPreferences.getString("proimg64", "proimg64");
        return proimg64;
    }

    public void setProimg64(Context context, String proimg64) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("proimg64", proimg64);
        editor.commit();
    }

    public String getmgsimg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String mgsimg = sharedPreferences.getString("mgsimg", "mgsimg");
        return mgsimg;
    }

    public void setmgsimg(Context context, String mgsimg) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mgsimg", mgsimg);
        editor.commit();
    }

    public String getmgsimg64(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String mgsimg64 = sharedPreferences.getString("mgsimg64", "mgsimg64");
        return mgsimg64;
    }

    public void setmgsimg64(Context context, String mgsimg64) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mgsimg64", mgsimg64);
        editor.commit();
    }

    public String getSender(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sender = sharedPreferences.getString("sender", "sender");
        return sender;
    }

    public void setSender(Context context, String sender) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sender", sender);
        editor.commit();
    }

    public String getReceiver(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String receiver = sharedPreferences.getString("receiver", "receiver");
        return receiver;
    }

    public void setReceiver(Context context, String receiver) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("receiver", receiver);
        editor.commit();
    }

    public String getCity(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String city = sharedPreferences.getString("city", "city");
        return city;
    }

    public void setCity(Context context, String city) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("city", city);
        editor.commit();
    }

    public String getCountry(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String country = sharedPreferences.getString("country", "country");
        return country;
    }

    public void setCountry(Context context, String country) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("country", country);
        editor.commit();
    }

    public String getState(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String state = sharedPreferences.getString("state", "state");
        return state;
    }

    public void setState(Context context, String state) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state", state);
        editor.commit();
    }

    public String getSelectedMsgimg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String selectedmsgimg = sharedPreferences.getString("selectedmsgimg", "selectedmsgimg");
        return selectedmsgimg;
    }

    public void setSelectedtype(Context context, String Selectedtype) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Selectedtype", Selectedtype);
        editor.commit();
    }

    public String getSelectedtype(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String Selectedtype = sharedPreferences.getString("Selectedtype", "Selectedtype");
        return Selectedtype;
    }


}