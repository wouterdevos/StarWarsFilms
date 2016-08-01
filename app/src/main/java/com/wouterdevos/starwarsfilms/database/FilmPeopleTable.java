package com.wouterdevos.starwarsfilms.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class FilmPeopleTable {

    public static final String TABLE_NAME = "film_people";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FILM_ID = "film_id";
    public static final String COLUMN_PEOPLE_ID = "people_id";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_FILM_ID + " INTEGER, "
            + COLUMN_PEOPLE_ID + " INTEGER"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static void insertFilmPeople(SQLiteDatabase database, long filmId, long peopleId) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FILM_ID, filmId);
        contentValues.put(COLUMN_PEOPLE_ID, peopleId);

        database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
