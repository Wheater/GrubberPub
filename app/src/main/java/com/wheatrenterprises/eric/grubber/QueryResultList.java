package com.wheatrenterprises.eric.grubber;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class with query results
 */
public class QueryResultList {

    private List<QueryResult> mResults = new ArrayList<QueryResult>(){};

    private static QueryResultList instance = null;

    private QueryResultList() {
        // Exists only to defeat instantiation.
    }

    public static QueryResultList getInstance(){

        if(instance == null){

            instance = new QueryResultList();
            return instance;
        }
        return instance;
    }

    public void addResult(QueryResult r){

        mResults.add(r);
    }

    public List<QueryResult> getResults(){
        return mResults;
    }

    public void clear(){
        mResults.clear();
    }
}
