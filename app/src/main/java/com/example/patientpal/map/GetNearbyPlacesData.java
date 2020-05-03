package com.example.patientpal.map;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePacesData;
    GoogleMap mMap;
    String url;


    //GETS called from map acitivity
    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        //System.out.println("Howdy do in baclground");
        DownloadURL dwnldURL = new DownloadURL();

        try {
            googlePacesData = dwnldURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Google places data" + googlePacesData);
        return googlePacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList;

        //Parse
        DataParser parser= new DataParser();
        nearbyPlaceList = parser.parse(s);

        //System.out.println("Size : " + nearbyPlaceList.size());
        showNearbyPlaces(nearbyPlaceList);
        //System.out.println("Howdy do in postexecute");
    }


    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList){
        //System.out.println("Howdy first showNearbtPlaces");
        //System.out.println("Size : " + nearbyPlacesList.size());

        for (int i =0; i<nearbyPlacesList.size(); i++){

            MarkerOptions mkrOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);

            String placeName = googlePlace.get("place_name");
            //String placeName = googlePlace.get("name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latitudeLongitude = new LatLng(lat,lng);


            mkrOptions.position(latitudeLongitude);
            mkrOptions.title(placeName + " : " + vicinity);
            mkrOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            mMap.addMarker(mkrOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latitudeLongitude));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            //System.out.println("Howdy do in showNearbtPlaces");
        }
    }
}
