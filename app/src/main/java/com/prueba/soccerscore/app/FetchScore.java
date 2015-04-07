package com.prueba.soccerscore.app;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.prueba.soccerscore.app.data.MatchContract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/*
 * Created by David on 26/03/2015.
 */
public class FetchScore extends AsyncTask<String, Void, Void> {

    private final Context mContext;
    private final String LOG_TAG = FetchScore.class.getSimpleName();

    public FetchScore(Context context) {
        mContext = context;
    }

    private void getScoreDataFromJson(String scoreJsonStr) throws JSONException {

        final String OWM_EVENTS = "events";
        final String OWM_GOALS = "goals";
        final String OWM_MINUTE_SCORE = "minute";
        final String OWM_ACTION = "action";
        final String OWM_PLAYER = "player";
        final String OWM_TEAM = "team";

        try {


            JSONObject matchJson = new JSONObject(scoreJsonStr);
            JSONObject matchArray = matchJson.getJSONObject(OWM_EVENTS);
            JSONArray goalsArray = matchArray.getJSONArray(OWM_GOALS);
            Vector<ContentValues> cVVector = new Vector<ContentValues>(goalsArray.length());
            for (int i = 0; i < goalsArray.length(); i++) {

                String minuteScore;
                String action;
                String player;
                String team;

                JSONObject match = goalsArray.getJSONObject(i);

                minuteScore = match.getString(OWM_MINUTE_SCORE);
                action = match.getString(OWM_ACTION);
                player = match.getString(OWM_PLAYER);
                team = match.getString(OWM_TEAM);

                ContentValues matchValues = new ContentValues();
                matchValues.put(MatchContract.ScoreEntry.COLUMN_MINUTE_SCORE, minuteScore);
                matchValues.put(MatchContract.ScoreEntry.COLUMN_ACTION, action);
                matchValues.put(MatchContract.ScoreEntry.COLUMN_PLAYER, player);
                matchValues.put(MatchContract.ScoreEntry.COLUMN_TEAM, team);

                cVVector.add(matchValues);
            }
            int inserted = 0;
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(MatchContract.ScoreEntry.CONTENT_URI, cvArray);
            }
            Log.d(LOG_TAG, "FetchScore Complete. " + inserted + " Inserted");

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();

            Vector<ContentValues> cVVector = new Vector<ContentValues>(0);

            ContentValues matchValues = new ContentValues();
            cVVector.add(matchValues);

            int inserted = 0;
            ContentValues[] cvArray = new ContentValues[0];
            cVVector.toArray(cvArray);
            inserted = mContext.getContentResolver().bulkInsert(MatchContract.ScoreEntry.CONTENT_URI, cvArray);

            Log.d(LOG_TAG, "FetchScore Complete. " + inserted + " Inserted");

        }

    }


    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String scoreJsonStr;
        try {

            final String Score_BASE_URL =
                    "http://www.resultados-futbol.com/scripts/api/api.php?tz=Europe/Madrid&format=json&req=match&key=7e210fae6e101bc9a25b2b432d00e501";
            final String ID_PARAM = "id";
            final String YEAR_PARAM = "year";

            Uri builtUri = Uri.parse(Score_BASE_URL).buildUpon()
                    .appendQueryParameter(ID_PARAM, params[0])
                    .appendQueryParameter(YEAR_PARAM, params[1])
                    .build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            scoreJsonStr = buffer.toString();
            getScoreDataFromJson(scoreJsonStr);

        } catch (IOException e) {
            Log.e("LOG_TAG", "Error ", e);

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("LOG_TAG", "Error closing stream", e);
                }
            }
        }
        return null;
    }
}