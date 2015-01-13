package com.wheatrenterprises.eric.grubber;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;
import java.util.List;

/**
 * Yelp API class for querying its database
 */
public class YelpAPI {

    private String API_HOST = "api.yelp.com";
    private String DEFAULT_LOCATION = "Houston, TX";
    private int SEARCH_LIMIT = 5;
    private String SEARCH_PATH = "/v2/search";

    //don't need business, but leaving here for the future
    //private String BUSINESS_PATH = "/v2/business";

    //auth credentials
    private static final String CONSUMER_KEY = "ib-HdM5QamC4VMIy3gQPww";
    private static final String CONSUMER_SECRET = "gbf5IDmb92OnX7dsQoTgytfILzM";
    private static final String TOKEN = "880_PeMzFjAFQWyGlzbyHOiSL2k1eidA";
    private static final String TOKEN_SECRET = "WuXhCOWa4_WmyF2xrCUNP3cKKTA";

    OAuthService service;
    Token accessToken;

    public YelpAPI() {

        this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);
    }

    public String searchForBusinessesByLocation(String term, String location) {

        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("ll", location);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
        return sendRequestAndGetResponse(request);
    }


    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
        return request;
    }

    private String sendRequestAndGetResponse(OAuthRequest request) {
        System.out.println("Querying " + request.getCompleteUrl() + " ...");
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }

    public void queryAPI(YelpAPI yelpApi, String queryValue, String location) {

        //initialize singleton
        QueryResultList qrl = QueryResultList.getInstance();
        //clear out singleton
        qrl.clear();

        String searchResponseJSON =
                yelpApi.searchForBusinessesByLocation(queryValue, location);

        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            response = (JSONObject) parser.parse(searchResponseJSON);
            Log.v("response", response.toString());
        } catch (ParseException pe) {
            Log.v("YelpAPI Error", "Error: could not parse JSON response:" + searchResponseJSON);

        }

        JSONArray businesses = (JSONArray) response.get("businesses");

        if(businesses != null) {
            for (int i = 0; i < businesses.size(); i++) {

                Log.v("nextJSON", ((JSONObject) businesses.get(i)).get("location").toString());

                QueryResult r = new QueryResult(((JSONObject) (businesses.get(i))).get("id").toString());

                JSONArray categories = (JSONArray) ((JSONObject) businesses.get(i)).get("categories");

                //add categories
                List<String> tempCategories = new ArrayList<String>() {
                };
                for (int j = 0; j < categories.size(); j++) {
                    tempCategories.add(categories.get(j).toString());
                    Log.v("categories", tempCategories.get(j));
                }
                r.setCategories(tempCategories);

                r.setPhoneNumber(((JSONObject) businesses.get(i)).get("display_phone").toString());

                //add open/closed
                r.setOpen((Boolean) ((JSONObject) businesses.get(i)).get("is_closed"));

                //add location
                r.setAddress((((JSONObject)
                        ((JSONObject) businesses.get(i))
                                .get("location"))
                        .get("address").toString()));

                r.setCity(((JSONObject)
                        ((JSONObject) businesses.get(i))
                                .get("location"))
                        .get("city").toString());

                r.setCountry(((JSONObject)
                        ((JSONObject) businesses.get(i))
                                .get("location"))
                        .get("country_code").toString());

                //add neighborhoods
                JSONArray neighbourhoods = (JSONArray) (((JSONObject)
                        ((JSONObject) businesses.get(i))
                                .get("location"))
                        .get("neighborhoods"));

                if (neighbourhoods != null) {
                    List<String> tempHoods = new ArrayList<String>() {
                    };
                    for (int k = 0; k < neighbourhoods.size(); k++) {
                        tempHoods.add(neighbourhoods.get(k).toString());
                        Log.v("neighborhoods", tempHoods.get(k));
                    }
                    r.setNeighbourhoods(tempHoods);
                }

                //add review count
                r.setReviewCount(((JSONObject) businesses.get(i)).get("review_count").toString());

                //add small rating img url
                r.setLargeRatingImgUrl(((JSONObject) businesses.get(i)).get("rating_img_url_large").toString());
                r.setImageUrl(((JSONObject) businesses.get(i)).get("image_url").toString());
                r.setRatingImageUrl(((JSONObject) businesses.get(i)).get("rating_img_url").toString());

                qrl.addResult(r);
            }
        }
    }
}
