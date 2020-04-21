package com.example.patientpal.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class Prescription {

    int prescriptionID;
    Map<String, Integer> medItems;
    String instructions;
    String pharmacyNameStr;
    String status;
    String pictureURL;
    Long prescriptionCreationTime;


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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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
}
