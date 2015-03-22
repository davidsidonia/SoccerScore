package com.prueba.soccerscore.app.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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


    private static final SQLiteQueryBuilder sMatchQueryBuilder = new SQLiteQueryBuilder();

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
        return null;
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
