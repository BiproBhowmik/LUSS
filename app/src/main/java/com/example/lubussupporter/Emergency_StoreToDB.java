package com.example.lubussupporter;

public class Emergency_StoreToDB {

    private String name, number;

    public Emergency_StoreToDB() {
    }

    public Emergency_StoreToDB(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
