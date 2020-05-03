package com.example.patientpal.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientpal.R;
import com.example.patientpal.model.Prescription;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.ViewHolder> {

    private List<Prescription> mListOfPrescriptions;
    private Context mContext;
    private OnPrescriptionListener onPrescriptionListener;

    public PrescriptionListAdapter(List<Prescription> list, Context context, OnPrescriptionListener onPrescriptionListener) {
        this.mListOfPrescriptions = list;
        this.mContext = context;
        this.onPrescriptionListener = onPrescriptionListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_row, parent, false);
        PrescriptionListAdapter.ViewHolder vh = new PrescriptionListAdapter.ViewHolder(v, onPrescriptionListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DateFormat df = new SimpleDateFormat("HH:mm dd/MM");

       Date creationDate = new Date(mListOfPrescriptions.get(position).getPrescriptionCreationTime());
       Date fulfillmentDate = new Date(mListOfPrescriptions.get(position).getPrescriptionFulfillmentTime());
       String pharmacyName = mListOfPrescriptions.get(position).getPharmacyNameStr();
//
        if(mListOfPrescriptions.get(position).getPrescriptionFulfillmentTime()==null || mListOfPrescriptions.get(position).getPrescriptionFulfillmentTime()<1){
            holder.mLatestPrescriptionDate.setText("Created   " +df.format(creationDate));
        }else{
            holder.mLatestPrescriptionDate.setText("Fulfilled   " +df.format(fulfillmentDate));
        }


////        holder.mFulfillmentDate.setText("Dispensed " +df.format(fulfillmentDate));
       holder.mStatusValue.setText("Status   " +mListOfPrescriptions.get(position).getStatus());
       holder.mStatusValue.setTextColor(Color.WHITE);
       holder.mPharmacyName.setText(pharmacyName);


            //holder.mStatusValue.setTextColor(ContextCompat.getColor(mContext, R.color.blue));


        switch (mListOfPrescriptions.get(position).getStatus().toLowerCase().replace(" ", "")){
            case "submitted":
//                holder.mStatusValue.setTextColor(Color.YELLOW);
                holder.layoutCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.lightGold));
                break;
            case "beingprepared":
//                holder.mStatusValue.setTextColor(Color.BLUE);
                holder.layoutCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.lightBlueCard));
                break;
            case "ready":
//                holder.mStatusValue.setTextColor(ContextCompat.getColor(mContext, R.color.DarkGreen));
                holder.layoutCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.lightGreenCard));
                break;
            case "fulfilled":
//                holder.mStatusValue.setTextColor(Color.GRAY);
                holder.layoutCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.lightPurpleGrey));
                break;
            case "cancelled":
//                holder.mStatusValue.setTextColor(Color.RED);
                holder.layoutCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.lightRedCard));
                break;

        }


    }

    @Override
    public int getItemCount() {
        return mListOfPrescriptions.size();
    }


    //Inner ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView mLatestPrescriptionDate;
        TextView mStatusValue;
        TextView mPharmacyName;
        CardView layoutCardView;
        OnPrescriptionListener onPrescriptionListener;

        public ViewHolder(View itemView, OnPrescriptionListener onPrescriptionListener){
            super(itemView);
            mStatusValue = itemView.findViewById(R.id.cardPrescriptionStatus);
            mLatestPrescriptionDate = itemView.findViewById(R.id.latestprescriptionDate);
            mPharmacyName = itemView.findViewById(R.id.cardPrescriptionPharmacyName);
            layoutCardView = itemView.findViewById(R.id.prescriptionCardView);

            this.onPrescriptionListener = onPrescriptionListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPrescriptionListener.onPrescriptionClick(getAdapterPosition());
        }
    }

    //interface for clicking
    public interface OnPrescriptionListener{
        void onPrescriptionClick(int position);
    }


}
