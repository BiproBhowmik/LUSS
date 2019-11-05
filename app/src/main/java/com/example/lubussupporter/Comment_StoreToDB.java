package com.example.lubussupporter;

public class Comment_StoreToDB {

    private String comment, username, studentID, key, date, time;

    public Comment_StoreToDB() {
    }

    public Comment_StoreToDB(String comment, String username, String studentID, String key, String date, String time) {
        this.comment = comment;
        this.username = username;
        this.studentID = studentID;
        this.key = key;
        this.date = date;
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
