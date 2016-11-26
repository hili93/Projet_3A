package com.example.hicham.myapplication.ClientActions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 18/11/2016.
 */

public class RequestClientReservations extends StringRequest {
    private static final String CLIENT_RESERVATIONS_REQUEST_URL="http://hili.comli.com/ClientHistoryReservations.php";
    private Map<String,String> params;
    public RequestClientReservations(String Email, Response.Listener<String> listener){
        super(Request.Method.POST,CLIENT_RESERVATIONS_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("EmailClient",Email);
    }
    public Map<String,String> getParams(){
        return params;
    }
}
