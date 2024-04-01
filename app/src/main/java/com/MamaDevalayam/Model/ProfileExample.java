package com.MamaDevalayam.Model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class ProfileExample {

  /*  public static void main(String[] args) {
        String profileUrl = "https://api.linkedin.com/v2/me";

        // Access token for the r_liteprofile permission
        String accessToken = "YOUR_ACCESS_TOKEN";

        try {
            JsonObject profileData = ProfileExample.sendGetRequest(profileUrl, accessToken);
            System.out.println(profileData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static JsonObject sendGetRequest(String urlString, String accessToken) throws Exception {
        URL url = new URL(urlString);
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);
        con.setRequestProperty("cache-control", "no-cache");
        con.setRequestProperty("X-Restli-Protocol-Version", "2.0.0");
        Log.e("con11",""+con);
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        Log.e("con11",""+br);
        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            jsonString.append(line);
            Log.e("jsonString",jsonString.toString());

        }
        br.close();
        Log.e("jsonString","demo");
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString.toString()));
        JsonObject jsonObject = jsonReader.readObject();
        return jsonObject;
    }
}