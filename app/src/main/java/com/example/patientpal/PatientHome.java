package com.example.patientpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.patientpal.adapters.PatientHomeAdapter;
import com.example.patientpal.appointments.AppointmentsMainActivity;
import com.example.patientpal.map.PharmaciesOnMapActivity;
import com.example.patientpal.medicine.MedicineActivity;
import com.example.patientpal.oldMedicineReminders.MedicationReminderActivity;
import com.example.patientpal.medicineReminders.MedicineReminderMainActivity;
import com.example.patientpal.model.Appointment;
import com.example.patientpal.model.LocationCovidStats;
import com.example.patientpal.model.PatientMenuItem;
import com.example.patientpal.covidActivities.CovidMainActivity;
import com.example.patientpal.prescription.PrescriptionHomeActivity;
import com.example.patientpal.services.TokenHandler;
import com.example.patientpal.services.VolleySingletonRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class PatientHome extends AppCompatActivity {

    private int LOCATION_PERMISSION_CODE = 1;

    //FirebaseAuth mAuth;

    ArrayList<LocationCovidStats> globalCovidStats;

    ArrayList<PatientMenuItem> menuItems;
    ArrayList<Appointment> userAppointments;

    RequestQueue mRequestQueue;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        //mAuth=FirebaseAuth.getInstance();

        //FirebaseUser user = mAuth.getCurrentUser();


        //menu options
        menuItems = new ArrayList<>();
        menuItems.add(new PatientMenuItem(R.drawable.prescription, "Prescriptions", "Manage your prescriptions" ));
        menuItems.add(new PatientMenuItem(R.drawable.medicine, "Medicine", "See list of your medication" ));
        menuItems.add(new PatientMenuItem(R.drawable.appointment_icon, "Appointments", "Schedule Appointments" ));
        menuItems.add(new PatientMenuItem(R.drawable.cross_location, "Nearby Pharmacies", "Show close by pharmacies" ));
        menuItems.add(new PatientMenuItem(R.drawable.virus_logo, "COVID-19", "Latest stats and information"));
        menuItems.add(new PatientMenuItem(R.drawable.about_icon, "About", "More information about Patient Pal" ));
        menuItems.add(new PatientMenuItem(R.drawable.logout, "Sign Out", "Sign out of patientpal" ));




       //Set RecView
        mRecyclerView = findViewById(R.id.recyclerViewPatientHome);
       mRecyclerView.setHasFixedSize(true);
       mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Set adapter
        mAdapter = new PatientHomeAdapter(this, menuItems);

        mLayoutManager = new LinearLayoutManager(this);
       mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);




    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void viewNearbyPharmaciesonMap(View view){
        if (ActivityCompat.checkSelfPermission(PatientHome.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PatientHome.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // If permission already given.
            Intent i = new Intent(this, PharmaciesOnMapActivity.class);
            i.putExtra("kmRadius", 2);
            startActivity(i);
            CustomIntent.customType(this, "left-to-right");

        }else{
              ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
            //            Intent i = new Intent(this, PharmaciesOnMapActivity.class);
//            startActivity(i);
//            CustomIntent.customType(this, "left-to-right");


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(this, PharmaciesOnMapActivity.class);
                startActivity(i);
                CustomIntent.customType(this, "left-to-right");
            }else{
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goToMedicinesActivity(View v){
        Intent intentito = new Intent(this, MedicineActivity.class);
        startActivity(intentito);
        CustomIntent.customType(this, "left-to-right");
    }

    public void gotoOldReminderActivity(View v){
        Intent intentito = new Intent(this, MedicationReminderActivity.class);
        startActivity(intentito);
        CustomIntent.customType(this, "left-to-right");
    }

    public void goToPrescriptionActivity(View v){
        Intent intentToPrescription = new Intent(this, PrescriptionHomeActivity.class);
        startActivity(intentToPrescription);
        CustomIntent.customType(this, "left-to-right");
    }

    public void goToAppointments(View v){

        mRequestQueue = VolleySingletonRequestQueue.getInstance(this).getRequestQueue();
        userAppointments = new ArrayList<>();

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.spring_boot_url)+"/mobile/getMyAppointments", null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i=0; i<response.length();i++) {

                        JSONObject appointmentJSONOBJ = response.getJSONObject(i);

                        Appointment appointment = new Appointment();
                        appointment.setAppointmentID(appointmentJSONOBJ.getInt("appointmentId"));
                        appointment.setAppointmenttitle(appointmentJSONOBJ.getString("appointmenttitle"));
                        appointment.setAdditionalInfo(appointmentJSONOBJ.getString("additionalInfo"));
                        appointment.setTimeinMillis(appointmentJSONOBJ.getLong("timeinMillis"));


                        userAppointments.add(appointment);

                    }

                    Intent appointmentActivityIntent = new Intent(PatientHome.this, AppointmentsMainActivity.class);
                    appointmentActivityIntent.putParcelableArrayListExtra("appointmentArray", userAppointments);
                    startActivity(appointmentActivityIntent);
                    CustomIntent.customType(PatientHome.this, "left-to-right");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), TokenHandler.getToken(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", TokenHandler.getToken());
                return params;
            }
        };

        mRequestQueue.add(jsonArrayRequest);


    }


    public void goToAboutActivity(View v){
        Intent intentToAboutActivity = new Intent(this, AboutActivityLoggedIn.class);
        startActivity(intentToAboutActivity);
        CustomIntent.customType(this, "left-to-right");
    }
    public void signOut(View v){

        //mAuth.signOut();
        TokenHandler.removeToken();
        Intent logOutIntent = new Intent(this, MainActivity.class);
        startActivity(logOutIntent);
        CustomIntent.customType(this, "fadein-to-fadeout");
    }


    public void CoronaCheck(){
        System.out.println("corona method hit");
        mRequestQueue = VolleySingletonRequestQueue.getInstance(this).getRequestQueue();
        globalCovidStats = new ArrayList<>();
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.spring_boot_url)+"covid19/all", null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                try {
                for(int i=0; i<response.length();i++) {

                        JSONObject locationCOV = response.getJSONObject(i);

                    LocationCovidStats locationStats = new LocationCovidStats();
                    locationStats.setProvince(locationCOV.getString("province"));
                    locationStats.setCountry(locationCOV.getString("country"));
                    locationStats.setLatestTotalCases(Integer.parseInt(locationCOV.getString("latestTotalCases")));
                    locationStats.setLatestTotalDeaths(Integer.parseInt(locationCOV.getString("latestTotalDeaths")));
                    locationStats.setLatestTotalRecoveries(Integer.parseInt(locationCOV.getString("latestTotalRecoveries")));


                    globalCovidStats.add(locationStats);
                    //System.out.println("Did this work? \n" + locationStats.toString());

                }
                //Still in try
                    //System.out.println(globalCovidStats);

                    Intent coronaActivityIntent = new Intent(PatientHome.this, CovidMainActivity.class);
                    coronaActivityIntent.putParcelableArrayListExtra("globalCovidArray", globalCovidStats);
                    startActivity(coronaActivityIntent);
                    CustomIntent.customType(PatientHome.this, "left-to-right");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(jsonArrayRequest);

    }
}
