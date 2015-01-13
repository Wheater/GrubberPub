package com.wheatrenterprises.eric.grubber;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * AsyncTask to query Yelp off the main thread
 */
public class YelpQueryTask extends AsyncTask<Void, Void, Void> {

    Activity context;

    public YelpQueryTask(Activity context){

        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.v("params", QueryBuilder.getInstance().buildQuery());

        YelpAPI yelpAPI = new YelpAPI();

        yelpAPI.queryAPI(yelpAPI,
                QueryBuilder.getInstance().buildQuery(),
                QueryBuilder.getInstance().getLocation());

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        context.startActivity(new Intent(this.context, ResultsActivity.class));
    }
}
