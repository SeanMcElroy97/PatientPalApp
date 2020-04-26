package com.example.patientpal.medicineReminders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.patientpal.R;

import java.lang.reflect.Array;

import maes.tech.intentanim.CustomIntent;

public class CreateReminderActivity extends AppCompatActivity {

    ImageView goBackButton;

    Button openTimePickerButtion;

    //Call API which checks medication Names
    private String [] mMedicationNamesAvailable = new String[]{"solpadine", "calpol", "lempsip", "loratex"};

    private AutoCompleteTextView medicationNameAutoTxtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        medicationNameAutoTxtV = findViewById(R.id.autoCompleteMedicationName);
        ArrayAdapter<String> medNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMedicationNamesAvailable);
        medicationNameAutoTxtV.setAdapter(medNameAdapter);

        openTimePickerButtion = findViewById(R.id.openTimePickerBtn);
        openTimePickerButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //Exit
        goBackButton = findViewById(R.id.ButtonLogo);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void openDialog(){
        ReminderTimePickerDialog reminderTimePickerDialog = new ReminderTimePickerDialog();
        reminderTimePickerDialog.show(getSupportFragmentManager(), "reminder time picker dialog");
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
