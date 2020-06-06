package com.example.mynotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        System.out.println("*******#############################################################################BroadCast Receiver call to ho rha hai.....;......?????????????????????????????????????");
        String item = intent.getStringExtra("item");
        //Toast.makeText(context, "Geofence Triggered...", Toast.LENGTH_LONG).show();
        NotificationHelper notificationHelper= new NotificationHelper(context);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }
        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence:geofenceList)
        {
            Log.d(TAG, "onReceive: "+geofence.getRequestId());
        }
//        Location location=geofencingEvent.getTriggeringLocation();
        int transitionType= geofencingEvent.getGeofenceTransition();
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "U have entered a Geofence", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("U have entered a Geofence, Check Your LIST","There is an "+ item+" in your list that" +
                        " might be available near you.",MapsActivity.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_EXIT","",MapsActivity.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_DWELL,Check Your LIST","There is an "+ item+" in your list that" +
                        " might be available near you.",MapsActivity.class);
                break;
        }
    }
}
