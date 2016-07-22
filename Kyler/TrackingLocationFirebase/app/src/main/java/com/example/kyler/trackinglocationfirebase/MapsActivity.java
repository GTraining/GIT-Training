package com.example.kyler.trackinglocationfirebase;

import android.database.DataSetObserver;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String username;
    private DatabaseReference mDatabase,mRef;
    private double latitude,longitude;
    private ScanAdapter scanAdapter;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getGPS();
        mRef = FirebaseDatabase.getInstance().getReference();
        mDatabase = mRef.child("Kyler");
        username = getIntent().getStringExtra("username");
        lv = (ListView) findViewById(R.id.mylv);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    public void changeData(View view){
        setChangeMylocation();
    }

    private void setChangeMylocation(){
        getGPS();
        writeNewPost(username);
        LatLng latLng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(username));
        CircleOptions circleOptions = new CircleOptions().radius(50).center(latLng);
        mMap.addCircle(circleOptions);
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
        setupListView();
    }

    private void setupListView(){
        scanAdapter = new ScanAdapter(mDatabase.limitToFirst(10),mMap,new LatLng(latitude,longitude),this,R.layout.listview_item,username);
        lv.setAdapter(scanAdapter);
        scanAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mMap.clear();
                setChangeMylocation();
            }
        });
    }

}
