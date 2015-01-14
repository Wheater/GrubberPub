package com.wheatrenterprises.eric.grubber;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class
 * Options set in persistence layer for Yelp API to pull from
 */
public class QueryBuilder implements Serializable {

    private String mAllergy = "";
    private String mKids = "";
    private String mSmoking = "";
    private String mSports = "";
    private String mGroup = "";
    private String mWhere = "Restaurant";
    private Location mLocation = null;
    private String mCategory = "";

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

        setCategory("");
        setGroup("");
        setKids("");
        setAllergy("");
        setSmoking("");
        setSports("");
        setWhere("");
    }

    public String buildQuery(){

        return  getAllergy() + "" +
                getKids() + " " +
                getGroup() + " " +
                getSmoking() + " " +
                getSports() + " " +
                getWhere() + " " +
                getCategory();
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

    public String getAllergy(){

        return mAllergy;
    }

    public String getLocation(){

        if(mLocation != null)
            return mLocation.getLatitude() + "," + mLocation.getLongitude();
        else
            return "37.7833,-122.4167";
    }

    public String getCategory(){ return mCategory;}

    public void setCategory(String category){
        mCategory = category;
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

    public void setAllergy(String allergy){

        mAllergy = allergy;
    }

    public void setLocation(Location location){

        this.mLocation = location;
    }
}
