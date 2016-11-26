package com.example.hicham.myapplication.ClientActions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 19/11/2016.
 */

public class RequestReservationBill extends StringRequest {
    private static final String RESERVATION_BILL_REQUEST_URL="http://hili.comli.com/RequestReservationBill.php";
    private Map<String,String> params;
    public RequestReservationBill(String reservationID, Response.Listener<String> listener){
        super(Request.Method.POST,RESERVATION_BILL_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("reservationID",reservationID);
    }
    public Map<String,String> getParams(){
        return params;
    }
}
