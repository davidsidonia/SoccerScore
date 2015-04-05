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

    private int glocal = 0;
    private int gvisitante = 0;


    public static class ViewHolder {

        public final TextView textViewJugadorLocal;
        public final TextView textViewMinutoLocal;
        public final TextView textViewMarcadorLocal;


        public ViewHolder(View view) {
            textViewJugadorLocal = (TextView) view.findViewById(R.id.list_item_nombre_jugador_local);
            textViewMinutoLocal = (TextView) view.findViewById(R.id.list_item_minute_local);
            textViewMarcadorLocal = (TextView) view.findViewById(R.id.list_item_marcador_local);

        }
    }

    public ScoreAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_goal_local, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

//        String minute = cursor.getString(ScoreFragment.COLUMN_MINUTE_SCORE);
//        String action = cursor.getString(ScoreFragment.COLUMN_ACTION);
//        String player = cursor.getString(ScoreFragment.COLUMN_PLAYER);
//        String team = cursor.getString(ScoreFragment.COLUMN_TEAM);
        String marcador = "";


//        if (action.equals("Gol propia puerta")){
//            player = player + " (pp)";
//        } else if (action.equals("Gol de penalti")){
//            player = player + " (p)";
//        }
//
//        if (team.equals("local")){
//            glocal++;
//        } else  if (team.equals("visitor")){
//            gvisitante++;
//        }
//
//        marcador = Integer.toString(glocal)+"-"+Integer.toString(gvisitante);
//
//
//        viewHolder.textViewJugadorLocal.setText(player);
//        viewHolder.textViewMinutoLocal.setText(minute);
//        viewHolder.textViewMarcadorLocal.setText(marcador);

        viewHolder.textViewJugadorLocal.setText("Pepito el caja");
        viewHolder.textViewMinutoLocal.setText("25");
        viewHolder.textViewMarcadorLocal.setText("0-3");
    }


}
