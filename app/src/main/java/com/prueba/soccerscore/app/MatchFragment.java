package com.prueba.soccerscore.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import com.prueba.soccerscore.app.data.MatchContract;

/*
 * Created by David on 26/03/2015.
 */
public class MatchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private MatchAdapter matchs;

    private ListView listView;
    private int mPosition = ListView.INVALID_POSITION;

    private static final String SELECTED_KEY = "selected_position";


    private static final int MATCH_LOADER = 0;

    private static final String[] MATCH_COLUMNS = {
            MatchContract.MatchEntry.TABLE_NAME + "." + MatchContract.MatchEntry._ID,
            MatchContract.MatchEntry.COLUMN_MATCH_KEY,
            MatchContract.MatchEntry.COLUMN_ROUND,
            MatchContract.MatchEntry.COLUMN_LOCAL,
            MatchContract.MatchEntry.COLUMN_VISITOR,
            MatchContract.MatchEntry.COLUMN_DATE,
            MatchContract.MatchEntry.COLUMN_HOUR,
            MatchContract.MatchEntry.COLUMN_MINUTE,
            MatchContract.MatchEntry.COLUMN_RESULT,
            MatchContract.MatchEntry.COLUMN_LIVE_MINUTE
    };


    static final int COL_MATCH_ID = 0;
    static final int COL_MATCH_MATCH_KEY = 1;
    static final int COL_MATCH_ROUND = 2;
    static final int COL_MATCH_LOCAL = 3;
    static final int COL_MATCH_VISITOR = 4;
    static final int COL_MATCH_DATE = 5;
    static final int COL_MATCH_HOUR = 6;
    static final int COL_MATCH_MINUTE = 7;
    static final int COL_MATCH_RESULT = 8;
    static final int COL_MATCH_LIVE_MINUTE = 9;


    public interface Callback {
        public void onItemSelected(Uri matchUri);
    }


    public MatchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.matchfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMatch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        matchs = new MatchAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview_match);
        listView.setAdapter(matchs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);

                if (cursor != null) {

                    ((Callback) getActivity())
                            .onItemSelected(MatchContract.MatchEntry.buildMatchWithMatchKey(cursor.getString(COL_MATCH_MATCH_KEY)));
                }
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {

            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MATCH_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    private void updateMatch() {
        FetchMatch fetchMatch = new FetchMatch(getActivity());
        fetchMatch.execute();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMatch();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Uri matchUri = MatchContract.MatchEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                matchUri,
                MATCH_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        matchs.swapCursor(cursor);

        if (mPosition != ListView.INVALID_POSITION) {

            listView.smoothScrollToPosition(mPosition);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        matchs.swapCursor(null);
    }


}