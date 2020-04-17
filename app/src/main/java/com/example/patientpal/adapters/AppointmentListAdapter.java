package com.example.patientpal.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientpal.R;
import com.example.patientpal.model.Appointment;
import com.example.patientpal.model.CountryCovidStats;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder>{

    //May make an appointment obj
    //private ArrayList<Event> mAppointments;
    private ArrayList<Appointment> mAppointments;
    private Context mContext;


    //Dialog mCountryDialog;


    //Adapter Constructor
//    public AppointmentListAdapter(Context ctx, ArrayList<Event> dateEvents){
//        mContext = ctx;
//        mAppointments = dateEvents;
//    }

    public AppointmentListAdapter(Context ctx, ArrayList<Appointment> dateAppointments){
        mContext = ctx;
        mAppointments = dateAppointments;
    }




    //Inner ViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder{

        //SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat();

        private LinearLayout mAppointmentItem;
        TextView mAppointmentTitle;
        TextView mAppointmentInfo;
        TextView mAppointmentTimeStr;

        public MyViewHolder(View itemView){
            super(itemView);
            mAppointmentItem = itemView.findViewById(R.id.appointment_item_layout);
            mAppointmentTitle = itemView.findViewById(R.id.appointmentTitle);
            mAppointmentInfo = itemView.findViewById(R.id.apoointmentInfoTextV);
            mAppointmentTimeStr = itemView.findViewById(R.id.appointmentTime);

        }

    }





    //Create our viewholder. layout manager calls
    public AppointmentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //Set contents of viewholder
    public void onBindViewHolder(@NonNull final AppointmentListAdapter.MyViewHolder vholder, int position) {

        Date d = new Date(mAppointments.get(position).getTimeinMillis());

        String formatHour = String.valueOf(d.getHours());
        String formatMinute = String.valueOf(d.getMinutes());

        if(formatHour.length()<2){
            formatHour = "0" + formatHour;
        }
        if(formatMinute.length()<2){
            formatMinute = "0" + formatMinute;
        }

        String timeText = formatHour + ":" + formatMinute;


        vholder.mAppointmentTitle.setText(mAppointments.get(position).getAppointmenttitle());
        vholder.mAppointmentInfo.setText(mAppointments.get(position).getAdditionalInfo());
        vholder.mAppointmentTimeStr.setText(timeText);
    }


    @Override
    public int getItemCount() {
        return mAppointments.size();
    }
}
