package com.example.patientpal.appointments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patientpal.R;
import com.example.patientpal.adapters.AppointmentListAdapter;
import com.example.patientpal.model.Appointment;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class AppointmentsMainActivity extends AppCompatActivity implements CreateAppointmentDialog.DialogListener {

    //Variables
    CompactCalendarView mCompactCalendarView;
    TextView mMonthTextView;
    private SimpleDateFormat mdateFormat = new SimpleDateFormat("dd  MMMM  yyyy", Locale.getDefault());
    private SimpleDateFormat mMilisecondFormat = new SimpleDateFormat("ss", Locale.getDefault());
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    ArrayList<Appointment> appointmentsOnSelectedDay;
    ArrayList<Appointment> allUserAppointments = new ArrayList<>();
    AppointmentListAdapter recViewAdapter;
    Date mselectedDate;

    CreateAppointmentDialog appointmentDialog;

    RecyclerView apponintmentRecyclerView;


    //Methods

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



        //Call api for appointments
        //for each appointment create an event to add to compact calendar


//        //Set Doctors appointment 10th
//        Event ev1 = new Event(Color.RED, 1586515338825L, "Going to get that thing checked out");

//
//        mCompactCalendarView.addEvent(ev1);

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //mMonthTextView.setText(mdateFormat.format(dateClicked).toUpperCase());
                mselectedDate = dateClicked;
                mMonthTextView.setText(mdateFormat.format(mselectedDate).toUpperCase());
                //pass arraylist of appointments
                try {
                    setupRecyclerView(dateClicked);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mMonthTextView.setText(mdateFormat.format(firstDayOfNewMonth).toUpperCase());
                try {
                    setupRecyclerView(firstDayOfNewMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.addAppointmentFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointmentDialog = new CreateAppointmentDialog();
                appointmentDialog.show(getSupportFragmentManager(), " create appointment dialog");
            }
        });
    }

    public void setupRecyclerView(Date d) throws ParseException {
        appointmentsOnSelectedDay = new ArrayList<>();

        for(Appointment a: allUserAppointments){
            Date y = new Date(a.getTimeinMillis());
            if(y.getDay() == d.getDay()){
                appointmentsOnSelectedDay.add(a);
            }

        }


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
    public void onDialogCreateButtonClick(String appointmentTitle, String additionalInfo, String hour24, String minute) {
        //mCompactCalendarView.addEvent(new Event(Color.BLUE, mselectedDate.setTime();, "Family Therapist"));
        int yearFormated = mselectedDate.getYear()-100;
        String monthFormated = String.valueOf(mselectedDate.getMonth()+1);

        if (monthFormated.length()<2){
            monthFormated = "0"+monthFormated;
        }

        String dateString = mselectedDate.getDate() + "/" + monthFormated + "/" + yearFormated + " " + hour24 + ":" + minute + ":" + 00;
        try {
//            Date date = sdf.parse("19/04/20 21:05:00");
            Date date = sdf.parse(dateString);
            Toast.makeText(this, date.toString(), Toast.LENGTH_LONG).show();

            mCompactCalendarView.addEvent(new Event(Color.RED, date.getTime(), appointmentTitle));
            Toast.makeText(this, "Date created " + date.getTime(), Toast.LENGTH_SHORT).show();
            allUserAppointments.add(new Appointment(appointmentTitle, additionalInfo, date.getTime()));
            setupRecyclerView(date);

            //Call api to save Appointment.

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
