package com.prueba.soccerscore.app.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by David on 21/03/2015.
 */
public class MatchContract {


    public static final String CONTENT_AUTHORITY = "com.prueba.soccerscore.app";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
// the content provider.
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
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_ROUND = "round";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_LOCAL = "local";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_VISITOR = "visitor";

        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_DATE = "date";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_HOUR = "hour";
        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_MINUTE = "minute";

        // Column with the foreign key into the location table.
        public static final String COLUMN_RESULT = "result";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_LIVE_MINUTE = "live_minute";

    }


    /* Inner class that defines the table contents of the weather table */
    public static final class ScoreEntry implements BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCORE).build();


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORE;



        public static final String TABLE_NAME = "score";

        // Column with the foreign key into the location table.
        public static final String COLUMN_MATCH_KEY = "match_id";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_MINUTE = "minute";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_ACTION = "action";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_PLAYER = "player";


        public static final String COLUMN_TEAM = "team";


        public static Uri buildScoreUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}
