package com.example.patientpal.patientActivities.covidActivities;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patientpal.R;
import com.example.patientpal.adapters.CountryListAdapter;
import com.example.patientpal.model.CountryCovidStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;


public class AllCountriesListFragment extends Fragment {

    private RecyclerView mRecView;
    private Set<CountryCovidStats> mCountries;



    public AllCountriesListFragment(Set<CountryCovidStats> countryStats) {
        // Required empty public constructor

        mCountries = countryStats;

        for (CountryCovidStats x: countryStats){
            System.out.println(x.getCountryName() + " : " + x.getCountryCode());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_countries_list, container, false);


        mRecView = view.findViewById(R.id.countryListRecView);

        ArrayList<CountryCovidStats> arrayListOfCountries = new ArrayList<>();

        arrayListOfCountries.addAll(mCountries);

        Collections.sort(arrayListOfCountries, new Comparator<CountryCovidStats>() {
            @Override
            public int compare(CountryCovidStats countryCovidStats, CountryCovidStats t1) {
                return t1.getTotalConfirmedCases()- countryCovidStats.getTotalConfirmedCases()  ;
            }
        });


        CountryListAdapter recViewAdapter = new CountryListAdapter(getContext(), putIrelandOnTopOfList(arrayListOfCountries));

        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecView.setAdapter(recViewAdapter);

        return view;
    }


    public ArrayList<CountryCovidStats> putIrelandOnTopOfList(ArrayList<CountryCovidStats> list){

        for(int i=0; i<list.size(); i++){
            if (list.get(i).getCountryName().equalsIgnoreCase("Ireland")){
                CountryCovidStats ireland = list.get(i);
                list.remove(i);
                list.add(0, ireland);
            }
        }

        return list;
    }


}
