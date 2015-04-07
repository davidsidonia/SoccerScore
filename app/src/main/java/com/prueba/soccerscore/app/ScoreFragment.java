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

    private final String LOG_TAG = ScoreFragment.class.getSimpleName();

    private ScoreAdapter scores;
    static final String DETAIL_URI = "URI";
    private Uri mUri;
    private static final int SCORE_LOADER = 0;

    private String local;
    private String visitor;
    private String result;
    private String live_minute;
    private String matchKey;

    private ImageView iconViewEscudoLocal;
    private ImageView iconViewEscudoVisitor;
    private TextView localTextView;
    private TextView visitorTextView;
    private TextView resultTextView;
    private TextView live_minuteTextView;
    private ListView listViewScore;

    private static final String[] SCORE_COLUMNS = {
            MatchContract.ScoreEntry.TABLE_NAME + "." + MatchContract.ScoreEntry._ID,
            MatchContract.ScoreEntry.COLUMN_MINUTE_SCORE,
            MatchContract.ScoreEntry.COLUMN_ACTION,
            MatchContract.ScoreEntry.COLUMN_PLAYER,
            MatchContract.ScoreEntry.COLUMN_TEAM
    };

    static final int COL_SCORE_MINUTE_SCORE = 1;
    static final int COL_SCORE_ACTION = 2;
    static final int COL_SCORE_PLAYER = 3;
    static final int COL_SCORE_TEAM = 4;


    public ScoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            local = arguments.getString("loc");
            visitor = arguments.getString("vis");
            result = arguments.getString("res");
            live_minute = arguments.getString("liv");
            matchKey = arguments.getString("mKey");

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
        rellenarGoleadores(matchKey);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SCORE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    private void rellenarGoleadores(String matKey) {
        FetchScore fetchScore = new FetchScore(getActivity());
        fetchScore.execute(matKey, "2015");
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri scoreUri = MatchContract.ScoreEntry.CONTENT_URI;
        return new CursorLoader(
                getActivity(),
                scoreUri,
                SCORE_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scores.swapCursor(data);

        if (live_minute.equals("DES") || live_minute.equals("des") || live_minute.equals("Des")) {
            live_minute = "DESC";
        } else if (live_minute.equals("")) {
            if (result.equals("x-x")) {
                live_minute = "";
            } else {
                live_minute = "FIN";
            }
//        } else {
//            live_minute = live_minute + "'";
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

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        scores.swapCursor(null);
    }
}