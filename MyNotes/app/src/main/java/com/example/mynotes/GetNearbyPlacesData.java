package com.example.mynotes;

import android.Manifest;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GetNearbyPlacesData extends AsyncTask<Object,String,String>{ //ContextWrapper {

    private static final String TAG = "MapsActivity";
//    public GetNearbyPlacesData(Context base) {
//        MapsActivity.Con(base);
//    }
    String googlePlacesData;
    GoogleMap googleMap;
    String url;
    String name;
    LatLng latLng;
    private float GEOFENCE_RADIUS=100;
    private String GEOFENCE_ID="SOME_GEOFENCE_ID";
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE=10002;
    static ArrayList<String> lat= new ArrayList<String>();
    static ArrayList<String> lng= new ArrayList<String>();




//    GeofenceHelper geofenceHelper;
//    MapsActivity mapsActivity=new MapsActivity();
//    public GoogleMap mMap;
    //GoogleMap googleMap;

    @Override
    protected String doInBackground(Object... objects) {
        googleMap=(GoogleMap)objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl=new DownloadUrl();
        try  {
            googlePlacesData = downloadUrl.readUrl(url);
            System.out.println("****************************************googlePLacesData = "+googlePlacesData);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultArray = parentObject.getJSONArray("results");
            System.out.println("****************************************INSIDE OnPOSTeXECUTE resultArray.size() = " + resultArray.length());
            for(int i=0;i<resultArray.length();i++)
            {
                JSONObject jsonObject = resultArray.getJSONObject(i);
                JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");

                String latitude = locationObj.getString("lat");
                String longitude = locationObj.getString("lng");

                JSONObject nameObject = resultArray.getJSONObject(i);

                name = nameObject.getString("name");

                latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                //MarkerOptions markerOptions = new MarkerOptions();
                //markerOptions.title(name);
                //markerOptions.position(latLng);
                    /////////////////////////////// ye latlng mil gayi hai hmlog ko yahan se bs grofence bnana hai;////////////////////
                //googleMap.addMarker(markerOptions);
                //googleMap.addCircle()
                MapsActivity mapsActivity=new MapsActivity();
                addMarker(latLng);
                addCircle(latLng,GEOFENCE_RADIUS);
                lat.add(latitude);
                lng.add(longitude);
                System.out.println("****************************************Should add marker and circle");

//                MapsActivity mapsActivity=new MapsActivity();
//                mapsActivity.getLOCATION(latLng,GEOFENCE_RADIUS);






/////////////////////////////// yahan pe humlog arraylist bnake data adapter se pass krenge mapsactivity mei wahan pe kaam hoga saara
//                              abhi k liye geofences add krne ka.. marker and circle wagera yahin pe ho jayega... bs geofences wahan pe bnane rhenge...






                //mapsActivity.addGeofence(latLng,GEOFENCE_RADIUS);
                //MapsActivity.getInstance().addGeofence(latLng,GEOFENCE_RADIUS);
                //addGeofence(latLng,GEOFENCE_RADIUS);
                //((MapsActivity)getActivity()).startChronometer();
                /*if(Build.VERSION.SDK_INT>=29)
                {
                    if(ContextCompat.checkSelfPermission(, Manifest.permission.ACCESS_BACKGROUND_LOCATION)==
                            PackageManager.PERMISSION_GRANTED) {
//                        mMap.clear();
                        addMarker(latLng);
                        addCircle(latLng,GEOFENCE_RADIUS);
                        addGeofence(latLng,GEOFENCE_RADIUS);
                    } else {
                        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                            ActivityCompat.requestPermissions(this,new String[]
                                    {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                        }
                        else
                        {
                            ActivityCompat.requestPermissions(this,new String[]
                                    {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                        }
                    }
                }
                else {
//                    mMap.clear();
                    addMarker(latLng);
                    addCircle(latLng,GEOFENCE_RADIUS);
                    addGeofence(latLng,GEOFENCE_RADIUS);
                }*/

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // setting the ArrayList Value
    public void setLatArrayList ( ArrayList lat )
    {
        this.lat = lat;
    }

    // getting the ArrayList value
    public static ArrayList getLatArrayList()
    {
        return lat;
    }

    public void setLongArrayList ( ArrayList lng )
    {
        this.lng = lng;
    }

    // getting the ArrayList value
    public static ArrayList getLongArrayList()
    {
        return lng;
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions= new MarkerOptions().title(name).position(latLng);
        googleMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255,255,0,0));
        circleOptions.fillColor(Color.argb(64,255,0,0));
        circleOptions.strokeWidth(4);
        googleMap.addCircle(circleOptions);
    }
}

















