package com.wouterdevos.starwarsfilms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wouterdevos.starwarsfilms.StarWarsFilmsApplication;
import com.wouterdevos.starwarsfilms.valueobject.ETag;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;
import com.wouterdevos.starwarsfilms.valueobject.People;
import com.wouterdevos.starwarsfilms.valueobject.PeopleResponse;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

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
    public void onCreate(SQLiteDatabase database) {
        FilmsTable.onCreate(database);
        PeopleTable.onCreate(database);
        FilmPeopleTable.onCreate(database);
        ETagsTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        FilmsTable.onUpgrade(database, oldVersion, newVersion);
        PeopleTable.onUpgrade(database, oldVersion, newVersion);
        FilmPeopleTable.onUpgrade(database, oldVersion, newVersion);
        ETagsTable.onUpgrade(database, oldVersion, newVersion);
    }

    public void insertFilms(FilmsResponse filmsResponse) {
        SQLiteDatabase database = getWritableDatabase();

        try {
            database.beginTransaction();
            List<Film> films = filmsResponse.getFilms();
            for (Film film : films) {
                FilmsTable.insertFilm(database, film);
            }
        } catch (Exception e) {
            Log.i(TAG, "Error inserting films! " + e.toString());
        }
    }

    public List<Film> readFilms() {
        SQLiteDatabase database = getReadableDatabase();
        List<Film> films = FilmsTable.readFilms(database);
        return films;
    }

    public void insertPeople(long filmId, PeopleResponse peopleResponse) {
        SQLiteDatabase database = getWritableDatabase();

        try {
            database.beginTransaction();
            List<People> people = peopleResponse.getPeople();
            for (People person : people) {
                long peopleId = PeopleTable.insertPeople(database, person);
                insertFilmPeople(database, filmId, peopleId);
            }
        } catch (Exception e) {
            Log.i(TAG, "Error inserting people! " + e.toString());
        }
    }

    public List<People> readPeople(Film film) {
        SQLiteDatabase database = getReadableDatabase();
        List<People> people = PeopleTable.readPeopleByFilm(database, film);
        return people;
    }

    private void insertFilmPeople(SQLiteDatabase database, long filmId, long peopleId) {
        try {
            database.beginTransaction();
            FilmPeopleTable.insertFilmPeople(database, filmId, peopleId);
        } catch (Exception e) {
            Log.i(TAG, "Error inserting film people! " + e.toString());
        }
    }

    public void insertETag(Long id, String url, String value) {
        SQLiteDatabase database = getWritableDatabase();

        ETag eTag = new ETag();
        eTag.setId(id);
        eTag.setUrl(url);
        eTag.setValue(value);
        try {
            database.beginTransaction();
            ETagsTable.insertETag(database, eTag);
        } catch (Exception e) {
            Log.i(TAG, "Error inserting e tag! " + e.toString());
        }
    }

    public ETag readETag(String url) {
        SQLiteDatabase database = getReadableDatabase();
        List<ETag> eTags = ETagsTable.readETag(database, url);
        return eTags != null && eTags.size() > 0 ? eTags.get(0) : null;
    }
}
