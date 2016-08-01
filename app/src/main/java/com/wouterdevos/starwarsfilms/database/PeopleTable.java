package com.wouterdevos.starwarsfilms.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.People;

import java.util.ArrayList;
import java.util.List;

public class PeopleTable {

    public static final String TABLE_NAME = "people";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

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

    public static long insertPeople(SQLiteDatabase database, People people) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, people.getId());
        contentValues.put(COLUMN_NAME, people.getName());

        return database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static List<People> readPeopleByFilm(SQLiteDatabase database, Film film) {
        String query = "SELECT * FROM " + TABLE_NAME + " pl, " + FilmsTable.TABLE_NAME + " fs, "
                + FilmPeopleTable.TABLE_NAME + " fp WHERE fs." + FilmsTable.COLUMN_ID + " = " + film.getId()
                + "AND pl." + COLUMN_ID + " = " + " fp." + FilmPeopleTable.COLUMN_PEOPLE_ID
                + " AND fs." + FilmsTable.COLUMN_ID + " = " + FilmPeopleTable.COLUMN_FILM_ID;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor == null || cursor.getCount() < 1) {
            return null;
        }

        List<People> people = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                people.add(readPeople(cursor));
            }
            while (cursor.moveToNext());
        }

        return people;
    }

    private static People readPeople(Cursor cursor) {
        int i = 0;
        People people = new People();

        people.setId(cursor.getLong(i++));
        people.setName(cursor.getString(i++));

        return people;
    }
}
