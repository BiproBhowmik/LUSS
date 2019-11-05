package com.example.lubussupporter;

public class GroupMessageStore {
    private String userName, messageS, id, date, time;

    public GroupMessageStore() {
    }

    public GroupMessageStore(String userName, String messageS, String id, String date, String time) {
        this.userName = userName;
        this.messageS = messageS;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageS() {
        return messageS;
    }

    public void setMessageS(String messageS) {
        this.messageS = messageS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
