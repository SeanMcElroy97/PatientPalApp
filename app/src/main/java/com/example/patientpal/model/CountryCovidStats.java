package com.example.patientpal.model;

import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.Volley;
import com.google.logging.type.HttpRequest;


import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;

public class CountryCovidStats {

    private String countryName;
    private ArrayList<LocationCovidStats> provinceStats = new ArrayList<>();
    private int totalConfirmedCases = 0;
    private int totalDeaths = 0;
    private int totalRecovered = 0;
    private String countryCode ="";

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<LocationCovidStats> getProvinceStats() {
        return provinceStats;
    }

    public void setProvinceStats(ArrayList<LocationCovidStats> provinceStats) {
        this.provinceStats = provinceStats;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(int totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }


    @Override
    public String toString() {
        return "CountryCovidStats{" +
                "countryName='" + countryName + '\'' +
                ", provinceStats=" + provinceStats +
                ", totalConfirmedCases=" + totalConfirmedCases +
                ", totalDeaths=" + totalDeaths +
                ", totalRecovered=" + totalRecovered +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
