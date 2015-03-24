package com.prueba.soccerscore.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.prueba.soccerscore.app.data.MatchContract;

/**
 * Created by David on 24/03/2015.
 */
public class MatchAdapter extends CursorAdapter {


    //quitar luego esto de TargetApi(11) porque no debe hacer falta
    @TargetApi(11)
    public MatchAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    private String convertCursorRowToUXFormat(Cursor cursor) {
// get row indices for our cursor
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

        TextView tv = (TextView) view;
        tv.setText(convertCursorRowToUXFormat(cursor));
    }
}
