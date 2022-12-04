package com.nordesk.nightlife.model;

public class User {
    private String uID;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;

    public User(String userName, String email, String phoneNumber, String password) {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassToken() {
        return password;
    }

    public void setPassToken(String passToken) {
        this.password = passToken;
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
}
