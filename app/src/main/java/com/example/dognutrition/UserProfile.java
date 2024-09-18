package com.example.dognutrition;

// com.example.dognutrition.UserProfile.java
public class UserProfile {
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(com.example.dognutrition.UserProfile.class)
    }

    public UserProfile(String userName, String email, String phoneNumber, String address) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
