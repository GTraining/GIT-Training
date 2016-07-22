package com.example.kyler.trackinglocationfirebase;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by kyler on 22/07/2016.
 */
public class ScanAdapter extends LocationAdapter<User> {
    private GoogleMap map;
    private LatLng myLatLng;
    private String username;

    public ScanAdapter(Query mRef, GoogleMap map, LatLng myLatLng,Activity activity, int layout,String username) {
        super(mRef, User.class,layout,activity);
        this.map = map;
        this.myLatLng = myLatLng;
        this.username = username;
    }

    @Override
    protected void populateView(User user) {
        if(getDistance(getMyLatLng(user.getLocation()),myLatLng)<50){
            setMap(user);
        }
    }

    private void setMap(User user){
        if(!user.getUsername().equals(username)) {
            LatLng latLng = getMyLatLng(user.getLocation());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                    .title(user.getUsername() + " - distance: "+getDistance(latLng,myLatLng))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            map.addMarker(markerOptions);
        }
        Log.e("Retrieve",user.getUsername()+" - "+user.getLocation()+" - "+user.getDateTime());
    }

    private LatLng getMyLatLng(String location){
        String[] arr = location.split(",");
        double latitude = Double.parseDouble(arr[0]);
        double longitude = Double.parseDouble(arr[1]);
        return new LatLng(latitude,longitude);
    }

    private float getDistance(LatLng start, LatLng end){
        Location A = new Location("A");
        A.setLatitude(start.latitude);
        A.setLongitude(start.longitude);

        Location B = new Location("B");
        B.setLatitude(end.latitude);
        B.setLongitude(end.longitude);

        return A.distanceTo(B);
    }
}
