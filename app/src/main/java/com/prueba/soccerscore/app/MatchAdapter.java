package com.prueba.soccerscore.app;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * Created by David on 26/03/2015.
 */
public class MatchAdapter extends CursorAdapter {

    public MatchAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    private String convertCursorRowToUXFormat(Cursor cursor) {

        return cursor.getString(MatchFragment.COL_MATCH_LOCAL) +
                " - " + cursor.getString(MatchFragment.COL_MATCH_VISITOR) +
                " / " + cursor.getString(MatchFragment.COL_MATCH_RESULT);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_match, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tv = (TextView) view.findViewById(R.id.list_item_local_team);
        tv.setText(convertCursorRowToUXFormat(cursor));
    }

}