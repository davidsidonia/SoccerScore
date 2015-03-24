package com.prueba.soccerscore.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.prueba.soccerscore.app.data.MatchContract.MatchEntry;
import com.prueba.soccerscore.app.data.MatchContract.ScoreEntry;

/**
 * Created by David on 21/03/2015.
 */
public class MatchDbHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;

    static final String DATABASE_NAME = "soccerscore.db";

    public MatchDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold locations. A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_MATCH_TABLE = "CREATE TABLE " + MatchEntry.TABLE_NAME + " (" +
                MatchEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MatchEntry.COLUMN_MATCH_KEY + " TEXT UNIQUE NOT NULL, " +
                MatchEntry.COLUMN_ROUND + " TEXT NOT NULL, " +
                MatchEntry.COLUMN_LOCAL + " TEXT NOT NULL, " +
                MatchEntry.COLUMN_VISITOR + " TEXT NOT NULL, " +
                MatchEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                MatchEntry.COLUMN_HOUR + " TEXT NOT NULL, " +
                MatchEntry.COLUMN_MINUTE + " TEXT NOT NULL, " +
                MatchEntry.COLUMN_RESULT + " TEXT NOT NULL, " +
                MatchEntry.COLUMN_LIVE_MINUTE + " TEXT NOT NULL);";


        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " + ScoreEntry.TABLE_NAME + " (" +
                ScoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScoreEntry.COLUMN_MATCH_KEY + " TEXT NOT NULL, " +
                ScoreEntry.COLUMN_MINUTE + " TEXT NOT NULL, " +
                ScoreEntry.COLUMN_ACTION + " TEXT NOT NULL, " +
                ScoreEntry.COLUMN_PLAYER + " TEXT NOT NULL," +
                ScoreEntry.COLUMN_TEAM + " TEXT NOT NULL);";


//                // Si lo hiciera con Clave foranea, tendria que añadir esto
//                " FOREIGN KEY (" + ScoreEntry.COLUMN_MATCH_KEY + ") REFERENCES " +
//                MatchEntry.TABLE_NAME + " (" + MatchEntry._ID + ");";


        sqLiteDatabase.execSQL(SQL_CREATE_MATCH_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MatchEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
