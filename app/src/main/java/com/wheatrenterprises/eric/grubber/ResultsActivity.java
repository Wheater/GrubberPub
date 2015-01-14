package com.wheatrenterprises.eric.grubber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;


public class ResultsActivity extends ActionBarActivity {

    ListView lv;
    ResultsAdapter resultsAdapter;

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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //filling list view
        resultsAdapter = new ResultsAdapter(this, QueryResultList.getInstance().getResults());
        lv = (ListView) findViewById(R.id.listview_results);
        lv.setAdapter(resultsAdapter);

        if(QueryResultList.getInstance().getResults().size() == 0){

            String[] strings = {"No suitable results. Please refine your search."};
            lv.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                            strings));
        }

        ImageView imgView = (ImageView) findViewById(R.id.image_view_yelp_image);
        Picasso.with(this)
                .load("http://s3-media4.fl.yelpcdn.com/assets/2/www/img/56884a7c4c0e/developers/reviewsFromYelpYLW.gif")
                .resize(460,100)
                .into(imgView);

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

        switch(id) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, upIntent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
