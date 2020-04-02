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
import com.example.patientpal.model.CountryCovidStats;
import com.example.patientpal.model.PatientMenuItem;
import com.example.patientpal.patientActivities.PatientHome;
import com.example.patientpal.patientActivities.covidActivities.CovidMainActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.MyViewHolder>{

    private ArrayList<CountryCovidStats> mCountryItems;
    private Context mContext;
    //CovidMainActivity mainCovidActivity;
    Dialog mCountryDialog;


    //Adapter Constructor
    public CountryListAdapter(Context ctx, ArrayList<CountryCovidStats> afectedCountries){
        mContext = ctx;
        mCountryItems = afectedCountries;
    }




    //Inner ViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout countryItem;
        ImageView mImageView;
        TextView mCountryName, mCountryTotalTxt, mCountryTotalsVal;

        public MyViewHolder(View itemView){
            super(itemView);
            countryItem = itemView.findViewById(R.id.country_item);
            mImageView = itemView.findViewById(R.id.country_flag_icon);
            mCountryName = itemView.findViewById(R.id.country_name_row_text);
            mCountryTotalTxt = itemView.findViewById(R.id.totalCasesText);
            mCountryTotalsVal = itemView.findViewById(R.id.totalCasesVal);
            //itemView.setOnClickListener(this);
        }



    }





    //Create our viewholder. layout manager calls
    public CountryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //Set contents of viewholder
    public void onBindViewHolder(@NonNull final CountryListAdapter.MyViewHolder vholder, int position) {


        mCountryDialog = new Dialog(mContext);
        mCountryDialog.setContentView(R.layout.dialog_country);
        /////////


        vholder.countryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_name = mCountryDialog.findViewById(R.id.dialog_country_name);
                ImageView dialog_flag = mCountryDialog.findViewById(R.id.dialog_country_flag);
                TextView txtclose = mCountryDialog.findViewById(R.id.dialog_close_x);
                TextView dialog_confirmed_val = mCountryDialog.findViewById(R.id.dialog_country_confirmed_num);
                TextView dialog_death_val = mCountryDialog.findViewById(R.id.dialog_country_deaths_num);
                TextView dialog_recovered_val = mCountryDialog.findViewById(R.id.dialog_country_recovered_num);
                /////////
                dialog_name.setText(mCountryItems.get(vholder.getAdapterPosition()).getCountryName());
                dialog_confirmed_val.setText(formatNumbers(mCountryItems.get(vholder.getAdapterPosition()).getTotalConfirmedCases()));
                dialog_death_val.setText(formatNumbers(mCountryItems.get(vholder.getAdapterPosition()).getTotalDeaths()));
                dialog_recovered_val.setText(formatNumbers(mCountryItems.get(vholder.getAdapterPosition()).getTotalRecovered()));
                //
                if(mCountryItems.get(vholder.getAdapterPosition()).getCountryCode().length()<1){
                    dialog_flag.setImageResource(R.drawable.about_icon);
                }else{
                    Picasso.get().load("https://www.countryflags.io/" + mCountryItems.get(vholder.getAdapterPosition()).getCountryCode() + "/shiny/64.png").into(dialog_flag);
                }
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCountryDialog.dismiss();
                    }
                });

                mCountryDialog.show();
            }
        });

        if(mCountryItems.get(position).getCountryCode().length()<1){
            vholder.mImageView.setImageResource(R.drawable.about_icon);
        }else{
            Picasso.get().load("https://www.countryflags.io/" + mCountryItems.get(position).getCountryCode() + "/shiny/64.png").into(vholder.mImageView);
        }
        vholder.mCountryName.setText(mCountryItems.get(position).getCountryName());
        vholder.mCountryTotalsVal.setText(formatNumbers(mCountryItems.get(position).getTotalConfirmedCases()));
    }

    public String formatNumbers(int x){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String numberAsFormatedText = formatter.format(x);
        return numberAsFormatedText;
    }

    @Override
    public int getItemCount() {
        return mCountryItems.size();
    }
}
