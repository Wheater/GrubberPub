package com.wheatrenterprises.eric.grubber;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Expandable List Adapter to display questions
 * and upon expanding, display the answer choices
 */
public class QuestionsAdapter extends BaseAdapter {

    private Activity context;
    Map<String, List<String>> questionCollection;
    //have to use a list because collection's .get() works with the key itself,
    //not with the index. lists work with indices, so we need a list to work with
    ArrayList<String> questionList;
    QueryBuilder qb = QueryBuilder.getInstance();

    public QuestionsAdapter(Activity context, Map<String, List<String>> questionCollection, ArrayList<String> questionList){

        this.questionList = questionList;
        this.questionCollection = questionCollection;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        List<String> currentItemList = (List<String>) this.getItem(position);
        String index = "";
        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(currentItemList.size() == 2 && currentItemList.get(0).equals("Yes")) {
                convertView = inflater.inflate(R.layout.list_item_questions_bool, null);
                index = "bool";

                //final variables for anonymous class
                final Switch toggleSwitch = (Switch) convertView.findViewById(R.id.switch_questions_bool);
                final int pos = position;

                toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        //get current value of list items
                        List<String> answerList = qb.getAnswerList();
                        QuestionCollection qc = new QuestionCollection();

                        if (answerList.get(pos) == ""){

                            //keep track of button positions
                            qb.setAnswerList(pos, "Yes");
                            //start building query
                            qc.setQueryBuilderAnswer(pos, "Yes");
                            toggleSwitch.setChecked(true);

                        } else {

                            //keep track of button positions
                            qb.setAnswerList(pos, "");
                            //start building query
                            qc.setQueryBuilderAnswer(pos, "");
                            toggleSwitch.setChecked(false);
                        }
                    }
                });
            }

            else if(currentItemList.size() == 3 && currentItemList.get(0).equals("Restaurant")) {
                convertView = inflater.inflate(R.layout.list_item_questions_delivery_restaurant, null);
                index = "restaurant_delivery";

                RadioButton rbRestaurant = (RadioButton) convertView.findViewById(R.id.radio_button_restaurant);
                RadioButton rbDelivery = (RadioButton) convertView.findViewById(R.id.radio_button_delivery);

                //radio button ids for onCheckChanged
                final int rbRestaurantId = rbRestaurant.getId();
                final int rbDeliveryId = rbDelivery.getId();

                RadioGroup rg = (RadioGroup) convertView.findViewById(R.id.radio_group_restaurant_delivery);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        Log.v("radiogroup", "checked");

                        if(rbRestaurantId == checkedId){

                            qb.setWhere("Restaurant");
                        } else if(rbDeliveryId == checkedId) {

                            qb.setWhere("Delivery");
                        } else{

                            qb.setWhere("Take-out");
                        }
                    }
                });
            }
            else if(currentItemList.size() == 4 && currentItemList.get(0).equals("Gluten-free")) {
                convertView = inflater.inflate(R.layout.list_item_questions_allergy, null);
                index = "pricing";

                RadioButton rbPrice1 = (RadioButton) convertView.findViewById(R.id.radio_button_gluten);
                RadioButton rbPrice2 = (RadioButton) convertView.findViewById(R.id.radio_button_vegan);
                RadioButton rbPrice3 = (RadioButton) convertView.findViewById(R.id.radio_button_vegetarian);

                //radio button ids for onCheckChanged
                final int rbGlutenId = rbPrice1.getId();
                final int rbVeganId = rbPrice2.getId();
                final int rbVegetarianId = rbPrice3.getId();

                RadioGroup rg = (RadioGroup) convertView.findViewById(R.id.radio_group_allergy);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        Log.v("radiogroup", "checked");

                        if(rbGlutenId == checkedId){

                            qb.setAllergy("Gluten-Free");
                        } else if(rbVeganId == checkedId) {

                            qb.setAllergy("Vegan");
                        } else if(rbVegetarianId == checkedId) {

                            qb.setAllergy("Vegetarian");
                        } else
                            qb.setAllergy("");
                        Log.v("query", qb.buildQuery());
                    }
                });
            } else{

                convertView = inflater.inflate(R.layout.list_item_food_category, null);
                index = "category";

                final View v = convertView;
                final EditText et = (EditText) convertView.findViewById(R.id.edit_text_food_category);
                et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                        if (keyEvent != null&& keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                            in.hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                            return true;

                        }
                        return false;
                    }
                });

                et.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                        qb.setCategory(s.toString());
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //required methods
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //required method
                    }
                });
            }
        }

        if(index.equals("bool")){

            TextView textView = (TextView) convertView.findViewById(R.id.textview_question_allergy);
            textView.setText(questionList.get(position));
        }


        return convertView;
    }

    @Override
    public int getCount() {

        return questionList.size();
    }

    @Override
    public Object getItem(int position) {

        return questionCollection.get(questionList.get(position));
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
}
