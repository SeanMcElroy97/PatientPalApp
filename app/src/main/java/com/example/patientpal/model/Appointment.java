package com.example.patientpal.model;


import com.github.sundeepk.compactcalendarview.domain.Event;

import java.time.LocalTime;
import java.util.Date;

public class Appointment {

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
}
