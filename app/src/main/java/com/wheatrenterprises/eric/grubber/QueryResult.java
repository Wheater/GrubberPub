package com.wheatrenterprises.eric.grubber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Business result returned from Yelp API query
 *
 */
public class QueryResult implements Serializable {

    private List<String> mCategories = new ArrayList<String>(){};
    private String mPhoneNumber = "";
    private boolean mIsClosed = false;
    private String mImageUrl = "";
    private String mAddress = "";
    private String mCity = "";
    private String mCountry = "";
    private List<String> mNeighbourhoods = new ArrayList<String>(){};
    private String mReviewCount = "";
    private String mSmallImageRatingUrl = "";
    private String mRatingImageUrl = "";
    private String id = "";
    private String rating = "";
    private String rating_img_url_large="";

    public QueryResult(String id){
        this.id = id;
    }

    public void setRating(String rating){this.rating = rating;}
    public void setId(String id){this.id = id;}
    public void setCategories(List<String> categories){ mCategories = categories; }
    public void setPhoneNumber(String phoneNumber){ mPhoneNumber = phoneNumber; }
    public void setOpen(boolean closed){ mIsClosed = closed;}
    public void setImageUrl(String url){ mImageUrl = url;}
    public void setAddress(String address){mAddress = address;}
    public void setCity(String city){mCity = city;}
    public void setCountry(String country){mCountry = country;}
    public void setNeighbourhoods(List<String> neighbourhoods){ mNeighbourhoods = neighbourhoods;}
    public void setReviewCount(String count){mReviewCount = count;}
    public void setLargeRatingImgUrl(String url){rating_img_url_large = url;}
    public void setRatingImageUrl(String url){mRatingImageUrl = url;}


    public String getRating(){return rating;}
    public String getId(){return id;}
    public List<String> getCategories(){return mCategories;}
    public String getPhoneNumber(){return mPhoneNumber;}
    public boolean getIsClosed(){return mIsClosed;}
    public String getImageUrl(){return mImageUrl;}
    public String getAddress(){return mAddress;}
    public String getCity(){return mCity;}
    public String getmCountry(){return mCountry;}
    public List<String> getNeighbourhoods(){return mNeighbourhoods;}
    public String getReviewCount(){return mReviewCount;}
    public String getLargeRatingImgUrl(){return rating_img_url_large;}
    public String getRatingImageUrl(){return mRatingImageUrl;}
}
