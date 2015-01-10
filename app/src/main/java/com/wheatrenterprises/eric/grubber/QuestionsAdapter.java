package com.wheatrenterprises.eric.grubber;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Expandable List Adapter to display questions
 * and upon expanding, display the answer choices
 */
public class QuestionsAdapter extends BaseExpandableListAdapter {

    private Activity context;
    Map<String, List<String>> questionCollection;
    //have to use a list because collection's .get() works with the key itself,
    //not with the index. lists work with indices, so we need a list to work with
    ArrayList<String> questionList;

    public QuestionsAdapter(Activity context, Map<String, List<String>> questionCollection, ArrayList<String> questionList){

        this.questionList = questionList;
        this.questionCollection = questionCollection;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return questionList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return questionCollection.get(questionList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return questionCollection.get(questionList.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return questionCollection.get(questionList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_questions, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textview_question_category);

        textView.setText(questionList.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_questions_child, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textview_question_answer);

        textView.setText(questionCollection.get(questionList.get(groupPosition)).get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
