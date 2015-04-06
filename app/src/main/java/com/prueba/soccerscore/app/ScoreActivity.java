package com.prueba.soccerscore.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/*
 * Created by David on 26/03/2015.
 */
public class ScoreActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ScoreFragment.DETAIL_URI, getIntent().getData());
            ScoreFragment fragment = new ScoreFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.score_container, fragment)
                    .commit();
        }
    }
}