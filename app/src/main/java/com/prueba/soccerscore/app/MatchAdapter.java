package com.prueba.soccerscore.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.TypedValue;
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
        public final TextView textViewResultFecha;
        public final TextView textViewLiveHora;
        public final TextView textViewVisitor;
        public final ImageView iconViewVisitor;


        public ViewHolder(View view) {
            iconViewLocal = (ImageView) view.findViewById(R.id.list_item_escudo_local_match);
            textViewLocal = (TextView) view.findViewById(R.id.list_item_local_team);
            textViewResultFecha = (TextView) view.findViewById(R.id.list_item_result_fecha);
            textViewLiveHora = (TextView) view.findViewById(R.id.list_item_live_hora);
            textViewVisitor = (TextView) view.findViewById(R.id.list_item_visitor_team);
            iconViewVisitor = (ImageView) view.findViewById(R.id.list_item_escudo_visitor_match);

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
        String visitor = cursor.getString(MatchFragment.COL_MATCH_VISITOR);
        String result = cursor.getString(MatchFragment.COL_MATCH_RESULT);
        String live_minute = cursor.getString(MatchFragment.COL_MATCH_LIVE_MINUTE);
        String date = cursor.getString(MatchFragment.COL_MATCH_DATE);
        String hour = cursor.getString(MatchFragment.COL_MATCH_HOUR);
        String minute = cursor.getString(MatchFragment.COL_MATCH_MINUTE);


        String horario = hour + ":" + minute;
        String dia = date.substring(8, 10);
        String mes = date.substring(5, 7);
        String fechaAlDerecho = dia + "/" + mes;


        String resultFecha = "";
        String liveHora = "";


        if (live_minute.equals("DES") || live_minute.equals("des") || live_minute.equals("Des")) {
            live_minute = "DESC";
        } else if (live_minute.equals("") && !result.equals("x-x")) {
            live_minute = "FIN";
        } else {
            live_minute = live_minute + "'";
        }

        if (result.equals("x-x")) {
            viewHolder.textViewResultFecha.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            resultFecha = fechaAlDerecho;
            liveHora = horario;
        } else if (!result.equals("x-x")) {
            viewHolder.textViewResultFecha.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            resultFecha = result;
            liveHora = live_minute;
        }


        viewHolder.iconViewLocal.setImageResource(Utility.getEscudoParaVistaMatch(local));
        viewHolder.textViewLocal.setText(local);
        viewHolder.textViewResultFecha.setText(resultFecha);
        viewHolder.textViewLiveHora.setText(liveHora);
        viewHolder.textViewVisitor.setText(visitor);
        viewHolder.iconViewVisitor.setImageResource(Utility.getEscudoParaVistaMatch(visitor));

    }
}