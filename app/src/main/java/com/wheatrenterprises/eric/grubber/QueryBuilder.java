package com.wheatrenterprises.eric.grubber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class
 * Options set in persistence layer for Yelp API to pull from
 */
public class QueryBuilder implements Serializable {

    private String mPrice = "";
    private String mKids = "";
    private String mSmoking = "";
    private String mSports = "";
    private String mGroup = "";
    private String mWhere = "";

    /*
    * tracker to determine which answers are currently selected
     */
    public List<String> answerList = new ArrayList<String>(){};

    private static QueryBuilder instance = null;

    private QueryBuilder() {
        // Exists only to defeat instantiation.
    }

    public static QueryBuilder getInstance(){

        if(instance == null){

            instance = new QueryBuilder();
            return instance;
        }
        return instance;
    }

    private void initializeAnswerList(){

        for(int i = 0; i < QuestionCollection.getQuestionList().size(); i++){
            answerList.add("");
        }
    }

    public List<String> getAnswerList(){

        if(answerList.size() == 0)
            initializeAnswerList();

        return answerList;
    }

    public void setAnswerList(int position, String value){

        answerList.set(position, value);
    }

    public void clear(){

        setGroup("");
        setKids("");
        setPrice("");
        setSmoking("");
        setSports("");
        setWhere("");
    }

    public String buildQuery(){

        return getKids() + " " +
                getGroup() + " " +
                getSmoking() + " " +
                getSports() + " " +
                getWhere();
    }

    public String getKids(){

        if(mKids.equals("Yes"))
            return "good for kids";
        else
            return "";
    }

    public String getSmoking(){

        if(mSmoking.equals("Yes"))
            return "smoking";
        else
            return "";
    }

    public String getGroup(){

        if(mGroup.equals("Yes"))
            return "good for groups";
        else
            return "";
    }

    public String getSports(){

        if(mSports.equals("Yes"))
            return "sports bar";
        else
            return "";
    }

    public String getWhere(){

        return mWhere;
    }

    public String getPrice(){

        return mPrice;
    }

    public void setKids(String kids){

        mKids = kids;
    }

    public void setSmoking(String smoking){

        mSmoking = smoking;
    }

    public void setGroup(String group){

        mGroup = group;
    }

    public void setSports(String sports){

        mSports = sports;
    }

    public void setWhere(String where){

        mWhere = where;
    }

    public void setPrice(String price){

        mPrice = price;
    }
}
