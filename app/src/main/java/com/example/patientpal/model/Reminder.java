package com.example.patientpal.model;

import java.util.List;

public class Reminder {

    private String medicationName;
    //Could search from autocomplete medicine. not definte

    private List<Long> reminderTimes;

    private Long initialReminderTime;

    private int duration;

    private int daysRemaining;


    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public List<Long> getReminderTimes() {
        return reminderTimes;
    }

    public void setReminderTimes(List<Long> reminderTimes) {
        this.reminderTimes = reminderTimes;
    }

    public Long getInitialReminderTime() {
        return initialReminderTime;
    }

    public void setInitialReminderTime(Long initialReminderTime) {
        this.initialReminderTime = initialReminderTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(int daysRemaining) {
        this.daysRemaining = daysRemaining;
    }
}
