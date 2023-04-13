package com.example.travel.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.Context;

public class TravelDB {
    private SQLiteDatabase db;

    public TravelDB(AppCompatActivity mainObj) {
        db = mainObj.getBaseContext().openOrCreateDatabase("app.db", Context.MODE_PRIVATE, null);
        makeMigration();
    }

    private void makeMigration() {
        createUsersTable();
        createCountriesTable();
        createNotesTable();
    }

    private void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users(name TEXT, age INTEGER, photo TEXT);";
        db.execSQL(query);
    }

    private void createCountriesTable() {
        String query = "CREATE TABLE IF NOT EXISTS countries(name TEXT);";
        db.execSQL(query);
    }

    private void createNotesTable() {
        String query = "CREATE TABLE IF NOT EXISTS notes(";
        query += "name TEXT, country INTEGER, city TEXT, description TEXT, user INTEGER,";
        query += "FOREIGN KEY(country) REFERENCES countries(id),";
        query += "FOREIGN KEY(user) REFERENCES users(id));";
        db.execSQL(query);
    }

    public void createUser(String username, int age, String path) {
        String query = "INSERT INTO users (name, age, path) VALUES (";
        query += "'" + username + "', ";
        query += Integer.toString(age) + ", ";
        query += path + ");";
        db.execSQL(query);
    }

    public ArrayList<String> getCountries() {
        ArrayList<String> countries = new ArrayList<String>();
        String query = "SELECT name FROM countries ORDER BY name ASC;";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            String name = cursor.getString(0);
            countries.add(name);
        }
        return countries;
    }

    public int getCountryIdByName(String country) {
        int id = -1;        
        String query = "SELECT id FROM countries WHERE name ='" + country + "';"; 
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    public void createNote(String name, int countryID, String city, String description, int userID) {
        String query = "INSERT INTO notes (name, country, city, description, user) VALUES (";
        query += "'" + name + "', ";
        query += Integer.toString(countryID) + ", ";
        query += "'" + city + "', ";
        query += "'" + description + "', ";
        query += Integer.toString(userID);
        query += ");";
        db.execSQL(query);
    }
}
