package com.example.patientpal.map;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.patientpal.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.sql.SQLOutput;

import maes.tech.intentanim.CustomIntent;

public class PharmaciesOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Intent mIntent;

    private SupportMapFragment mapFragment;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private LocationRequest locationRequest;
    private ImageView mLogo;

    private TextView mRadiusText;
    private SeekBar mRadiusSeekBar;
    private int mRadiusInMeters;


    private double latitude,longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacieson_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLogo = findViewById(R.id.logo);
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


         mIntent = getIntent();
        int kmRadFromIntent = mIntent.getIntExtra("kmRadius", 0);

        mRadiusText = findViewById(R.id.radiusText);


        mRadiusSeekBar = findViewById(R.id.radiusSeekBar);
        mRadiusSeekBar.setProgress(kmRadFromIntent);

        mRadiusInMeters = mRadiusSeekBar.getProgress() * 1000;

        mRadiusText.setText(mRadiusSeekBar.getProgress() + " km");

        mRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mRadiusText.setText(i + " km");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mIntent.putExtra("kmRadius", seekBar.getProgress());
                finishFade();
                startActivity(mIntent);
            }
        });



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    //Changes transition of up button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void finish(){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void finishFade(){
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        final MarkerOptions markerOptions = new MarkerOptions();

        getLastLocation();



    }

    public void getLastLocation(){
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            mMap.setMyLocationEnabled(true);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            //markerOptions.position(latLng);
                            // mMap.addMarker(markerOptions);

                            String url = getUrl(latitude, longitude, "pharmacy");
                            //System.out.println(url);

                            Object dataTransfer[] = new Object[2];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;

                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                        }
                    }
                });
    }


    private String getUrl(double latitude, double longitude, String nearByPharmacy){
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
//        googlePlaceUrl.append("&radius="+5000);
        googlePlaceUrl.append("&radius="+mRadiusInMeters);
        googlePlaceUrl.append("&type="+nearByPharmacy);
        googlePlaceUrl.append("&key="+"AIzaSyC8X9WyCq4kPtjRdrWHf4zdnhJfJcQG0mI");
        //System.out.println("Toby Maguire");
        return googlePlaceUrl.toString();
    }
}
