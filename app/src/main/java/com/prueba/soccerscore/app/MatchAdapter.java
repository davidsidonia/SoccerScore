package com.prueba.soccerscore.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Created by David on 26/03/2015.
 */
public class MatchAdapter extends CursorAdapter {

    public static class ViewHolder {
        public final ImageView iconViewLocal;
        public final TextView textViewLocal;
        public final TextView textViewResult;
        public final TextView textViewVisitor;
        public final ImageView iconViewVisitor;
        //TODO 1 INICIO *********************************************************************
        public final TextView textViewJornada;
        public final TextView textViewHorario;
        public final TextView textViewfechaAlDerecho;
        public final TextView textViewResultado;
        public final TextView textViewLive;

        //TODO 1 FIN *********************************************************************
        public ViewHolder(View view) {
            iconViewLocal = (ImageView) view.findViewById(R.id.list_item_escudo_local_match);
            textViewLocal = (TextView) view.findViewById(R.id.list_item_local_team);
            textViewResult = (TextView) view.findViewById(R.id.list_item_score);
            textViewVisitor = (TextView) view.findViewById(R.id.list_item_visitor_team);
            iconViewVisitor = (ImageView) view.findViewById(R.id.list_item_escudo_visitor_match);
//TODO 2 INICIO *********************************************************************
            textViewJornada = (TextView) view.findViewById(R.id.textView_jornada);
            textViewHorario = (TextView) view.findViewById(R.id.list_item_hora);
            textViewfechaAlDerecho = (TextView) view.findViewById(R.id.list_item_fecha);
            textViewResultado = (TextView) view.findViewById(R.id.list_item_resultado);
            textViewLive = (TextView) view.findViewById(R.id.list_item_live);
//TODO 2 FIN *********************************************************************
        }
    }
    public MatchAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_match, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String local = cursor.getString(MatchFragment.COL_MATCH_LOCAL);
        String result = cursor.getString(MatchFragment.COL_MATCH_RESULT);
        String visitor = cursor.getString(MatchFragment.COL_MATCH_VISITOR);
//TODO 3 INICIO *********************************************************************
        String round = cursor.getString(MatchFragment.COL_MATCH_VISITOR);
        String date = cursor.getString(MatchFragment.COL_MATCH_DATE);
        String hour = cursor.getString(MatchFragment.COL_MATCH_HOUR);
        String minute = cursor.getString(MatchFragment.COL_MATCH_MINUTE);
        String live_minute = cursor.getString(MatchFragment.COL_MATCH_LIVE_MINUTE);
        String jornada = "JORNADA " + round;
        String horario = hour + ":" + minute;
        String dia = date.substring(8, 10);
        String mes = date.substring(5, 7);
        String fechaAlDerecho = dia + "/" + mes;
        String resultado = result;
        if (!result.equals("x-x")) {
            horario = "";
            fechaAlDerecho = "";
        }
        if (live_minute.equals("DES")) {
            live_minute = "DESC";
        } else if (live_minute.equals("des")) {
            live_minute = "desc";
        } else if (live_minute.equals("Des")) {
            live_minute = "Desc";
        } else if (live_minute.equals("")) {
            if (result.equals("x-x")) {
                live_minute = null;
            } else {
                live_minute = "FIN";
            }
        }
        if (result.equals("x-x")) {
            result = "";
            resultado = " - ";
        }
//TODO 3 FIN *********************************************************************
        viewHolder.iconViewLocal.setImageResource(Utility.getEscudoParaVistaMatch(local));
        viewHolder.textViewLocal.setText(local);
        viewHolder.textViewResult.setText(result);
        viewHolder.textViewVisitor.setText(visitor);
        viewHolder.iconViewVisitor.setImageResource(Utility.getEscudoParaVistaMatch(visitor));
//TODO 4 INICIO *********************************************************************
// viewHolder.textViewJornada.setText(jornada);
        viewHolder.textViewHorario.setText(horario);
        viewHolder.textViewfechaAlDerecho.setText(fechaAlDerecho);
        viewHolder.textViewResultado.setText(resultado);
        viewHolder.textViewLive.setText(live_minute);
//TODO 4 FIN *********************************************************************
    }
}