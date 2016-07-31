package com.wouterdevos.starwarsfilms.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wouterdevos.starwarsfilms.util.Utils;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;

import java.util.ArrayList;
import java.util.List;

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

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static void insertFilms(SQLiteDatabase database, FilmsResponse filmsResponse) {
        List<Film> films = filmsResponse.getFilms();
        for (Film film : films) {
            insertFilm(database, film);
        }
    }

    public static void insertFilm(SQLiteDatabase database, Film film) {
        ContentValues contentValues = new ContentValues();

        long id = Utils.getIdFromUrl(film.getUrl());
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_TITLE, film.getTitle());
        contentValues.put(COLUMN_RELEASE_DATE, film.getReleaseDate());
        contentValues.put(COLUMN_OPENING_CRAWL, film.getOpeningCrawl());

        database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static List<Film> readFilms(SQLiteDatabase database) {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_RELEASE_DATE + " DESC";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor == null || cursor.getCount() < 1) {
            return null;
        }

        List<Film> films = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                films.add(readFilm(cursor));
            }
            while (cursor.moveToNext());
        }

        return films;
    }

    public static Film readFilm(Cursor cursor) {
        int i = 0;
        Film film = new Film();

        film.setId(cursor.getLong(i++));
        film.setTitle(cursor.getString(i++));
        film.setReleaseDate(cursor.getString(i++));
        film.setOpeningCrawl(cursor.getString(i++));

        return film;
    }
}
