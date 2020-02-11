package com.example.patientpal.model;

public class PatientMenuItem {

    private int mImage;
    private String mOptionTitle;
    private String mOptionDescription;

    public PatientMenuItem(int image, String optionTitle, String optionDescription) {
        this.mImage = image;
        this.mOptionTitle = optionTitle;
        this.mOptionDescription = optionDescription;
    }


    public int getmImage() {
        return mImage;
    }

    public String getmOptionTitle() {
        return mOptionTitle;
    }

    public String getmOptionDescription() {
        return mOptionDescription;
    }

}


