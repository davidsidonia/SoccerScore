package com.prueba.soccerscore.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * Created by David on 03/04/2015.
 */
public class ScoreAdapter extends CursorAdapter {

    public static class ViewHolder {

        public final TextView textViewJugadorLocalGoal;
        public final TextView textViewMinutoGoal;
        public final TextView textViewJugadorVisitanteGoal;


        public ViewHolder(View view) {
            textViewJugadorLocalGoal = (TextView) view.findViewById(R.id.list_item_nombre_jugador_local_goal);
            textViewMinutoGoal = (TextView) view.findViewById(R.id.list_item_minute_goal);
            textViewJugadorVisitanteGoal = (TextView) view.findViewById(R.id.list_item_nombre_jugador_visitor_goal);

        }
    }

    public ScoreAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_goal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String minute = cursor.getString(ScoreFragment.COL_SCORE_MINUTE_SCORE);
        String action = cursor.getString(ScoreFragment.COL_SCORE_ACTION);
        String player = cursor.getString(ScoreFragment.COL_SCORE_PLAYER);
        String team = cursor.getString(ScoreFragment.COL_SCORE_TEAM);

        minute = minute + "'";


        if (action.equals("Gol propia puerta")) {
            player = player + " (pp)";
        } else if (action.equals("Gol de penalti")) {
            player = player + " (p)";
        }


        viewHolder.textViewMinutoGoal.setText(minute);
        if (team.equals("local")) {
            viewHolder.textViewJugadorLocalGoal.setText(player);
            viewHolder.textViewJugadorVisitanteGoal.setText("");
        } else if (team.equals("visitor")) {
            viewHolder.textViewJugadorLocalGoal.setText("");
            viewHolder.textViewJugadorVisitanteGoal.setText(player);
        }


    }

}
