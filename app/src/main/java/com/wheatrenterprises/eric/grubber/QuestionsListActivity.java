package com.wheatrenterprises.eric.grubber;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;


public class QuestionsListActivity extends ActionBarActivity {

    private QuestionsAdapter questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questions_list);
        //set actionbar to toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }

        //filling list view
        final ExpandableListView lv = (ExpandableListView) findViewById(R.id.expandablelistview_questions);
        questionsAdapter = new QuestionsAdapter(this, QuestionCollection.getQuestionCollection(), QuestionCollection.getQuestionList());
        lv.setAdapter(questionsAdapter);
        final QuestionsAdapter qa = questionsAdapter;
        //close any open views when a new view is clicked
        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                // Implement this method to scroll to the correct position as this doesn't
                // happen automatically if we override onGroupExpand() as above
                //parent.smoothScrollToPosition(groupPosition);

                for(int j = 0; j < qa.getGroupCount(); j++){

                    //set all groups to unchecked. later we will highlight the correct item
                    //get group position and click position
                    int groupIndex = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(j));

                    parent.setItemChecked(groupIndex, false);
                }

                for(int i = 0; i < parent.getCount(); i++){

                    if(parent.isGroupExpanded(i)) {

                        parent.collapseGroup(i);
                    }
                }
                // Need default behaviour here otherwise group does not get expanded/collapsed
                // on click
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                    //this is only valid if choice_mode has been set
                    parent.setItemChecked(groupPosition, false);
                } else {
                    parent.expandGroup(groupPosition);
                    parent.setItemChecked(groupPosition, true);
                }

                //reset checked item if there is one

                //returns true if click is handled
                return true;
            }
        });

        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //get group position and click position
                int groupIndex = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));

                //reset all children to unchecked if not click position
                for (int i = 0; i < QuestionCollection.getQuestionCollection().get(QuestionCollection.getQuestionList().get(groupPosition)).size(); i++){

                    if(groupIndex + i + 1 != index)
                        parent.setItemChecked(groupIndex + i + 1, false);
                }

                //check new position
                if (parent.isItemChecked(index))
                    parent.setItemChecked(index, false);
                else
                    parent.setItemChecked(index, true);

                return true;
            }
        });
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

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){

        DialogFragment frag = SettingsFragment.newInstance();
        frag.show(getSupportFragmentManager(), "dialog");
    }
}
