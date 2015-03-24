package com.prueba.soccerscore.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ListView;
import com.prueba.soccerscore.app.data.MatchContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MatchFragment extends Fragment {

    private MatchAdapter matchs;

    public MatchFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.matchfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMatch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Sort order: Ascending, by date.

        //*************** comentado esto para quitar orden

        // String sortOrder = MatchContract.MatchEntry.COLUMN_MATCH_KEY + " ASC";


//        Cursor cur = getActivity().getContentResolver().query(MatchContract.MatchEntry.CONTENT_URI,
//                null, null, null, sortOrder);

        //*****************************

        Cursor cur = getActivity().getContentResolver().query(MatchContract.MatchEntry.CONTENT_URI,
                null, null, null, null);


        matchs = new MatchAdapter(getActivity(), cur, 0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_match);
        listView.setAdapter(matchs);


        return rootView;

    }

    private void updateMatch() {


        FetchMatch fetchMatch = new FetchMatch(getActivity());
        fetchMatch.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMatch();
    }


}