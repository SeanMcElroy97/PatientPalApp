package com.example.patientpal.model;

public class PharmacyUser {

    private String pharmacyName;
    private String pharmacyEmail;
    private String pharmacyAddress;
    private String pharmacyNumber;


    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyEmail() {
        return pharmacyEmail;
    }

    public void setPharmacyEmail(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyNumber() {
        return pharmacyNumber;
    }

    public void setPharmacyNumber(String pharmacyNumber) {
        this.pharmacyNumber = pharmacyNumber;
    }


    @Override
    public String toString() {
        return "PharmacyUser{" +
                "pharmacyName='" + pharmacyName + '\'' +
                ", pharmacyEmail='" + pharmacyEmail + '\'' +
                ", pharmacyAddress='" + pharmacyAddress + '\'' +
                ", pharmacyNumber='" + pharmacyNumber + '\'' +
                '}';
    }
}
