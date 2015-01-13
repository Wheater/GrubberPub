package com.wheatrenterprises.eric.grubber;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class ResultsActivity extends ActionBarActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //set actionbar to toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_results);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }

        //filling list view
        //lv = (ListView) findViewById(R.id.listview_questions);
        //lv.setAdapter(questionsAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
