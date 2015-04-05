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
import android.widget.ListView;
import android.widget.TextView;
import com.prueba.soccerscore.app.data.MatchContract;


public class ScoreFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ScoreAdapter scores;
    static final String DETAIL_URI = "URI";
    private Uri mUri;
    private static final int SCORE_LOADER = 0;

    private ImageView iconViewEscudoLocal;
    private ImageView iconViewEscudoVisitor;
    private TextView localTextView;
    private TextView visitorTextView;
    private TextView resultTextView;
    private TextView live_minuteTextView;
    private ListView listViewScore;

    private static final String[] SCORE_COLUMNS = {
            MatchContract.MatchEntry.TABLE_NAME + "." + MatchContract.MatchEntry._ID,
            MatchContract.MatchEntry.COLUMN_MATCH_KEY,
            MatchContract.MatchEntry.COLUMN_LOCAL,
            MatchContract.MatchEntry.COLUMN_VISITOR,
            MatchContract.MatchEntry.COLUMN_RESULT,
            MatchContract.MatchEntry.COLUMN_LIVE_MINUTE,
//            MatchContract.ScoreEntry.COLUMN_MINUTE_SCORE,
//            MatchContract.ScoreEntry.COLUMN_ACTION,
//            MatchContract.ScoreEntry.COLUMN_PLAYER,
//            MatchContract.ScoreEntry.COLUMN_TEAM
    };

    static final int COL_MATCH_ID = 0;
    static final int COL_MATCH_MATCH_KEY = 1;
    static final int COL_MATCH_LOCAL = 2;
    static final int COL_MATCH_VISITOR = 3;
    static final int COL_MATCH_RESULT = 4;
    static final int COL_MATCH_LIVE_MINUTE = 5;
//    static final int COL_SCORE_MINUTE_SCORE = 6;
//    static final int COL_SCORE_ACTION = 7;
//    static final int COL_SCORE_PLAYER = 8;
    // static final int COL_SCORE_TEAM = 9;


    public ScoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(ScoreFragment.DETAIL_URI);
        }

        scores = new ScoreAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_score, container, false);

        iconViewEscudoLocal = (ImageView) rootView.findViewById(R.id.list_item_escudo_local_score);
        iconViewEscudoVisitor = (ImageView) rootView.findViewById(R.id.list_item_escudo_visitor_score);
        localTextView = (TextView) rootView.findViewById(R.id.textView_nombre_eq_local);
        visitorTextView = (TextView) rootView.findViewById(R.id.textView_nombre_eq_visitante);
        resultTextView = (TextView) rootView.findViewById(R.id.textView_resultado);
        live_minuteTextView = (TextView) rootView.findViewById(R.id.textView_estado_partido);

        listViewScore = (ListView) rootView.findViewById(R.id.listview_score);
        listViewScore.setAdapter(scores);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        rellenarGoleadores("19812");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SCORE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    private void rellenarGoleadores(String matchKey) {
        FetchScore fetchScore = new FetchScore(getActivity());
        fetchScore.execute(matchKey, "2015");
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    SCORE_COLUMNS,
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

            //rellenarGoleadores(data.getString(COL_MATCH_MATCH_KEY));

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