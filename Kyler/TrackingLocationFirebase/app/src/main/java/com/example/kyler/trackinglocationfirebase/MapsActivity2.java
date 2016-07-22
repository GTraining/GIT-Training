package com.example.kyler.trackinglocationfirebase;

import android.database.DataSetObserver;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapsActivity2 extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private String username;

    private List<User> mModels;
    private List<String> mKeys;
    private DatabaseReference mDatabase,mRef;
    private double latitude,longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        getGPS();
        mModels = new ArrayList<>();
        mKeys = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference();
        mDatabase = mRef.child("Kyler");
        username = getIntent().getStringExtra("username");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                User model = dataSnapshot.getValue(User.class);
                String key = dataSnapshot.getKey();

                // Insert into the correct location, based on previousChildName
                if (previousChildName == null) {
                    mModels.add(0, model);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(model);
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, model);
                        mKeys.add(nextIndex, key);
                    }
                }
                onChange();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                User newModel = dataSnapshot.getValue(User.class);
                int index = mKeys.indexOf(key);

                mModels.set(index, newModel);
                onChange();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = mKeys.indexOf(key);

                mKeys.remove(index);
                mModels.remove(index);
                onChange();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                User newModel = dataSnapshot.getValue(User.class);
                int index = mKeys.indexOf(key);
                mModels.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    mModels.add(0, newModel);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(newModel);
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, newModel);
                        mKeys.add(nextIndex, key);
                    }
                }
                onChange();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }
        });
    }

    private void getGPS(){
        GPSTracker gpsTracker = new GPSTracker(this);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void writeNewPost(String name) {
        String location = latitude+","+longitude;
        String datetime= new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy").format(Calendar.getInstance().getTime());
        User user = new User(name, location, datetime);
        mDatabase.child(username).setValue(user);
    }

    public void changeData2(View view){
        setChangeMylocation();
    }

    private void setChangeMylocation(){
        getGPS();
        writeNewPost(username);
        LatLng latLng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(username));
        CircleOptions circleOptions = new CircleOptions().radius(50).center(latLng);
        mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(19.0f));
        Toast.makeText(this,"Changed",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        writeNewPost(username);
        mMap = googleMap;
        LatLng latLng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(username));
        CircleOptions circleOptions = new CircleOptions().radius(50).center(latLng);
        mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }

    private void onChange(){
        mMap.clear();
        for(User user:mModels){
            if(!user.getUsername().equals(username)&&getDistance(getMyLatLng(user.getLocation()),new LatLng(latitude,longitude))<50)
                setMap(user);
        }
        setChangeMylocation();
    }

    private void setMap(User user){
        if(!user.getUsername().equals(username)) {
            LatLng latLng = getMyLatLng(user.getLocation());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                    .title(user.getUsername() + " - distance: "+getDistance(latLng,new LatLng(latitude,longitude)))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
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
