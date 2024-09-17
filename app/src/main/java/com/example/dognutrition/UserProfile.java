package com.example.dognutrition;

// com.example.dognutrition.UserProfile.java
public class UserProfile {
    private String name;
    private String address;
    private String paymentMethod;

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(com.example.dognutrition.UserProfile.class)
    }

    public UserProfile(String name, String address, String paymentMethod) {
        this.name = name;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
