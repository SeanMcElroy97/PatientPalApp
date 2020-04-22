package com.example.patientpal.covidActivities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patientpal.R;
import com.example.patientpal.model.LocationCovidStats;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class TotalsCovidFragment extends Fragment{

    private ArrayList<LocationCovidStats> locationStats;
    private TextView txtVConfirmed, txtVDeaths, txtVRecovered, txtVConfirmedNum, txtVDeathsNum, txtVRecoveredNum;

    //Data total vars
    int globalTotalConfirmed;
    int globalTotalRecovered;
    int globalTotalDeaths;

    public TotalsCovidFragment(ArrayList<LocationCovidStats> locStats) {
        locationStats = locStats;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_totals_covid, container, false);


        globalTotalConfirmed = 0;
        globalTotalRecovered = 0;
        globalTotalDeaths = 0;

        setTotalCases(locationStats);

        txtVConfirmed = view.findViewById(R.id.TextViewConfirmed);
        txtVConfirmedNum = view.findViewById(R.id.TextViewConfirmedNum);

        txtVDeaths = view.findViewById(R.id.TextViewDeaths);
        txtVDeathsNum = view.findViewById(R.id.TextViewDeathsNum);

        txtVRecovered = view.findViewById(R.id.TextViewRecovered);
        txtVRecoveredNum = view.findViewById(R.id.TextViewRecoveredNum);

        txtVConfirmed.setText("Total Confirmed");
        txtVDeaths.setText("Total Deaths");
        txtVRecovered.setText("Total Recovered");

///////////////////////////////////////////////////////
        txtVConfirmedNum.setText(formatNumbers(globalTotalConfirmed));
        txtVRecoveredNum.setText(formatNumbers(globalTotalRecovered));
        txtVDeathsNum.setText(formatNumbers(globalTotalDeaths));

        return view;
    }


    public void setTotalCases(ArrayList<LocationCovidStats> arrlist){
        for(LocationCovidStats location: arrlist){
            globalTotalConfirmed+= location.getLatestTotalCases();
            globalTotalDeaths += location.getLatestTotalDeaths();
            globalTotalRecovered += location.getLatestTotalRecoveries();
        }
//        System.out.println("Confirmed Cases: \t" + globalTotalConfirmed);
//        System.out.println("Total Deaths: \t" + globalTotalDeaths);
//        System.out.println("Total Recovered: \t" + globalTotalRecovered);

    }

    public String formatNumbers(int x){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String numberAsFormatedText = formatter.format(x);
        return numberAsFormatedText;
    }
}
