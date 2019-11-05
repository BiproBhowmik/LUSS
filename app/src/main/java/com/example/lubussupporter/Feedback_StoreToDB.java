package com.example.lubussupporter;

public class Feedback_StoreToDB {

    private String id;
    private float rating;

    public Feedback_StoreToDB() {
    }

    public Feedback_StoreToDB(String id, float rating) {
        this.id = id;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
