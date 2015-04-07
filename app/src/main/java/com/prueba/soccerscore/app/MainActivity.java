package com.prueba.soccerscore.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/*
 * Created by David on 26/03/2015.
 */
public class MainActivity extends ActionBarActivity implements MatchFragment.Callback {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.score_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.score_container, new ScoreFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(ScoreFragment.DETAIL_URI, contentUri);
            ScoreFragment fragment = new ScoreFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.score_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ScoreActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }
}