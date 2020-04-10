package com.example.patientpal.model;


import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;

public class Appointment {

    private Event calendarEvent;
    private String additionalInfo;
    private Date time;


    public Event getCalendarEvent() {
        return calendarEvent;
    }

    public void setCalendarEvent(Event calendarEvent) {
        this.calendarEvent = calendarEvent;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
