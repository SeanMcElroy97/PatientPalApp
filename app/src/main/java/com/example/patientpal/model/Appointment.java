package com.example.patientpal.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.time.LocalTime;
import java.util.Date;

public class Appointment implements Parcelable {

    private int appointmentID;
    private String appointmenttitle;
    private String additionalInfo;
    private Long timeinMillis;


    public Appointment(String appointmenttitle, String additionalInfo, Long timeinMillis) {
        this.appointmenttitle = appointmenttitle;
        this.additionalInfo = additionalInfo;
        this.timeinMillis = timeinMillis;
    }

    public Appointment() {
    }


    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmenttitle() {
        return appointmenttitle;
    }

    public void setAppointmenttitle(String appointmenttitle) {
        this.appointmenttitle = appointmenttitle;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Long getTimeinMillis() {
        return timeinMillis;
    }

    public void setTimeinMillis(Long timeinMillis) {
        this.timeinMillis = timeinMillis;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "appointmenttitle='" + appointmenttitle + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", timeinMillis='" + timeinMillis + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(appointmenttitle);
        parcel.writeString(additionalInfo);
        parcel.writeLong(timeinMillis);

    }


    protected Appointment(Parcel in) {
        appointmenttitle = in.readString();
        additionalInfo = in.readString();
        timeinMillis = in.readLong();

    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}
