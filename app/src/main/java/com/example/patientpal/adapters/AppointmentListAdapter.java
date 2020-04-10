package com.example.patientpal.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientpal.R;
import com.example.patientpal.model.CountryCovidStats;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder>{

    //May make an appointment obj
    private ArrayList<Event> mAppointments;
    private Context mContext;
    //Dialog mCountryDialog;


    //Adapter Constructor
    public AppointmentListAdapter(Context ctx, ArrayList<Event> dateEvents){
        mContext = ctx;
        mAppointments = dateEvents;
    }




    //Inner ViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mAppointmentItem;
        TextView mAppointmentTitle;

        public MyViewHolder(View itemView){
            super(itemView);
            mAppointmentItem = itemView.findViewById(R.id.appointment_item_layout);
            mAppointmentTitle = itemView.findViewById(R.id.appointmentTitle);

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


        //mCountryDialog = new Dialog(mContext);
        //mCountryDialog.setContentView(R.layout.dialog_country);
        /////////


//        vholder.countryItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView dialog_name = mCountryDialog.findViewById(R.id.dialog_country_name);
//                ImageView dialog_flag = mCountryDialog.findViewById(R.id.dialog_country_flag);
//                TextView txtclose = mCountryDialog.findViewById(R.id.dialog_close_x);
//                TextView dialog_confirmed_val = mCountryDialog.findViewById(R.id.dialog_country_confirmed_num);
//                TextView dialog_death_val = mCountryDialog.findViewById(R.id.dialog_country_deaths_num);
//                TextView dialog_recovered_val = mCountryDialog.findViewById(R.id.dialog_country_recovered_num);
//                /////////
//                dialog_name.setText(mCountryItems.get(vholder.getAdapterPosition()).getCountryName());
//                dialog_confirmed_val.setText(formatNumbers(mCountryItems.get(vholder.getAdapterPosition()).getTotalConfirmedCases()));
//                dialog_death_val.setText(formatNumbers(mCountryItems.get(vholder.getAdapterPosition()).getTotalDeaths()));
//                dialog_recovered_val.setText(formatNumbers(mCountryItems.get(vholder.getAdapterPosition()).getTotalRecovered()));
//                //
//                if(mCountryItems.get(vholder.getAdapterPosition()).getCountryCode().length()<1){
//                    dialog_flag.setImageResource(R.drawable.about_icon);
//                }else{
//                    Picasso.get().load("https://www.countryflags.io/" + mCountryItems.get(vholder.getAdapterPosition()).getCountryCode() + "/shiny/64.png").into(dialog_flag);
//                }
//                txtclose.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mCountryDialog.dismiss();
//                    }
//                });
//
//                mCountryDialog.show();
//            }
//        });


        vholder.mAppointmentTitle.setText(mAppointments.get(position).getData().toString());
    }


    @Override
    public int getItemCount() {
        return mAppointments.size();
    }
}
