package com.example.lubussupporter;

public class StoreToDB_UsersInfo {

    public String userId, emailString, passwordString, key, usernameString, idString;

    public StoreToDB_UsersInfo() {
    }

    public StoreToDB_UsersInfo(String userId, String emailString, String passwordString, String key, String usernameString, String idString) {
        this.userId = userId;
        this.emailString = emailString;
        this.passwordString = passwordString;
        this.key = key;
        this.usernameString = usernameString;
        this.idString = idString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString = passwordString;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsernameString() {
        return usernameString;
    }

    public void setUsernameString(String usernameString) {
        this.usernameString = usernameString;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }
}
