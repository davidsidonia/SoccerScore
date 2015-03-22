package com.prueba.soccerscore.app.data;

import android.provider.BaseColumns;

/**
 * Created by David on 21/03/2015.
 */
public class MatchContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
//             public static long normalizeDate(long startDate) {
//         // normalize the start date to the beginning of the (UTC) day
//                 Time time = new Time();
//         time.set(startDate);
//         int julianDay = Time.getJulianDay(startDate, time.gmtoff);
//         return time.setJulianDay(julianDay);
//         }

    /*
Inner class that defines the table contents of the location table
Students: This is where you will add the strings. (Similar to what has been
done for WeatherEntry)
*/
    public static final class MatchEntry implements BaseColumns {
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

    }


}
