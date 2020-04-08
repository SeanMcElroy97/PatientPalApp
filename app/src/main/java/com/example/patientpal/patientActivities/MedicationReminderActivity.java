package com.example.patientpal.patientActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.patientpal.R;
import com.example.patientpal.timePicker.AlertReceiver;
import com.example.patientpal.timePicker.NotificationHelper;
import com.example.patientpal.timePicker.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MedicationReminderActivity extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener {

    private NotificationHelper nHelper;
    Calendar c;
    private EditText medReminderEditTxt;
    TextView txtView;

    ArrayList<Calendar> alarmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if alarmlist is empty then
        setContentView(R.layout.old_reminder_activity);

        alarmList = new ArrayList<>();

        nHelper = new NotificationHelper(this);
         c= Calendar.getInstance();

        txtView = findViewById(R.id.timetxt);
         medReminderEditTxt = findViewById(R.id.medicineReminderMessage);
    }

    public void openTimeChooser(View v){
        DialogFragment timeChooser = new TimePickerFragment();
        timeChooser.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        txtView.setText(hourOfDay + ":" + minute);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND,0);

        //startAlarm(c);

    }


    public void reminderOn(View v){
        Toast.makeText(this,"Reminder set for " + c.getTime().getHours() +" : " + c.getTime().getMinutes(),Toast.LENGTH_LONG).show();
        alarmList.add(c);
        startAlarm(c);
    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlertReceiver.class);
        i.putExtra("message", medReminderEditTxt.getText().toString());
        int reqCode = alarmList.size();
        i.putExtra("reqCode", reqCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reqCode, i,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    public void cancelAlarm(View v){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, i,0);

        alarmManager.cancel(pendingIntent);
        txtView.setText("Cancelled alarm");
    }
}
