package com.prueba.soccerscore.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MatchFragment extends Fragment implements ListenerMatch {

    private ArrayAdapter<String> matchs;

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


        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        matchs =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_match, // The name of the layout ID.
                        R.id.list_item_local_team, // The ID of the textview to populate.
                        new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_match);
        listView.setAdapter(matchs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String oneMatch = matchs.getItem(position);


                Intent intent = new Intent(getActivity(), ScoreActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, oneMatch);
                startActivity(intent);


            }
        });


        return rootView;

    }

    private void updateMatch() {


        FetchMatch fetchMatch = new FetchMatch(this, this);
        //
        fetchMatch.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMatch();
    }


    @Override
    public void cuandoTengasLosDatos(String[] result) {
        matchs.clear();
        for (String matchStr : result) {
            matchs.add(matchStr);
        }
    }
}