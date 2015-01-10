package com.wheatrenterprises.eric.grubber;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;


public class QuestionsListActivity extends ActionBarActivity {

    private QuestionsAdapter questionsAdapter;
    /*
     * Question Collection contains all questions
     * and associated answers built from a set of
     * strings of question and their list answers
     */
    Map<String, List<String>> questionCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questions_list);
        //set actionbar to toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
        }

        //filling list view
        ExpandableListView lv = (ExpandableListView) findViewById(R.id.expandablelistview_questions);
        questionsAdapter = new QuestionsAdapter(this, QuestionCollection.getQuestionCollection(), QuestionCollection.getQuestionList());
        lv.setAdapter(questionsAdapter);
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

        return super.onOptionsItemSelected(item);
    }
}
