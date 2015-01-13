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
        for(int i = 0; i < businesses.size(); i++){
            Log.v("businesses", businesses.get(i).toString());
            Log.v("nextJSON", ((JSONObject) businesses.get(i)).get("location").toString());
            Log.v("nextJSON", ((JSONObject) ((JSONObject) businesses.get(i)).get("location")).get("city").toString());
            Log.v("nextJSON", ((JSONObject) businesses.get(i)).get("snippet_image_url").toString());
            Log.v("nextJSON", ((JSONObject) businesses.get(i)).get("url").toString());
        }
        JSONObject firstBusiness = (JSONObject) businesses.get(0);
        String firstBusinessID = firstBusiness.get("id").toString();

    }
}
