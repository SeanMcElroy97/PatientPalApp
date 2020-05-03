package com.example.patientpal.model;

public class LineItem {

    private int lineItemId;
    private String medicineName;
    private int quantity;
    private String medInstructions;

    public LineItem() {
    }

    public LineItem(int lineItemId, String medicineName, int quantity, String medInstructions) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.medInstructions = medInstructions;
        this.lineItemId = lineItemId;
    }


    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMedInstructions() {
        return medInstructions;
    }

    public void setMedInstructions(String medInstructions) {
        this.medInstructions = medInstructions;
    }
}
