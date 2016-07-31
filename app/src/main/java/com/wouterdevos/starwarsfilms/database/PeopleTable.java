package com.wouterdevos.starwarsfilms.database;

import android.database.sqlite.SQLiteDatabase;

import com.wouterdevos.starwarsfilms.valueobject.People;

public class PeopleTable {

    private static final String TABLE_NAME = "people";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME + " TEXT"
            + ")";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
    }

    public static void insertPeople(SQLiteDatabase database, People people) {
        
    }
}
