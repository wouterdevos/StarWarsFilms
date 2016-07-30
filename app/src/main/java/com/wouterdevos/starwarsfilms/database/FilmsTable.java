package com.wouterdevos.starwarsfilms.database;

import android.database.sqlite.SQLiteDatabase;

import com.wouterdevos.starwarsfilms.valueobject.Film;

public class FilmsTable {

    private static final String TABLE_NAME = "films";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_RELEASE_DATE = "release_date";
    private static final String COLUMN_OPENING_CRAWL = "opening_crawl";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_TITLE + " TEXT, "
            + COLUMN_RELEASE_DATE + " TEXT, "
            + COLUMN_OPENING_CRAWL + " TEXT"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static void insertFilm(SQLiteDatabase database, Film film) {

    }
}
