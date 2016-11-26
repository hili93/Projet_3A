package com.example.hicham.myapplication.Register;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hicham on 07/11/2016.
 */

public class RegisterRequestClient extends StringRequest{
    private static final String REGISTER_REQUEST_URL_CLIENT="http://hili.comli.com/RegisterClient.php";
    private Map<String,String> params;

    public RegisterRequestClient(String name, String familyName, String email,  String password, String city, Response.Listener<String> listener){
        super(Request.Method.POST,REGISTER_REQUEST_URL_CLIENT,listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("familyName",familyName);
        params.put("email",email);
        params.put("password",password);
        params.put("city",city);
    }
    public Map<String,String> getParams(){
        return params;
    }
}
