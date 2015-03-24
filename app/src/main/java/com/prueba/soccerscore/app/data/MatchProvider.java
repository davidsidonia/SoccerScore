package com.prueba.soccerscore.app.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by David on 22/03/2015.
 */
public class MatchProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MatchDbHelper mOpenHelper;


    private static final int MATCH = 100;
    private static final int SCORE = 101;


    private static final SQLiteQueryBuilder sMatchQueryBuilder;

    static {

        sMatchQueryBuilder = new SQLiteQueryBuilder();
        sMatchQueryBuilder.setTables(MatchContract.MatchEntry.TABLE_NAME);
    }


    private static final String sIdMatchSelection =
            MatchContract.ScoreEntry.TABLE_NAME +
                    "." + MatchContract.ScoreEntry.COLUMN_MATCH_KEY + " = ? ";





    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MatchContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MatchContract.PATH_MATCH, MATCH);
        matcher.addURI(authority, MatchContract.PATH_SCORE + "/# ", SCORE);


        return matcher;
    }


    private Cursor getMatch(
            Uri uri, String[] projection, String sortOrder) {

        return sMatchQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }


    private Cursor getScore(Uri uri, String[] projection, String sortOrder) {

        long matchId = ContentUris.parseId(uri);
        return sMatchQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sIdMatchSelection,
                new String[]{Long.toString(matchId)},
                null,
                null,
                sortOrder
        );
    }




    @Override
    public boolean onCreate() {
        mOpenHelper = new MatchDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {

            case MATCH:
                retCursor = getMatch(uri, projection, sortOrder);
                break;
            case SCORE:
                retCursor = getScore(uri, projection, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)) {

            case MATCH:
                return MatchContract.MatchEntry.CONTENT_TYPE;
            case SCORE:
                return MatchContract.ScoreEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case MATCH: {

                db.execSQL("DELETE FROM " + MatchContract.MatchEntry.TABLE_NAME);
                db.insert(MatchContract.MatchEntry.TABLE_NAME, null, values);
                //TODO   me puede hacer falta lo mismo que abajo
            }
            case SCORE: {
                db.execSQL("DELETE FROM " + MatchContract.ScoreEntry.TABLE_NAME);
                long _id = db.insert(MatchContract.ScoreEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MatchContract.ScoreEntry.buildScoreUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount;
        switch (match) {

            case MATCH:
                db.execSQL("DELETE FROM " + MatchContract.MatchEntry.TABLE_NAME);
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MatchContract.MatchEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;


            case SCORE:
                db.execSQL("DELETE FROM " + MatchContract.ScoreEntry.TABLE_NAME);
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MatchContract.ScoreEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


}
