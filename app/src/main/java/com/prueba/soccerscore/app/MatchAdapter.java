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

        public ViewHolder(View view) {
            iconViewLocal = (ImageView) view.findViewById(R.id.list_item_escudo_local_match);
            textViewLocal = (TextView) view.findViewById(R.id.list_item_local_team);
            textViewResult = (TextView) view.findViewById(R.id.list_item_score);
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


        viewHolder.iconViewLocal.setImageResource(R.drawable.ic_launcher);


        String local = cursor.getString(MatchFragment.COL_MATCH_LOCAL);
        viewHolder.textViewLocal.setText(local);


        String result = cursor.getString(MatchFragment.COL_MATCH_RESULT);
        viewHolder.textViewResult.setText(result);


        String visitor = cursor.getString(MatchFragment.COL_MATCH_VISITOR);
        viewHolder.textViewVisitor.setText(visitor);


        viewHolder.iconViewVisitor.setImageResource(R.drawable.ic_launcher);


    }

}