package com.prueba.soccerscore.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.prueba.soccerscore.app.data.MatchContract;


public class ScoreFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    static final String DETAIL_URI = "URI";
    private Uri mUri;
    private static final int SCORE_LOADER = 0;

    private static final String[] MATCH_COLUMNS = {
            MatchContract.MatchEntry.TABLE_NAME + "." + MatchContract.MatchEntry._ID,
            MatchContract.MatchEntry.COLUMN_MATCH_KEY,
            MatchContract.MatchEntry.COLUMN_LOCAL,
            MatchContract.MatchEntry.COLUMN_VISITOR,
            MatchContract.MatchEntry.COLUMN_RESULT,
            MatchContract.MatchEntry.COLUMN_LIVE_MINUTE
    };

    static final int COL_MATCH_ID = 0;
    static final int COL_MATCH_MATCH_KEY = 1;
    static final int COL_MATCH_LOCAL = 2;
    static final int COL_MATCH_VISITOR = 3;
    static final int COL_MATCH_RESULT = 4;
    static final int COL_MATCH_LIVE_MINUTE = 5;

    ImageView iconViewEscudoLocal;
    ImageView iconViewEscudoVisitor;
    TextView localTextView;
    TextView visitorTextView;
    TextView resultTextView;
    TextView live_minuteTextView;

    public ScoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(ScoreFragment.DETAIL_URI);
        }

        View rootView = inflater.inflate(R.layout.fragment_score, container, false);
        iconViewEscudoLocal = (ImageView) rootView.findViewById(R.id.list_item_escudo_local_score);
        iconViewEscudoVisitor = (ImageView) rootView.findViewById(R.id.list_item_escudo_visitor_score);
        localTextView = (TextView) rootView.findViewById(R.id.textView_nombre_eq_local);
        visitorTextView = (TextView) rootView.findViewById(R.id.textView_nombre_eq_visitante);
        resultTextView = (TextView) rootView.findViewById(R.id.textView_resultado);
        live_minuteTextView = (TextView) rootView.findViewById(R.id.textView_estado_partido);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SCORE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    MATCH_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            String local = data.getString(COL_MATCH_LOCAL);
            String visitor = data.getString(COL_MATCH_VISITOR);
            String result = data.getString(COL_MATCH_RESULT);
            String live_minute = data.getString(COL_MATCH_LIVE_MINUTE);

            if (live_minute.equals("DES") || live_minute.equals("des") || live_minute.equals("Des")) {
                live_minute = "DESC";
            } else if (live_minute.equals("")) {
                if (result.equals("x-x")) {
                    live_minute = "";
                } else {
                    live_minute = "FIN";
                }
            } else {
                live_minute = live_minute + "'";
            }


            if (result.equals("x-x")) {
                result = " - ";
            }

            iconViewEscudoLocal.setImageResource(Utility.getEscudoParaVistaScore(local));
            iconViewEscudoVisitor.setImageResource(Utility.getEscudoParaVistaScore(visitor));
            localTextView.setText(local);
            visitorTextView.setText(visitor);
            resultTextView.setText(result);
            live_minuteTextView.setText(live_minute);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}