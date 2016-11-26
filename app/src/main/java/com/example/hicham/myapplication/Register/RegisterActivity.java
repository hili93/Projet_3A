package com.example.hicham.myapplication.Register;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.Loging.LoginActivity;
import com.example.hicham.myapplication.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle args = RegisterActivity.this.getIntent().getExtras();
        final boolean restaurant= args.getBoolean("restaurant",false);
        final boolean client = args.getBoolean("client",false);


        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etFamilyName = (EditText) findViewById(R.id.etFamilyName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etCity = (EditText) findViewById(R.id.etCity);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String name = etName.getText().toString();
                final String familyName = etFamilyName.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String city = etCity.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                             if(success){
                                 Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                 intent.putExtra("restaurant",restaurant);
                                 intent.putExtra("client",client);
                                 RegisterActivity.this.startActivity(intent);

                             }
                            else{
                                 AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                 builder.setMessage("Register Failed !").setNegativeButton("Retry",null).create().show();

                             }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                if(restaurant==true){
                    RegisterRequestRestaurant registerRequest = new RegisterRequestRestaurant(name,email,password,city,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
                else if (client == true){
                    RegisterRequestClient registerRequest = new RegisterRequestClient(name,familyName,email,password,city,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });



    }
}
