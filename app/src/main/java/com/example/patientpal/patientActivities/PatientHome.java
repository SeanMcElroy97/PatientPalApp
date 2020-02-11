package com.example.patientpal.patientActivities;

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

import com.example.patientpal.MainActivity;
import com.example.patientpal.R;
import com.example.patientpal.adapters.PatientHomeAdapter;
import com.example.patientpal.map.PharmaciesOnMapActivity;
import com.example.patientpal.medicineReminders.MedicineReminderMainActivity;
import com.example.patientpal.model.PatientMenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class PatientHome extends AppCompatActivity {

    private int LOCATION_PERMISSION_CODE = 1;

    FirebaseAuth mAuth;
    FirebaseFirestore db;


    ArrayList<PatientMenuItem> menuItems;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();

        //menu options
        menuItems = new ArrayList<>();
        menuItems.add(new PatientMenuItem(R.drawable.prescription, "Prescriptions", "manage your prescriptions" ));
        menuItems.add(new PatientMenuItem(R.drawable.reminder_icon_green, "Reminders", "Set medication Reminders" ));
        menuItems.add(new PatientMenuItem(R.drawable.health, "Appointments", "Schedule Appointments" ));
        menuItems.add(new PatientMenuItem(R.drawable.cross_location, "Nearby Pharmacies", "Show close by pharmacies" ));
        menuItems.add(new PatientMenuItem(R.drawable.about_icon, "About", "more information about Patient Pal" ));
        menuItems.add(new PatientMenuItem(R.drawable.logout, "Sign Out", "sign out of patientpal" ));




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

    public void viewNearbyPharmaciesonMap(View view){
        if (ActivityCompat.checkSelfPermission(PatientHome.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PatientHome.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // If permission already given.
            Intent i = new Intent(this, PharmaciesOnMapActivity.class);
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

    public void gotoReminderActivity(View v){
        Intent intentito = new Intent(this, MedicineReminderMainActivity.class);
        startActivity(intentito);
        CustomIntent.customType(this, "left-to-right");
    }

    public void gotoOldReminderActivity(View v){
        Intent intentito = new Intent(this, MedicationReminderActivity.class);
        startActivity(intentito);
        CustomIntent.customType(this, "left-to-right");
    }

    public void viewRegisteredPharmacies(View v){
        Intent goToRegisteredPharmacies;
    }

    public void signOut(View v){

        mAuth.signOut();
        Intent logOutIntent = new Intent(this, MainActivity.class);
        startActivity(logOutIntent);
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}
