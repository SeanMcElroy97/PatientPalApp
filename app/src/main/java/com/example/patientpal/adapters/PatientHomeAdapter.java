package com.example.patientpal.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientpal.R;
import com.example.patientpal.model.PatientMenuItem;
import com.example.patientpal.patientActivities.PatientHome;

import java.util.ArrayList;

public class PatientHomeAdapter extends RecyclerView.Adapter<PatientHomeAdapter.MyViewHolder>{

    private ArrayList<PatientMenuItem> mMenuItems;
    private Context context;
    PatientHome patientHomeActivity;


    //Adapter Constructor
    public PatientHomeAdapter(PatientHome phome, ArrayList<PatientMenuItem> menuItems){
        patientHomeActivity = phome;
        mMenuItems = menuItems;
    }




    //Inner ViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageView;
        TextView mMainText;
        TextView mSubText;

        public MyViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.menu_icon);
            mMainText = itemView.findViewById(R.id.menu_main_text);
            mSubText = itemView.findViewById(R.id.menu_description_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int index = this.getAdapterPosition();

            switch (index){
                case 0:
                    Toast.makeText(patientHomeActivity, "Prescriptions", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    //Toast.makeText(context, "Reminders", Toast.LENGTH_LONG).show();
                    patientHomeActivity.gotoReminderActivity(v);
                    break;
                case 2:
                    Toast t = Toast.makeText(patientHomeActivity, "Appointments", Toast.LENGTH_LONG);
                    break;
                case 3:
                    //Toast.makeText(context, "Reminders", Toast.LENGTH_LONG).show();
                    patientHomeActivity.viewNearbyPharmaciesonMap(v);
                    break;
                case 4:
                    //Toast.makeText(patientHomeActivity, "About", Toast.LENGTH_LONG).show();
                    patientHomeActivity.gotoOldReminderActivity(v);
                    //patientHomeActivity.viewNearbyPharmaciesonMap(v);
                    break;
                case 5:
                    //Toast.makeText(context, "Sign Out", Toast.LENGTH_LONG).show();
                    patientHomeActivity.signOut(v);
                    break;
            }
        }

    }





    //Create our viewholder. layout manager calls
    public PatientHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_home_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //Set contents of viewholder
    public void onBindViewHolder(@NonNull PatientHomeAdapter.MyViewHolder vholder, int position) {
        //PatientMenuItem currentMenuItem = mMenuItems.get(position);

        vholder.mImageView.setImageResource(mMenuItems.get(position).getmImage());
        vholder.mMainText.setText(mMenuItems.get(position).getmOptionTitle());
        vholder.mSubText.setText(mMenuItems.get(position).getmOptionDescription());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }
}
