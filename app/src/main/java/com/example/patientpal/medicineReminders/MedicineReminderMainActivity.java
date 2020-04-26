package com.example.patientpal.medicineReminders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.patientpal.R;
import com.example.patientpal.model.Reminder;
import com.example.patientpal.oldMedicineReminders.AddReminderActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class MedicineReminderMainActivity extends AppCompatActivity {


    private ArrayList<Reminder> myReminders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MedicineReminderMainActivity.this, "Floating Button Clicked", Toast.LENGTH_LONG).show();
                startCreateReminderActivity();
            }
        });
    }

    public void startCreateReminderActivity(){
        Intent i = new Intent(MedicineReminderMainActivity.this, CreateReminderActivity.class);
        startActivity(i);
        CustomIntent.customType(this, "left-to-right");
    }

    public void finish(){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void finish(View view){
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}
