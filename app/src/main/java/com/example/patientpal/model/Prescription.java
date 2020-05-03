package com.example.patientpal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.Map;

public class Prescription implements Parcelable {

    int prescriptionID;
    Map<String, Integer> medItems;
    String patientMessage;
    String pharmacyNameStr;
    String status;
    String pictureURL;
    Long prescriptionCreationTime;
    Long prescriptionFulfillmentTime =0L;
    String Doctor;


    public Prescription() {
    }


    public Prescription(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }


    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public void setMedItems(Map<String, Integer> medItems) {
        this.medItems = medItems;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPrescriptionCreationTime() {
        return prescriptionCreationTime;
    }

    public void setPrescriptionCreationTime(Long prescriptionCreationTime) {
        this.prescriptionCreationTime = prescriptionCreationTime;
    }

    public Map<String, Integer> getMedItems() {
        return medItems;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getPharmacyNameStr() {
        return pharmacyNameStr;
    }

    public void setPharmacyNameStr(String pharmacyNameStr) {
        this.pharmacyNameStr = pharmacyNameStr;
    }

    public String getPatientMessage() {
        return patientMessage;
    }

    public void setPatientMessage(String patientMessage) {
        this.patientMessage = patientMessage;
    }

    public Long getPrescriptionFulfillmentTime() {
        return prescriptionFulfillmentTime;
    }

    public void setPrescriptionFulfillmentTime(Long prescriptionFulfillmentTime) {
        this.prescriptionFulfillmentTime = prescriptionFulfillmentTime;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    protected Prescription(Parcel in) {
        prescriptionID = in.readInt();
        patientMessage = in.readString();
        pharmacyNameStr = in.readString();
        status = in.readString();
        pictureURL = in.readString();
        if (in.readByte() == 0) {
            prescriptionCreationTime = null;
        } else {
            prescriptionCreationTime = in.readLong();
        }
        if (in.readByte() == 0) {
            prescriptionFulfillmentTime = null;
        } else {
            prescriptionFulfillmentTime = in.readLong();
        }
        Doctor = in.readString();
    }

    public static final Creator<Prescription> CREATOR = new Creator<Prescription>() {
        @Override
        public Prescription createFromParcel(Parcel in) {
            return new Prescription(in);
        }

        @Override
        public Prescription[] newArray(int size) {
            return new Prescription[size];
        }
    };


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(prescriptionID);
        parcel.writeString(patientMessage);
        parcel.writeString(pharmacyNameStr);
        parcel.writeString(status);
        parcel.writeString(pictureURL);
        if (prescriptionCreationTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(prescriptionCreationTime);
        }
        if (prescriptionFulfillmentTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(prescriptionFulfillmentTime);
        }
        parcel.writeString(Doctor);
    }
}

