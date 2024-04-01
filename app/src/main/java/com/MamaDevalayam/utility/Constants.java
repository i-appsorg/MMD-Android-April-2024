package com.MamaDevalayam.utility;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;
import java.util.Locale;

public class Constants {
    public static final String API_NOT_CONNECTED = "Google API not connected";
    public static final String SOMETHING_WENT_WRONG = "OOPs!!! Something went wrong...";
    public static String PlacesTag = "Google Places Auto Complete";

    public static LatLng getFromLocation(Context context, String address) {
        double latitude = 0.0, longtitude = 0.0;
        LatLng loc = null;

        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                Barcode.GeoPoint p = new Barcode.GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6),
                        (int) (addresses.get(0).getLongitude() * 1E6));
                latitude = p.lat / 1E6;
                longtitude = p.lng / 1E6;

                loc = new LatLng(latitude, longtitude);
            }
        } catch (Exception ee) {

        }
        return loc;
    }
}
