package com.example.patientpal.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {



//Step 1. Parse json data sent
    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }


//Step 2. Called from Step 1
    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int count = jsonArray.length();

        List<HashMap<String,String>> placesList = new ArrayList<>();
        HashMap<String,String> placeMap;

        for(int i =0;i<count;i++){
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //List of hashmaps. Each Hashmap  element is a pharmacy
        return placesList;
    }


    //Step 3 called from step 2
    private HashMap<String,String> getPlace(JSONObject googlePlaceJson){

        HashMap<String, String> googlePlacesMap = new HashMap<>();

        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latititude= "";
        String longitude= "";
        String reference = "";

        try{
            if(!googlePlaceJson.isNull("name")){

                placeName = googlePlaceJson.getString("name");
            }

            if(!googlePlaceJson.isNull("vicinity")){
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latititude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name", placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latititude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("reference", reference);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesMap;
    }



}
