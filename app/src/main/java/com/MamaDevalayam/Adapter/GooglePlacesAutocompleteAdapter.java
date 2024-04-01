package com.MamaDevalayam.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.MamaDevalayam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GooglePlacesAutocompleteAdapter extends RecyclerView.Adapter<GooglePlacesAutocompleteAdapter.PredictionHolder> implements Filterable {
    private ArrayList resultList;
    private int layout;
    private Context mContext;
    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

   private static final String API_KEY = "AIzaSyDfOZf_bITlewuOVvnVICvjQ70VdFHmR2Q";
    //private static final String API_KEY = "AIzaSyAQNhE2MjC-tR7NBLKFBQOTQltO69EvU0w";


    public GooglePlacesAutocompleteAdapter(Context context, int resource) {
        mContext = context;
        layout = resource;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
//                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    @Override
    public PredictionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(layout, viewGroup, false);
        PredictionHolder mPredictionHolder = new PredictionHolder(convertView);
        return mPredictionHolder;
    }

    @Override
    public void onBindViewHolder(PredictionHolder mPredictionHolder, final int i) {
        mPredictionHolder.mPrediction.setText((CharSequence) resultList.get(i));
        /*mPredictionHolder.mRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetLatLonCallback.getLocation(resultList.get(i).toString());
            }
        });*/
    }

    public class PredictionHolder extends RecyclerView.ViewHolder {
        private TextView mPrediction;
        private RelativeLayout mRow;
        public PredictionHolder(View itemView) {

            super(itemView);
            mPrediction = (TextView) itemView.findViewById(R.id.address);
            mRow=(RelativeLayout) itemView.findViewById(R.id.predictedRow);
        }

    }

    @Override
    public int getItemCount() {
        if(resultList != null)
            return resultList.size();
        else
            return 0;
    }

    public String getItem(int position) {
        if(resultList != null)
        return String.valueOf(resultList.get(position));
        else
            return "0";
    }

    @SuppressLint("LongLogTag")
    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
           // sb.append("&components=country:us");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            sb.append("&components=country:" + "US"+"|"+"country:" + "PR"+"|"+"country:" + "AS"+"|"+"country:" + "GU"+"|"+"country:" + "VI");
            //sb.append("&ComponentCountry:" + "US"+"|"+"ComponentCountry:" + "PR"+"|"+"ComponentCountry:" + "AS"+"|"+"ComponentCountry:" + "GU"+"|"+"ComponentCountry:" + "VI"+"|"+"ComponentCountry:" + "MP"+"|"+"ComponentCountry:" + "MH"+"|"+"ComponentCountry:" + "FM"+"|"+"ComponentCountry:" + "PW"+"|"+"ComponentCountry:" + "AA"+"|"+"ComponentCountry:" + "AE"+"|"+"ComponentCountry:" + "AP");

            URL url = new URL(sb.toString());
            Log.e(LOG_TAG, sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

}
