package com.prueba.soccerscore.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.TextView;
import com.prueba.soccerscore.app.data.MatchContract;

/*
 * Created by David on 26/03/2015.
 */
public class ScoreActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ScoreFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A ScoreFragment fragment containing a simple view.
     */

    public static class ScoreFragment extends Fragment implements LoaderCallbacks<Cursor> {

        private static final String LOG_TAG = ScoreFragment.class.getSimpleName();

        private String scoreStr;

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


        public ScoreFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_score, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getLoaderManager().initLoader(SCORE_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Intent intent = getActivity().getIntent();
            if (intent == null) {
                return null;
            }

            return new CursorLoader(
                    getActivity(),
                    intent.getData(),
                    MATCH_COLUMNS,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (!data.moveToFirst()) {
                return;
            }


            String local = data.getString(COL_MATCH_LOCAL);
            String visitor = data.getString(COL_MATCH_VISITOR);
            String result = data.getString(COL_MATCH_RESULT);
            String live_minute = data.getString(COL_MATCH_LIVE_MINUTE);


            TextView localTextView = (TextView) getView().findViewById(R.id.textView_nombre_eq_local);
            localTextView.setText(local);

            TextView visitorTextView = (TextView) getView().findViewById(R.id.textView_nombre_eq_visitante);
            visitorTextView.setText(visitor);

            TextView resultTextView = (TextView) getView().findViewById(R.id.textView_resultado);
            resultTextView.setText(result);

            TextView live_minuteTextView = (TextView) getView().findViewById(R.id.textView_estado_partido);
            live_minuteTextView.setText(live_minute);

        }


        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }

    }
}