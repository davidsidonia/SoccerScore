package com.prueba.soccerscore.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/*
 * Created by David on 26/03/2015.
 */
public class MatchFragment extends Fragment implements ListenerMatch {
    private ArrayAdapter<String> matchs;

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

        matchs =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_match,
                        R.id.list_item_local_team,
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
        FetchMatch fetchMatch = new FetchMatch(getActivity(), this, this);
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