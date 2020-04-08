package com.example.patientpal.medicineReminders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patientpal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import maes.tech.intentanim.CustomIntent;

public class MedicineReminderMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MedicineReminderMainActivity.this, "Floating Button Clicked", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MedicineReminderMainActivity.this, AddReminderActivity.class);
                startActivity(i);
            }
        });
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
