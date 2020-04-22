package com.example.patientpal.covidActivities;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.patientpal.R;
import com.example.patientpal.model.CountryCovidStats;
import com.example.patientpal.model.LocationCovidStats;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CovidFragmentCollectionAdapter extends FragmentPagerAdapter {

    ArrayList<LocationCovidStats> covidTotals;
    Set<CountryCovidStats> setOfCountries;


    Context context;

    final String COUNTRY_TO_COUNTRYCODE_URL = "https://pkgstore.datahub.io/core/country-list/data_csv/data/d7c9d7cfb42cb69f4422dec222dbbaa8/data_csv.csv";

    public CovidFragmentCollectionAdapter(@NonNull FragmentManager fm, Bundle b1, Context ctx) {
        super(fm);
        covidTotals = b1.getParcelableArrayList("globalCovidArray");
        context=ctx;


        setOfCountries= filterCountryList(covidTotals);

        try {
            setOfCountries = putFlagsOnCountries(setOfCountries);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0: return new TotalsCovidFragment(covidTotals);
            case 1: return new AllCountriesListFragment(setOfCountries);
            case 2: return new CovidAdviceFragment();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }



    public Set<CountryCovidStats> filterCountryList(ArrayList<LocationCovidStats> provinceData){

        Set<CountryCovidStats> countriesSet = new HashSet<>();

        Set<String> countryNames = new HashSet<String>();

        for(LocationCovidStats province: provinceData){

            countryNames.add(province.getCountry());

        }


        for(String countryName: countryNames){
            CountryCovidStats aCountry = new CountryCovidStats();
            aCountry.setCountryName(countryName);
            countriesSet.add(aCountry);
        }


        for(LocationCovidStats province: provinceData){

            for (CountryCovidStats countryObj : countriesSet){
                if(province.getCountry().equals(countryObj.getCountryName())){
                    countryObj.getProvinceStats().add(province);
                    countryObj.setTotalConfirmedCases(countryObj.getTotalConfirmedCases() + province.getLatestTotalCases());
                    countryObj.setTotalDeaths(countryObj.getTotalDeaths()+ province.getLatestTotalDeaths());
                    countryObj.setTotalRecovered(countryObj.getTotalRecovered() + province.getLatestTotalRecoveries());
                }
            }

        }

        return countriesSet;
    }



    public Set<CountryCovidStats> putFlagsOnCountries(Set<CountryCovidStats> countries) throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.country_name_code);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));


        //Reader in = new FileReader("path/to/file.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : records) {
            String countryName = record.get("Name");
            String countryCode = record.get("Code");

            for(CountryCovidStats j : countries){
                if(countryName.contains(j.getCountryName())){
                    j.setCountryCode(countryCode);
                }
            }

        }
        return countries;
    }
}
