package com.wheatrenterprises.eric.grubber;

import android.content.res.Configuration;
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

import java.util.List;


public class QuestionsListActivity extends ActionBarActivity {

    private ListView lv;
    private QuestionsAdapter questionsAdapter;
    private QuestionCollection qc = new QuestionCollection();
    private List<String> answerList;
    private QueryBuilder qb = QueryBuilder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("QueryBuilder", qb);

        for(int i = 0; i < answerList.size(); i++)
            outState.putString(String.valueOf(i), answerList.get(i));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        qb = (QueryBuilder) savedInstanceState.getSerializable("QueryBuilder");

        if(lv == null){
            lv = (ListView) findViewById(R.id.listview_questions);
            questionsAdapter = new QuestionsAdapter(this, QuestionCollection.getQuestionCollection(), QuestionCollection.getQuestionList());
            lv.setAdapter(questionsAdapter);
        }
        //notify datasetchanged to refill lv and avoid null values
        questionsAdapter.notifyDataSetChanged();


    }
}
