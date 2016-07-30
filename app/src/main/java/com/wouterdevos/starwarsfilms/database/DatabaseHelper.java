package com.wouterdevos.starwarsfilms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wouterdevos.starwarsfilms.StarWarsFilmsApplication;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "starwars.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper sDatabaseHelper;

    public static DatabaseHelper getInstance() {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(StarWarsFilmsApplication.getContext());
        }

        return sDatabaseHelper;
    }


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
