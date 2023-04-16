package com.example.travel.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.Context;

import com.example.travel.models.User;
import com.example.travel.models.Note;

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
        query += "name TEXT, country INTEGER, city TEXT, description TEXT, user INTEGER, created_at INTEGER,";
        query += "FOREIGN KEY(country) REFERENCES countries(id),";
        query += "FOREIGN KEY(user) REFERENCES users(id));";
        db.execSQL(query);
    }

    public void createUser(User user) {
        String query = "INSERT INTO users (name, age, photopath) VALUES (";
        query += "'" + user.name + "', ";
        query += Integer.toString(user.age) + ", ";
        query += user.photoPath + ");";
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

    public void createNote(Note note) {
        String query = "INSERT INTO notes (name, country, city, description, user) VALUES (";
        query += "'" + note.name + "', ";
        query += Integer.toString(note.countryID) + ", ";
        query += "'" + note.city + "', ";
        query += "'" + note.description + "', ";
        query += Integer.toString(note.userID) + ", ";
        query += Integer.toString(note.created_at);
        query += ");";
        db.execSQL(query);
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();
        String query = "SELECT id, name FROM notes ORDER BY created DESC;";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            notes.add(new Note(id, name));
        }
        return notes;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM users ORDER BY name ASC;";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String photoPath = cursor.getString(3);
            users.add(new User(id, name, age, photoPath));
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;    
        String query = "SELECT * FROM users WHERE id =" + id + ";"; 
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.photoPath = cursor.getString(3);
        }
        return user;
    }

    public String getCountryById(int id) {
        String country = "";  
        String query = "SELECT name FROM countries WHERE id =" + id + ";"; 
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            country = cursor.getString(0);
        }
        return country;
    }

    public Note getNoteById(int id) {
        Note note = null;    
        String query = "SELECT * FROM notes WHERE id =" + id + ";"; 
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            note = new Note();
            note.id = cursor.getInt(0);
            note.name = cursor.getString(1);
            note.countryID = cursor.getInt(2);
            note.city = cursor.getString(3);
            note.description = cursor.getString(3);
            note.userID = cursor.getInt(5);

            note.country = this.getCountryById(note.user.id);
            note.user = this.getUserById(note.user.id);

        }
        return note;
    }
}
