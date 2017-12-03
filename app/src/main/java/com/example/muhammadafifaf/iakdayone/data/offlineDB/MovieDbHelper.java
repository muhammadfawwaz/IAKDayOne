package com.example.muhammadafifaf.iakdayone.data.offlineDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.muhammadafifaf.iakdayone.data.offlineDB.MovieContract.MovieEntry;

/**
 * Created by Muhammad Afif AF on 03/12/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movieDb.db";

    private static final int VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + "(" +
                MovieEntry._ID                      +       " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_FAVORITE_IDS      +       " INTEGER NOT NULL, "   +
                MovieEntry.COLUMN_TITLE             +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ORI_TITLE         +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW          +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER_PATH       +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_BACKDROP_PATH     +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ORIGINAL_LANG     +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_ADULT             +       " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_VIDEO             +       " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE      +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_GENRE             +       " TEXT NOT NULL, " +
                MovieEntry.COLUMN_VOTE_COUNT        +       " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_VOTE_AVG          +       " REAL NOT NULL, " +
                MovieEntry.COLUMN_POPULARITY        +       " REAL NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
