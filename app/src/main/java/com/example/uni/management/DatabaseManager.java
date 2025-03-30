package com.example.uni.management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.uni.management.SQLiteDB;

public class DatabaseManager {
    private static DatabaseManager instance;
    private SQLiteDatabase database;
    private static SQLiteDB dbHelper;

    private DatabaseManager(Context context) {
        dbHelper = new SQLiteDB(context);
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}