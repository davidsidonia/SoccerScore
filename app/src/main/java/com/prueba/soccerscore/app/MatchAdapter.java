package com.prueba.soccerscore.app;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.prueba.soccerscore.app.data.MatchContract;

/*
 * Created by David on 26/03/2015.
 */
public class MatchAdapter extends CursorAdapter {

    public MatchAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    private String convertCursorRowToUXFormat(Cursor cursor) {
        int idx_local = cursor.getColumnIndex(MatchContract.MatchEntry.COLUMN_LOCAL);
        int idx_visitor = cursor.getColumnIndex(MatchContract.MatchEntry.COLUMN_VISITOR);
        int idx_result = cursor.getColumnIndex(MatchContract.MatchEntry.COLUMN_RESULT);

        return cursor.getString(idx_local) +
                " - " + cursor.getString(idx_visitor) +
                " / " + cursor.getString(idx_result);

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