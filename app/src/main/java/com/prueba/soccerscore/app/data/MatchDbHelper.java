package com.prueba.soccerscore.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.prueba.soccerscore.app.data.MatchContract.MatchEntry;
import com.prueba.soccerscore.app.data.MatchContract.ScoreEntry;

/*
 * Created by David on 26/03/2015.
 */
public class MatchDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    static final String DATABASE_NAME = "soccerscore.db";

    public MatchDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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
                ScoreEntry.COLUMN_ID_MATCH + " TEXT , " +
                ScoreEntry.COLUMN_MINUTE_SCORE + " TEXT NOT NULL, " +
                ScoreEntry.COLUMN_ACTION + " TEXT NOT NULL, " +
                ScoreEntry.COLUMN_PLAYER + " TEXT NOT NULL," +
                ScoreEntry.COLUMN_TEAM + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MATCH_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MatchEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}