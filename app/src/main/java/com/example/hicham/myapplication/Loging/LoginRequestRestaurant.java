package com.example.hicham.myapplication.Loging;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 07/11/2016.
 */

public class LoginRequestRestaurant extends StringRequest {
    private static final String LOGIN_REQUEST_URL_RESTAURANT="http://hili.comli.com/LoginRestaurant.php";
    private Map<String,String> params;
    public LoginRequestRestaurant(String email, String password,String token, Response.Listener<String> listener){
        super(Request.Method.POST,LOGIN_REQUEST_URL_RESTAURANT,listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        params.put("Token",token);
    }
    public Map<String,String> getParams(){
        return params;
    }

}
