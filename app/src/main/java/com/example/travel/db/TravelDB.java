package com.example.travel.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite;

public class TravelDB {
    private SQLiteDatabase db;

    public TravelDB() {
        // change getBaseContext()
        //db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
    }

    private void makeMigration() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users(name TEXT, age INTEGER, photo TEXT)";
        db.execSQL(createUsers);
    }
}
