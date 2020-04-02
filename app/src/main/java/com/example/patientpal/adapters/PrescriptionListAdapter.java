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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientpal.R;
import com.example.patientpal.model.Prescription;

import java.util.List;

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.ViewHolder> {

    private List<Prescription> mListOfPrescriptions;
    private Context mContext;


    public PrescriptionListAdapter(List<Prescription> list, Context context) {
        this.mListOfPrescriptions = list;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_row, parent, false);
        PrescriptionListAdapter.ViewHolder vh = new PrescriptionListAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mPrescriptionID.setText(String.valueOf(mListOfPrescriptions.get(position).getPrescriptionID()) );
        holder.mStatusValue.setText(mListOfPrescriptions.get(position).getStatus());


            //holder.mStatusValue.setTextColor(ContextCompat.getColor(mContext, R.color.blue));


        switch (mListOfPrescriptions.get(position).getStatus().toLowerCase()){
            case "being prepared":
                holder.mStatusValue.setTextColor(Color.BLUE);
                break;
            case"ready":
                holder.mStatusValue.setTextColor(ContextCompat.getColor(mContext, R.color.DarkGreen));
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mListOfPrescriptions.size();
    }


    //Inner ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mPrescriptionID;
        TextView mStatusValue;

        public ViewHolder(View itemView){
            super(itemView);
            mPrescriptionID = itemView.findViewById(R.id.prescription_row_text);
            mImageView = itemView.findViewById(R.id.status_circle_icon);
            mStatusValue = itemView.findViewById(R.id.status_val);

        }


    }


}
