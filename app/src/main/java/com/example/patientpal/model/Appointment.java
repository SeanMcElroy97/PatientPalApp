package com.example.patientpal.model;


import com.github.sundeepk.compactcalendarview.domain.Event;

import java.time.LocalTime;
import java.util.Date;

public class Appointment {

    private Event calendarEvent;
    private String additionalInfo;
    private LocalTime time;


    public Appointment() {
    }

    public Appointment(Event calendarEvent, String additionalInfo, LocalTime time) {
        this.calendarEvent = calendarEvent;
        this.additionalInfo = additionalInfo;
        this.time = time;
    }

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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
