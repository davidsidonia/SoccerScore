package com.prueba.soccerscore.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by David on 26/03/2015.
 */
public class FetchScore {

    private String[] getScoreDataFromJson(String scoreJsonStr) throws JSONException {

        final String OWM_EVENTS = "events";
        final String OWM_GOALS = "goals";
        final String OWM_MINUTE = "minute";
        final String OWM_ACTION = "action";
        final String OWM_PLAYER = "player";
        final String OWM_TEAM = "team";

        JSONObject matchJson = new JSONObject(scoreJsonStr);
        JSONObject matchArray = matchJson.getJSONObject(OWM_EVENTS);
        JSONArray goalsArray = matchArray.getJSONArray(OWM_GOALS);

        String[] resultStrs = new String[goalsArray.length()];

        for (int i = 0; i < goalsArray.length(); i++) {

            String minute;
            String action;
            String player;
            String team;

            JSONObject match = goalsArray.getJSONObject(i);

            minute = match.getString(OWM_MINUTE);
            action = match.getString(OWM_ACTION);
            player = match.getString(OWM_PLAYER);
            team = match.getString(OWM_TEAM);

            resultStrs[i] = minute + " / " + action + " / " + player + " / " + team;
        }
        return resultStrs;
    }
}