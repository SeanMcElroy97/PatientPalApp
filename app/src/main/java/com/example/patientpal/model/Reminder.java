package com.example.patientpal.model;

import java.util.List;

public class Reminder {


    private int reminderID;

    private String reminderMedicationName;
    //Could search from autocomplete medicine. not definte

    private List<Long> reminderTimes;

    private String reminderTimeDisplay;

    private int reminderDuration;
    private int reminderDaysRemaining;


    public Reminder(String reminderMedicationName, List<Long> reminderTimes, String reminderTimeDisplay, int reminderDuration, int reminderDaysRemaining) {
        this.reminderMedicationName = reminderMedicationName;
        this.reminderTimes = reminderTimes;
        this.reminderTimeDisplay = reminderTimeDisplay;
        this.reminderDuration = reminderDuration;
        this.reminderDaysRemaining = reminderDaysRemaining;
    }



    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public String getReminderMedicationName() {
        return reminderMedicationName;
    }

    public void setReminderMedicationName(String reminderMedicationName) {
        this.reminderMedicationName = reminderMedicationName;
    }

    public List<Long> getReminderTimes() {
        return reminderTimes;
    }

    public void setReminderTimes(List<Long> reminderTimes) {
        this.reminderTimes = reminderTimes;
    }

    public String getReminderTimeDisplay() {
        return reminderTimeDisplay;
    }

    public void setReminderTimeDisplay(String reminderTimeDisplay) {
        this.reminderTimeDisplay = reminderTimeDisplay;
    }

    public int getReminderDuration() {
        return reminderDuration;
    }

    public void setReminderDuration(int reminderDuration) {
        this.reminderDuration = reminderDuration;
    }

    public int getReminderDaysRemaining() {
        return reminderDaysRemaining;
    }

    public void setReminderDaysRemaining(int reminderDaysRemaining) {
        this.reminderDaysRemaining = reminderDaysRemaining;
    }


    @Override
    public String toString() {
        return "Reminder{" +
                "reminderID=" + reminderID +
                ", reminderMedicationName='" + reminderMedicationName + '\'' +
                ", reminderTimes=" + reminderTimes +
                ", reminderTimeDisplay='" + reminderTimeDisplay + '\'' +
                ", reminderDuration=" + reminderDuration +
                ", reminderDaysRemaining=" + reminderDaysRemaining +
                '}';
    }
}