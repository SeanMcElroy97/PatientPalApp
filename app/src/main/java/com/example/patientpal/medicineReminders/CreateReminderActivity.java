package com.example.patientpal.medicineReminders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patientpal.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import maes.tech.intentanim.CustomIntent;

public class CreateReminderActivity extends AppCompatActivity  implements ReminderTimePickerDialog.ReminderTimePickerDialogListener {

    ImageView goBackButton;

    //Call API which checks medication Names
    private String [] mMedicationNamesAvailable = new String[]{"solpadine", "calpol", "lempsip", "loratex"};
    private AutoCompleteTextView medicationNameAutoTxtV;

    Button openTimePickerButtion, submitBtn;

    TextView mReminderTimeValueTxtView;

    LinearLayout numberPickerLayout;

    SwitchCompat repeatYesNo;

    //Not regular
    NumberPicker dayNumberPicker;


    int mHourVal = 0;
    int mMinuteVal = 0;


    //Time
    String displayedTime = "No time set";

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

        mReminderTimeValueTxtView = findViewById(R.id.ReminderTimeValueTxt);
        mReminderTimeValueTxtView.setText(displayedTime);


        //Yes Or no for repeat reminder
        repeatYesNo = findViewById(R.id.repeatDaysYesNo);

        numberPickerLayout = findViewById(R.id.layoutRepeatDays);
        dayNumberPicker = findViewById(R.id.day_number_picker);
        numberPickerLayout.setVisibility(View.INVISIBLE);

        repeatYesNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repeatYesNo.isChecked()){
                    numberPickerLayout.setVisibility(View.VISIBLE);
                }else {
                    numberPickerLayout.setVisibility(View.INVISIBLE);
                }
            }
        });



        //Submit
        submitBtn = findViewById(R.id.createReminderBTN);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if everything filled
                createReminder();
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

    @Override
    public void getHourMinuteStr(String hour24Str, String minute60str, int hourVal, int minuteVal) {
        mReminderTimeValueTxtView.setText(hour24Str + " : " + minute60str);
        mHourVal = hourVal;
        mMinuteVal = minuteVal;
    }

    public void createReminder(){

        String reminderMedicineName = medicationNameAutoTxtV.getText().toString();
        String reminderDisplayTime = mReminderTimeValueTxtView.getText().toString();
        int duration =  (repeatYesNo.isChecked()) ? dayNumberPicker.getValue() : 1;
        int daysRemaining = duration;
        Long originalReminderTimeinMillis = null;
//        List<Long> allReminders;


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,mHourVal );
        cal.set(Calendar.MINUTE, mMinuteVal);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        //If date.get time is less than now then set 24 hours later in long
        Date originalReminderDate = cal.getTime();

        //Working
        Long originalReminderTime = (cal.getTimeInMillis() < new Date().getTime()) ? cal.getTimeInMillis()+86400000 : cal.getTimeInMillis();

//        Date firstReminderDate = new Date(originalReminderTime * 1000);


//        Toast.makeText(this, "Date is : " + originalReminderDate.getDate() + "  Time is " + originalReminderDate.getHours() + ":" + originalReminderDate.getMinutes(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Date is : " + firstReminderDate.getDate() + "  Time is " + firstReminderDate.getHours() + ":" + firstReminderDate.getMinutes(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, originalReminderTime.toString(), Toast.LENGTH_SHORT).show();


    }
}
