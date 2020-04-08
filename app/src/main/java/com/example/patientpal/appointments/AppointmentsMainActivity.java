package com.example.patientpal.appointments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patientpal.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class AppointmentsMainActivity extends AppCompatActivity {

    CompactCalendarView mCompactCalendarView;
    TextView mMonthTextView;
    private SimpleDateFormat mdateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_main);


        mCompactCalendarView = findViewById(R.id.compactCalendarView);
        mCompactCalendarView.setUseThreeLetterAbbreviation(true);

        mMonthTextView = findViewById(R.id.MonthTextView);
        mMonthTextView.setText(mdateFormatMonth.format(mCompactCalendarView.getFirstDayOfCurrentMonth()).toUpperCase());

        //Set Doctors appointment 10th
        Event ev1 = new Event(Color.RED, 1585695600, "Going to get that thing checked out");
        mCompactCalendarView.addEvent(ev1);


        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context ctx = getApplicationContext();

                if(dateClicked.toString().compareTo("Sat May 04 00:00:00 AST 2020")== 0){
                    Toast.makeText(ctx, "Going to get that thing checked out", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ctx, "No events are planned", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mMonthTextView.setText(mdateFormatMonth.format(firstDayOfNewMonth).toUpperCase());
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
