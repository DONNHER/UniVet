package com.example.uni.management;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class SQLiteDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_ADDRESS = "address";

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_EMAIL + " TEXT,"+
                COL_PHONE+ " TEXT,"+
                COL_ADDRESS + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Load all users
    public ArrayList<owner> getAllUsers() {
        ArrayList<owner> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS));
                userList.add(new owner(id, name, email, phone, address));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }

    // Insert new user
    public long insertUser(owner user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        return db.insert(TABLE_USERS, null, values);
    }

    // Update user by ID
    public boolean updateUser(owner user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        int rows = db.update(TABLE_USERS, values, COL_ID + "=?", new String[]{String.valueOf(user.getId())});
        return rows > 0;
    }
}
