package com.example.patientpal.appointments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.patientpal.R;
import com.example.patientpal.adapters.AppointmentListAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class AppointmentsMainActivity extends AppCompatActivity implements CreateAppointmentDialog.DialogListerner {

    CompactCalendarView mCompactCalendarView;
    TextView mMonthTextView;
    private SimpleDateFormat mdateFormat = new SimpleDateFormat("dd  MMMM  yyyy", Locale.getDefault());
    private SimpleDateFormat mMilisecondFormat = new SimpleDateFormat("ss", Locale.getDefault());
    ArrayList<Event> appointmentsOnSelectedDay;
    AppointmentListAdapter recViewAdapter;
    Date mselectedDate;

    CreateAppointmentDialog appointmentDialog;


    RecyclerView apponintmentRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_main);

        apponintmentRecyclerView = findViewById(R.id.recycler_view_appointments);

        mselectedDate = new Date();

        mCompactCalendarView = findViewById(R.id.compactCalendarView);
        mCompactCalendarView.setUseThreeLetterAbbreviation(true);

        mMonthTextView = findViewById(R.id.MonthTextView);
        mMonthTextView.setText(mdateFormat.format(mselectedDate).toUpperCase());





//        //Set Doctors appointment 10th
//        Event ev1 = new Event(Color.RED, 1586515338825L, "Going to get that thing checked out");
//        Event ev2 = new Event(Color.RED, 1586515348825L, "Doctor visit");
//        Event ev3 = new Event(Color.RED, 1586602800000L, "Dentist Visit");
//        Event ev4 = new Event(Color.BLUE, 1586602800000L, "Physio Appointment");
//        Event ev5 = new Event(Color.RED, 1586602800000L, "Quad Appointment");
//        Event ev6 = new Event(Color.BLUE, 1586602800000L, "Massage Appointment");
//        Event ev7 = new Event(Color.RED, 1586602800000L, "Therapist");
//        Event ev8 = new Event(Color.BLUE, 1586602800000L, "Family Therapist");
//
//        mCompactCalendarView.addEvent(ev1);
//        mCompactCalendarView.addEvent(ev2);
//        mCompactCalendarView.addEvent(ev3);
//        mCompactCalendarView.addEvent(ev4);
//        mCompactCalendarView.addEvent(ev5);
//        mCompactCalendarView.addEvent(ev6);
//        mCompactCalendarView.addEvent(ev7);
//        mCompactCalendarView.addEvent(ev8);
//
//        setupRecyclerView(new Date());

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //mMonthTextView.setText(mdateFormat.format(dateClicked).toUpperCase());
                mselectedDate = dateClicked;
                mMonthTextView.setText(mdateFormat.format(mselectedDate).toUpperCase());
                setupRecyclerView(dateClicked);
//                List<Event> events = mCompactCalendarView.getEvents(dateClicked);
//                Toast.makeText(getApplicationContext(), dateClicked  + "  " + events.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mMonthTextView.setText(mdateFormat.format(firstDayOfNewMonth).toUpperCase());
                setupRecyclerView(firstDayOfNewMonth);
            }
        });


        FloatingActionButton fab = findViewById(R.id.addAppointmentFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Add Appointment Clicked + date selected " + mselectedDate, Toast.LENGTH_LONG).show();
               // Event eventy = new Event(Color.BLUE, mselectedDate.getTime(), "Hypnotist");
                //mCompactCalendarView.addEvent(eventy);
                //setupRecyclerView(mselectedDate);
                //


                appointmentDialog = new CreateAppointmentDialog();
                appointmentDialog.show(getSupportFragmentManager(), " create appointment dialog");

//                appointmentDialog.redExText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        appointmentDialog.dismiss();
//                    }
//                });
            }
        });
    }

    public void setupRecyclerView(Date d){
        appointmentsOnSelectedDay = new ArrayList<>();
        appointmentsOnSelectedDay.addAll(mCompactCalendarView.getEvents(d));


        recViewAdapter = new AppointmentListAdapter(getApplicationContext(), appointmentsOnSelectedDay);

        apponintmentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        apponintmentRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        apponintmentRecyclerView.setAdapter(recViewAdapter);
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
    public void onRedExClicked() {
        appointmentDialog.dismiss();
    }
}
