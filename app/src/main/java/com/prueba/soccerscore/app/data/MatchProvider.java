package com.prueba.soccerscore.app.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/*
 * Created by David on 26/03/2015.
 */
public class MatchProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MatchDbHelper mOpenHelper;
    private static final int MATCH = 100;
    private static final int MATCH_WITH_MATH_KEY = 101;
    private static final int SCORE = 102;
    private static final SQLiteQueryBuilder sMatchQueryBuilder;
    private static final SQLiteQueryBuilder sScoreQueryBuilder;
    static {
        sMatchQueryBuilder = new SQLiteQueryBuilder();
        sMatchQueryBuilder.setTables(MatchContract.MatchEntry.TABLE_NAME);
    }

    static {
        sScoreQueryBuilder = new SQLiteQueryBuilder();
        sScoreQueryBuilder.setTables(MatchContract.ScoreEntry.TABLE_NAME);
    }
    private static final String sMatchKeySelection =
            MatchContract.MatchEntry.TABLE_NAME +
                    "." + MatchContract.MatchEntry.COLUMN_MATCH_KEY + " = ? ";

    private static final String sIdMatchSelection =
            MatchContract.ScoreEntry.TABLE_NAME +
                    "." + MatchContract.ScoreEntry.COLUMN_ID_MATCH + " = ? ";

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MatchContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MatchContract.PATH_MATCH, MATCH);
        matcher.addURI(authority, MatchContract.PATH_MATCH + "/*", MATCH_WITH_MATH_KEY);
        matcher.addURI(authority, MatchContract.PATH_SCORE, SCORE);
        return matcher;
    }

    private Cursor getMatch(Uri uri, String[] projection, String sortOrder) {
        return sMatchQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMatchWithMatchID(Uri uri, String[] projection, String sortOrder) {
        String matchId = MatchContract.MatchEntry.getMatch_idFromUri(uri);
        return sMatchQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sMatchKeySelection,
                new String[]{matchId},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getScore(Uri uri, String[] projection, String sortOrder) {
        return sScoreQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
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
        //este 'match' es un metodo de UriMatcher (no tiene nada que ver con las variables match que he creado
        // yo al tratarse la aplicacion de partidos)
        switch (sUriMatcher.match(uri)) {
            case MATCH:
                retCursor = getMatch(uri, projection, sortOrder);
                break;
            case MATCH_WITH_MATH_KEY:
                retCursor = getMatchWithMatchID(uri, projection, sortOrder);
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
        //este 'match' es un metodo de UriMatcher (no tiene nada que ver con las variables match que he creado
        // yo al tratarse la aplicacion de partidos)
        switch (sUriMatcher.match(uri)) {
            case MATCH:
                return MatchContract.MatchEntry.CONTENT_TYPE;
            case MATCH_WITH_MATH_KEY:
                return MatchContract.MatchEntry.CONTENT_ITEM_TYPE;
            case SCORE:
                return MatchContract.ScoreEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        //  Uri returnUri;

        //este 'match' es un metodo de UriMatcher (no tiene nada que ver con las variables match que he creado
        // yo al tratarse la aplicacion de partidos)
        switch (sUriMatcher.match(uri)) {
            case MATCH: {
                db.execSQL("DELETE FROM " + MatchContract.MatchEntry.TABLE_NAME);
                db.insert(MatchContract.MatchEntry.TABLE_NAME, null, values);
//TODO me puede hacer falta lo mismo que abajo
            }
            case SCORE: {
                db.execSQL("DELETE FROM " + MatchContract.ScoreEntry.TABLE_NAME);
                db.insert(MatchContract.ScoreEntry.TABLE_NAME, null, values);

//                long _id = db.insert(MatchContract.ScoreEntry.TABLE_NAME, null, values);
//                if (_id > 0)
//                    returnUri = MatchContract.ScoreEntry.buildScoreUri(_id);
//                else
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // getContext().getContentResolver().notifyChange(uri, null);
        // return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        //este 'match' es un metodo de UriMatcher (no tiene nada que ver con las variables match que he creado
        // yo al tratarse la aplicacion de partidos)
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