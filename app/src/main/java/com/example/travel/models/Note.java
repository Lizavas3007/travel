package com.example.travel.models;

public class Note {
    public int id;
    public String name;
    public int countryID;
    public String country;
    public String city;
    public String description;
    public int userID;
    public String user;

    public Note(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
