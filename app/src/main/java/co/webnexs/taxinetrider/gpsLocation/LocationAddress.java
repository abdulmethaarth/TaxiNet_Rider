package co.webnexs.taxinetrider.gpsLocation;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by techintegrity on 08/07/16.
 */
public class LocationAddress {

    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler)
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                /*try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                       }
                       sb.append(address.getAdminArea()).append("\n");
                       sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        result = sb.toString();
                       *//* for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            if(!address.getAddressLine(i).equals("null"))
                                sb.append(address.getAddressLine(i)).append(",");
                        }*//*
                        if(address.getLocality() != null && !address.getLocality().equals("null"))
                            sb.append(address.getLocality()).append(",");
                        if(address.getPostalCode() != null && !address.getPostalCode().equals("null"))
                            sb.append(address.getPostalCode()).append(",");
                        if(address.getCountryName() != null && !address.getCountryName().equals("null"))
                            sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } */
                try {
                    List<Address> addressList = geocoder.getFromLocation( latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        //String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() /*&& !country.isEmpty()*/)
                            result = locality/* + "  " + country*/;

                    }

                }catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                    result = "Unable connect to Geocoder";

                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static void getAddressFromLocation(final String address,
                                              final Context context, final Handler handler)
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocationName(address, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();

                        sb.append(address.getLatitude()).append(",");
                        sb.append(address.getLongitude());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                    result = "Unable connect to Geocoder";

                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

}

