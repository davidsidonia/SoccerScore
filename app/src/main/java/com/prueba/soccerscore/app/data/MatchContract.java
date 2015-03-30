package com.prueba.soccerscore.app.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/*
 * Created by David on 26/03/2015.
 */
public class MatchContract {
    public static final String CONTENT_AUTHORITY = "com.prueba.soccerscore.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MATCH = "match";
    public static final String PATH_SCORE = "score";


    public static final class MatchEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MATCH).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MATCH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MATCH;

        public static final String TABLE_NAME = "match";

        public static final String COLUMN_MATCH_KEY = "match_id";
        public static final String COLUMN_ROUND = "round";
        public static final String COLUMN_LOCAL = "local";
        public static final String COLUMN_VISITOR = "visitor";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_RESULT = "result";
        public static final String COLUMN_LIVE_MINUTE = "live_minute";


        public static Uri buildMatchWithMatchKey(String match_id) {
            return CONTENT_URI.buildUpon().appendPath(match_id).build();
        }

        public static String getMatch_idFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }


    public static final class ScoreEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCORE).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORE;

        public static final String TABLE_NAME = "score";

        public static final String COLUMN_ID_MATCH = "id_match";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_ACTION = "action";
        public static final String COLUMN_PLAYER = "player";
        public static final String COLUMN_TEAM = "team";

        public static Uri buildScoreUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}