package com.wheatrenterprises.eric.grubber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Returns question collection.
 */
public class QuestionCollection {

    private static Map<String, List<String>> mQuestionCollection;
    private QueryBuilder qb = QueryBuilder.getInstance();

    private static ArrayList<String> mQuestionList = new ArrayList<String>(){{
        add("What price range are you interested in?");
        add("Where are you eating?");
        add("Are you in a group larger than 5?");
        add("Do you want to watch sports?");
        add("Are children attending?");
        add("Will your party be smoking?");
    }};

    //answers
    final static String[] costAnswers = {"$", "$$", "$$$", "$$$$"};
    final static String[] sportsAnswers = {"Yes", "No"};
    final static String[] smokingAnswers = {"Yes", "No"};
    final static String[] groupAnswers = {"Yes", "No"};
    final static String[] kidsAnswers = {"Yes", "No"};
    final static String[] locationAnswers = {"Restaurant", "Delivery", "Take out"};


    private static void createCollection(){

        mQuestionCollection = new LinkedHashMap<String, List<String>>();

        for(String question : mQuestionList){

            if(question.equals("What price range are you interested in?"))
                    mQuestionCollection.put(question, new ArrayList<String>(Arrays.asList(costAnswers)));
            else if(question.equals("Where are you eating?"))
                mQuestionCollection.put(question, new ArrayList<String>(Arrays.asList(locationAnswers)));
            else if(question.equals("Are you in a group larger than 5?"))
                mQuestionCollection.put(question, new ArrayList<String>(Arrays.asList(groupAnswers)));
            else if(question.equals("Do you want to watch sports?"))
                mQuestionCollection.put(question, new ArrayList<String>(Arrays.asList(sportsAnswers)));
            else if(question.equals("Are children attending?"))
                mQuestionCollection.put(question, new ArrayList<String>(Arrays.asList(kidsAnswers)));
            else if(question.equals("Will your party be smoking?"))
                mQuestionCollection.put(question, new ArrayList<String>(Arrays.asList(smokingAnswers)));

        }
    }

    public void setQueryBuilderAnswer(int position, String value){

        switch(position){

            case 0:
                qb.setPrice(value);
                break;
            case 1:
                qb.setWhere(value);
                break;
            case 2:
                qb.setGroup(value);
                break;
            case 3:
                qb.setSports(value);
                break;
            case 4:
                qb.setKids(value);
                break;
            case 5:
                qb.setSmoking(value);
                break;
        }
    }

    public static Map<String, List<String>> getQuestionCollection(){

        createCollection();

        return mQuestionCollection;
    }

    public static ArrayList<String> getQuestionList(){

        return mQuestionList;
    }

}
