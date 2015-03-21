package com.prueba.soccerscore.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MatchFragment extends Fragment {

    private ArrayAdapter<String> matchs;

    public MatchFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.matchfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMatch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        matchs =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_match, // The name of the layout ID.
                        R.id.list_item_local_team, // The ID of the textview to populate.
                        new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_match);
        listView.setAdapter(matchs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String oneMatch = matchs.getItem(position);


                Intent intent = new Intent(getActivity(), ScoreActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, oneMatch);
                startActivity(intent);


            }
        });


        return rootView;

    }

    private void updateMatch() {
        FetchMatch fetchMatch = new FetchMatch();
        fetchMatch.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMatch();
    }


    public class FetchMatch extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchMatch.class.getSimpleName();


        /**
         * + * Take the String representing the complete forecast in JSON Format and
         * + * pull out the data we need to construct the Strings needed for the wireframes.
         * + *
         * + * Fortunately parsing is easy: constructor takes the JSON string and converts it
         * + * into an Object hierarchy for us.
         * +
         */
        private String[] getMatchDataFromJson(String matchJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_MATCH = "match";
            final String OWM_ID = "id";
            final String OWM_ROUND = "round";
            final String OWM_LOCAL = "local";
            final String OWM_VISITOR = "visitor";
            final String OWM_SCHEDULE = "schedule";
            final String OWM_DATE = "date";
            final String OWM_HOUR = "hour";
            final String OWM_MINUTE = "minute";
            final String OWM_RESULT = "result";
            final String OWM_LIVE_MINUTE = "live_minute";

            JSONObject matchJson = new JSONObject(matchJsonStr);
            JSONArray matchArray = matchJson.getJSONArray(OWM_MATCH);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.


//**************************** en el tamaño del array 'resultStrs'  no se si va a funcionar
            String[] resultStrs = new String[matchArray.length()];

            for (int i = 0; i < matchArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String id;
                String local;
                String result;
                String visitor;

                // Get the JSON object representing the day
                JSONObject match = matchArray.getJSONObject(i);

                id = match.getString(OWM_ID);
                local = match.getString(OWM_LOCAL);
                result = match.getString(OWM_RESULT);
                visitor = match.getString(OWM_VISITOR);


//**************  comentado por mi, porque es de sunshine y me puede servir de guia para SoccerScore
//// The date/time is returned as a long. We need to convert that
//// into something human-readable, since most people won't read "1400356800" as
//// "this saturday".
//                long dateTime;
//// Cheating to convert this to UTC time, which is what we want anyhow
//                dateTime = dayTime.setJulianDay(julianStartDay+i);
//                day = getReadableDateString(dateTime);
//
//
//                // description is in a child array called "weather", which is 1 element long.
//                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
//                description = weatherObject.getString(OWM_DESCRIPTION);
//
//                // Temperatures are in a child object called "temp". Try not to name variables
//                // "temp" when working with temperature. It confuses everybody.
//                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
//                double high = temperatureObject.getDouble(OWM_MAX);
//                double low = temperatureObject.getDouble(OWM_MIN);
//
//                highAndLow = formatHighLows(high, low);
//***************************************************************************************

                resultStrs[i] = local + " / " + result + " / " + visitor + " / " + id;
            }

            return resultStrs;

        }


        @Override
        protected String[] doInBackground(Void... params) {


            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String matchsJsonStr = null;

            try {
                // Construct the URL for the resultados-futbol query

                URL url = new URL("http://www.resultados-futbol.com/scripts/api/api.php?format=json&req=matchs&key=7e210fae6e101bc9a25b2b432d00e501&league=1");

                // Create the request to OpenWeatherMap, and open the connection
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

            } catch (IOException e) {
                Log.e("LOG_TAG", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;


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

            try {
                return getMatchDataFromJson(matchsJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.


            return null;
        }


        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                matchs.clear();
                for (String matchStr : result) {
                    matchs.add(matchStr);
                }
                // New data is back from the server. Hooray!
            }
        }

    }
}