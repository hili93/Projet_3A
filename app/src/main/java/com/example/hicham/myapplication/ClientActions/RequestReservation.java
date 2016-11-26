package com.example.hicham.myapplication.ClientActions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 13/11/2016.
 */

public class RequestReservation extends StringRequest {
    private static final String RESERVATION_REQUEST_URL = "http://hili.comli.com/RequestReservation.php";
    private Map<String, String> params;

    public RequestReservation(String resto, String client,String date,String time, Response.Listener<String> listener) {
        super(Request.Method.POST, RESERVATION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("resto", resto);
        params.put("client", client);
        params.put("date",date);
        params.put("time",time);

        System.out.println("IN REQUEST RESERVATION: ");
        System.out.println("CLIENT= " + client);
        System.out.println("RESTO = " + resto);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
