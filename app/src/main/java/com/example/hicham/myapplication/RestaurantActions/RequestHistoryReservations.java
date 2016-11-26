package com.example.hicham.myapplication.RestaurantActions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 13/11/2016.
 */

public class RequestHistoryReservations extends StringRequest{
    private static final String RESERVATION_HISTORY_REQUEST_URL = "http://hili.comli.com/RestaurantHistoryReservations.php";
    private Map<String, String> params;

    public RequestHistoryReservations(String restaurantName, Response.Listener<String> listener) {
        super(Request.Method.POST, RESERVATION_HISTORY_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("resto", restaurantName);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
