package com.wheatrenterprises.eric.grubber;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;


public class QuestionsListActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ListView lv;
    private QuestionsAdapter questionsAdapter;
    private QuestionCollection qc = new QuestionCollection();
    private List<String> answerList;
    private QueryBuilder qb = QueryBuilder.getInstance();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize sharedPrefs
        sharedPreferences = getSharedPreferences("MySharedPreferences", 0);

        //set up google maps api client
        buildGoogleApiClient();

        //get current value of list items
        answerList = qb.getAnswerList();

        setContentView(R.layout.activity_questions_list);
        //set actionbar to toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }

        //filling list view
        lv = (ListView) findViewById(R.id.listview_questions);
        questionsAdapter = new QuestionsAdapter(this, QuestionCollection.getQuestionCollection(), QuestionCollection.getQuestionList());
        lv.setAdapter(questionsAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v("itemClick", String.valueOf(position));

                Switch toggleSwitch = (Switch) view.findViewById(R.id.switch_questions_bool);

                //get current value of list items
                answerList = qb.getAnswerList();

                if (answerList.get(position) == "")
                    toggleSwitch.setChecked(true);
                else
                    toggleSwitch.setChecked(false);

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        for(int i = 0; i < answerList.size(); i++) {

            String indexString = answerList.get(i);

            View v = questionsAdapter.getView(i, null, lv);

            if(i >= 2){

                Switch toggleSwitch = (Switch) v.findViewById(R.id.switch_questions_bool);

                if (indexString == "Yes")
                    toggleSwitch.setChecked(true);

            } else if(i < 1){

                if(indexString == "$"){

                    RadioButton rb = (RadioButton) v.findViewById(R.id.radio_button_price_1);
                    rb.setChecked(true);
                }
                else if(indexString == "$$"){

                    RadioButton rb = (RadioButton) v.findViewById(R.id.radio_button_price_2);
                    rb.setChecked(true);
                } else if(indexString == "$$$"){

                    RadioButton rb = (RadioButton) v.findViewById(R.id.radio_button_price_3);
                    rb.setChecked(true);
                } else if(indexString == "$$$$"){

                    RadioButton rb = (RadioButton) v.findViewById(R.id.radio_button_price_4);
                    rb.setChecked(true);
                }

            } else{

                if(indexString == "Restaurant"){

                    RadioButton rb = (RadioButton) v.findViewById(R.id.radio_button_price_1);
                    rb.setChecked(true);
                }
                else if(indexString == "Delivery"){

                    RadioButton rb = (RadioButton) v.findViewById(R.id.radio_button_price_2);
                    rb.setChecked(true);
                } else if(indexString == "Take-out"){

                    RadioButton rb = (RadioButton) v.findViewById(R.id.radio_button_price_3);
                    rb.setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.questions_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_settings:
                showDialog();
                break;
            case R.id.menu_search:
                //connect right before search to grab current
                //shared preferences
                if (mLastLocation != null && sharedPreferences.getString("LocationType", "Current").equals("Current")) {
                    qb.setLocation(mLastLocation);
                    Log.v("current", "current");
                } else {
                    //make GPS coordinates from chosen location zip code
                    Log.v("chosen", "chosen");
                    qb.setLocation(buildCoordinatesFromZip());
                }

                new YelpQueryTask(this).execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){

        DialogFragment frag = SettingsFragment.newInstance();

        frag.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(this,
                "Could not retrieve location. Please choose a location in settings.",
                Toast.LENGTH_LONG).show();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private Location buildCoordinatesFromZip(){

        String mLocation = sharedPreferences.getString("Location", "Houston");

        final Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> addresses = geocoder.getFromLocationName(mLocation, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                Location loc = new Location("location");
                loc.setLatitude(address.getLatitude());
                loc.setLongitude(address.getLongitude());

                return loc;

            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to geocode location", Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (IOException e) {
            // handle exception
            return null;
        }
    }
}
