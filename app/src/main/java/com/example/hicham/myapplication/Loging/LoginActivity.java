package com.example.hicham.myapplication.Loging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hicham.myapplication.ClientActions.AreaActivityClient;
import com.example.hicham.myapplication.RestaurantActions.AreaActivityRestaurant;
import com.example.hicham.myapplication.R;
import com.example.hicham.myapplication.Register.RegisterActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;


import static com.example.hicham.myapplication.WelcomeActivity.PREFS_NAME;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Bundle args = LoginActivity.this.getIntent().getExtras();
        final boolean restaurant = args.getBoolean("restaurant", false);
        final boolean client = args.getBoolean("client", false);


        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);

                registerIntent.putExtra("restaurant", restaurant);
                registerIntent.putExtra("client", client);
                startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if (success) {
                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                if (restaurant) {
                                    String name = jsonResponse.getString("name");
                                    String city = jsonResponse.getString("city");

                                    Intent intent = new Intent(LoginActivity.this, AreaActivityRestaurant.class);

                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("Name", name);
                                    editor.putString("City", city);
                                    editor.putString("Email", email);

                                    CheckBox cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
                                    if (cbRememberMe.isChecked()) {
                                        editor.putBoolean("RememberMe", true);
                                        editor.putString("TypeClient", "Restaurant");
                                    }


                                    editor.commit();


                                    LoginActivity.this.startActivity(intent);
                                }
                                if (client) {
                                    String name = jsonResponse.getString("name");
                                    String familyName = jsonResponse.getString("familyName");
                                    String city = jsonResponse.getString("city");

                                    Intent intent = new Intent(LoginActivity.this, AreaActivityClient.class);

                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("Name", name);
                                    editor.putString("FamilyName", familyName);
                                    editor.putString("City", city);
                                    editor.putString("Email", email);

                                    CheckBox cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
                                    if (cbRememberMe.isChecked()) {
                                        editor.putBoolean("RememberMe", true);
                                        editor.putString("TypeClient", "Client");
                                    }
                                    editor.commit();

                                    LoginActivity.this.startActivity(intent);
                                }

                                //Generating token for the app
                                FirebaseMessaging.getInstance().subscribeToTopic("Reservations");
                                String token = FirebaseInstanceId.getInstance().getToken();
                                System.out.println("Token generated :" + token);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed !").setNegativeButton("Retry", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                if (client) {
                    LoginRequestClient loginRequestClient = new LoginRequestClient(email, password, FirebaseInstanceId.getInstance().getToken(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequestClient);
                }
                if (restaurant) {
                    LoginRequestRestaurant loginRequestRestaurant = new LoginRequestRestaurant(email, password, FirebaseInstanceId.getInstance().getToken(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequestRestaurant);
                }
            }
        });
    }
}

