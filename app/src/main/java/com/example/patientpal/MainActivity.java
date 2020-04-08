package com.example.patientpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.patientpal.map.PharmaciesOnMapActivity;
import com.example.patientpal.patientActivities.PatientHome;
import com.example.patientpal.patientActivities.PatientSignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    //FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //mAuth= FirebaseAuth.getInstance();
        
//        if(mAuth.getCurrentUser()!=null){
//            Intent loggedIn = new Intent(this, PatientHome.class);
//            startActivity(loggedIn);
//        }else {
//            setContentView(R.layout.activity_main);
//        }



    }

    public void goToPatientSignUP(View v){
        Intent intent = new Intent(this, PatientSignUpActivity.class);

        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
    }


}
