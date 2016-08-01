package com.wouterdevos.starwarsfilms.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wouterdevos.starwarsfilms.valueobject.ETag;

import java.util.ArrayList;
import java.util.List;

public class ETagsTable {

    private static final String TABLE_NAME = "e_tags";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_E_TAG = "e_tag";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_URL + " TEXT, "
            + COLUMN_E_TAG + " TEXT"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static void insertETag(SQLiteDatabase database, ETag eTag) {
        ContentValues contentValues = new ContentValues();

        if (eTag.getId() != null) {
            contentValues.put(COLUMN_ID, eTag.getId());
        }
        contentValues.put(COLUMN_URL, eTag.getUrl());
        contentValues.put(COLUMN_E_TAG, eTag.getValue());

        database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static List<ETag> readETag(SQLiteDatabase database, String url) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_URL + " = " + url;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor == null || cursor.getCount() < 1) {
            return null;
        }

        List<ETag> eTags = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                eTags.add(readETag(cursor));
            }
            while (cursor.moveToNext());
        }

        return eTags;
    }

    public static ETag readETag(Cursor cursor) {
        int i = 0;
        ETag eTag = new ETag();

        eTag.setId(cursor.getLong(i++));
        eTag.setUrl(cursor.getString(i++));
        eTag.setValue(cursor.getString(i++));

        return eTag;
    }
}
