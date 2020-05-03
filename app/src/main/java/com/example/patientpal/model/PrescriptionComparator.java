package com.example.patientpal.model;

import java.util.Comparator;

public class PrescriptionComparator implements Comparator<Prescription> {

    @Override
    public int compare(Prescription prescription, Prescription t1) {
        return Long.compare(t1.getPrescriptionCreationTime(), prescription.getPrescriptionCreationTime());
    }

//    @Override
//    public Comparator<Prescription> reversed() {
//        return null;
//    }
}
