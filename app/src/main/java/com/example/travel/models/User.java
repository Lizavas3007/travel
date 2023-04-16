package com.example.travel.models;

public class User {
    public int id;
    public String name;
    public int age;
    public String photoPath;

    public User(){}
    public User(int id, String name, int age, String photoPath) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photoPath = photoPath;
    }
}
