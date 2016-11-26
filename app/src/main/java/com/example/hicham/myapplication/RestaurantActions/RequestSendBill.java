package com.example.hicham.myapplication.RestaurantActions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 19/11/2016.
 */

public class RequestSendBill extends StringRequest{
    private static final String SEARCH_REQUEST_URL="http://hili.comli.com/RequestSendBill.php";
    private Map<String,String> params;
    public RequestSendBill(String reservationID,String clientID,String price, Response.Listener<String> listener){
        super(Request.Method.POST,SEARCH_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("reservationID",reservationID);
        params.put("clientID",clientID);
        params.put("price",price);
    }
    public Map<String,String> getParams(){
        return params;
    }
}
