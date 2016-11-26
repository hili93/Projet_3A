package com.example.hicham.myapplication.ClientActions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 07/11/2016.
 *
 * Request to search for restaurants in a city
 */

public class SearchRequest extends StringRequest{
    private static final String SEARCH_REQUEST_URL="http://hili.comli.com/Search.php";
    private Map<String,String> params;
    public SearchRequest(String city, Response.Listener<String> listener){
        super(Request.Method.POST,SEARCH_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("city",city);
    }
    public Map<String,String> getParams(){
        return params;
    }
}
