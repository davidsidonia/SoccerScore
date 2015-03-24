package com.prueba.soccerscore.app;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.prueba.soccerscore.app.data.MatchContract.MatchEntry;
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

/**
 * Created by David on 22/03/2015.
 */
public class FetchMatch extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = FetchMatch.class.getSimpleName();

    private final Context mContext;


    public FetchMatch(Context context) {
        mContext = context;
    }


    /*    metodo que recoje el JSON y lo parsea obteniendo los datos que queremos, en este caso y de momento en un String
          para poder mostrarlos en el LIstview
     */
    private void getMatchDataFromJson(String matchJsonStr)
            throws JSONException {


        // These are the names of the JSON objects that need to be extracted.
        final String OWM_MATCH = "match";
        final String OWM_ID = "id";
        final String OWM_ROUND = "round";
        final String OWM_LOCAL = "local";
        final String OWM_VISITOR = "visitor";
        final String OWM_DATE = "date";
        final String OWM_HOUR = "hour";
        final String OWM_MINUTE = "minute";
        final String OWM_RESULT = "result";
        final String OWM_LIVE_MINUTE = "live_minute";

        try {
            JSONObject matchJson = new JSONObject(matchJsonStr);
            JSONArray matchArray = matchJson.getJSONArray(OWM_MATCH);

//************************
            // String[] resultStrs = new String[matchArray.length()];
//******************************************

            // Insert the new weather information into the database
            Vector<ContentValues> cVVector = new Vector<ContentValues>(matchArray.length());

            for (int i = 0; i < matchArray.length(); i++) {

                String id;
                String round;
                String local;
                String visitor;
                String date;
                String hour;
                String minute;
                String result;
                String live_minute;


                // Get the JSON object representing the match
                JSONObject match = matchArray.getJSONObject(i);

                id = match.getString(OWM_ID);
                round = match.getString(OWM_ROUND);
                local = match.getString(OWM_LOCAL);
                visitor = match.getString(OWM_VISITOR);
                date = match.getString(OWM_DATE);
                hour = match.getString(OWM_HOUR);
                minute = match.getString(OWM_MINUTE);
                result = match.getString(OWM_RESULT);
                live_minute = match.getString(OWM_LIVE_MINUTE);


                ContentValues matchValues = new ContentValues();

                matchValues.put(MatchEntry.COLUMN_MATCH_KEY, id);
                matchValues.put(MatchEntry.COLUMN_ROUND, round);
                matchValues.put(MatchEntry.COLUMN_LOCAL, local);
                matchValues.put(MatchEntry.COLUMN_VISITOR, visitor);
                matchValues.put(MatchEntry.COLUMN_DATE, date);
                matchValues.put(MatchEntry.COLUMN_HOUR, hour);
                matchValues.put(MatchEntry.COLUMN_MINUTE, minute);
                matchValues.put(MatchEntry.COLUMN_RESULT, result);
                matchValues.put(MatchEntry.COLUMN_LIVE_MINUTE, live_minute);


                cVVector.add(matchValues);

//*********************************
                // resultStrs[i] = id + " - " + local;
                //*********************************
            }

            int inserted = 0;

            // add to database
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(MatchEntry.CONTENT_URI, cvArray);
            }

            Log.d(LOG_TAG, "FetchMatch Complete. " + inserted + " Inserted");

//********************************************************
            //return resultStrs;
//*********************************************************

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }


    @Override
    protected Void doInBackground(Void... params) {

        if (params.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String matchsJsonStr = null;

        try {
            // Construct the URL for the resultados-futbol query


            URL url = new URL("http://www.resultados-futbol.com/scripts/api/api.php?format=json&req=matchs&key=7e210fae6e101bc9a25b2b432d00e501&league=1");

            // Create the request to resultados-futbol, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            matchsJsonStr = buffer.toString();
            getMatchDataFromJson(matchsJsonStr);

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


//    @Override
//    protected void onPostExecute(String[] result) {
//        if (result != null) {
//            listenerMatch.cuandoTengasLosDatos(result);
//        }
//    }

}
