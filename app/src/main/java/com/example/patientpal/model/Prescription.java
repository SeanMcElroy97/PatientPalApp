package com.example.patientpal.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class Prescription {

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
}
