package com.wheatrenterprises.eric.grubber;

/**
 * Options set in persistence layer for Yelp API to pull from
 */
public class QueryBuilder {

    private String mPrice;
    private String mKids;
    private String mSmoking;
    private String mSports;
    private String mGroup;
    private String mWhere;

    public String buildQuery(){

        return getKids() + " " +
                getGroup() + " " +
                getSmoking() + " " +
                getPrice() + " " +
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
