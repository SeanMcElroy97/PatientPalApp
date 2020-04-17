package com.example.patientpal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationCovidStats implements Parcelable {

    private String province;
    private String country;
    private int latestTotalCases;
    private int latestTotalDeaths;
    private int latestTotalRecoveries;


    public LocationCovidStats() {
    }

    protected LocationCovidStats(Parcel in) {
        province = in.readString();
        country = in.readString();
        latestTotalCases = in.readInt();
        latestTotalDeaths = in.readInt();
        latestTotalRecoveries = in.readInt();
    }

    public static final Creator<LocationCovidStats> CREATOR = new Creator<LocationCovidStats>() {
        @Override
        public LocationCovidStats createFromParcel(Parcel in) {
            return new LocationCovidStats(in);
        }

        @Override
        public LocationCovidStats[] newArray(int size) {
            return new LocationCovidStats[size];
        }
    };

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getLatestTotalDeaths() {
        return latestTotalDeaths;
    }

    public void setLatestTotalDeaths(int latestTotalDeaths) {
        this.latestTotalDeaths = latestTotalDeaths;
    }

    public int getLatestTotalRecoveries() {
        return latestTotalRecoveries;
    }

    public void setLatestTotalRecoveries(int latestTotalRecoveries) {
        this.latestTotalRecoveries = latestTotalRecoveries;
    }


    @Override
    public String toString() {
        return "LocationCovidStats{" +
                "province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", latestTotalDeaths=" + latestTotalDeaths +
                ", latestTotalRecoveries=" + latestTotalRecoveries +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(province);
        parcel.writeString(country);
        parcel.writeInt(latestTotalCases);
        parcel.writeInt(latestTotalDeaths);
        parcel.writeInt(latestTotalRecoveries);

    }
}
